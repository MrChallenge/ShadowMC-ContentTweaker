package link.sho8814.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class BlockTest1 extends Block {

    public BlockTest1() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NETHER)
                .strength(0.5f, 0.5f)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
    }
}