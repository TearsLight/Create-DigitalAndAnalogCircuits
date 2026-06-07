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
 * N-Channel Enhancement-Mode MOSFET.
 *
 * <p>Three terminals:
 * <ul>
 *   <li><b>Drain  (D)</b> – high-side power in; faces {@code facing} direction</li>
 *   <li><b>Source (S)</b> – low-side output; faces {@code facing.getOpposite()}</li>
 *   <li><b>Gate   (G)</b> – voltage-controlled input; perpendicular face</li>
 * </ul>
 *
 * <p>Unlike the BJT the Gate draws virtually no current.
 * The channel opens when Gate voltage ≥ threshold voltage (Vgs_th, configurable).
 *
 * <p>Key advantages over {@link TransistorBlock}:
 * <ul>
 *   <li>Much higher switching speed (suitable for PWM and digital logic)</li>
 *   <li>Voltage-controlled (not current-controlled) → easier to interface with logic gates</li>
 *   <li>Higher drain current capacity at the same block size</li>
 * </ul>
 *
 * <p>BlockState: {@code facing} = drain side; {@code powered} = channel open.
 *
 * <p>TODO: Vgs_th configurable via NBT / GUI.
 * TODO: gate-terminal direction BlockState property.
 * TODO: P-Channel variant (PChannelMOSFETBlock) – inverted logic.
 */
public class MOSFETBlock extends Block {

    /** Drain direction (high side). */
    public static final DirectionProperty FACING  = BlockStateProperties.FACING;
    /** True when the gate voltage exceeds Vgs_th and the channel is conducting. */
    public static final BooleanProperty   POWERED = BlockStateProperties.POWERED;

    public MOSFETBlock(BlockBehaviour.Properties properties) {
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
