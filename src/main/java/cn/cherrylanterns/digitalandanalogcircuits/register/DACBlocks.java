package cn.cherrylanterns.digitalandanalogcircuits.register;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import cn.cherrylanterns.digitalandanalogcircuits.block.WireBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.connector.BidirectionalConnectorBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.connector.FourWayConnectorBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.gate.GateType;
import cn.cherrylanterns.digitalandanalogcircuits.block.gate.LogicGateBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.generator.ThreePhaseAlternatorBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.generator.TwoPhaseAlternatorBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.motor.MotorBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.motor.MotorSize;
import cn.cherrylanterns.digitalandanalogcircuits.block.power.BetteryBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.power.StepDownTransformerBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.power.StepUpTransformerBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.power.VoltageRegulatorBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor.DiodeBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor.LEDBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor.MOSFETBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor.TransistorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DACBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(DigitalAndAnalogCircuits.MOD_ID);
    // 导线，合成材料为两个铜粒（Create），绝缘导线需要加一个塑料（此Mod新增）
    public static final DeferredBlock<WireBlock> WHITE_WIRE           = registerWire("white_wire",       MapColor.SNOW);
    public static final DeferredBlock<WireBlock> ORANGE_WIRE        = registerWire("orange_wire",      MapColor.COLOR_ORANGE);
    public static final DeferredBlock<WireBlock> MAGENTA_WIRE      = registerWire("magenta_wire",     MapColor.COLOR_MAGENTA);
    public static final DeferredBlock<WireBlock> LIGHT_BLUE_WIRE    = registerWire("light_blue_wire",  MapColor.COLOR_LIGHT_BLUE);
    public static final DeferredBlock<WireBlock> YELLOW_WIRE         = registerWire("yellow_wire",      MapColor.COLOR_YELLOW);
    public static final DeferredBlock<WireBlock> LIME_WIRE               = registerWire("lime_wire",        MapColor.COLOR_LIGHT_GREEN);
    public static final DeferredBlock<WireBlock> PINK_WIRE              = registerWire("pink_wire",        MapColor.COLOR_PINK);
    public static final DeferredBlock<WireBlock> GRAY_WIRE             = registerWire("gray_wire",        MapColor.COLOR_GRAY);
    public static final DeferredBlock<WireBlock> LIGHT_GRAY_WIRE   = registerWire("light_gray_wire",  MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<WireBlock> CYAN_WIRE             = registerWire("cyan_wire",        MapColor.COLOR_CYAN);
    public static final DeferredBlock<WireBlock> PURPLE_WIRE           = registerWire("purple_wire",      MapColor.COLOR_PURPLE);
    public static final DeferredBlock<WireBlock> BLUE_WIRE               = registerWire("blue_wire",        MapColor.COLOR_BLUE);
    public static final DeferredBlock<WireBlock> BROWN_WIRE          = registerWire("brown_wire",       MapColor.COLOR_BROWN);
    public static final DeferredBlock<WireBlock> GREEN_WIRE            = registerWire("green_wire",       MapColor.COLOR_GREEN);
    public static final DeferredBlock<WireBlock> RED_WIRE                = registerWire("red_wire",         MapColor.COLOR_RED);
    public static final DeferredBlock<WireBlock> BLACK_WIRE            = registerWire("black_wire",       MapColor.COLOR_BLACK);
    //塑料
//    public static final DeferredBlock<Plastic> PLASTIC_BLOCK =

    // 连接器
    // 双向连接器
    public static final DeferredBlock<BidirectionalConnectorBlock> BIDIRECTIONAL_CONNECTOR =
            BLOCKS.register("bidirectional_connector",
                    () -> new BidirectionalConnectorBlock(connectorProps(MapColor.METAL)));
    //四向连接器
    public static final DeferredBlock<FourWayConnectorBlock> FOUR_WAY_CONNECTOR =
            BLOCKS.register("four_way_connector",
                    () -> new FourWayConnectorBlock(connectorProps(MapColor.METAL)));

    // 交流发电机
    // 双向交流发电机
    public static final DeferredBlock<TwoPhaseAlternatorBlock> TWO_PHASE_ALTERNATOR =
            BLOCKS.register("two_phase_alternator",
                    () -> new TwoPhaseAlternatorBlock(generatorProps()));
    // 三相交流发电机
    public static final DeferredBlock<ThreePhaseAlternatorBlock> THREE_PHASE_ALTERNATOR =
            BLOCKS.register("three_phase_alternator",
                    () -> new ThreePhaseAlternatorBlock(generatorProps()));

    // 电动机
    // 小型电动机
    public static final DeferredBlock<MotorBlock> SMALL_MOTOR =
            BLOCKS.register("small_motor",
                    () -> new MotorBlock(MotorSize.SMALL, motorProps(1.5f)));
    // 中型电动机
    public static final DeferredBlock<MotorBlock> MEDIUM_MOTOR =
            BLOCKS.register("medium_motor",
                    () -> new MotorBlock(MotorSize.MEDIUM, motorProps(2.5f)));
    // 大型电动机
    public static final DeferredBlock<MotorBlock> LARGE_MOTOR =
            BLOCKS.register("large_motor",
                    () -> new MotorBlock(MotorSize.LARGE, motorProps(3.5f)));
    // 超大型电动机
    public static final DeferredBlock<MotorBlock> EXTRA_LARGE_MOTOR =
            BLOCKS.register("extra_large_motor",
                    () -> new MotorBlock(MotorSize.EXTRA_LARGE, motorProps(5.0f)));

    // 电源管理
    // 变压器
    public static final DeferredBlock<VoltageRegulatorBlock> VOLTAGE_REGULATOR =
            BLOCKS.register("voltage_regulator",
                    () -> new VoltageRegulatorBlock(powerProps()));
   // 升压器
    public static final DeferredBlock<StepUpTransformerBlock> STEP_UP_TRANSFORMER =
            BLOCKS.register("step_up_transformer",
                    () -> new StepUpTransformerBlock(powerProps()));
    // 降压器
    public static final DeferredBlock<StepDownTransformerBlock> STEP_DOWN_TRANSFORMER =
            BLOCKS.register("step_down_transformer",
                    () -> new StepDownTransformerBlock(powerProps()));
    // 子类：蓄电池
    // 小型电池，低压，3.7 V 以内，能量 100Wh 以内，功率 50W以内，可堆叠，自由组合串并联
    public  static final DeferredBlock<BetteryBlock> SMALL_BATTERY =
            BLOCKS.register("small_battery",
                    () -> new BetteryBlock(powerProps()));
    // 中型电池 ，低压，48V 以内，容量 200Ah，能量 10 kwh 以内，功率 5 kw 以内，可堆叠，自由组合串并联
    // 普通工作台无法合成，需要合成终端
    public  static final DeferredBlock<BetteryBlock> MEDIUM_BATTERY =
            BLOCKS.register("medium_battery",
                    () -> new BetteryBlock(powerProps()));
    //大型电池，高压，可堆叠，自由组合串并联
    public  static final DeferredBlock<BetteryBlock> LARGE_BATTERY =
            BLOCKS.register("large_battery",
                    () -> new BetteryBlock(powerProps()));
    // ══════════════════════════════════════════════════════════════════════
    // 半导体元件
    // ══════════════════════════════════════════════════════════════════════

    public static final DeferredBlock<DiodeBlock> DIODE =
            BLOCKS.register("diode",
                    () -> new DiodeBlock(semiconductorProps()));

    public static final DeferredBlock<LEDBlock> LED =
            BLOCKS.register("led",
                    () -> new LEDBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SNOW)
                            .strength(0.3f, 1.0f)
                            .sound(SoundType.GLASS)
                            .noOcclusion()
                            .lightLevel(state -> state.getValue(LEDBlock.LIT) ? 15 : 0)));

    public static final DeferredBlock<TransistorBlock> TRANSISTOR =
            BLOCKS.register("transistor",
                    () -> new TransistorBlock(semiconductorProps()));

    public static final DeferredBlock<MOSFETBlock> MOSFET =
            BLOCKS.register("mosfet",
                    () -> new MOSFETBlock(semiconductorProps()));

    // ══════════════════════════════════════════════════════════════════════
    // 逻辑门
    // ══════════════════════════════════════════════════════════════════════

    public static final DeferredBlock<LogicGateBlock> AND_GATE  = registerGate(GateType.AND);
    public static final DeferredBlock<LogicGateBlock> OR_GATE   = registerGate(GateType.OR);
    public static final DeferredBlock<LogicGateBlock> NOT_GATE  = registerGate(GateType.NOT);
    public static final DeferredBlock<LogicGateBlock> XOR_GATE  = registerGate(GateType.XOR);
    public static final DeferredBlock<LogicGateBlock> XNOR_GATE = registerGate(GateType.XNOR);

    // ══════════════════════════════════════════════════════════════════════
    // 工厂辅助方法
    // ══════════════════════════════════════════════════════════════════════

    /** 注册一种颜色的导线方块 */
    private static DeferredBlock<WireBlock> registerWire(String name, MapColor color) {
        return BLOCKS.register(name, () -> new WireBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .strength(0.5f, 1.0f)
                        .sound(SoundType.WOOL)
                        .noOcclusion()
                        .noCollission()));
    }

    /** 注册一种类型的逻辑门方块 */
    private static DeferredBlock<LogicGateBlock> registerGate(GateType type) {
        return BLOCKS.register(type.getSerializedName() + "_gate",
                () -> new LogicGateBlock(type, BlockBehaviour.Properties.of()
                        .mapColor(MapColor.METAL)
                        .strength(1.0f, 4.0f)
                        .sound(SoundType.METAL)
                        .noOcclusion()));
    }

    private static BlockBehaviour.Properties connectorProps(MapColor color) {
        return BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(1.5f, 6.0f)
                .sound(SoundType.METAL)
                .noOcclusion();
    }

    private static BlockBehaviour.Properties generatorProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(3.0f, 8.0f)
                .sound(SoundType.METAL);
    }

    private static BlockBehaviour.Properties motorProps(float hardness) {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(hardness, 8.0f)
                .sound(SoundType.METAL);
    }

    private static BlockBehaviour.Properties powerProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(2.0f, 6.0f)
                .sound(SoundType.METAL)
                .noOcclusion();
    }

    private static BlockBehaviour.Properties semiconductorProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(0.5f, 2.0f)
                .sound(SoundType.METAL)
                .noOcclusion();
    }
}
