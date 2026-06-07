package cn.cherrylanterns.digitalandanalogcircuits.block.gate;

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
 * Digital logic gate block.
 *
 * <p>Each gate type (AND / OR / NOT / XOR / XNOR) is registered as a separate
 * block instance sharing this class, distinguished by the {@link GateType} field.
 *
 * <p>Terminal layout (looking from above, {@code facing} = NORTH):
 * <pre>
 *            [Output]  ← facing side
 *               │
 *    ┌──────────┴──────────┐
 *    │      LOGIC GATE     │
 *    └──────┬──────────┬───┘
 *       [Input A]  [Input B]  ← opposite + side faces
 * </pre>
 *
 * <p>For NOT gates Input B is unused. Inputs are sampled from adjacent wire networks
 * on the two rear faces; the output drives the wire on the front face.
 *
 * <p>BlockState properties:
 * <ul>
 *   <li>{@code facing} – direction of the output port</li>
 *   <li>{@code powered} – current output value (true = HIGH, false = LOW)</li>
 * </ul>
 *
 * <p>TODO: implement wire-signal sampling in a BlockEntity tick.
 * TODO: propagation delay mechanic (1 game tick per gate by default).
 */
public class LogicGateBlock extends Block {

    public static final DirectionProperty FACING  = BlockStateProperties.FACING;
    public static final BooleanProperty   POWERED = BlockStateProperties.POWERED;

    private final GateType gateType;

    public LogicGateBlock(GateType gateType, BlockBehaviour.Properties properties) {
        super(properties);
        this.gateType = gateType;
        registerDefaultState(stateDefinition.any()
                .setValue(FACING,  Direction.NORTH)
                .setValue(POWERED, false));
    }

    public GateType getGateType() {
        return gateType;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING,  context.getNearestLookingDirection().getOpposite())
                .setValue(POWERED, false);
    }
}
