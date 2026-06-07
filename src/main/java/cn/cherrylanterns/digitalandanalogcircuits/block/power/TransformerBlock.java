package cn.cherrylanterns.digitalandanalogcircuits.block.power;

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
 * Base class for step-up and step-down transformers.
 *
 * <p>Transformers change the voltage level between two wire segments.
 * They are essential for long-distance transmission (step-up on the sender side,
 * step-down on the receiver side) to reduce energy loss.
 *
 * <p>BlockState: {@code facing} – primary (input) winding side.
 * The secondary (output) winding is on the opposite face.
 *
 * <p>TODO: add BlockEntity with configurable turn-ratio (N-primary : N-secondary).
 * TODO: implement energy loss based on distance and wire resistance.
 */
public abstract class TransformerBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public TransformerBlock(BlockBehaviour.Properties properties) {
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
}
