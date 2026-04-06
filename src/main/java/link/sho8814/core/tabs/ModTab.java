package link.sho8814.core.tabs;

import link.sho8814.core.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import link.sho8814.core.ShadowMC_ContentTweaker;
import link.sho8814.core.items.ModItems;

import java.util.Set;

public class ModTab extends CreativeModeTab {

    protected ModTab(Builder builder) {
        super(builder);
    }

    private static final Set<String> EXCEPTED_LIST = Set.of(
            "null" // исключения (столбиком)
    );

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ShadowMC_ContentTweaker.MODID);

    public static final RegistryObject<CreativeModeTab> CONTENTTWEAKER_TAB = CREATIVE_MODE_TABS.register("contenttweaker_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.DICE_BLOCK.get()))
                    .title(Component.translatable("creative_tab.shadowmc_contenttweaker"))
                    .displayItems((pParameters, pOutput) -> {

                        ModItems.ITEMS.getEntries().forEach(item -> {
                            String name = item.getId().getPath();

                            if (!EXCEPTED_LIST.contains(name)) {
                                pOutput.accept(item.get());
                            }
                        });
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}