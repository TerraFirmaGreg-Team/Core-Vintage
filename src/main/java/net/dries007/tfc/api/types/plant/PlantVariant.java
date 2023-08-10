package net.dries007.tfc.api.types.plant;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.blocks.plants.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiFunction;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum PlantVariant implements IStringSerializable {
    STANDARD(BlockPlantTFC::new),
    CREEPING(BlockCreepingPlantTFC::new),
    HANGING(BlockHangingPlantTFC::new),
    FLOATING(BlockFloatingWaterTFC::new),
    FLOATING_SEA(BlockFloatingWaterTFC::new),
    DESERT(BlockPlantTFC::new),
    DESERT_TALL_PLANT(BlockTallPlantTFC::new),
    DRY(BlockPlantTFC::new),
    DRY_TALL_PLANT(BlockTallPlantTFC::new),
    TALL_PLANT(BlockTallPlantTFC::new),
    CACTUS(BlockCactusTFC::new),
    SHORT_GRASS(BlockShortGrassTFC::new),
    TALL_GRASS(BlockTallGrassTFC::new),
    EPIPHYTE(BlockEpiphyteTFC::new),
    REED(BlockPlantTFC::new),
    REED_SEA(BlockPlantTFC::new),
    TALL_REED(BlockTallPlantTFC::new),
    TALL_REED_SEA(BlockTallPlantTFC::new),
    WATER(BlockWaterPlantTFC::new),
    WATER_SEA(BlockWaterPlantTFC::new),
    TALL_WATER(BlockTallWaterPlantTFC::new),
    TALL_WATER_SEA(BlockTallWaterPlantTFC::new),
    EMERGENT_TALL_WATER(BlockEmergentTallWaterPlantTFC::new),
    EMERGENT_TALL_WATER_SEA(BlockEmergentTallWaterPlantTFC::new),
    MUSHROOM(BlockMushroomTFC::new);

    public static final PlantVariant[] VALUES = PlantVariant.values();
    private final BiFunction<PlantVariant, Plant, IPlantBlock> blockFactory;

    PlantVariant(BiFunction<PlantVariant, Plant, IPlantBlock> blockFactory) {
        this.blockFactory = blockFactory;
    }

    public static PlantVariant valueOf(int i) {
        return i >= 0 && i < VALUES.length ? VALUES[i] : STANDARD;
    }

    public IPlantBlock create(Plant plant) {
        return blockFactory.apply(this, plant);
    }

    public Material getPlantMaterial() {
        return switch (this) {
            case CACTUS -> Material.CACTUS;
            case HANGING, SHORT_GRASS, TALL_GRASS -> Material.VINE;
            case WATER, WATER_SEA, TALL_WATER, TALL_WATER_SEA, EMERGENT_TALL_WATER, EMERGENT_TALL_WATER_SEA ->
                    Material.CORAL;
            default -> Material.PLANTS;
        };
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
