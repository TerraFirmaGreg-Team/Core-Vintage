package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
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
    RockBlockType getRockBlockType();

    @Nullable
    RockBlockVariant getRockBlockVariant();

    @Nonnull
    Rock getRock();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRock().getRockCategory();
    }

    @Nonnull
    default String getRegistryString() {

        if (getRockBlockVariant() == null) {
            return String.format("rock/%s/%s", getRockBlockType(), getRock());
        }

        return String.format("rock/%s/%s/%s", getRockBlockType(), getRockBlockVariant(), getRock());
    }

    @Nonnull
    default ResourceLocation getResourceLocation() {
        if (getRockBlockVariant() == null) {
            return new ResourceLocation(MOD_ID, String.format("rock/%s", getRockBlockType()));
        }

        return new ResourceLocation(MOD_ID, String.format("rock/%s/%s", getRockBlockType(), getRockBlockVariant()));
    }

    @Nonnull
    default String getTranslationString() {
        return MOD_ID + "." + getRegistryString().toLowerCase().replace("/", ".");
    }


    default float getFinalHardness() {
        var rockBlockVariant = getRockBlockVariant();

        if (rockBlockVariant != null) {
            return getRockCategory().getHardnessModifier() + rockBlockVariant.getBaseHardness();
        }

        return getRockCategory().getHardnessModifier();
    }
}
