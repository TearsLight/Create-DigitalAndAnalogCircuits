package cn.cherrylanterns.digitalandanalogcircuits.block.motor;

import net.minecraft.util.StringRepresentable;

/**
 * Size / power tier of an electric motor.
 *
 * <p>Each tier maps to a different stress budget that the motor can add to
 * Create's kinetic network, and determines the physical model size.
 *
 * <table border="1">
 *   <tr><th>Tier</th><th>Stress capacity (SU)</th><th>Typical use</th></tr>
 *   <tr><td>SMALL</td>    <td>128  SU</td><td>Small gears, conveyors, fans</td></tr>
 *   <tr><td>MEDIUM</td>   <td>512  SU</td><td>Medium blades, mixers, deployers</td></tr>
 *   <tr><td>LARGE</td>    <td>2048 SU</td><td>Heavy machinery, presses</td></tr>
 *   <tr><td>EXTRA_LARGE</td><td>8192 SU</td><td>Industrial power stations</td></tr>
 * </table>
 *
 * <p>Exact SU values will be tuned via config once the Create API integration is done.
 */
public enum MotorSize implements StringRepresentable {

    SMALL      ("small",       128),
    MEDIUM     ("medium",      512),
    LARGE      ("large",      2048),
    EXTRA_LARGE("extra_large", 8192);

    private final String serialName;
    /** Stress units this motor can supply to the kinetic network. */
    public final int stressCapacity;

    MotorSize(String serialName, int stressCapacity) {
        this.serialName    = serialName;
        this.stressCapacity = stressCapacity;
    }

    @Override
    public String getSerializedName() {
        return serialName;
    }
}
