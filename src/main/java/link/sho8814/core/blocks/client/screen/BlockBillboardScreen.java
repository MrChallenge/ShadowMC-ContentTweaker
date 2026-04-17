package link.sho8814.core.blocks.client.screen;

import link.sho8814.core.blocks.client.renderer.BlockBillboardRenderer;
import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import link.sho8814.core.network.BlockBillboardUpdatePacket;
import link.sho8814.core.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BlockBillboardScreen extends Screen {
    private static final ResourceLocation BG =
            new ResourceLocation("minecraft", "textures/gui/demo_background.png");

    private final BlockPos pos;
    private final BlockBillboardEntity entity;

    private int leftPos;
    private int topPos;
    private final int imageWidth = 176;
    private final int imageHeight = 166;

    private EditBox urlBox;
    private EditBox scaleBox;
    private EditBox offsetXBox;
    private EditBox offsetYBox;

    private float scale = 1.0f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;

    public BlockBillboardScreen(BlockPos pos) {
        super(Component.translatable("gui.shadowmc_contenttweaker.block_billboard.namespace"));
        this.pos = pos;

        this.entity = (BlockBillboardEntity) Minecraft.getInstance().level.getBlockEntity(pos);

        if (entity != null) {
            this.scale = entity.getScale();
            this.offsetX = entity.getOffsetX();
            this.offsetY = entity.getOffsetY();
        }
    }

    @Override
    protected void init() {

        leftPos = (width - imageWidth) / 2;
        topPos = (height - imageHeight) / 2;

        int y = topPos + 30;
        int spacing = 28;

        // Scale
        scaleBox = createBox(leftPos + 63, y, scale);
        addRenderableWidget(scaleBox);

        // -
        addRenderableWidget(createButton(leftPos + 20, y, "-", () -> {
            scale -= 0.25f;
            updateFields();
        }));

        // +
        addRenderableWidget(createButton(leftPos + 140, y, "+", () -> {
            scale += 0.25f;
            updateFields();
        }));

        // Offset X
        offsetXBox = createBox(leftPos + 63, y + spacing, offsetX);
        addRenderableWidget(offsetXBox);

        // -
        addRenderableWidget(createButton(leftPos + 20, y + spacing, "-", () -> {
            offsetX -= 0.25f;
            updateFields();
        }));

        // +
        addRenderableWidget(createButton(leftPos + 140, y + spacing, "+", () -> {
            offsetX += 0.25f;
            updateFields();
        }));

        // Offset Y
        offsetYBox = createBox(leftPos + 63, y + spacing * 2, offsetY);
        addRenderableWidget(offsetYBox);

        // -
        addRenderableWidget(createButton(leftPos + 20, y + spacing * 2, "-", () -> {
            offsetY -= 0.25f;
            updateFields();
        }));

        // +
        addRenderableWidget(createButton(leftPos + 140, y + spacing * 2, "+", () -> {
            offsetY += 0.25f;
            updateFields();
        }));

        // URL
        urlBox = new EditBox(font, leftPos + 20, topPos + 110, 136, 20,
                Component.translatable("gui.shadowmc_contenttweaker.block_billboard.url"));

        urlBox.setMaxLength(1024);
        urlBox.setValue(entity != null ? entity.getImageUrl() : "");
        addRenderableWidget(urlBox);

        // Done
        addRenderableWidget(Button.builder(
                Component.translatable("gui.shadowmc_contenttweaker.block_billboard.done"),
                b -> sendToServer()
        ).bounds(leftPos + 40, topPos + 135, 100, 20).build());

        updateFields();
    }

    private EditBox createBox(int x, int y, float value) {
        EditBox box = new EditBox(font, x, y, 50, 18, Component.empty());
        box.setMaxLength(20);
        box.setValue(String.format("%.2f", value));
        return box;
    }

    private Button createButton(int x, int y, String text, Runnable action) {
        return Button.builder(Component.literal(text), b -> action.run())
                .bounds(x, y, 20, 20)
                .build();
    }

    private void updateFields() {
        scaleBox.setValue(String.format("%.2f", scale));
        offsetXBox.setValue(String.format("%.2f", offsetX));
        offsetYBox.setValue(String.format("%.2f", offsetY));

        updatePreview();
    }

    private void updatePreview() {
        BlockBillboardRenderer.previewEnabled = true;
        BlockBillboardRenderer.previewScale = scale;
        BlockBillboardRenderer.previewOffsetX = offsetX;
        BlockBillboardRenderer.previewOffsetY = offsetY;
    }

    private void sendToServer() {
        try {
            scale = Float.parseFloat(scaleBox.getValue());
            offsetX = Float.parseFloat(offsetXBox.getValue());
            offsetY = Float.parseFloat(offsetYBox.getValue());
        } catch (Exception ignored) {}

        String url = urlBox.getValue();

        ModNetworking.CHANNEL.sendToServer(
                new BlockBillboardUpdatePacket(pos, scale, offsetX, offsetY, url)
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

        guiGraphics.blit(BG, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        guiGraphics.drawCenteredString(
                font,
                Component.translatable("gui.shadowmc_contenttweaker.block_billboard.namespace"),
                width / 2,
                topPos + 10,
                0xFFFFFF
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}