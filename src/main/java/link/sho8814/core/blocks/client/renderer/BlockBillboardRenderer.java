package link.sho8814.core.blocks.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)

public class BlockBillboardRenderer implements BlockEntityRenderer<BlockBillboardEntity> {
    public BlockBillboardRenderer(BlockEntityRendererProvider.Context context) {}

    public static boolean previewEnabled = false;
    public static float previewScale = 1.0f;
    public static float previewOffsetX = 0.0f;
    public static float previewOffsetY = 0.0f;
    public static float previewOffsetZ = 0.0f;

    @Override
    public void render(BlockBillboardEntity entity,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       int packedOverlay) {

        poseStack.pushPose();

        Minecraft mc = Minecraft.getInstance();

        poseStack.translate(0.5, 0.5, 0.5);

        poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());

        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        //System.out.println("RENDER SCALE: " + entity.getScale());

        float scale = previewEnabled ? previewScale : entity.getScale();
        poseStack.scale(scale, scale, scale);

        float offsetX = previewEnabled ? previewOffsetX : entity.getOffsetX();
        float offsetY = previewEnabled ? previewOffsetY : entity.getOffsetY();
        float offsetZ = previewEnabled ? previewOffsetZ : entity.getOffsetZ();

        poseStack.translate(offsetX, offsetY, entity.getOffsetZ());

        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(
                "shadowmc_contenttweaker",
                entity.getTexturePath()
        );

        TextureAtlasSprite sprite = mc.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(texture);

        VertexConsumer vc = buffer.getBuffer(RenderType.cutout());
        Matrix4f matrix = poseStack.last().pose();

        vc.vertex(matrix, -0.5f, -0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(sprite.getU0(), sprite.getV1())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        vc.vertex(matrix, 0.5f, -0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(sprite.getU1(), sprite.getV1())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        vc.vertex(matrix, 0.5f, 0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(sprite.getU1(), sprite.getV0())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        vc.vertex(matrix, -0.5f, 0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(sprite.getU0(), sprite.getV0())
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        poseStack.popPose();
    }
}