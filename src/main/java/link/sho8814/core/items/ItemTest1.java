package link.sho8814.core.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ItemTest1 extends Item {
    public ItemTest1() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable("item.shadowmc_contenttweaker.item_test1.line1"));
        tooltip.add(Component.translatable("item.shadowmc_contenttweaker.item_test1.line2"));
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        return 800;
    }
}