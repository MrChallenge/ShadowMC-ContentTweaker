package link.sho8814.core.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BlockItemModelTest2 extends BlockItem {

    public BlockItemModelTest2(Block block) {
        super(block, new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("item.shadowmc_contenttweaker.model_test2.tooltip")
                    .withStyle(ChatFormatting.ITALIC, ChatFormatting.WHITE));
        } else {
            tooltip.add(Component.translatable("util.shadowmc_contenttweaker.tooltip.press_shift")
                    .withStyle(ChatFormatting.BOLD, ChatFormatting.GRAY));
        }
    }
}