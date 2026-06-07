package cn.cherrylanterns.digitalandanalogcircuits.register;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DACCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DigitalAndAnalogCircuits.MOD_ID);

    public static final Supplier<CreativeModeTab> DAC_TAB = CREATIVE_MODE_TABS.register(
            "dac_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.digitalandanalogcircuits.dac_tab"))
                    .icon(() -> DACItems.WHITE_WIRE.get().getDefaultInstance())
                    .displayItems((params, output) -> {

                        // 导线（16色）
                        output.accept(DACItems.WHITE_WIRE.get());
                        output.accept(DACItems.ORANGE_WIRE.get());
                        output.accept(DACItems.MAGENTA_WIRE.get());
                        output.accept(DACItems.LIGHT_BLUE_WIRE.get());
                        output.accept(DACItems.YELLOW_WIRE.get());
                        output.accept(DACItems.LIME_WIRE.get());
                        output.accept(DACItems.PINK_WIRE.get());
                        output.accept(DACItems.GRAY_WIRE.get());
                        output.accept(DACItems.LIGHT_GRAY_WIRE.get());
                        output.accept(DACItems.CYAN_WIRE.get());
                        output.accept(DACItems.PURPLE_WIRE.get());
                        output.accept(DACItems.BLUE_WIRE.get());
                        output.accept(DACItems.BROWN_WIRE.get());
                        output.accept(DACItems.GREEN_WIRE.get());
                        output.accept(DACItems.RED_WIRE.get());
                        output.accept(DACItems.BLACK_WIRE.get());

                        // 连接器
                        output.accept(DACItems.BIDIRECTIONAL_CONNECTOR.get());
                        output.accept(DACItems.FOUR_WAY_CONNECTOR.get());

                        // 交流发电机
                        output.accept(DACItems.TWO_PHASE_ALTERNATOR.get());
                        output.accept(DACItems.THREE_PHASE_ALTERNATOR.get());

                        // 电动机
                        output.accept(DACItems.SMALL_MOTOR.get());
                        output.accept(DACItems.MEDIUM_MOTOR.get());
                        output.accept(DACItems.LARGE_MOTOR.get());
                        output.accept(DACItems.EXTRA_LARGE_MOTOR.get());

                        // 电源管理
                        output.accept(DACItems.VOLTAGE_REGULATOR.get());
                        output.accept(DACItems.STEP_UP_TRANSFORMER.get());
                        output.accept(DACItems.STEP_DOWN_TRANSFORMER.get());

                        // 半导体
                        output.accept(DACItems.DIODE.get());
                        output.accept(DACItems.LED.get());
                        output.accept(DACItems.TRANSISTOR.get());
                        output.accept(DACItems.MOSFET.get());

                        // 逻辑门
                        output.accept(DACItems.AND_GATE.get());
                        output.accept(DACItems.OR_GATE.get());
                        output.accept(DACItems.NOT_GATE.get());
                        output.accept(DACItems.XOR_GATE.get());
                        output.accept(DACItems.XNOR_GATE.get());
                    })
                    .build()
    );

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
