package net.dries007.tfc.api.types.rock.util;

import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.RockType;
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
    RockType getRockType();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRockType().getRockCategory();
    }

    @Nonnull
    default String getRegistryString() {

        if (getRockBlockVariant() == null) {
            return String.format("rock/%s/%s", getRockBlockType(), getRockType());
        }

        return String.format("rock/%s/%s/%s", getRockBlockType(), getRockBlockVariant(), getRockType());
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


    // TODO: 06.08.2023
    default float getFinalHardness() {
        return 0F;
    }
}
