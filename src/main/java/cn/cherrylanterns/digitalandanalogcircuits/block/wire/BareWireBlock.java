package cn.cherrylanterns.digitalandanalogcircuits.block.wire;

import java.util.Map;

import cn.cherrylanterns.digitalandanalogcircuits.Config;
import cn.cherrylanterns.digitalandanalogcircuits.blockentity.WireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 裸导线方块（无绝缘层）
 * Bare Wire Block - electrically dangerous, causes shock damage on contact.
 * <p>
 * Bare wires connect to other bare wires regardless of material.
 * Standing on an energized bare wire deals damage based on voltage level.
 */
public class BareWireBlock extends Block implements EntityBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public static final Map<Direction, BooleanProperty> PROPERTY_MAP = Map.of(
            Direction.NORTH, NORTH,
            Direction.EAST, EAST,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST,
            Direction.UP, UP,
            Direction.DOWN, DOWN
    );

    private static final VoxelShape CORE_SHAPE = Block.box(5, 5, 5, 11, 11, 11);
    private static final VoxelShape NORTH_SHAPE = Block.box(5, 5, 0, 11, 11, 5);
    private static final VoxelShape SOUTH_SHAPE = Block.box(5, 5, 11, 11, 11, 16);
    private static final VoxelShape EAST_SHAPE = Block.box(11, 5, 5, 16, 11, 11);
    private static final VoxelShape WEST_SHAPE = Block.box(0, 5, 5, 5, 11, 11);
    private static final VoxelShape UP_SHAPE = Block.box(5, 11, 5, 11, 16, 11);
    private static final VoxelShape DOWN_SHAPE = Block.box(5, 0, 5, 11, 5, 11);

    public BareWireBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    // ===== BlockState =====

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState();
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);
            state = state.setValue(PROPERTY_MAP.get(dir),
                    canConnectTo(neighborState, level, pos, dir));
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction,
                                   BlockState neighborState, LevelAccessor level,
                                   BlockPos pos, BlockPos neighborPos) {
        return state.setValue(PROPERTY_MAP.get(direction),
                canConnectTo(neighborState, level, pos, direction));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                 Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            BlockState newState = state;
            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                BlockState neighborState = level.getBlockState(neighborPos);
                newState = newState.setValue(PROPERTY_MAP.get(dir),
                        canConnectTo(neighborState, level, pos, dir));
            }
            if (newState != state) {
                level.setBlock(pos, newState, Block.UPDATE_ALL);
            }
        }
    }

    /**
     * 裸导线连接规则：
     * - 连接到其他裸导线
     * - 连接到有能量能力的 BlockEntity
     * - 不连接到绝缘导线（绝缘导线有颜色隔离，裸导线不能直连绝缘导线）
     */
    protected boolean canConnectTo(BlockState neighborState, LevelAccessor level,
                                    BlockPos myPos, Direction direction) {
        // 裸导线互联
        if (neighborState.getBlock() instanceof BareWireBlock) {
            return true;
        }
        // 不连接绝缘导线
        if (neighborState.getBlock() instanceof WireBlock) {
            return false;
        }
        // 连接到能量设备
        BlockEntity be = level.getBlockEntity(myPos.relative(direction));
        return be != null && be != level.getBlockEntity(myPos);
    }

    // ===== 碰撞形状 =====

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos,
                                CollisionContext context) {
        VoxelShape shape = CORE_SHAPE;
        if (state.getValue(NORTH)) shape = Shapes.or(shape, NORTH_SHAPE);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, SOUTH_SHAPE);
        if (state.getValue(EAST)) shape = Shapes.or(shape, EAST_SHAPE);
        if (state.getValue(WEST)) shape = Shapes.or(shape, WEST_SHAPE);
        if (state.getValue(UP)) shape = Shapes.or(shape, UP_SHAPE);
        if (state.getValue(DOWN)) shape = Shapes.or(shape, DOWN_SHAPE);
        return shape;
    }

    // ===== 电击伤害 =====

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide && Config.enableElectricDamage && entity instanceof LivingEntity) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof WireBlockEntity wireBe) {
                int voltage = wireBe.getVoltage();
                if (voltage >= Config.electricDamageThreshold) {
                    float damage = calculateShockDamage(voltage);
                    entity.hurt(level.damageSources().lightningBolt(), damage);

                    // 高压附加缓慢效果
                    if (voltage >= 144) {
                        if (entity instanceof LivingEntity living) {
                            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN,
                                    100, 0));
                        }
                    }
                }
            }
        }
        super.stepOn(level, pos, state, entity);
    }

    /**
     * 根据电压计算电击伤害
     * 36V~72V:   1 点伤害/秒
     * 72V~144V:  2 点伤害/秒
     * 144V~288V: 4 点伤害 + 缓慢
     * 288V+:     即死（20 点伤害）
     */
    private float calculateShockDamage(int voltage) {
        if (voltage >= 288) return 20.0f;   // 即死
        if (voltage >= 144) return 4.0f;    // 高伤害
        if (voltage >= 72) return 2.0f;     // 中等伤害
        return 1.0f;                         // 低伤害
    }

    // ===== BlockEntity =====

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WireBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                    BlockEntityType<T> type) {
        if (level.isClientSide) return null;
        return (BlockEntityTicker<T>) (BlockEntityTicker<WireBlockEntity>)
                (lvl, pos, st, be) -> be.serverTick(lvl, pos, st);
    }
}
