package cn.cherrylanterns.digitalandanalogcircuits.block.wire;

import java.util.Map;

import cn.cherrylanterns.digitalandanalogcircuits.api.WireColor;
import cn.cherrylanterns.digitalandanalogcircuits.blockentity.WireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
 * 绝缘导线方块
 * Insulated Wire Block - supports 6-directional connections with color-based isolation.
 * <p>
 * Connections: two wires connect only if they share the same color.
 * Shape: thin 2px profile with arm extensions in connected directions.
 */
public class WireBlock extends Block implements EntityBlock {

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

    // 核心形状：2px 厚的中心点
    private static final VoxelShape CORE_SHAPE = Block.box(5, 5, 5, 11, 11, 11);
    // 每个方向的臂形
    private static final VoxelShape NORTH_SHAPE = Block.box(5, 5, 0, 11, 11, 5);
    private static final VoxelShape SOUTH_SHAPE = Block.box(5, 5, 11, 11, 11, 16);
    private static final VoxelShape EAST_SHAPE = Block.box(11, 5, 5, 16, 11, 11);
    private static final VoxelShape WEST_SHAPE = Block.box(0, 5, 5, 5, 11, 11);
    private static final VoxelShape UP_SHAPE = Block.box(5, 11, 5, 11, 16, 11);
    private static final VoxelShape DOWN_SHAPE = Block.box(5, 0, 5, 11, 5, 11);

    private final WireColor color;

    public WireBlock(Properties properties, WireColor color) {
        super(properties);
        this.color = color;
        BlockState defaultState = this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false);
        registerDefaultState(defaultState);
    }

    public WireColor getWireColor() {
        return color;
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
            state = state.setValue(PROPERTY_MAP.get(dir), canConnectTo(neighborState, level, pos, dir));
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
            // 重新计算所有方向的连接
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
     * 判断是否可以连接到相邻方块
     * <ul>
     *   <li>同色绝缘导线 → 连接</li>
     *   <li>裸导线 → 不连接（颜色隔离）</li>
     *   <li>有能量能力的 BlockEntity → 连接</li>
     * </ul>
     */
    protected boolean canConnectTo(BlockState neighborState, LevelAccessor level,
                                    BlockPos myPos, Direction direction) {
        // 同色绝缘导线互联
        if (neighborState.getBlock() instanceof WireBlock neighborWire) {
            return neighborWire.getWireColor() == this.color;
        }
        // 连接到裸导线 — 不允许（裸导线无绝缘，不应直连绝缘导线）
        // 如果需要互联，应使用连接器
        if (neighborState.getBlock() instanceof BareWireBlock) {
            return false;
        }
        // 连接到有能量能力的方块（机器、变压器、电池等）
        BlockEntity be = level.getBlockEntity(myPos.relative(direction));
        return be != null && be != level.getBlockEntity(myPos); // 不连接自己
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
