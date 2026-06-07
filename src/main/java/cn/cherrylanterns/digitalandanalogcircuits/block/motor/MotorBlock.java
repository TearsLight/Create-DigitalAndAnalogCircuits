package cn.cherrylanterns.digitalandanalogcircuits.block.motor;

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
 * Electric motor block – converts electrical energy from the wire network
 * into mechanical rotation for Create's kinetic network.
 *
 * <p>Four tiers are registered individually (see {@link MotorSize}).
 * Each tier is a separate block instance sharing this class, distinguished
 * by the {@link MotorSize} field passed at construction.
 *
 * <p>BlockState properties:
 * <ul>
 *   <li>{@code facing} – direction the output shaft points</li>
 *   <li>{@code powered} – true while electrical power is being consumed</li>
 * </ul>
 *
 * <p>TODO: hook into Create's {@code IRotate} / KineticBlockEntity.
 * TODO: add BlockEntity to store RPM setpoint and current draw.
 * TODO: overheat mechanic when current exceeds rated capacity.
 */
public class MotorBlock extends Block {

    public static final DirectionProperty FACING  = BlockStateProperties.FACING;
    public static final BooleanProperty   POWERED = BlockStateProperties.POWERED;

    /** The power tier of this particular motor block instance. */
    private final MotorSize size;

    public MotorBlock(MotorSize size, BlockBehaviour.Properties properties) {
        super(properties);
        this.size = size;
        registerDefaultState(stateDefinition.any()
                .setValue(FACING,  Direction.NORTH)
                .setValue(POWERED, false));
    }

    public MotorSize getSize() {
        return size;
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
