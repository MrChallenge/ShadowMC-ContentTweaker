package link.sho8814.core.blocks.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import link.sho8814.core.network.UrlTextureLoader;
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

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BlockBillboardRenderer implements BlockEntityRenderer<BlockBillboardEntity> {
    public BlockBillboardRenderer(BlockEntityRendererProvider.Context context) {}

    public static boolean previewEnabled = false;
    public static boolean previewFlipX = false;
    public static boolean previewFlipY = false;

    public static float previewScale = 1.0f;
    public static float previewOffsetX = 0.0f;
    public static float previewOffsetY = 0.0f;

    private static final Map<String, ResourceLocation> textureCache = new HashMap<>();

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

        boolean flipX = previewEnabled ? previewFlipX : entity.isFlipX();
        boolean flipY = previewEnabled ? previewFlipY : entity.isFlipY();

        float scale = previewEnabled ? previewScale : entity.getScale();
        poseStack.scale(scale, scale, scale);

        float offsetX = previewEnabled ? previewOffsetX : entity.getOffsetX();
        float offsetY = previewEnabled ? previewOffsetY : entity.getOffsetY();
        poseStack.translate(offsetX, offsetY, 0);

        String url = entity.getImageUrl();
        ResourceLocation texture = textureCache.get(url);

        if (texture == null && url != null && !url.isEmpty()) {
            textureCache.put(url, null);

            UrlTextureLoader.loadTexture(url).thenAccept(tex -> {
                if (tex != null) textureCache.put(url, tex);
            });
        }

        VertexConsumer vc;
        float u0, u1, v0, v1;

        if (texture != null) {
            vc = buffer.getBuffer(RenderType.entityCutout(texture));

            u0 = 0f;
            u1 = 1f;
            v0 = 0f;
            v1 = 1f;

        } else {
            ResourceLocation blockTex = new ResourceLocation(
                    "shadowmc_contenttweaker",
                    "block/block_billboard"
            );

            TextureAtlasSprite sprite = mc.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                    .apply(blockTex);

            vc = buffer.getBuffer(RenderType.cutout());

            u0 = sprite.getU0();
            u1 = sprite.getU1();
            v0 = sprite.getV0();
            v1 = sprite.getV1();
        }

        if (flipX) {
            float tmp = u0;
            u0 = u1;
            u1 = tmp;
        }

        if (flipY) {
            float tmp = v0;
            v0 = v1;
            v1 = tmp;
        }

        Matrix4f matrix = poseStack.last().pose();

        vc.vertex(matrix, -0.5f, -0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(u0, v1)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        vc.vertex(matrix, 0.5f, -0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(u1, v1)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        vc.vertex(matrix, 0.5f, 0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(u1, v0)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        vc.vertex(matrix, -0.5f, 0.5f, 0.0f)
                .color(255, 255, 255, 255)
                .uv(u0, v0)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(0, 0, 1)
                .endVertex();

        poseStack.popPose();
    }
}