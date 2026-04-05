package link.sho8814.core.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockItemTest1 extends BlockItem {

    public BlockItemTest1(Block block) {
        super(block, new Item.Properties());
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }
}