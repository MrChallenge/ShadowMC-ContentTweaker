package link.sho8814.core.blocks.client;

import link.sho8814.core.blocks.ModBlocksEntities;
import link.sho8814.core.blocks.client.renderer.BlockBillboardRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static link.sho8814.core.ShadowMC_ContentTweaker.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BlockEntityRenderers.register(
                ModBlocksEntities.ENTITY_BILLBOARD.get(),
                BlockBillboardRenderer::new
        );
    }
}