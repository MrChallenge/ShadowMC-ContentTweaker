package link.sho8814.core.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;

public class ModelTest2 extends HorizontalDirectionalBlock {

    public static final EnumProperty<Half> HALF = EnumProperty.create("half", Half.class);

    public enum Half implements StringRepresentable {
        LOWER, UPPER;

        @Override
        public String getSerializedName() {
            return this == LOWER ? "lower" : "upper";
        }
    }

    public ModelTest2() {
        super(Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(0.7f, 0.4f)
                .sound(SoundType.STONE)
                .noOcclusion()
        );

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.LOWER)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.getBlockState(pos.above()).canBeReplaced()) {
            return null;
        }

        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(HALF, Half.LOWER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(),
                state.setValue(HALF, Half.UPPER),
                3);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        Half half = state.getValue(HALF);
        BlockPos otherPos = (half == Half.LOWER) ? pos.above() : pos.below();

        BlockState otherState = level.getBlockState(otherPos);

        if (otherState.getBlock() == this) {
            level.destroyBlock(otherPos, false);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {

        Half half = state.getValue(HALF);

        if (half == Half.LOWER && direction == Direction.UP && neighborState.getBlock() != this) {
            return Blocks.AIR.defaultBlockState();
        }

        if (half == Half.UPPER && direction == Direction.DOWN && neighborState.getBlock() != this) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }
}