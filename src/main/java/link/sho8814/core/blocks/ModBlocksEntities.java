package link.sho8814.core.blocks;

import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocksEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "shadowmc_contenttweaker");

    public static final RegistryObject<BlockEntityType<BlockBillboardEntity>> ENTITY_BILLBOARD =
            BLOCK_ENTITIES.register("block_billboard",
                    () -> BlockEntityType.Builder.of(
                            BlockBillboardEntity::new,
                            ModBlocks.BLOCK_BILLBOARD.get()
                    ).build());
}