package link.sho8814.core;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ShadowMC_ContentTweaker.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue BILLBOARD_MAX_SCALE = BUILDER
                    .comment("Maximum Billboard scale. Default 32.0")
                    .defineInRange("billboardMaxScale", 32.0, 1.0, 1024.0);

    private static final ForgeConfigSpec.DoubleValue BILLBOARD_MAX_OFFSET_X = BUILDER
                    .comment("Maximum Billboard offset X. Default 32.0")
                    .defineInRange("billboardMaxOffsetX", 32.0, 0.0, 1024.0);

    private static final ForgeConfigSpec.DoubleValue BILLBOARD_MAX_OFFSET_Y = BUILDER
                    .comment("Maximum Billboard offset Y. Default 32.0")
                    .defineInRange("billboardMaxOffsetY", 32.0, 0.0, 1024.0);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static double billboardMaxScale;
    public static double billboardMaxOffsetX;
    public static double billboardMaxOffsetY;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        billboardMaxScale = BILLBOARD_MAX_SCALE.get();
        billboardMaxOffsetX = BILLBOARD_MAX_OFFSET_X.get();
        billboardMaxOffsetY = BILLBOARD_MAX_OFFSET_Y.get();
    }
}