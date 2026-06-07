package cn.cherrylanterns.digitalandanalogcircuits.block.generator;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for all AC generator (alternator) blocks.
 *
 * <p>An alternator converts mechanical rotation (provided by Create's kinetic network)
 * into alternating current on the attached wire network.
 *
 * <p>BlockState properties:
 * <ul>
 *   <li>{@code facing} – the direction the output shaft faces (determines wire-connection side)</li>
 *   <li>{@code powered} – whether the generator is currently producing power</li>
 * </ul>
 *
 * <p>TODO: hook into Create's {@code IRotate} / {@code KineticBlockEntity} once the API is imported.
 * TODO: add BlockEntity to store RPM → voltage conversion and current phase output.
 */
public abstract class GeneratorBlock extends Block {

    public static final DirectionProperty FACING  = BlockStateProperties.FACING;
    public static final BooleanProperty   POWERED = BlockStateProperties.POWERED;

    public GeneratorBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING,  Direction.NORTH)
                .setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(POWERED, false);
    }
}
