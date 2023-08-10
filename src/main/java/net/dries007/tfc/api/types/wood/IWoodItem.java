package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public interface IWoodItem {

    @Nonnull
    WoodType getWoodType();

    @Nonnull
    default ResourceLocation getRegistryLocation(String subType) {
        return new ResourceLocation(MOD_ID, String.format("wood/%s/%s", subType, getWoodType()));
    }

    @Nonnull
    default ResourceLocation getResourceLocation(String subType) {
        return new ResourceLocation(MOD_ID, String.format("wood/%s", subType));
    }

    @Nonnull
    default String getTranslationName(String subType) {
        return getRegistryLocation(subType).toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
