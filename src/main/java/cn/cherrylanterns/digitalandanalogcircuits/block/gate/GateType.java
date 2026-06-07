package cn.cherrylanterns.digitalandanalogcircuits.block.gate;

import net.minecraft.util.StringRepresentable;

/**
 * Enumeration of all supported digital logic gate types.
 *
 * <p>Each value corresponds to a registered block (see {@link LogicGateBlock}).
 * The {@link #evaluate} method performs the boolean calculation for two inputs;
 * single-input gates (NOT) ignore the second argument.
 */
public enum GateType implements StringRepresentable {

    /** Output = A AND B */
    AND("and") {
        @Override public boolean evaluate(boolean a, boolean b) { return a && b; }
    },

    /** Output = A OR B */
    OR("or") {
        @Override public boolean evaluate(boolean a, boolean b) { return a || b; }
    },

    /** Output = NOT A  (single-input; B is ignored) */
    NOT("not") {
        @Override public boolean evaluate(boolean a, boolean b) { return !a; }
    },

    /** Output = A XOR B  (exclusive-or) */
    XOR("xor") {
        @Override public boolean evaluate(boolean a, boolean b) { return a ^ b; }
    },

    /** Output = A XNOR B  (exclusive-nor; true when A == B) */
    XNOR("xnor") {
        @Override public boolean evaluate(boolean a, boolean b) { return a == b; }
    };

    private final String serialName;

    GateType(String serialName) {
        this.serialName = serialName;
    }

    /** Evaluate the gate's boolean function. */
    public abstract boolean evaluate(boolean a, boolean b);

    @Override
    public String getSerializedName() {
        return serialName;
    }
}
