package cn.cherrylanterns.digitalandanalogcircuits.block.connector;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Four-way connector – four wire ports on the four horizontal faces (N/S/E/W).
 *
 * <p>Acts as a junction node: any of the four connected wire colours can
 * exchange signals with any other colour through this block.
 *
 * <p>Unlike {@link BidirectionalConnectorBlock} this block has no orientation
 * (it is rotationally symmetric around the Y-axis), so no FACING property is needed.
 *
 * <p>TODO: add BlockEntity for per-port colour mapping.
 * TODO: getShape() – cross-shaped thin AABB.
 */
public class FourWayConnectorBlock extends ConnectorBlock {

    public FourWayConnectorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
