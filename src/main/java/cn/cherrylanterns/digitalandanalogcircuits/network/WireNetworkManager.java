package cn.cherrylanterns.digitalandanalogcircuits.network;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/**
 * 导线网络管理器 — World 级别单例
 * WireNetworkManager — manages all wire networks in a world.
 * <p>
 * Each Level has its own manager. Networks are keyed by Long ID.
 * When a wire is placed or broken, the affected network is marked dirty
 * and rediscovered on the next tick.
 */
public class WireNetworkManager {

    /** 每个世界一个管理器 */
    private static final Map<LevelAccessor, WireNetworkManager> MANAGERS = new HashMap<>();

    private final Map<Long, WireNetwork> networks = new HashMap<>();
    private long nextNetworkId = 1;

    private WireNetworkManager() {}

    /**
     * 获取指定世界的网络管理器
     */
    public static WireNetworkManager get(LevelAccessor level) {
        return MANAGERS.computeIfAbsent(level, k -> new WireNetworkManager());
    }

    /**
     * 清理世界数据（世界卸载时调用）
     */
    public static void unload(LevelAccessor level) {
        MANAGERS.remove(level);
    }

    // ===== 网络管理 =====

    /**
     * 创建新网络（导线放置时调用）
     */
    public WireNetwork createNetwork(Level level, BlockPos pos) {
        long id = nextNetworkId++;
        WireNetwork network = WireNetwork.discover(level, pos, id);
        if (network != null) {
            networks.put(id, network);
            return network;
        }
        return null;
    }

    /**
     * 重新发现网络（导线破坏或邻居变化时调用）
     */
    public WireNetwork rediscover(Level level, BlockPos pos) {
        // 查找包含此位置的网络
        WireNetwork oldNetwork = findNetworkAt(pos);
        if (oldNetwork != null) {
            networks.remove(oldNetwork.getNetworkId());
        }
        // 重新发现
        return createNetwork(level, pos);
    }

    /**
     * 移除网络
     */
    public void removeNetwork(long networkId) {
        networks.remove(networkId);
    }

    /**
     * 查找包含指定位置的网络
     */
    public WireNetwork findNetworkAt(BlockPos pos) {
        for (WireNetwork network : networks.values()) {
            if (network.getWirePositions().contains(pos)
                    || network.getAttachedDevices().contains(pos)) {
                return network;
            }
        }
        return null;
    }

    /**
     * 获取指定 ID 的网络
     */
    public WireNetwork getNetwork(long networkId) {
        return networks.get(networkId);
    }

    /**
     * 对世界中的所有网络进行能量分配 tick
     */
    public void tickAll(Level level) {
        for (WireNetwork network : networks.values()) {
            network.distributeEnergy(level);
        }
    }

    /**
     * 获取网络总数（调试用）
     */
    public int getNetworkCount() {
        return networks.size();
    }
}
