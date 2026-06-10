package cn.cherrylanterns.digitalandanalogcircuits;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * 模组配置类
 * Mod Configuration
 */
@EventBusSubscriber(modid = DigitalAndAnalogCircuits.MOD_ID)
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // ===== 导线系统配置 =====

    /** 导线网络每 tick 最大能量传输量 (FE) */
    public static final ModConfigSpec.IntValue WIRE_MAX_TRANSFER_RATE = BUILDER
            .comment("Maximum energy transfer rate per wire segment per tick (FE)")
            .defineInRange("wireMaxTransferRate", 100, 1, Integer.MAX_VALUE);

    /** 是否启用电击伤害 */
    public static final ModConfigSpec.BooleanValue ENABLE_ELECTRIC_DAMAGE = BUILDER
            .comment("Whether bare wires cause electric shock damage when stepped on")
            .define("enableElectricDamage", true);

    /** 电击伤害的基础电压阈值 (V) */
    public static final ModConfigSpec.IntValue ELECTRIC_DAMAGE_THRESHOLD = BUILDER
            .comment("Minimum voltage (V) for bare wires to cause damage")
            .defineInRange("electricDamageThreshold", 36, 1, 1000);

    /** 导线网络更新间隔 (tick) */
    public static final ModConfigSpec.IntValue WIRE_NETWORK_TICK_INTERVAL = BUILDER
            .comment("Ticks between wire network energy distribution updates")
            .defineInRange("wireNetworkTickInterval", 5, 1, 100);

    static final ModConfigSpec SPEC = BUILDER.build();

    // 运行时缓存值
    public static int wireMaxTransferRate;
    public static boolean enableElectricDamage;
    public static int electricDamageThreshold;
    public static int wireNetworkTickInterval;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            wireMaxTransferRate = WIRE_MAX_TRANSFER_RATE.get();
            enableElectricDamage = ENABLE_ELECTRIC_DAMAGE.get();
            electricDamageThreshold = ELECTRIC_DAMAGE_THRESHOLD.get();
            wireNetworkTickInterval = WIRE_NETWORK_TICK_INTERVAL.get();
        }
    }
}
