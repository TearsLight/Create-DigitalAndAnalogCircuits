package cn.cherrylanterns.digitalandanalogcircuits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.cherrylanterns.digitalandanalogcircuits.register.DACBlockEntities;
import cn.cherrylanterns.digitalandanalogcircuits.register.DACBlocks;
import cn.cherrylanterns.digitalandanalogcircuits.register.DACCreativeTab;
import cn.cherrylanterns.digitalandanalogcircuits.register.DACItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * 数字与模拟电路模组主类
 * Create: Digital and Analog Circuits Mod Main Class
 */
@Mod(DigitalAndAnalogCircuits.MOD_ID)
public class DigitalAndAnalogCircuits {

    public static final String MOD_ID = "digitalandanalogcircuits";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public DigitalAndAnalogCircuits(IEventBus modEventBus, ModContainer modContainer) {
        // 注册方块
        DACBlocks.BLOCKS.register(modEventBus);

        // 注册物品
        DACItems.ITEMS.register(modEventBus);

        // 注册 BlockEntity
        DACBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);

        // 注册物品栏标签页
        DACCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        // 注册能量能力
        modEventBus.addListener(this::registerCapabilities);

        // 通用设置事件
        modEventBus.addListener(this::commonSetup);

        // 客户端设置事件
        modEventBus.addListener(this::clientSetup);

        // 注册配置
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("Create: Digital and Analog Circuits 模组已初始化");
    }

    /**
     * 注册方块实体的能量能力
     */
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                DACBlockEntities.WIRE_BE.get(),
                (be, side) -> be.getEnergyStorage());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("通用设置阶段完成");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info("客户端设置阶段完成");
    }
}
