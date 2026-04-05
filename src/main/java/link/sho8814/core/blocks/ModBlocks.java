package link.sho8814.core.blocks;

import link.sho8814.core.items.BlockItemTest1;
import link.sho8814.core.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import link.sho8814.core.ShadowMC_ContentTweaker;

import java.util.function.Supplier;

import static link.sho8814.core.items.ModItems.ITEMS;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ShadowMC_ContentTweaker.MODID);

    public static final RegistryObject<Block> BLOCK_TEST0 = BLOCKS.register("test/block_test0", BlockTest0::new);
    public static final RegistryObject<Block> BLOCK_TEST1 = BLOCKS.register("test/block_test1", BlockTest1::new);
    public static final RegistryObject<Block> BLOCK_TEST2 = BLOCKS.register("test/block_test2", BlockTest2::new);
    public static final RegistryObject<Block> BLOCK_TEST3 = BLOCKS.register("test/block_test3", BlockTest3::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}