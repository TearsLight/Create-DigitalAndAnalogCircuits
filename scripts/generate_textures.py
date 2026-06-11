"""Generate wire textures for all metals and insulated colors."""
from PIL import Image, ImageDraw, ImageFilter
import os, math, random

TEX_DIR = "src/main/resources/assets/digitalandanalogcircuits/textures/block"

# ── Metal colors (RGB) ──────────────────────────────────
METALS = {
    "bare_copper_wire":   (187, 121, 7),    # Copper orange-brown
    "bare_iron_wire":     (130, 130, 130),   # Iron grey
    "bare_silver_wire":   (192, 192, 200),   # Silver bright
    "bare_gold_wire":     (222, 177, 44),    # Gold yellow
    "bare_aluminum_wire": (168, 184, 200),   # Aluminum light blue-grey
}

# ── 16 Minecraft dye colors (RGB) ────────────────────────
DYE_COLORS = {
    "white":       (240, 240, 240),
    "orange":      (249, 128, 29),
    "magenta":     (199, 78, 189),
    "light_blue":  (58, 179, 218),
    "yellow":      (254, 216, 61),
    "lime":        (128, 199, 31),
    "pink":        (243, 139, 170),
    "gray":        (71, 79, 82),
    "light_gray":  (157, 157, 151),
    "cyan":        (22, 156, 156),
    "purple":      (137, 50, 184),
    "blue":        (60, 68, 170),
    "brown":       (131, 84, 50),
    "green":       (94, 124, 22),
    "red":         (176, 46, 38),
    "black":       (20, 20, 20),
}


def metallic_variant(base, x, y, seed=0):
    """Add subtle metallic grain variation."""
    rng = random.Random(hash((x, y, seed)))
    noise = rng.randint(-12, 12)
    return tuple(max(0, min(255, c + noise)) for c in base)


def create_bare_wire_texture(base_color, path, size=32):
    """Create a bare wire texture with metallic strand pattern.

    UV layout on 32x32 texture:
      - Core faces: UV [0-9, 0-9] -> pixels [0-18, 0-18] (top-left corner)
      - Arm faces:  UV [0-16]     -> pixels [0-32]       (full texture)

    Design: horizontal wire strands across the full width,
    with the core region showing a tighter zoom of the same pattern.
    """
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    random.seed(hash(base_color))

    # ── Wire strand pattern (horizontal lines) ──
    strand_color = tuple(max(0, c - 30) for c in base_color)
    highlight_color = tuple(min(255, c + 40) for c in base_color)
    shadow_color = tuple(max(0, c - 50) for c in base_color)

    for y in range(size):
        # Base metallic fill with grain
        for x in range(size):
            px = metallic_variant(base_color, x, y)
            draw.point((x, y), px + (255,))

    # Wire strand horizontal lines
    strand_positions = []
    for i in range(6):
        pos = 3 + i * 5 + random.randint(-1, 1)
        strand_positions.append(pos)

    for sp in strand_positions:
        for x in range(size):
            # Dark line for strand separation
            c = shadow_color + (200,)
            draw.point((x, max(0, min(size - 1, sp))), c)
        # Highlight just below the dark line
        for x in range(size):
            c = highlight_color + (120,)
            draw.point((x, max(0, min(size - 1, sp + 1))), c)

    # ── Arm-specific detail (center-right area, UV 5-16) ──
    # Add some cross-hatching detail
    for y in range(5, size - 5):
        for x in range(10, size - 5):
            if (x + y) % 7 == 0:
                c = shadow_color + (80,)
                draw.point((x, y), c)

    # ── Subtle vignette on core region ──
    for y in range(0, 18):
        for x in range(0, 18):
            dist = math.sqrt((x - 9) ** 2 + (y - 9) ** 2)
            if dist > 7:
                factor = min(1.0, (dist - 7) / 4) * 0.15
                px = img.getpixel((x, y))
                darkened = tuple(int(c * (1 - factor)) for c in px[:3]) + (px[3],)
                draw.point((x, y), darkened)

    img.save(path)
    print(f"  Created: {path}")


def create_insulated_wire_texture(base_color, path, size=32):
    """Create an insulated wire texture.

    UV layout (old wire_core model, 0-16 range):
      - Core faces: UV [5,5,11,11] -> center 6x6 region
      - Arm faces: various regions across full texture

    Design: colored insulation with copper strand visible at edges.
    """
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    random.seed(hash(base_color))

    copper = (187, 121, 7)
    ins_dark = tuple(max(0, c - 35) for c in base_color)
    ins_light = tuple(min(255, c + 35) for c in base_color)

    for y in range(size):
        for x in range(size):
            px = metallic_variant(base_color, x, y, seed=42)
            draw.point((x, y), px + (255,))

    # Insulation banding (vertical stripes for easy visual ID)
    for i in range(0, size, 4):
        for y in range(size):
            c = ins_dark + (150,)
            draw.point((i, y), c)

    # Copper core visible in center region (UV 5-11 -> pixels 10-22 on 32x32)
    for y in range(10, 22):
        for x in range(10, 22):
            px = metallic_variant(copper, x, y, seed=99)
            draw.point((x, y), px + (255,))

    # Gap between insulation and core
    for y in range(9, 23):
        for x in (9, 22):
            draw.point((x, y), (40, 40, 40, 200))
    for x in range(9, 23):
        for y in (9, 22):
            draw.point((x, y), (40, 40, 40, 200))

    # Highlight edge of insulation
    for y in range(10, 22):
        draw.point((10, y), ins_light + (180,))

    img.save(path)
    print(f"  Created: {path}")


# ═══════════════════════════════════════════════════════
#  MAIN
# ═══════════════════════════════════════════════════════
os.makedirs(TEX_DIR, exist_ok=True)

print("\n── Bare Metal Wires ──")
for name, color in METALS.items():
    path = os.path.join(TEX_DIR, f"{name}.png")
    if os.path.exists(path):
        print(f"  SKIP (exists): {path}")
        continue
    create_bare_wire_texture(color, path)

print("\n── Insulated Wires ──")
for name, color in DYE_COLORS.items():
    path = os.path.join(TEX_DIR, f"{name}_wire.png")
    if os.path.exists(path):
        print(f"  SKIP (exists): {path}")
        continue
    create_insulated_wire_texture(color, path)

print("\nDone!")
