package cn.cherrylanterns.digitalandanalogcircuits.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Base class for all coloured wire blocks.
 *
 * <p>In the first iteration this is a plain thin block.
 * Later iterations will add:
 * <ul>
 *   <li>Six-directional connection states (NORTH / SOUTH / EAST / WEST / UP / DOWN)</li>
 *   <li>Voltage / current propagation via the {@code IWireNetwork} interface</li>
 *   <li>Colour-based isolation (two different-colour wires refuse to merge networks)</li>
 * </ul>
 */
public class WireBlock extends Block {

    public WireBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    // TODO: override getShape() to return a thin flat AABB (e.g. 0,0,0 → 1,0.125,1)
    // TODO: override getStateForPlacement() for directional auto-connection
    // TODO: implement IWireNetwork carrier logic once the network system is ready
}
