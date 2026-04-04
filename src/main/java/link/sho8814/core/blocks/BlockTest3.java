package link.sho8814.core.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class BlockTest3 extends Block {

    public BlockTest3() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0f, 3.0f)
                .sound(SoundType.WOOD)
                .ignitedByLava()
        );
    }
}