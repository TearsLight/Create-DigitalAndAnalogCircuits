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
 * Voltage Regulator – maintains a constant output voltage regardless of
 * fluctuations on the input wire network.
 *
 * <p>Typical use: placed between a generator and sensitive equipment
 * (logic gates, semiconductors) to prevent damage from voltage spikes.
 *
 * <p>BlockState: {@code facing} – input port direction; output is the opposite face.
 *
 * <p>TODO: add BlockEntity with configurable target voltage (GUI slider).
 * TODO: implement ripple rejection / efficiency loss mechanic.
 */
public class VoltageRegulatorBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public VoltageRegulatorBlock(BlockBehaviour.Properties properties) {
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
