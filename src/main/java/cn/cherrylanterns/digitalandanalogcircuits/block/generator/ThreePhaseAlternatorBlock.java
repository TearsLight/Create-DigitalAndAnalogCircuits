package cn.cherrylanterns.digitalandanalogcircuits.block.generator;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Three-phase AC alternator.
 *
 * <p>Outputs three wire ports carrying signals 120 ° apart.
 * Required for large motors, industrial machinery, and high-efficiency power transmission.
 *
 * <p>Phase topology:
 * <pre>
 *   Phase A ──┐
 *   Phase B ──┤  [GENERATOR]
 *   Phase C ──┘
 *   (each 120° apart)
 * </pre>
 *
 * <p>TODO: implement three-port wire attachment on the three side faces.
 * TODO: neutral line support (optional fourth port).
 */
public class ThreePhaseAlternatorBlock extends GeneratorBlock {

    public ThreePhaseAlternatorBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
