package cn.cherrylanterns.digitalandanalogcircuits.register;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 物品栏（创造模式标签页）注册
 * Creative Mode Tab Registry
 */
public class DACCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DigitalAndAnalogCircuits.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DAC_TAB =
            CREATIVE_MODE_TABS.register("dac_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.digitalandanalogcircuits.dac_tab"))
                    .icon(() -> new ItemStack(DACItems.BARE_COPPER_WIRE.get()))
                    .displayItems((params, output) -> {
                        // ===== 裸导线 =====
                        output.accept(DACItems.BARE_COPPER_WIRE.get());
                        output.accept(DACItems.BARE_IRON_WIRE.get());
                        output.accept(DACItems.BARE_SILVER_WIRE.get());
                        output.accept(DACItems.BARE_GOLD_WIRE.get());
                        output.accept(DACItems.BARE_ALUMINUM_WIRE.get());

                        // ===== 绝缘铜导线 (16色) =====
                        output.accept(DACItems.INSULATED_COPPER_WIRE_WHITE.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_ORANGE.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_MAGENTA.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_LIGHT_BLUE.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_YELLOW.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_LIME.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_PINK.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_GRAY.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_LIGHT_GRAY.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_CYAN.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_PURPLE.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_BLUE.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_BROWN.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_GREEN.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_RED.get());
                        output.accept(DACItems.INSULATED_COPPER_WIRE_BLACK.get());
                    })
                    .build());
}
