package cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor;

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
 * Diode – allows current to flow in one direction only (anode → cathode).
 *
 * <p>Placing the diode "backwards" (cathode facing the source) will block all current.
 * This is useful for preventing back-feed into generators and for half-wave rectifiers.
 *
 * <p>BlockState: {@code facing} is the direction current is <em>allowed to exit</em>
 * (the cathode side). The anode (input) is on the opposite face.
 *
 * <p>TODO: forward-voltage drop mechanic (small energy loss across the junction).
 * TODO: breakdown voltage – if reverse voltage exceeds threshold the diode is destroyed.
 */
public class DiodeBlock extends Block {

    /** Direction current exits (cathode / output side). */
    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public DiodeBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        // The player faces the anode; current flows away from the player → FACING = look direction
        return defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
    }
}
