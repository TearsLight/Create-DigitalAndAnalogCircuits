"""Generate ore block + raw material + ingot textures."""
from PIL import Image, ImageDraw, ImageFilter
import os, random, math

TEX_BLOCK = "src/main/resources/assets/digitalandanalogcircuits/textures/block"
TEX_ITEM = "src/main/resources/assets/digitalandanalogcircuits/textures/item"
os.makedirs(TEX_BLOCK, exist_ok=True)
os.makedirs(TEX_ITEM, exist_ok=True)


def noise_pixel(base, x, y, rng, spread=20):
    """Add random noise to a base color."""
    return tuple(max(0, min(255, c + rng.randint(-spread, spread))) for c in base)


def create_ore_texture(ore_color, path, size=16, density=35, base_name="stone"):
    """Create an ore texture: stone/deepslate base with colored ore specks.

    ore_color: (R, G, B) of the ore mineral
    density: approx number of ore specks
    base_name: 'stone' or 'deepslate'
    """
    rng = random.Random(hash(path))

    # Base stone colors
    if base_name == "stone":
        base = (120, 120, 120)
    else:  # deepslate
        base = (70, 70, 75)

    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Fill with stone base + noise
    for y in range(size):
        for x in range(size):
            px = noise_pixel(base, x, y, rng, 8)
            draw.point((x, y), px + (255,))

    # Add ore specks
    for _ in range(density):
        cx = rng.randint(1, size - 2)
        cy = rng.randint(1, size - 2)
        radius = rng.randint(1, 2)

        for dy in range(-radius, radius + 1):
            for dx in range(-radius, radius + 1):
                if dx * dx + dy * dy <= radius * radius:
                    px_x = max(0, min(size - 1, cx + dx))
                    px_y = max(0, min(size - 1, cy + dy))
                    speck_color = noise_pixel(ore_color, px_x, px_y, rng, 15)
                    # Blend with existing pixel
                    existing = img.getpixel((px_x, px_y))
                    blend = tuple((speck_color[i] * 2 + existing[i]) // 3 for i in range(3))
                    draw.point((px_x, px_y), blend + (255,))

    img.save(path)
    print(f"  Created: {path}")


def create_raw_material_texture(color, path, size=16):
    """Create a raw material item texture (chunk style)."""
    rng = random.Random(hash(path))
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    dark = tuple(max(0, c - 40) for c in color)
    light = tuple(min(255, c + 50) for c in color)

    # Irregular blob shape in center
    cx, cy = size // 2, size // 2
    for y in range(2, size - 2):
        for x in range(2, size - 2):
            dist = math.sqrt((x - cx) ** 2 + (y - cy) ** 2)
            jitter = rng.randint(-1, 1)
            if dist < size // 3 + jitter:
                px = noise_pixel(color, x, y, rng, 20)
                draw.point((x, y), px + (255,))

    # Highlight edge
    for y in range(2, size - 2):
        for x in range(2, size - 2):
            dist = math.sqrt((x - cx) ** 2 + (y - cy) ** 2)
            if size // 3 - 1 <= dist <= size // 3 + 2:
                existing = img.getpixel((x, y))
                if existing[3] > 0:
                    hl = tuple(min(255, existing[i] + 25) for i in range(3))
                    draw.point((x, y), hl + (255,))

    img.save(path)
    print(f"  Created: {path}")


def create_ingot_texture(color, path, size=16):
    """Create an ingot item texture (bar shape)."""
    rng = random.Random(hash(path))
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    light = tuple(min(255, c + 60) for c in color)
    dark = tuple(max(0, c - 50) for c in color)

    # Ingot shape: horizontal bar
    bar_top = 3
    bar_bottom = size - 4
    bar_left = 1
    bar_right = size - 2

    for y in range(bar_top, bar_bottom):
        for x in range(bar_left, bar_right):
            # Tapered ends
            if (y <= bar_top + 1 or y >= bar_bottom - 1) and (x <= 3 or x >= size - 4):
                continue
            px = noise_pixel(color, x, y, rng, 12)
            draw.point((x, y), px + (255,))

    # Shading: darker edges
    for y in range(bar_top, bar_bottom):
        for x in [bar_left, bar_left + 1, bar_right - 2, bar_right - 1]:
            existing = img.getpixel((x, y))
            if existing[3] > 0:
                sd = tuple(max(0, existing[i] - 30) for i in range(3))
                draw.point((x, y), sd + (255,))

    # Highlight top edge
    for x in range(bar_left + 1, bar_right - 1):
        existing = img.getpixel((x, bar_top + 1))
        if existing[3] > 0:
            hl = tuple(min(255, existing[i] + 30) for i in range(3))
            draw.point((x, bar_top + 1), hl + (255,))

    img.save(path)
    print(f"  Created: {path}")


# ═══════════════════════════════════════════════════════
#  MAIN
# ═══════════════════════════════════════════════════════

# Ore colors
ALUMINUM_ORE_COLOR = (200, 195, 185)   # whitish-grey metallic
LITHIUM_ORE_COLOR = (210, 160, 200)    # pinkish-purple

# Ingot / raw colors
RAW_ALUMINUM = (170, 165, 155)
RAW_LITHIUM = (180, 140, 170)
ALUMINUM_INGOT = (210, 210, 215)       # silver-white
LITHIUM_INGOT = (190, 180, 195)        # grey-silver

print("── Ore Blocks ──")
create_ore_texture(ALUMINUM_ORE_COLOR, os.path.join(TEX_BLOCK, "aluminum_ore.png"), base_name="stone")
create_ore_texture(ALUMINUM_ORE_COLOR, os.path.join(TEX_BLOCK, "deepslate_aluminum_ore.png"), base_name="deepslate")
create_ore_texture(LITHIUM_ORE_COLOR, os.path.join(TEX_BLOCK, "lithium_ore.png"), base_name="stone")
create_ore_texture(LITHIUM_ORE_COLOR, os.path.join(TEX_BLOCK, "deepslate_lithium_ore.png"), base_name="deepslate")

print("\n── Raw Materials ──")
create_raw_material_texture(RAW_ALUMINUM, os.path.join(TEX_ITEM, "raw_aluminum.png"))
create_raw_material_texture(RAW_LITHIUM, os.path.join(TEX_ITEM, "raw_lithium.png"))

print("\n── Ingots ──")
create_ingot_texture(ALUMINUM_INGOT, os.path.join(TEX_ITEM, "aluminum_ingot.png"))
create_ingot_texture(LITHIUM_INGOT, os.path.join(TEX_ITEM, "lithium_ingot.png"))

print("\nDone!")
