package cn.cherrylanterns.digitalandanalogcircuits.item.wire;

import java.util.List;

import cn.cherrylanterns.digitalandanalogcircuits.api.WireColor;
import cn.cherrylanterns.digitalandanalogcircuits.api.WireMaterial;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

/**
 * 绝缘导线物品（BlockItem）
 * Insulated Wire Item - places the corresponding WireBlock in world.
 */
public class InsulatedWireItem extends BlockItem {

    private final WireMaterial material;
    private final WireColor color;

    public InsulatedWireItem(Block block, Properties properties, WireMaterial material, WireColor color) {
        super(block, properties);
        this.material = material;
        this.color = color;
    }

    public WireMaterial getMaterial() {
        return material;
    }

    public WireColor getColor() {
        return color;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                 List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.wire_material",
                material.getName()));
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.wire_color",
                color.getName()));
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.resistivity",
                String.format("%.4f", material.getResistivity())));
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.insulated_wire_safe"));
    }
}
