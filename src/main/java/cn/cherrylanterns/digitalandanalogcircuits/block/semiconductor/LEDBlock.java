package cn.cherrylanterns.digitalandanalogcircuits.block.semiconductor;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

/**
 * Light-Emitting Diode (LED) – emits light when forward-biased, in a colour
 * chosen by right-clicking with a {@link DyeItem}.
 *
 * <p>Like {@link DiodeBlock} it only allows current in one direction.
 * When powered in the correct direction the block emits coloured light
 * (light level 15 when on, 0 when off).
 *
 * <p>BlockState properties:
 * <ul>
 *   <li>{@code facing} – cathode / output side (same convention as DiodeBlock)</li>
 *   <li>{@code lit}    – true while forward current flows</li>
 *   <li>{@code color}  – one of the 16 Minecraft dye colours (default WHITE)</li>
 * </ul>
 *
 * <p>Interaction: right-click with any dye item to change the LED colour in-place
 * without breaking the block.
 *
 * <p>TODO: dynamic light level via LightEmitter once block-light API is stable.
 * TODO: tint the block model texture by colour (using block colour provider).
 */
public class LEDBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty   LIT    = BlockStateProperties.LIT;
    public static final EnumProperty<DyeColor> COLOR =
            EnumProperty.create("color", DyeColor.class);

    public LEDBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT,    false)
                .setValue(COLOR,  DyeColor.WHITE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, COLOR);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getNearestLookingDirection())
                .setValue(LIT,    false)
                .setValue(COLOR,  DyeColor.WHITE);
    }

    /**
     * Right-clicking with a dye changes the LED colour without breaking the block.
     */
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                             Player player, BlockHitResult hit) {
        if (!level.isClientSide && player.getMainHandItem().getItem() instanceof DyeItem dyeItem) {
            DyeColor newColor = dyeItem.getDyeColor();
            if (state.getValue(COLOR) != newColor) {
                level.setBlockAndUpdate(pos, state.setValue(COLOR, newColor));
                if (!player.isCreative()) {
                    player.getMainHandItem().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /** Light level: 15 when lit, 0 when off. */
    @Override
    public int getLightEmission(BlockState state, net.minecraft.world.level.BlockGetter level,
                                BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }
}
