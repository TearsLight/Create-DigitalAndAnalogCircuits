package cn.cherrylanterns.digitalandanalogcircuits.item.wire;

import java.util.List;

import cn.cherrylanterns.digitalandanalogcircuits.api.WireMaterial;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

/**
 * 裸导线物品（BlockItem）
 * Bare Wire Item - places the corresponding BareWireBlock in world.
 */
public class BareWireItem extends BlockItem {

    private final WireMaterial material;

    public BareWireItem(Block block, Properties properties, WireMaterial material) {
        super(block, properties);
        this.material = material;
    }

    public WireMaterial getMaterial() {
        return material;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                 List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.wire_material",
                material.getName()));
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.resistivity",
                String.format("%.4f", material.getResistivity())));
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.conductivity",
                String.format("%.1f", material.getConductivity())));
        tooltip.add(Component.translatable("tooltip.digitalandanalogcircuits.bare_wire_warning"));
    }
}
