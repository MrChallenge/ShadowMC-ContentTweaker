package link.sho8814.core.blocks.entity;

import link.sho8814.core.blocks.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBillboardEntity extends BlockEntity {
    public BlockBillboardEntity(BlockPos pos, BlockState state) {
        super(ModBlocksEntities.ENTITY_BILLBOARD.get(), pos, state);
    }
}