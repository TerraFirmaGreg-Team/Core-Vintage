package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.block.type.RockType;
import net.dries007.tfc.api.types.rock.block.variant.RockVariant;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public interface IRockBlock extends IHasModel, IItemProvider {

    @Nonnull
    RockType getRockType();

    @Nullable
    RockVariant getRockVariant();

    @Nonnull
    Rock getRock();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRock().getRockCategory();
    }

    @Nonnull
    default String getRegistryString() {

        if (getRockVariant() == null) {
            return String.format("rock/%s/%s", getRockType(), getRock());
        }

        return String.format("rock/%s/%s/%s", getRockType(), getRockVariant(), getRock());
    }

    @Nonnull
    default ResourceLocation getResourceLocation() {
        if (getRockVariant() == null) {
            return new ResourceLocation(MOD_ID, String.format("rock/%s", getRockType()));
        }

        return new ResourceLocation(MOD_ID, String.format("rock/%s/%s", getRockType(), getRockVariant()));
    }

    @Nonnull
    default String getTranslationString() {
        return MOD_ID + "." + getRegistryString().toLowerCase().replace("/", ".");
    }


    default float getFinalHardness() {
        var rockVariant = getRockVariant();

        if (rockVariant != null) {
            return getRockCategory().getHardnessModifier() + rockVariant.getBaseHardness();
        }

        return getRockCategory().getHardnessModifier();
    }
}
