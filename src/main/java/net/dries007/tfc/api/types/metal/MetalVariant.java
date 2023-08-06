package net.dries007.tfc.api.types.metal;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.types.metal.util.IMetalBlock;
import net.dries007.tfc.objects.blocks.metal.BlockMetalAnvil;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public enum MetalVariant implements IStringSerializable {
    ANVIL(BlockMetalAnvil::new);


    public static final MetalVariant[] VALUES = MetalVariant.values();
    private final BiFunction<MetalVariant, Material, IMetalBlock> blockFactory;

    MetalVariant(BiFunction<MetalVariant, Material, IMetalBlock> blockFactory) {
        this.blockFactory = blockFactory;
    }

    public IMetalBlock create(Material material) {
        return this.blockFactory.apply(this, material);
    }

    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
