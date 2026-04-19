package link.sho8814.core.blocks.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ArrowButtons extends Button {

    private final ResourceLocation normalTexture;
    private final ResourceLocation hoverTexture;
    private final ResourceLocation disabledTexture;

    public ArrowButtons(int x, int y, int width, int height,
                        ResourceLocation normal,
                        ResourceLocation hover,
                        ResourceLocation disabled,
                        OnPress onPress) {

        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);

        this.normalTexture = normal;
        this.hoverTexture = hover;
        this.disabledTexture = disabled;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        ResourceLocation texture;

        if (!this.active) {
            texture = disabledTexture;
        } else if (this.isHoveredOrFocused()) {
            texture = hoverTexture;
        } else {
            texture = normalTexture;
        }

        guiGraphics.blit(
                texture,
                this.getX(),
                this.getY(),
                0,
                0,
                this.width,
                this.height,
                this.width,
                this.height
        );
    }
}