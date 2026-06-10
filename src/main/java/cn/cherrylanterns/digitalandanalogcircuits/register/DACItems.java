package cn.cherrylanterns.digitalandanalogcircuits.register;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import cn.cherrylanterns.digitalandanalogcircuits.api.WireColor;
import cn.cherrylanterns.digitalandanalogcircuits.api.WireMaterial;
import cn.cherrylanterns.digitalandanalogcircuits.item.wire.BareWireItem;
import cn.cherrylanterns.digitalandanalogcircuits.item.wire.InsulatedWireItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 数字电路物品注册表
 * Digital Circuits Item Registry
 * <p>
 * 所有方块物品均使用与对应方块相同的注册名，以便自动匹配模型。
 */
public class DACItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, DigitalAndAnalogCircuits.MOD_ID);

    // ===== 裸导线 BlockItem — 注册名与 DACBlocks 一致 =====

    public static final DeferredHolder<Item, BareWireItem> BARE_COPPER_WIRE =
            ITEMS.register("bare_copper_wire",
                    () -> new BareWireItem(DACBlocks.BARE_COPPER_WIRE.get(),
                            new Item.Properties(), WireMaterial.COPPER));

    public static final DeferredHolder<Item, BareWireItem> BARE_IRON_WIRE =
            ITEMS.register("bare_iron_wire",
                    () -> new BareWireItem(DACBlocks.BARE_IRON_WIRE.get(),
                            new Item.Properties(), WireMaterial.IRON));

    public static final DeferredHolder<Item, BareWireItem> BARE_SILVER_WIRE =
            ITEMS.register("bare_silver_wire",
                    () -> new BareWireItem(DACBlocks.BARE_SILVER_WIRE.get(),
                            new Item.Properties(), WireMaterial.SILVER));

    public static final DeferredHolder<Item, BareWireItem> BARE_GOLD_WIRE =
            ITEMS.register("bare_gold_wire",
                    () -> new BareWireItem(DACBlocks.BARE_GOLD_WIRE.get(),
                            new Item.Properties(), WireMaterial.GOLD));

    public static final DeferredHolder<Item, BareWireItem> BARE_ALUMINUM_WIRE =
            ITEMS.register("bare_aluminum_wire",
                    () -> new BareWireItem(DACBlocks.BARE_ALUMINUM_WIRE.get(),
                            new Item.Properties(), WireMaterial.ALUMINUM));

    // ===== 绝缘导线 BlockItem — 每色注册名与 DACBlocks 的块名一致 =====
    // 注意：注册名如 "white_wire" 匹配方块注册名，模型自动查找 models/item/white_wire.json

    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_WHITE =
            registerInsulatedWire("white_wire", WireMaterial.COPPER, WireColor.WHITE, DACBlocks.WHITE_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_ORANGE =
            registerInsulatedWire("orange_wire", WireMaterial.COPPER, WireColor.ORANGE, DACBlocks.ORANGE_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_MAGENTA =
            registerInsulatedWire("magenta_wire", WireMaterial.COPPER, WireColor.MAGENTA, DACBlocks.MAGENTA_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_LIGHT_BLUE =
            registerInsulatedWire("light_blue_wire", WireMaterial.COPPER, WireColor.LIGHT_BLUE, DACBlocks.LIGHT_BLUE_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_YELLOW =
            registerInsulatedWire("yellow_wire", WireMaterial.COPPER, WireColor.YELLOW, DACBlocks.YELLOW_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_LIME =
            registerInsulatedWire("lime_wire", WireMaterial.COPPER, WireColor.LIME, DACBlocks.LIME_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_PINK =
            registerInsulatedWire("pink_wire", WireMaterial.COPPER, WireColor.PINK, DACBlocks.PINK_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_GRAY =
            registerInsulatedWire("gray_wire", WireMaterial.COPPER, WireColor.GRAY, DACBlocks.GRAY_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_LIGHT_GRAY =
            registerInsulatedWire("light_gray_wire", WireMaterial.COPPER, WireColor.LIGHT_GRAY, DACBlocks.LIGHT_GRAY_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_CYAN =
            registerInsulatedWire("cyan_wire", WireMaterial.COPPER, WireColor.CYAN, DACBlocks.CYAN_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_PURPLE =
            registerInsulatedWire("purple_wire", WireMaterial.COPPER, WireColor.PURPLE, DACBlocks.PURPLE_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_BLUE =
            registerInsulatedWire("blue_wire", WireMaterial.COPPER, WireColor.BLUE, DACBlocks.BLUE_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_BROWN =
            registerInsulatedWire("brown_wire", WireMaterial.COPPER, WireColor.BROWN, DACBlocks.BROWN_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_GREEN =
            registerInsulatedWire("green_wire", WireMaterial.COPPER, WireColor.GREEN, DACBlocks.GREEN_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_RED =
            registerInsulatedWire("red_wire", WireMaterial.COPPER, WireColor.RED, DACBlocks.RED_WIRE);
    public static final DeferredHolder<Item, InsulatedWireItem> INSULATED_COPPER_WIRE_BLACK =
            registerInsulatedWire("black_wire", WireMaterial.COPPER, WireColor.BLACK, DACBlocks.BLACK_WIRE);

    // ===== 辅助方法 =====

    private static DeferredHolder<Item, InsulatedWireItem> registerInsulatedWire(
            String name, WireMaterial material, WireColor color, DeferredBlock<?> block) {
        return ITEMS.register(name,
                () -> new InsulatedWireItem(block.get(),
                        new Item.Properties(), material, color));
    }
}
