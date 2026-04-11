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
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)

public class BlockBillboardRenderer implements BlockEntityRenderer<BlockBillboardEntity> {
    private static final ResourceLocation TEXTURE_BILLBOARD =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "block/test/block_billboard");

    public BlockBillboardRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BlockBillboardEntity entity,
                       float partialTicks,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       int packedOverlay) {

        Minecraft mc = Minecraft.getInstance();

        poseStack.pushPose();

        poseStack.translate(0.5, 0.5, 0.5);

        poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());

        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        float scale = getTextureScale();
        poseStack.scale(scale, scale, scale);

        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutout(TEXTURE_BILLBOARD));
        Matrix4f matrix = poseStack.last().pose();

        vc.vertex(matrix, -0.5f, -0.5f, 0).uv(0, 1).endVertex();
        vc.vertex(matrix, 0.5f, -0.5f, 0).uv(1, 1).endVertex();
        vc.vertex(matrix, 0.5f, 0.5f, 0).uv(1, 0).endVertex();
        vc.vertex(matrix, -0.5f, 0.5f, 0).uv(0, 0).endVertex();

        poseStack.popPose();
    }

    private float getTextureScale() {
        return 1.0f;
    }
}