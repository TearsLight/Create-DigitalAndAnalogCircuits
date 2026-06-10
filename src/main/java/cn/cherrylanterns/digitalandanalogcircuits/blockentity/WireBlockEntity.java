package cn.cherrylanterns.digitalandanalogcircuits.blockentity;

import cn.cherrylanterns.digitalandanalogcircuits.Config;
import cn.cherrylanterns.digitalandanalogcircuits.block.wire.BareWireBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.wire.WireBlock;
import cn.cherrylanterns.digitalandanalogcircuits.register.DACBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * 导线方块实体
 * Wire BlockEntity - stores energy and propagates it through the wire network.
 */
public class WireBlockEntity extends BlockEntity {

    /** 内部能量存储 — 导线作为传输介质，自身缓存少量能量 */
    private final EnergyStorage energyStorage = new EnergyStorage(1000, 100, 100, 0);

    /** 当前电压 (mV) */
    private int voltage = 0;

    /** 上次 tick 计数，用于控制传输频率 */
    private long lastTransferTick = 0;

    public WireBlockEntity(BlockPos pos, BlockState state) {
        super(DACBlockEntities.WIRE_BE.get(), pos, state);
    }

    // ===== 能量存储访问 =====

    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
        setChanged();
    }

    // ===== 服务端 Tick =====

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        long gameTime = level.getGameTime();
        if (gameTime - lastTransferTick < Config.wireNetworkTickInterval) {
            return;
        }
        lastTransferTick = gameTime;

        // 向所有连接的相邻方块传输能量
        for (Direction dir : Direction.values()) {
            if (isConnectedInDirection(state, dir)) {
                transferEnergyTo(level, pos, dir);
            }
        }
    }

    /**
     * 检查 BlockState 中该方向是否有连接
     */
    private boolean isConnectedInDirection(BlockState state, Direction dir) {
        if (state.getBlock() instanceof WireBlock) {
            BooleanProperty prop = WireBlock.PROPERTY_MAP.get(dir);
            return state.getValue(prop);
        }
        if (state.getBlock() instanceof BareWireBlock) {
            BooleanProperty prop = BareWireBlock.PROPERTY_MAP.get(dir);
            return state.getValue(prop);
        }
        return false;
    }

    /**
     * 向指定方向的相邻方块传输能量
     */
    private void transferEnergyTo(Level level, BlockPos pos, Direction dir) {
        BlockPos targetPos = pos.relative(dir);
        BlockEntity targetBe = level.getBlockEntity(targetPos);

        // 向相邻导线 BlockEntity 传输
        if (targetBe instanceof WireBlockEntity targetWire) {
            int available = energyStorage.getEnergyStored();
            if (available <= 0) return;

            // 均衡分配：发送方一半能量给接收方
            int half = available / 2;
            if (half <= 0) half = 1;

            int extracted = energyStorage.extractEnergy(half, false);
            if (extracted > 0) {
                int accepted = targetWire.energyStorage.receiveEnergy(extracted, false);
                // 退回未接受的能量
                if (accepted < extracted) {
                    energyStorage.receiveEnergy(extracted - accepted, false);
                }
            }
            return;
        }

        // 向有能量能力的设备传输
        IEnergyStorage targetStorage = level.getCapability(
                Capabilities.EnergyStorage.BLOCK,
                targetPos,
                dir.getOpposite());
        if (targetStorage != null && targetStorage.canReceive()) {
            int toSend = Math.min(energyStorage.getEnergyStored(),
                    Config.wireMaxTransferRate);
            if (toSend <= 0) return;

            int extracted = energyStorage.extractEnergy(toSend, true); // 模拟
            int accepted = targetStorage.receiveEnergy(extracted, false);
            if (accepted > 0) {
                energyStorage.extractEnergy(accepted, false);           // 实际提取
            }
        }
    }

    // ===== NBT =====

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("energy", energyStorage.getEnergyStored());
        tag.putInt("voltage", voltage);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("energy")) {
            // EnergyStorage 没有直接的 set 方法，通过 receive 来恢复
            int savedEnergy = tag.getInt("energy");
            energyStorage.receiveEnergy(savedEnergy, false);
        }
        if (tag.contains("voltage")) {
            voltage = tag.getInt("voltage");
        }
    }

    // ===== 客户端同步 =====

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
