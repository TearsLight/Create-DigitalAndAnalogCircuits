package cn.cherrylanterns.digitalandanalogcircuits.register;

import java.util.ArrayList;
import java.util.List;

import cn.cherrylanterns.digitalandanalogcircuits.DigitalAndAnalogCircuits;
import cn.cherrylanterns.digitalandanalogcircuits.blockentity.WireBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 方块实体类型注册表
 * BlockEntity Type Registry
 */
public class DACBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DigitalAndAnalogCircuits.MOD_ID);

    /**
     * 导线方块实体 — 绑定所有绝缘导线方块和裸导线方块
     */
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WireBlockEntity>> WIRE_BE =
            BLOCK_ENTITY_TYPES.register("wire_be", () -> {
                List<Block> validBlocks = new ArrayList<>();

                // 16 色绝缘导线
                validBlocks.add(DACBlocks.WHITE_WIRE.get());
                validBlocks.add(DACBlocks.ORANGE_WIRE.get());
                validBlocks.add(DACBlocks.MAGENTA_WIRE.get());
                validBlocks.add(DACBlocks.LIGHT_BLUE_WIRE.get());
                validBlocks.add(DACBlocks.YELLOW_WIRE.get());
                validBlocks.add(DACBlocks.LIME_WIRE.get());
                validBlocks.add(DACBlocks.PINK_WIRE.get());
                validBlocks.add(DACBlocks.GRAY_WIRE.get());
                validBlocks.add(DACBlocks.LIGHT_GRAY_WIRE.get());
                validBlocks.add(DACBlocks.CYAN_WIRE.get());
                validBlocks.add(DACBlocks.PURPLE_WIRE.get());
                validBlocks.add(DACBlocks.BLUE_WIRE.get());
                validBlocks.add(DACBlocks.BROWN_WIRE.get());
                validBlocks.add(DACBlocks.GREEN_WIRE.get());
                validBlocks.add(DACBlocks.RED_WIRE.get());
                validBlocks.add(DACBlocks.BLACK_WIRE.get());

                // 5 种裸导线
                validBlocks.add(DACBlocks.BARE_COPPER_WIRE.get());
                validBlocks.add(DACBlocks.BARE_IRON_WIRE.get());
                validBlocks.add(DACBlocks.BARE_SILVER_WIRE.get());
                validBlocks.add(DACBlocks.BARE_GOLD_WIRE.get());
                validBlocks.add(DACBlocks.BARE_ALUMINUM_WIRE.get());

                return BlockEntityType.Builder.of(
                        WireBlockEntity::new,
                        validBlocks.toArray(new Block[0]))
                        .build(null);
            });
}
