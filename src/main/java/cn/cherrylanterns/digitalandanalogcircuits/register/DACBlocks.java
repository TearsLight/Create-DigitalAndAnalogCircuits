package cn.cherrylanterns.digitalandanalogcircuits.register;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import cn.cherrylanterns.digitalandanalogcircuits.api.WireColor;
import cn.cherrylanterns.digitalandanalogcircuits.block.wire.BareWireBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.wire.WireBlock;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 数字电路方块注册表
 * Digital Circuits Block Registry
 */
public class DACBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(DigitalAndAnalogCircuits.MOD_ID);

    // ===== 绝缘导线 - 16 色 =====

    public static final DeferredBlock<WireBlock> WHITE_WIRE = registerInsulatedWire(WireColor.WHITE, MapColor.SNOW);
    public static final DeferredBlock<WireBlock> ORANGE_WIRE = registerInsulatedWire(WireColor.ORANGE, MapColor.COLOR_ORANGE);
    public static final DeferredBlock<WireBlock> MAGENTA_WIRE = registerInsulatedWire(WireColor.MAGENTA, MapColor.COLOR_MAGENTA);
    public static final DeferredBlock<WireBlock> LIGHT_BLUE_WIRE = registerInsulatedWire(WireColor.LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE);
    public static final DeferredBlock<WireBlock> YELLOW_WIRE = registerInsulatedWire(WireColor.YELLOW, MapColor.COLOR_YELLOW);
    public static final DeferredBlock<WireBlock> LIME_WIRE = registerInsulatedWire(WireColor.LIME, MapColor.COLOR_LIGHT_GREEN);
    public static final DeferredBlock<WireBlock> PINK_WIRE = registerInsulatedWire(WireColor.PINK, MapColor.COLOR_PINK);
    public static final DeferredBlock<WireBlock> GRAY_WIRE = registerInsulatedWire(WireColor.GRAY, MapColor.COLOR_GRAY);
    public static final DeferredBlock<WireBlock> LIGHT_GRAY_WIRE = registerInsulatedWire(WireColor.LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<WireBlock> CYAN_WIRE = registerInsulatedWire(WireColor.CYAN, MapColor.COLOR_CYAN);
    public static final DeferredBlock<WireBlock> PURPLE_WIRE = registerInsulatedWire(WireColor.PURPLE, MapColor.COLOR_PURPLE);
    public static final DeferredBlock<WireBlock> BLUE_WIRE = registerInsulatedWire(WireColor.BLUE, MapColor.COLOR_BLUE);
    public static final DeferredBlock<WireBlock> BROWN_WIRE = registerInsulatedWire(WireColor.BROWN, MapColor.COLOR_BROWN);
    public static final DeferredBlock<WireBlock> GREEN_WIRE = registerInsulatedWire(WireColor.GREEN, MapColor.COLOR_GREEN);
    public static final DeferredBlock<WireBlock> RED_WIRE = registerInsulatedWire(WireColor.RED, MapColor.COLOR_RED);
    public static final DeferredBlock<WireBlock> BLACK_WIRE = registerInsulatedWire(WireColor.BLACK, MapColor.COLOR_BLACK);

    // ===== 裸导线 - 5 种材料 =====

    public static final DeferredBlock<BareWireBlock> BARE_COPPER_WIRE =
            registerBareWire("bare_copper_wire", MapColor.COLOR_ORANGE);
    public static final DeferredBlock<BareWireBlock> BARE_IRON_WIRE =
            registerBareWire("bare_iron_wire", MapColor.COLOR_GRAY);
    public static final DeferredBlock<BareWireBlock> BARE_SILVER_WIRE =
            registerBareWire("bare_silver_wire", MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<BareWireBlock> BARE_GOLD_WIRE =
            registerBareWire("bare_gold_wire", MapColor.GOLD);
    public static final DeferredBlock<BareWireBlock> BARE_ALUMINUM_WIRE =
            registerBareWire("bare_aluminum_wire", MapColor.COLOR_LIGHT_GRAY);

    // ===== 矿石 - 铝 & 锂 =====

    public static final DeferredBlock<DropExperienceBlock> ALUMINUM_ORE =
            registerOre("aluminum_ore", MapColor.STONE, 0, 0);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_ALUMINUM_ORE =
            registerOre("deepslate_aluminum_ore", MapColor.DEEPSLATE, 0, 0);
    public static final DeferredBlock<DropExperienceBlock> LITHIUM_ORE =
            registerOre("lithium_ore", MapColor.STONE, 1, 2);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_LITHIUM_ORE =
            registerOre("deepslate_lithium_ore", MapColor.DEEPSLATE, 1, 4);

    // ===== 工厂方法 =====

    /** 注册绝缘导线方块 */
    private static DeferredBlock<WireBlock> registerInsulatedWire(WireColor color, MapColor mapColor) {
        return BLOCKS.register(color.getName() + "_wire", () -> new WireBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .strength(0.5f, 1.0f)
                        .sound(SoundType.WOOL)
                        .noOcclusion(),
                color));
    }

    /** 注册裸导线方块 */
    private static DeferredBlock<BareWireBlock> registerBareWire(String name, MapColor mapColor) {
        return BLOCKS.register(name, () -> new BareWireBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .strength(0.3f, 1.0f)
                        .sound(SoundType.METAL)
                        .noOcclusion()));
    }

    /** 注册矿石方块 */
    private static DeferredBlock<DropExperienceBlock> registerOre(
            String name, MapColor mapColor, int minExp, int maxExp) {
        return BLOCKS.register(name, () -> new DropExperienceBlock(
                ConstantInt.of(maxExp),
                BlockBehaviour.Properties.of()
                        .mapColor(mapColor)
                        .strength(3.0f, 3.0f)
                        .sound(SoundType.STONE)
                        .requiresCorrectToolForDrops()));
    }

    // ============================================================
    // 以下方块待后续 Phase 实现，暂时注释
    // ============================================================

    // TODO: Phase 2 - 连接器
    // public static final DeferredBlock<BidirectionalConnectorBlock> BIDIRECTIONAL_CONNECTOR = ...
    // public static final DeferredBlock<FourWayConnectorBlock> FOUR_WAY_CONNECTOR = ...

    // TODO: Phase 3 - 发电机
    // public static final DeferredBlock<TwoPhaseAlternatorBlock> TWO_PHASE_ALTERNATOR = ...
    // public static final DeferredBlock<ThreePhaseAlternatorBlock> THREE_PHASE_ALTERNATOR = ...

    // TODO: Phase 3 - 电动机
    // public static final DeferredBlock<MotorBlock> SMALL_MOTOR = ...

    // TODO: Phase 4 - 变压器
    // public static final DeferredBlock<VoltageRegulatorBlock> VOLTAGE_REGULATOR = ...

    // TODO: Phase 5 - 电池
    // public static final DeferredBlock<BetteryBlock> SMALL_BATTERY = ...

    // TODO: Phase 6 - 半导体元件
    // public static final DeferredBlock<DiodeBlock> DIODE = ...

    // TODO: Phase 7 - 逻辑门
    // public static final DeferredBlock<LogicGateBlock> AND_GATE = ...
}
