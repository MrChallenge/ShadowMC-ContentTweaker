package link.sho8814.core.blocks.client.screen;

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

    public BlockBillboardScreen(BlockPos pos) {
        super(Component.literal("Billboard Settings"));
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
        addRenderableWidget(Button.builder(Component.literal("Scale +"),
                b -> scale += 0.1f).bounds(width / 2 - 50, height / 2 - 40, 100, 20).build());

        if (entity != null) {
            entity.setClientValues(scale, offsetX, offsetY);
        }

        addRenderableWidget(Button.builder(Component.literal("Scale -"),
                b -> scale -= 0.1f).bounds(width / 2 - 50, height / 2 - 15, 100, 20).build());

        if (entity != null) {
            entity.setClientValues(scale, offsetX, offsetY);
        }
        addRenderableWidget(Button.builder(Component.literal("Offset X +"),
                b -> offsetX += 0.1f).bounds(width / 2 - 50, height / 2 + 10, 100, 20).build());

        if (entity != null) {
            entity.setClientValues(scale, offsetX, offsetY);
        }
        addRenderableWidget(Button.builder(Component.literal("Offset Y +"),
                b -> offsetY += 0.1f).bounds(width / 2 - 50, height / 2 + 35, 100, 20).build());

        if (entity != null) {
            entity.setClientValues(scale, offsetX, offsetY);
        }
        addRenderableWidget(Button.builder(Component.literal("Apply"),
                b -> sendToServer()).bounds(width / 2 - 50, height / 2 + 70, 100, 20).build());
    }

    private void sendToServer() {
        ModNetworking.CHANNEL.sendToServer(
                new BlockBillboardUpdatePacket(pos, scale, offsetX, offsetY)
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(font, "Billboard Config", width / 2, 20, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
