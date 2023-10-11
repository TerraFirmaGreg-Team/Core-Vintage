package net.dries007.tfc.module.plant.api.types.variant.block;

import net.dries007.tfc.module.plant.api.types.type.PlantType;
import net.dries007.tfc.module.plant.objects.blocks.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public enum PlantEnumVariant implements IStringSerializable {
    STANDARD(BlockPlant::new),
    CREEPING(BlockPlantCreeping::new),
    HANGING(BlockPlantHanging::new),
    FLOATING(BlockPlantFloatingWater::new),
    FLOATING_SEA(BlockPlantFloatingWater::new),
    DESERT(BlockPlant::new),
    DESERT_TALL_PLANT(BlockPlantTall::new),
    DRY(BlockPlant::new),
    DRY_TALL_PLANT(BlockPlantTall::new),
    TALL_PLANT(BlockPlantTall::new),
    CACTUS(BlockPlantCactus::new),
    SHORT_GRASS(BlockPlantShortGrass::new),
    TALL_GRASS(BlockPlantTallGrass::new),
    EPIPHYTE(BlockPlantEpiphyte::new),
    REED(BlockPlant::new),
    REED_SEA(BlockPlant::new),
    TALL_REED(BlockPlantTall::new),
    TALL_REED_SEA(BlockPlantTall::new),
    WATER(BlockPlantWater::new),
    WATER_SEA(BlockPlantWater::new),
    TALL_WATER(BlockPlantTallWater::new),
    TALL_WATER_SEA(BlockPlantTallWater::new),
    EMERGENT_TALL_WATER(BlockPlantEmergentTallWater::new),
    EMERGENT_TALL_WATER_SEA(BlockPlantEmergentTallWater::new),
    MUSHROOM(BlockPlantMushroom::new);

    public static final PlantEnumVariant[] VALUES = PlantEnumVariant.values();
    private final BiFunction<PlantEnumVariant, PlantType, IPlantBlock> blockFactory;

    PlantEnumVariant(BiFunction<PlantEnumVariant, PlantType, IPlantBlock> blockFactory) {
        this.blockFactory = blockFactory;
    }

    public static PlantEnumVariant valueOf(int i) {
        return i >= 0 && i < VALUES.length ? VALUES[i] : STANDARD;
    }

    public IPlantBlock create(PlantType plant) {
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

    @Nonnull
    @Override
    public String getName() {
        return name();
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
