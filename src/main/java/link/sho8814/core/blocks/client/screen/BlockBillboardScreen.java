package link.sho8814.core.blocks.client.screen;

import link.sho8814.core.blocks.client.renderer.BlockBillboardRenderer;
import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import link.sho8814.core.network.BlockBillboardUpdatePacket;
import link.sho8814.core.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class BlockBillboardScreen extends Screen {

    private final BlockPos pos;
    private final BlockBillboardEntity entity;

    private float scale = 1.0f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;
    private float offsetZ = 0.0f;

    public BlockBillboardScreen(BlockPos pos) {
        super(Component.translatable("Billboard Settings"));
        this.pos = pos;

        this.entity = (BlockBillboardEntity) Minecraft.getInstance().level.getBlockEntity(pos);

        if (entity != null) {
            this.scale = entity.getScale();
            this.offsetX = entity.getOffsetX();
            this.offsetY = entity.getOffsetY();
            this.offsetZ = entity.getOffsetZ();
        }
    }

    @Override
    protected void init() {

        // SCALE +
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.scale+"), b -> {
            scale += 0.25f;
            updatePreview();
        }).bounds(width / 2 - 105, height / 2 - 130, 100, 20).build());

        // SCALE -
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.scale-"), b -> {
            scale -= 0.25f;
            updatePreview();
        }).bounds(width / 2 + 5, height / 2 - 130, 100, 20).build());

        // OFFSET X +
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.offsetX+"), b -> {
            offsetX += 0.25f;
            updatePreview();
        }).bounds(width / 2 - 105, height / 2 - 105, 100, 20).build());

        // OFFSET X -
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.offsetX-"), b -> {
            offsetX -= 0.25f;
            updatePreview();
        }).bounds(width / 2 + 5, height / 2 - 105, 100, 20).build());

        // OFFSET Y +
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.offsetY+"), b -> {
            offsetY += 0.25f;
            updatePreview();
        }).bounds(width / 2 - 105, height / 2 - 80, 100, 20).build());

        // OFFSET Y -
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.offsetY-"), b -> {
            offsetY -= 0.25f;
            updatePreview();
        }).bounds(width / 2 + 5, height / 2 - 80, 100, 20).build());

        // OFFSET Z +
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.offsetZ+"), b -> {
            offsetZ += 0.25f;
            updatePreview();
        }).bounds(width / 2 - 105, height / 2 - 55, 100, 20).build());

        // OFFSET Z -
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.offsetZ-"), b -> {
            offsetZ -= 0.25f;
            updatePreview();
        }).bounds(width / 2 + 5, height / 2 - 55, 100, 20).build());

        // APPLY
        addRenderableWidget(Button.builder(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.done"), b -> {
            sendToServer();

            BlockBillboardRenderer.previewEnabled = false;

        }).bounds(width / 2 - 50, height / 2 - 15, 100, 20).build());
    }

    private void updatePreview() {
        BlockBillboardRenderer.previewEnabled = true;
        BlockBillboardRenderer.previewScale = scale;
        BlockBillboardRenderer.previewOffsetX = offsetX;
        BlockBillboardRenderer.previewOffsetY = offsetY;
        BlockBillboardRenderer.previewOffsetZ = offsetZ;
    }

    private void sendToServer() {
        ModNetworking.CHANNEL.sendToServer(
                new BlockBillboardUpdatePacket(pos, scale, offsetX, offsetY, offsetZ)
        );
    }

    @Override
    public void onClose() {
        BlockBillboardRenderer.previewEnabled = false;
        super.onClose();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(font, Component.translatable("gui.shadowmc_contenttweaker.block_billboard.namespace"), width / 2, 20, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}