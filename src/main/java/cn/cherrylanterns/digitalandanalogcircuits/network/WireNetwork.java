package cn.cherrylanterns.digitalandanalogcircuits.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cn.cherrylanterns.digitalandanalogcircuits.Config;
import cn.cherrylanterns.digitalandanalogcircuits.block.wire.BareWireBlock;
import cn.cherrylanterns.digitalandanalogcircuits.block.wire.WireBlock;
import cn.cherrylanterns.digitalandanalogcircuits.blockentity.WireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * 导线网络
 * WireNetwork — represents a connected graph of same-color wire segments.
 * <p>
 * Uses BFS to discover all connected wire blocks from a starting position.
 * Distributes energy evenly across all attached energy-capable devices.
 */
public class WireNetwork {

    private final long networkId;
    private final Set<BlockPos> wirePositions = new HashSet<>();
    private final Set<BlockPos> attachedDevices = new HashSet<>();
    private boolean dirty = false;

    public WireNetwork(long networkId) {
        this.networkId = networkId;
    }

    public long getNetworkId() {
        return networkId;
    }

    public Set<BlockPos> getWirePositions() {
        return wirePositions;
    }

    public Set<BlockPos> getAttachedDevices() {
        return attachedDevices;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void markDirty() {
        this.dirty = true;
    }

    // ===== BFS 网络发现 =====

    /**
     * 从起始位置执行 BFS，发现完整的导线网络
     * @param level 世界
     * @param start 起始位置
     * @param networkId 网络 ID
     * @return 发现的网络，如果起始位置非导线则返回 null
     */
    public static WireNetwork discover(Level level, BlockPos start, long networkId) {
        BlockState startState = level.getBlockState(start);
        if (!(startState.getBlock() instanceof WireBlock)
                && !(startState.getBlock() instanceof BareWireBlock)) {
            return null;
        }

        WireNetwork network = new WireNetwork(networkId);
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            if (visited.contains(pos)) continue;
            visited.add(pos);

            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof WireBlock wireBlock) {
                network.wirePositions.add(pos);
                // 检查所有连接方向
                for (Direction dir : Direction.values()) {
                    if (state.getValue(WireBlock.PROPERTY_MAP.get(dir))) {
                        BlockPos neighborPos = pos.relative(dir);
                        if (!visited.contains(neighborPos)) {
                            queue.add(neighborPos);
                        }
                    }
                }
            } else if (state.getBlock() instanceof BareWireBlock) {
                network.wirePositions.add(pos);
                for (Direction dir : Direction.values()) {
                    if (state.getValue(BareWireBlock.PROPERTY_MAP.get(dir))) {
                        BlockPos neighborPos = pos.relative(dir);
                        if (!visited.contains(neighborPos)) {
                            queue.add(neighborPos);
                        }
                    }
                }
            } else {
                // 这是一个非导线方块（如机器、电池等）
                IEnergyStorage storage = level.getCapability(
                        Capabilities.EnergyStorage.BLOCK, pos, null);
                if (storage != null) {
                    network.attachedDevices.add(pos);
                }
            }
        }

        return network;
    }

    // ===== 能量分配 =====

    /**
     * 在网络中均衡分配能量
     */
    public void distributeEnergy(Level level) {
        if (attachedDevices.isEmpty() && wirePositions.size() <= 1) return;

        // 收集所有能量设备
        Map<BlockPos, IEnergyStorage> deviceStorages = new HashMap<>();
        int totalEnergy = 0;
        int totalCapacity = 0;

        for (BlockPos pos : attachedDevices) {
            IEnergyStorage storage = level.getCapability(
                    Capabilities.EnergyStorage.BLOCK, pos, null);
            if (storage != null) {
                deviceStorages.put(pos, storage);
                totalEnergy += storage.getEnergyStored();
                totalCapacity += storage.getMaxEnergyStored();
            }
        }

        // 也收集导线自身的能量
        for (BlockPos pos : wirePositions) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof WireBlockEntity wireBe) {
                IEnergyStorage storage = wireBe.getEnergyStorage();
                totalEnergy += storage.getEnergyStored();
                totalCapacity += storage.getMaxEnergyStored();
            }
        }

        if (totalCapacity == 0) return;

        // 计算平均能量水平（均衡目标）
        int avgEnergy = totalEnergy / (deviceStorages.size() + wirePositions.size());

        // 从高能量设备取出，给低能量设备
        List<EnergyTransfer> transfers = new ArrayList<>();
        for (var entry : deviceStorages.entrySet()) {
            int current = entry.getValue().getEnergyStored();
            if (current > avgEnergy) {
                int excess = current - avgEnergy;
                int toExtract = Math.min(excess, Config.wireMaxTransferRate);
                transfers.add(new EnergyTransfer(entry.getKey(), entry.getValue(), -toExtract));
            } else if (current < avgEnergy) {
                int deficit = avgEnergy - current;
                int toReceive = Math.min(deficit, Config.wireMaxTransferRate);
                transfers.add(new EnergyTransfer(entry.getKey(), entry.getValue(), toReceive));
            }
        }

        // 执行传输
        for (EnergyTransfer transfer : transfers) {
            if (transfer.amount > 0) {
                transfer.storage.receiveEnergy(transfer.amount, false);
            } else if (transfer.amount < 0) {
                transfer.storage.extractEnergy(-transfer.amount, false);
            }
        }
    }

    // ===== 内部类 =====

    private static class EnergyTransfer {
        final BlockPos pos;
        final IEnergyStorage storage;
        final int amount; // 正=接收，负=提取

        EnergyTransfer(BlockPos pos, IEnergyStorage storage, int amount) {
            this.pos = pos;
            this.storage = storage;
            this.amount = amount;
        }
    }
}
