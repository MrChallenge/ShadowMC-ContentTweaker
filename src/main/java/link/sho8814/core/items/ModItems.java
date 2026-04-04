package link.sho8814.core.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import link.sho8814.core.ShadowMC_ContentTweaker;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ShadowMC_ContentTweaker.MODID);

    public static final RegistryObject<Item> ITEM_TEST0 = ITEMS.register("item_test0", ItemTest0::new);
    public static final RegistryObject<Item> ITEM_TEST1 = ITEMS.register("item_test1", ItemTest1::new);
    public static final RegistryObject<Item> ITEM_TEST2 = ITEMS.register("item_test2", ItemTest2::new);
    public static final RegistryObject<Item> ITEM_TEST3 = ITEMS.register("item_test3", ItemTest3::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
