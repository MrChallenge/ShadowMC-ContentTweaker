package link.sho8814.core.items;

import link.sho8814.core.blocks.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import link.sho8814.core.ShadowMC_ContentTweaker;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ShadowMC_ContentTweaker.MODID);

    // Предметы
    public static final RegistryObject<Item> ITEM_TEST0 = ITEMS.register("test/item_test0", ItemTest0::new);
    public static final RegistryObject<Item> ITEM_TEST1 = ITEMS.register("test/item_test1", ItemTest1::new);
    public static final RegistryObject<Item> ITEM_TEST2 = ITEMS.register("test/item_test2", ItemTest2::new);
    public static final RegistryObject<Item> ITEM_TEST3 = ITEMS.register("test/item_test3", ItemTest3::new);

    // Блок-предметы
    public static final RegistryObject<Item> BLOCK_TEST0 = ITEMS.register("test/block_test0",
            () -> new BlockItem(ModBlocks.BLOCK_TEST0.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLOCK_TEST1 = ITEMS.register("test/block_test1",
            () -> new BlockItemTest1(ModBlocks.BLOCK_TEST1.get()));

    public static final RegistryObject<Item> BLOCK_TEST2 = ITEMS.register("test/block_test2",
            () -> new BlockItem(ModBlocks.BLOCK_TEST2.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLOCK_TEST3 = ITEMS.register("test/block_test3",
            () -> new BlockItem(ModBlocks.BLOCK_TEST3.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
