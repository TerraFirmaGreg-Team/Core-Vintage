package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public interface IRockBlock extends IHasModel, IItemProvider {

    @Nonnull
    RockBlockVariant getRockBlockVariant();

    @Nonnull
    RockType getRockType();

    @Nonnull
    default RockCategory getRockCategory() {
        return getRockType().getRockCategory();
    }

    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return new ResourceLocation(MOD_ID, String.format("rock/%s/%s", getRockBlockVariant(), getRockType()));
    }

    @Nonnull
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(MOD_ID, String.format("rock/%s", getRockBlockVariant()));
    }

    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }


    default float getFinalHardness() {
        return getRockBlockVariant().getBaseHardness() + getRockCategory().getHardnessModifier();
    }
}
