package link.sho8814.core.items;

import link.sho8814.core.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemAdminSword extends SwordItem {

    private static final Tier ADMIN_TIER = new Tier() {
        @Override public int getUses() { return 0; }
        @Override public float getSpeed() { return 10.0F; }
        @Override public float getAttackDamageBonus() { return 1000.0F; }
        @Override public int getLevel() { return 4; }
        @Override public int getEnchantmentValue() { return 50; }
        @Override public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }
    };

    public ItemAdminSword() {
        super(ADMIN_TIER, 3, -2.4F,
                new Item.Properties()
                        .durability(1)
        );
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return state.is(ModBlocks.BLOCK_PROP_ADMIN.get());
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            target.kill();

            spawnVisualLightning((ServerLevel) attacker.level(),
                    target.getX(), target.getY(), target.getZ());
        }

        return true;
    }

    private void spawnVisualLightning(ServerLevel level, double x, double y, double z) {
        LightningBolt lightning = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(level);

        if (lightning != null) {
            lightning.moveTo(x, y, z);
            lightning.setVisualOnly(true);
            level.addFreshEntity(lightning);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (!level.isClientSide) {
            BlockPos pos = context.getClickedPos().relative(context.getClickedFace());

            if (level.isEmptyBlock(pos)) {
                BlockPlaceContext placeContext = new BlockPlaceContext(context);
                BlockState state = ModBlocks.BLOCK_PROP_ADMIN.get().getStateForPlacement(placeContext);

                if (state != null) {
                    level.setBlock(pos, state, 3);
                }
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}