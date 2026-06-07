package cn.cherrylanterns.digitalandanalogcircuits.block.power;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Step-Down Transformer – decreases voltage from the primary wire to the secondary wire.
 *
 * <p>Use case: reduce the high transmission-line voltage back down to a safe level
 * before delivering power to motors, logic gates, or other load blocks.
 *
 * <p>Example ratio: 10:1 → 1200 V in, 120 V out (values are game-defined).
 *
 * <p>TODO: configurable turn ratio via GUI.
 */
public class StepDownTransformerBlock extends TransformerBlock {

    public StepDownTransformerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
