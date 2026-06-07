package cn.cherrylanterns.digitalandanalogcircuits.block.generator;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Two-phase (single-phase split) AC alternator.
 *
 * <p>Outputs two wire ports carrying signals 180 ° out of phase with each other.
 * Suitable for small appliances, lighting circuits, and simple motor drives.
 *
 * <p>Phase topology:
 * <pre>
 *   Phase A ──┤  [GENERATOR]  ├── Phase B  (180° offset)
 * </pre>
 *
 * <p>TODO: implement two-port wire attachment and phase-offset signal generation.
 */
public class TwoPhaseAlternatorBlock extends GeneratorBlock {

    public TwoPhaseAlternatorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
