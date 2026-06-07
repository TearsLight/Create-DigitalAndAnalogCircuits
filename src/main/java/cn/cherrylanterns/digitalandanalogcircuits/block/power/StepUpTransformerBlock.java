package cn.cherrylanterns.digitalandanalogcircuits.block.power;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Step-Up Transformer – increases voltage from the primary wire to the secondary wire.
 *
 * <p>Use case: boost the output voltage of a generator before feeding it into
 * a long-distance transmission line to reduce resistive losses.
 *
 * <p>Example ratio: 1:10 → 120 V in, 1200 V out (values are game-defined).
 *
 * <p>TODO: configurable turn ratio via GUI.
 */
public class StepUpTransformerBlock extends TransformerBlock {

    public StepUpTransformerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
