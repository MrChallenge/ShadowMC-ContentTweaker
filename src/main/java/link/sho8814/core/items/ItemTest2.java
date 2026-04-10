package link.sho8814.core.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ItemTest2 extends Item {
    public ItemTest2() {
        super(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(5)
                .saturationMod(0.6f)
                .meat()
                .fast()
                .alwaysEat()
                .effect(new MobEffectInstance(MobEffects.DARKNESS, 20, 0 ), 0.5F)
                .build()));
    }
}