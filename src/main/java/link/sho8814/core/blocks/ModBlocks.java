package link.sho8814.core.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import link.sho8814.core.ShadowMC_ContentTweaker;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ShadowMC_ContentTweaker.MODID);

    // Test
    public static final RegistryObject<Block> BLOCK_TEST0 = BLOCKS.register("test/block_test0", BlockTest0::new);
    public static final RegistryObject<Block> BLOCK_TEST1 = BLOCKS.register("test/block_test1", BlockTest1::new);
    public static final RegistryObject<Block> BLOCK_TEST2 = BLOCKS.register("test/block_test2", BlockTest2::new);
    public static final RegistryObject<Block> BLOCK_TEST3 = BLOCKS.register("test/block_test3", BlockTest3::new);
    public static final RegistryObject<Block> BLOCK_TEST4 = BLOCKS.register("test/block_test4", BlockTest4::new);
    public static final RegistryObject<Block> BLOCK_TEST5 = BLOCKS.register("test/block_test5", BlockTest5::new);
    public static final RegistryObject<Block> BLOCK_TEST6 = BLOCKS.register("test/block_test6", BlockTest6::new);
    public static final RegistryObject<Block> MODEL_TEST1 = BLOCKS.register("test/model_test1", ModelTest1::new);
    public static final RegistryObject<Block> MODEL_TEST2 = BLOCKS.register("test/model_test2", ModelTest2::new);

    // All
    public static final RegistryObject<Block> DICE_BLOCK = BLOCKS.register("dice_block", DiceBlock::new);
    public static final RegistryObject<Block> BLOCK_BILLBOARD = BLOCKS.register("block_billboard", BlockBillboard::new);
    public static final RegistryObject<Block> BLOCK_PROP_ADMIN = BLOCKS.register("block_prop_admin", BlockPropAdmin::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}