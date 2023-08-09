package net.dries007.tfc.api.types.soil;

import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public interface ISoilBlock extends IHasModel, IItemProvider {
    @Nonnull
    SoilBlockVariant getSoilBlockVariant();

    @Nonnull
    SoilType getSoilType();

    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return new ResourceLocation(MOD_ID, String.format("soil/%s/%s", getSoilBlockVariant(), getSoilType()));
    }

    @Nonnull
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(MOD_ID, String.format("soil/%s", getSoilBlockVariant()));
    }

    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
