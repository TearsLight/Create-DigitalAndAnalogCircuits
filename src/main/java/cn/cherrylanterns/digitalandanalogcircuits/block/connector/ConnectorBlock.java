package cn.cherrylanterns.digitalandanalogcircuits.block.connector;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Base class for all wire connector blocks.
 *
 * <p>Connectors allow differently-coloured wires to exchange signals.
 * Subclasses define how many input/output ports are exposed and
 * what signal-routing logic applies.
 *
 * <p>Future additions:
 * <ul>
 *   <li>Port direction BlockState properties</li>
 *   <li>GUI for configuring signal mapping between wire colours</li>
 *   <li>BlockEntity for storing routing configuration</li>
 * </ul>
 */
public abstract class ConnectorBlock extends Block {

    public ConnectorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
