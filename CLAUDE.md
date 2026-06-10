# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Identity

**Create: Digital and Analog Circuits** — a Minecraft NeoForge mod that adds a complete electrical power system (generation → transmission → transformation → storage → consumption) and digital logic circuits to the Create mod ecosystem. All devices integrate with Create's rotational shaft system.

## Build & Run

```bash
./gradlew build          # Full build (compile + resources + jar)
./gradlew compileJava    # Compile only Java sources
./gradlew runClient      # Launch Minecraft client with the mod
./gradlew runServer      # Launch dedicated server
```

**Environment**: Java 21, NeoForge 21.1.233, Minecraft 1.21.1. Uses Parchment mappings (`2025.12.20` / `1.21.11`).

## Architecture

```
cn.cherrylanterns.digitalandanalogcircuits
├── DigitalAndAnalogCircuits.java  → @Mod entry point, MOD_ID = "digitalandanalogcircuits"
├── Config.java                    → NeoForge ModConfigSpec (wire transfer rate, shock damage, tick interval)
├── api/
│   ├── WireMaterial.java          → Enum: 5 metals (SILVER/COPPER/GOLD/ALUMINUM/IRON) with resistivity, conductivity, RGB
│   └── WireColor.java             → Enum: 16 standard dye colors with index, RGB, texture-safe name
├── block/
│   └── wire/
│       ├── WireBlock.java         → Insulated wire: 6-dir BooleanProperty connections, color-isolated, EntityBlock
│       └── BareWireBlock.java     → Bare wire: connects to any bare wire, stepOn() shock damage (36V→288V+ scale)
├── blockentity/
│   └── WireBlockEntity.java       → Per-segment energy buffer (EnergyStorage 1000FE), tick-based neighbor propagation, NBT persistence
├── item/wire/
│   ├── BareWireItem.java          → extends BlockItem — places BareWireBlock, tooltip shows material + shock warning
│   └── InsulatedWireItem.java     → extends BlockItem — places WireBlock, tooltip shows material + color + safety
├── network/
│   ├── WireNetwork.java           → BFS graph discovery, energy equalization across attached devices
│   └── WireNetworkManager.java    → World-scoped singleton (Map<LevelAccessor, Manager>), network CRUD
└── register/
    ├── DACBlocks.java             → DeferredRegister.Blocks: 16 insulated wire blocks + 5 bare wire blocks
    ├── DACItems.java              → DeferredRegister<Item>: all BlockItems with BlockItem subclasses (custom tooltips)
    ├── DACBlockEntities.java      → WireBlockEntity type bound to all 21 wire blocks
    └── DACCreativeTab.java        → CreativeModeTab displaying all 21 wire items
```

## Key Design Decisions

1. **Items are BlockItems** — `BareWireItem`/`InsulatedWireItem` extend `BlockItem`, not plain `Item`. Each item's registry name **matches its block's registry name** so the model loader resolves automatically (no `item.*` lang keys needed — `block.*` covers both).

2. **Color isolation via separate blocks** — 16 distinct `WireBlock` instances (one per color), not a single block with an `EnumProperty<DyeColor>`. The `canConnectTo()` check compares `this.getWireColor() == neighbor.getWireColor()`. This avoids blockstate permutation explosion.

3. **6-directional connection** — Uses vanilla `BlockStateProperties.NORTH/EAST/SOUTH/WEST/UP/DOWN` (all `BooleanProperty`). Connection is recalculated in `getStateForPlacement()`, `updateShape()`, and `neighborChanged()`.

4. **Energy propagation** — `WireBlockEntity.serverTick()` pushes to connected neighbors every N ticks (configurable, default 5). Uses `IEnergyStorage` capability. The `WireNetwork` BFS system provides whole-network energy equalization as an optional higher-level optimization.

5. **Shock damage scaling** — Bare wires: 36-72V→1hp, 72-144V→2hp, 144-288V→4hp+slowness, 288V+→instant death. All thresholds configurable.

## Registration Pattern

All registries use `DeferredRegister`:
- `DACBlocks.BLOCKS` → `DeferredRegister.Blocks` (via `createBlocks()`)
- `DACItems.ITEMS` → `DeferredRegister<Item>` (via `DeferredRegister.create(Registries.ITEM, MOD_ID)`)
- `DACBlockEntities.BLOCK_ENTITY_TYPES` → `DeferredRegister<BlockEntityType<?>>`
- `DACCreativeTab.CREATIVE_MODE_TABS` → `DeferredRegister<CreativeModeTab>`

Each must be registered in the mod constructor via `.register(modEventBus)`.

## Resource File Conventions

- **Blockstates**: Multipart JSON per block (`models/block/{name}_core.json` + `{name}_arm.json`)
- **Item models**: One JSON per item at `models/item/{registry_name}.json`, parent pointing to the core block model
- **Textures**: `textures/block/{name}.png` (16×16 default), shared by core and arm models
- **Recipes**: `data/digitalandanalogcircuits/recipe/*.json`
- **Lang**: `assets/digitalandanalogcircuits/lang/{en_us,zh_cn}.json`

## Dependencies

- **Create** `6.0.10-280:slim` — rotational power integration (Ponder + Flywheel transitively)
- **Registrate** `MC1.21-1.3.0+67` — helper library for Create-style registration
- All from `maven.createmod.net` and `maven.ithundxr.dev/snapshots`
