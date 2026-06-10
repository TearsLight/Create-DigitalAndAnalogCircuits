package cn.cherrylanterns.digitalandanalogcircuits.api;

/**
 * 导线材料枚举
 * Wire Material - defines electrical properties for each metal type
 */
public enum WireMaterial {
    /** 银 - 最优导体，电阻率最低 */
    SILVER("silver", 0.0159f, 105.0f, 0xC0C0C0),
    /** 铜 - 标准导电材料 */
    COPPER("copper", 0.0172f, 100.0f, 0xB87333),
    /** 金 - 耐腐蚀，导电性良好 */
    GOLD("gold", 0.0244f, 70.5f, 0xFFD700),
    /** 铝 - 轻质，适合远距离输电 */
    ALUMINUM("aluminum", 0.0283f, 60.78f, 0xD0D0D0),
    /** 铁 - 电阻高，廉价 */
    IRON("iron", 0.0978f, 17.6f, 0xA8A8A8);

    private final String name;
    private final float resistivity;   // 电阻率 Ω·mm²/m
    private final float conductivity;  // 相对导电率 % (铜=100%)
    private final int color;           // RGB 颜色

    WireMaterial(String name, float resistivity, float conductivity, int color) {
        this.name = name;
        this.resistivity = resistivity;
        this.conductivity = conductivity;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    /** 电阻率 Ω·mm²/m */
    public float getResistivity() {
        return resistivity;
    }

    /** 相对导电率 % */
    public float getConductivity() {
        return conductivity;
    }

    /** RGB 颜色值 */
    public int getColor() {
        return color;
    }

    /**
     * 根据长度和截面积计算电阻
     * @param lengthMeters 长度（米）
     * @param crossSectionMm2 截面积（mm²）
     * @return 电阻值（Ω）
     */
    public float calculateResistance(float lengthMeters, float crossSectionMm2) {
        return (resistivity * lengthMeters) / crossSectionMm2;
    }
}
