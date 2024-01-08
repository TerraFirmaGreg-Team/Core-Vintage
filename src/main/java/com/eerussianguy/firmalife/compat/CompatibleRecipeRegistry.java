package com.eerussianguy.firmalife.compat;

import com.eerussianguy.firmalife.recipe.NutRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static su.terrafirmagreg.Constants.MODID_FL;

public class CompatibleRecipeRegistry {
	private final ResourceLocation registryName;

	public CompatibleRecipeRegistry(ResourceLocation registryName) {
		this.registryName = registryName;
	}

	public CompatibleRecipeRegistry(String registryName) {
		this(new ResourceLocation(MODID_FL, registryName));
	}

	public ResourceLocation getRegistryName() {
		return registryName;
	}

	public void init(FMLInitializationEvent event) {

	}

	public void registerNutRecipes(IForgeRegistry<NutRecipe> r) {

	}
}
