package link.sho8814.core.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ItemTest2 extends Item {
    public ItemTest2() {
        super(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(5) // сколько голода восстанавливает
                .saturationMod(0.6f) // насыщение (множитель)
                .meat() // едят волки
                .fast() // х2 быстрее съедается
                .alwaysEat() // можно всегда съесть
                .effect(new MobEffectInstance(MobEffects.DARKNESS, 20, 0 ), 0.5F)
                        // тики | уровень | шанс получения 0.01F(1%) - 1F(100%)
                .build()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable("item.shadowmc_contenttweaker.item_test2.line1"));
        tooltip.add(Component.translatable("item.shadowmc_contenttweaker.item_test2.line2"));
    }
}