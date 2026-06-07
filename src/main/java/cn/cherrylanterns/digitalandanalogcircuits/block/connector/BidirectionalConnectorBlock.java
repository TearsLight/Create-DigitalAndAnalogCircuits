package cn.cherrylanterns.digitalandanalogcircuits.block.connector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Bidirectional connector – two wire ports on opposite faces.
 *
 * <p>The connector is placed along an axis (e.g. NORTH↔SOUTH, EAST↔WEST, DOWN↔UP).
 * It bridges two wire networks of <em>different</em> colours, allowing signal flow
 * between them in both directions.
 *
 * <p>BlockState: {@code facing} stores the direction the player was looking when
 * placing, giving the "input" side. The "output" side is always {@code facing.getOpposite()}.
 *
 * <p>TODO: add BlockEntity to configure per-channel voltage mapping.
 */
public class BidirectionalConnectorBlock extends ConnectorBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public BidirectionalConnectorBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    // TODO: getShape() – return a small cube centered on the block
    // TODO: use() – open config GUI (which wire colour maps to which)
}
