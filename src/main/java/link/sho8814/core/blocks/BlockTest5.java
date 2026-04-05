package link.sho8814.core.blocks;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class BlockTest5 extends HorizontalDirectionalBlock {

    public BlockTest5() {
        super(Properties.of()
                .mapColor(MapColor.STONE)
                .strength(0.5f, 0.5f)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops()
        );
    }
}