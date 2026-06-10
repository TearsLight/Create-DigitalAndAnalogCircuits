package cn.cherrylanterns.digitalandanalogcircuits.api;

/**
 * 导线颜色枚举
 * Wire Color - 16 standard Minecraft dye colors for insulated wires
 */
public enum WireColor {
    WHITE(0, 0xF0F0F0, "white"),
    ORANGE(1, 0xFFB347, "orange"),
    MAGENTA(2, 0xFF77FF, "magenta"),
    LIGHT_BLUE(3, 0x77DDFF, "light_blue"),
    YELLOW(4, 0xFFFF00, "yellow"),
    LIME(5, 0x00FF00, "lime"),
    PINK(6, 0xFF99DD, "pink"),
    GRAY(7, 0x888888, "gray"),
    LIGHT_GRAY(8, 0xCCCCCC, "light_gray"),
    CYAN(9, 0x00FFFF, "cyan"),
    PURPLE(10, 0xBB00FF, "purple"),
    BLUE(11, 0x0000FF, "blue"),
    BROWN(12, 0x8B4513, "brown"),
    GREEN(13, 0x00AA00, "green"),
    RED(14, 0xFF0000, "red"),
    BLACK(15, 0x000000, "black");

    private final int index;
    private final int rgb;
    private final String name;

    WireColor(int index, int rgb, String name) {
        this.index = index;
        this.rgb = rgb;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public int getRgb() {
        return rgb;
    }

    public String getName() {
        return name;
    }

    /**
     * 获取 RGB 颜色中红色分量 (0-1)
     */
    public float getRed() {
        return ((rgb >> 16) & 0xFF) / 255.0f;
    }

    /**
     * 获取 RGB 颜色中绿色分量 (0-1)
     */
    public float getGreen() {
        return ((rgb >> 8) & 0xFF) / 255.0f;
    }

    /**
     * 获取 RGB 颜色中蓝色分量 (0-1)
     */
    public float getBlue() {
        return (rgb & 0xFF) / 255.0f;
    }
}
