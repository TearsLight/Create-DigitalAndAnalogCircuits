package cn.cherrylanterns.digitalandanalogcircuits.register;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DACItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(DigitalAndAnalogCircuits.MOD_ID);

    // ── 导线 ──────────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> WHITE_WIRE      = blockItem("white_wire",      DACBlocks.WHITE_WIRE);
    public static final DeferredItem<BlockItem> ORANGE_WIRE     = blockItem("orange_wire",     DACBlocks.ORANGE_WIRE);
    public static final DeferredItem<BlockItem> MAGENTA_WIRE    = blockItem("magenta_wire",    DACBlocks.MAGENTA_WIRE);
    public static final DeferredItem<BlockItem> LIGHT_BLUE_WIRE = blockItem("light_blue_wire", DACBlocks.LIGHT_BLUE_WIRE);
    public static final DeferredItem<BlockItem> YELLOW_WIRE     = blockItem("yellow_wire",     DACBlocks.YELLOW_WIRE);
    public static final DeferredItem<BlockItem> LIME_WIRE       = blockItem("lime_wire",       DACBlocks.LIME_WIRE);
    public static final DeferredItem<BlockItem> PINK_WIRE       = blockItem("pink_wire",       DACBlocks.PINK_WIRE);
    public static final DeferredItem<BlockItem> GRAY_WIRE       = blockItem("gray_wire",       DACBlocks.GRAY_WIRE);
    public static final DeferredItem<BlockItem> LIGHT_GRAY_WIRE = blockItem("light_gray_wire", DACBlocks.LIGHT_GRAY_WIRE);
    public static final DeferredItem<BlockItem> CYAN_WIRE       = blockItem("cyan_wire",       DACBlocks.CYAN_WIRE);
    public static final DeferredItem<BlockItem> PURPLE_WIRE     = blockItem("purple_wire",     DACBlocks.PURPLE_WIRE);
    public static final DeferredItem<BlockItem> BLUE_WIRE       = blockItem("blue_wire",       DACBlocks.BLUE_WIRE);
    public static final DeferredItem<BlockItem> BROWN_WIRE      = blockItem("brown_wire",      DACBlocks.BROWN_WIRE);
    public static final DeferredItem<BlockItem> GREEN_WIRE      = blockItem("green_wire",      DACBlocks.GREEN_WIRE);
    public static final DeferredItem<BlockItem> RED_WIRE        = blockItem("red_wire",        DACBlocks.RED_WIRE);
    public static final DeferredItem<BlockItem> BLACK_WIRE      = blockItem("black_wire",      DACBlocks.BLACK_WIRE);

    // ── 连接器 ────────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> BIDIRECTIONAL_CONNECTOR =
            blockItem("bidirectional_connector", DACBlocks.BIDIRECTIONAL_CONNECTOR);
    public static final DeferredItem<BlockItem> FOUR_WAY_CONNECTOR =
            blockItem("four_way_connector", DACBlocks.FOUR_WAY_CONNECTOR);

    // ── 交流发电机 ────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> TWO_PHASE_ALTERNATOR =
            blockItem("two_phase_alternator", DACBlocks.TWO_PHASE_ALTERNATOR);
    public static final DeferredItem<BlockItem> THREE_PHASE_ALTERNATOR =
            blockItem("three_phase_alternator", DACBlocks.THREE_PHASE_ALTERNATOR);

    // ── 电动机 ────────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> SMALL_MOTOR       = blockItem("small_motor",       DACBlocks.SMALL_MOTOR);
    public static final DeferredItem<BlockItem> MEDIUM_MOTOR      = blockItem("medium_motor",      DACBlocks.MEDIUM_MOTOR);
    public static final DeferredItem<BlockItem> LARGE_MOTOR       = blockItem("large_motor",       DACBlocks.LARGE_MOTOR);
    public static final DeferredItem<BlockItem> EXTRA_LARGE_MOTOR = blockItem("extra_large_motor", DACBlocks.EXTRA_LARGE_MOTOR);

    // ── 电源管理 ──────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> VOLTAGE_REGULATOR  = blockItem("voltage_regulator",  DACBlocks.VOLTAGE_REGULATOR);
    public static final DeferredItem<BlockItem> STEP_UP_TRANSFORMER = blockItem("step_up_transformer", DACBlocks.STEP_UP_TRANSFORMER);
    public static final DeferredItem<BlockItem> STEP_DOWN_TRANSFORMER = blockItem("step_down_transformer", DACBlocks.STEP_DOWN_TRANSFORMER);

    // ── 半导体元件 ────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> DIODE      = blockItem("diode",      DACBlocks.DIODE);
    public static final DeferredItem<BlockItem> LED        = blockItem("led",        DACBlocks.LED);
    public static final DeferredItem<BlockItem> TRANSISTOR = blockItem("transistor", DACBlocks.TRANSISTOR);
    public static final DeferredItem<BlockItem> MOSFET     = blockItem("mosfet",     DACBlocks.MOSFET);

    // ── 逻辑门 ────────────────────────────────────────────────────────────
    public static final DeferredItem<BlockItem> AND_GATE  = blockItem("and_gate",  DACBlocks.AND_GATE);
    public static final DeferredItem<BlockItem> OR_GATE   = blockItem("or_gate",   DACBlocks.OR_GATE);
    public static final DeferredItem<BlockItem> NOT_GATE  = blockItem("not_gate",  DACBlocks.NOT_GATE);
    public static final DeferredItem<BlockItem> XOR_GATE  = blockItem("xor_gate",  DACBlocks.XOR_GATE);
    public static final DeferredItem<BlockItem> XNOR_GATE = blockItem("xnor_gate", DACBlocks.XNOR_GATE);

    // ──────────────────────────────────────────────────────────────────────
    private static <B extends net.minecraft.world.level.block.Block>
    DeferredItem<BlockItem> blockItem(String name,
                                      net.neoforged.neoforge.registries.DeferredBlock<B> block) {
        return ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
