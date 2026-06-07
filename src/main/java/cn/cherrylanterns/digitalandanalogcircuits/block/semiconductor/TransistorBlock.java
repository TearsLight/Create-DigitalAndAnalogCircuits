package cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor;

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
 * NPN Bipolar Junction Transistor (BJT).
 *
 * <p>Three terminals:
 * <ul>
 *   <li><b>Collector (C)</b> – high-voltage supply; faces {@code facing} direction</li>
 *   <li><b>Emitter  (E)</b> – output / ground side; faces {@code facing.getOpposite()}</li>
 *   <li><b>Base    (B)</b> – control signal; faces the side perpendicular to FACING
 *       (determined by the horizontal rotation at placement)</li>
 * </ul>
 *
 * <p>Operating modes (simplified):
 * <ul>
 *   <li><b>Cut-off</b>  – base signal = 0, C-E path open (no current)</li>
 *   <li><b>Saturation</b> – base signal ≥ threshold, C-E path closed (full current)</li>
 *   <li><b>Active</b>   – analogue mode: Ic = β × Ib (future feature)</li>
 * </ul>
 *
 * <p>BlockState: {@code facing} = collector side; {@code powered} = saturated.
 *
 * <p>TODO: analogue gain (β factor) configurable via GUI.
 * TODO: base-terminal direction needs a second BlockState property.
 */
public class TransistorBlock extends Block {

    /** Collector output direction. */
    public static final DirectionProperty FACING  = BlockStateProperties.FACING;
    /** True when the transistor is in saturation (base threshold met). */
    public static final BooleanProperty   POWERED = BlockStateProperties.POWERED;

    public TransistorBlock(BlockBehaviour.Properties properties) {
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
                .setValue(FACING,  context.getNearestLookingDirection().getOpposite())
                .setValue(POWERED, false);
    }
}
