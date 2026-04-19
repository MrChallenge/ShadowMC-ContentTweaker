package link.sho8814.core.blocks.client.screen;

import link.sho8814.core.blocks.client.renderer.BlockBillboardRenderer;
import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import link.sho8814.core.network.BlockBillboardUpdatePacket;
import link.sho8814.core.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BlockBillboardScreen extends Screen {
    private static final ResourceLocation FORWARD =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "textures/gui/billboard/arrow_forward.png");

    private static final ResourceLocation FORWARD_HOVER =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "textures/gui/billboard/arrow_forward_hover.png");

    private static final ResourceLocation BACKWARD =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "textures/gui/billboard/arrow_backward.png");

    private static final ResourceLocation BACKWARD_HOVER =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "textures/gui/billboard/arrow_backward_hover.png");

    private static final ResourceLocation FORWARD_DISABLED =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "textures/gui/billboard/arrow_forward_disabled.png");

    private static final ResourceLocation BACKWARD_DISABLED =
            ResourceLocation.fromNamespaceAndPath("shadowmc_contenttweaker", "textures/gui/billboard/arrow_backward_disabled.png");

    private static final float SCALE_MIN = 0f;
    private static final float SCALE_MAX = 32f;

    private static final float OFFSET_MIN = -32f;
    private static final float OFFSET_MAX = 32f;

    private final BlockPos pos;
    private final BlockBillboardEntity entity;

    private EditBox urlBox;
    private EditBox scaleBox;
    private EditBox offsetXBox;
    private EditBox offsetYBox;

    private Checkbox flipXBox;
    private Checkbox flipYBox;

    private ArrowButtons scalePlus;
    private ArrowButtons scaleMinus;

    private ArrowButtons offsetXPlus;
    private ArrowButtons offsetXMinus;

    private ArrowButtons offsetYPlus;
    private ArrowButtons offsetYMinus;

    private boolean flipX = false;
    private boolean flipY = false;

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
            this.flipX = entity.isFlipX();
            this.flipY = entity.isFlipY();
        }
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;

        int startY = centerY - 50;
        int rowSpacing = 30;

        int boxWidth = 60;
        int boxHeight = 18;

        int arrowOffset = 50;
        int arrowX = 14;
        int arrowY = 22;
        int arrowYOffset = (boxHeight - arrowY) / 2;

        // ================= SCALE =================

        int yScale = startY;

        scaleBox = createBox(centerX - boxWidth / 2, yScale, scale);
        addRenderableWidget(scaleBox);

        scalePlus = new ArrowButtons(
                centerX + arrowOffset - arrowX / 2,
                yScale + arrowYOffset,
                arrowX, arrowY,
                FORWARD, FORWARD_HOVER, FORWARD_DISABLED,
                b -> {
                    scale = Math.min(SCALE_MAX, scale + getStep());
                    updateFields();
                }
        );

        scaleMinus = new ArrowButtons(
                centerX - arrowOffset - arrowX / 2,
                yScale + arrowYOffset,
                arrowX, arrowY,
                BACKWARD, BACKWARD_HOVER, BACKWARD_DISABLED,
                b -> {
                    scale = Math.max(SCALE_MIN, scale - getStep());
                    updateFields();
                }
        );

        addRenderableWidget(scalePlus);
        addRenderableWidget(scaleMinus);

        // ================= OFFSET Y =================

        int yOffsetY = startY + rowSpacing;

        offsetYBox = createBox(centerX - boxWidth / 2, yOffsetY, offsetY);
        addRenderableWidget(offsetYBox);

        offsetYPlus = new ArrowButtons(
                centerX + arrowOffset - arrowX / 2,
                yOffsetY + arrowYOffset,
                arrowX, arrowY,
                FORWARD, FORWARD_HOVER, FORWARD_DISABLED,
                b -> {
                    offsetY = Math.min(OFFSET_MAX, offsetY + getStep());
                    updateFields();
                }
        );

        offsetYMinus = new ArrowButtons(
                centerX - arrowOffset - arrowX / 2,
                yOffsetY + arrowYOffset,
                arrowX, arrowY,
                BACKWARD, BACKWARD_HOVER, BACKWARD_DISABLED,
                b -> {
                    offsetY = Math.max(OFFSET_MIN, offsetY - getStep());
                    updateFields();
                }
        );

        addRenderableWidget(offsetYPlus);
        addRenderableWidget(offsetYMinus);

        // ================= OFFSET X =================

        int yOffsetX = startY + rowSpacing * 2;

        offsetXBox = createBox(centerX - boxWidth / 2, yOffsetX, offsetX);
        addRenderableWidget(offsetXBox);

        offsetXPlus = new ArrowButtons(
                centerX + arrowOffset - arrowX / 2,
                yOffsetX + arrowYOffset,
                arrowX, arrowY,
                FORWARD, FORWARD_HOVER, FORWARD_DISABLED,
                b -> {
                    offsetX = Math.min(OFFSET_MAX, offsetX + getStep());
                    updateFields();
                }
        );

        offsetXMinus = new ArrowButtons(
                centerX - arrowOffset - arrowX / 2,
                yOffsetX + arrowYOffset,
                arrowX, arrowY,
                BACKWARD, BACKWARD_HOVER, BACKWARD_DISABLED,
                b -> {
                    offsetX = Math.max(OFFSET_MIN, offsetX - getStep());
                    updateFields();
                }
        );

        addRenderableWidget(offsetXPlus);
        addRenderableWidget(offsetXMinus);

        // ================= URL =================

        int urlY = startY + rowSpacing * 3 + 10;

        urlBox = new EditBox(font,
                centerX - 80,
                urlY,
                160,
                20,
                Component.empty());

        urlBox.setMaxLength(1024);
        urlBox.setValue(entity != null ? entity.getImageUrl() : "");
        addRenderableWidget(urlBox);

        // ================= Layout =================

        int spacing = 25;

        int checkY = urlY + spacing;
        int checkboxSpacing = 22;

        int buttonY = checkY + checkboxSpacing * 2 + 5;

        // ================= Checkbox =================

        flipXBox = new Checkbox(
                centerX - 80,
                checkY,
                20,
                20,
                Component.translatable("gui.shadowmc_contenttweaker.block_billboard.flipX"),
                flipX
        );

        flipYBox = new Checkbox(
                centerX - 80,
                checkY + checkboxSpacing,
                20,
                20,
                Component.translatable("gui.shadowmc_contenttweaker.block_billboard.flipY"),
                flipY
        );

        addRenderableWidget(flipXBox);
        addRenderableWidget(flipYBox);

        // ================= DONE =================

        addRenderableWidget(Button.builder(
                        Component.translatable("gui.shadowmc_contenttweaker.block_billboard.done"),
                        b -> sendToServer())
                .bounds(centerX - 50, buttonY, 100, 20)
                .build());

        updateFields();
    }

    private EditBox createBox(int x, int y, float value) {
        EditBox box = new EditBox(font, x, y, 60, 18, Component.empty());
        box.setMaxLength(20);
        box.setValue(formatFloat(value));
        return box;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        boolean result = super.mouseReleased(mouseX, mouseY, button);

        if (this.getFocused() instanceof Button) {
            this.setFocused(null);
        }

        return result;
    }

    private void updateFields() {
        if (!scaleBox.isFocused()) scaleBox.setValue(formatFloat(scale));
        if (!offsetXBox.isFocused()) offsetXBox.setValue(formatFloat(offsetX));
        if (!offsetYBox.isFocused()) offsetYBox.setValue(formatFloat(offsetY));

        updateButtons();
        updatePreview();
    }

    @Override
    public void tick() {
        super.tick();
        updateFromFields();
    }

    private String normalize(String input) {
        return input.replace(",", ".");
    }

    private boolean isValidNumber(String s) {
        return s.matches("-?\\d*(\\.\\d{0,4})?");
    }

    private void updateFromFields() {
        try {
            if (scaleBox.isFocused()) {
                String val = normalize(scaleBox.getValue());
                if (isValidNumber(val)) scale = Float.parseFloat(val);
            }

            if (offsetXBox.isFocused()) {
                String val = normalize(offsetXBox.getValue());
                if (isValidNumber(val)) offsetX = Float.parseFloat(val);
            }

            if (offsetYBox.isFocused()) {
                String val = normalize(offsetYBox.getValue());
                if (isValidNumber(val)) offsetY = Float.parseFloat(val);
            }

        } catch (NumberFormatException ignored) {}

        scale = Math.max(SCALE_MIN, Math.min(SCALE_MAX, scale));
        offsetX = Math.max(OFFSET_MIN, Math.min(OFFSET_MAX, offsetX));
        offsetY = Math.max(OFFSET_MIN, Math.min(OFFSET_MAX, offsetY));

        flipX = flipXBox.selected();
        flipY = flipYBox.selected();

        updatePreview();
    }

    private String formatFloat(float value) {
        java.math.BigDecimal bd = new java.math.BigDecimal(Float.toString(value));
        bd = bd.setScale(4, java.math.RoundingMode.DOWN);
        bd = bd.stripTrailingZeros();

        String result = bd.toPlainString();
        if (!result.contains(".")) result += ".0";
        return result;
    }

    private float getStep() {
        boolean ctrl = hasControlDown();
        boolean shift = hasShiftDown();
        boolean alt = hasAltDown();

        if (ctrl && shift && alt) return 0.0001f;
        if (shift && ctrl) return 0.001f;
        if (ctrl) return 0.01f;
        if (shift) return 0.1f;
        if (alt) return 10.0f;
        return 1.0f;
    }

    private void updateButtons() {
        scalePlus.active = scale < SCALE_MAX;
        scaleMinus.active = scale > SCALE_MIN;

        offsetXPlus.active = offsetX < OFFSET_MAX;
        offsetXMinus.active = offsetX > OFFSET_MIN;

        offsetYPlus.active = offsetY < OFFSET_MAX;
        offsetYMinus.active = offsetY > OFFSET_MIN;
    }

    private void updatePreview() {
        BlockBillboardRenderer.previewEnabled = true;
        BlockBillboardRenderer.previewScale = scale;
        BlockBillboardRenderer.previewOffsetX = offsetX;
        BlockBillboardRenderer.previewOffsetY = offsetY;
        BlockBillboardRenderer.previewFlipX = flipX;
        BlockBillboardRenderer.previewFlipY = flipY;
    }

    private void sendToServer() {
        try {
            scale = Float.parseFloat(scaleBox.getValue());
            offsetX = Float.parseFloat(offsetXBox.getValue());
            offsetY = Float.parseFloat(offsetYBox.getValue());
        } catch (Exception ignored) {}

        String url = urlBox.getValue();

        ModNetworking.CHANNEL.sendToServer(
                new BlockBillboardUpdatePacket(pos, scale, offsetX, offsetY, url, flipX, flipY)
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

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(
                font,
                Component.translatable("gui.shadowmc_contenttweaker.block_billboard.namespace"),
                width / 2,
                height / 2 - 70,
                0xFFFFFF
        );
    }
}