package cn.cherrylanterns.digitalandanalogcircuits;

import cn.cherrylanterns.digitalandanalogcircuits.register.DACBlocks;
import cn.cherrylanterns.digitalandanalogcircuits.register.DACCreativeTab;
import cn.cherrylanterns.digitalandanalogcircuits.register.DACItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(DigitalAndAnalogCircuits.MOD_ID)
public class DigitalAndAnalogCircuits {

    public static final String MOD_ID = "digitalandanalogcircuits";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DigitalAndAnalogCircuits(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("[DAC] Digital and Analog Circuits mod loading...");

        DACBlocks.BLOCKS.register(modEventBus);
        DACItems.ITEMS.register(modEventBus);
        DACCreativeTab.register(modEventBus);

        LOGGER.info("[DAC] Digital and Analog Circuits mod loaded successfully!");
    }
}
