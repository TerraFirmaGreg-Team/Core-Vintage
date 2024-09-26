package com.eerussianguy.firmalife.compat;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.eerussianguy.firmalife.recipe.NutRecipe;

import static su.terrafirmagreg.data.Constants.MODID_FL;

public class CompatibleRecipeRegistry {

  private final ResourceLocation registryName;

  public CompatibleRecipeRegistry(String registryName) {
    this(new ResourceLocation(MODID_FL, registryName));
  }

  public CompatibleRecipeRegistry(ResourceLocation registryName) {
    this.registryName = registryName;
  }

  public ResourceLocation getRegistryName() {
    return registryName;
  }

  public void init(FMLInitializationEvent event) {

  }

  public void registerNutRecipes(IForgeRegistry<NutRecipe> r) {

  }
}
