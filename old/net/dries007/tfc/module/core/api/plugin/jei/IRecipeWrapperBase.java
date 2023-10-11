package net.dries007.tfc.module.core.api.plugin.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public interface IRecipeWrapperBase extends IRecipeWrapper {

    @Nullable
    ResourceLocation getRegistryName();

}
