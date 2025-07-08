package net.dries007.firmalife.compat;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.objects.recipes.NutRecipe;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.FL;

public class CompatibleRecipeRegistry {

  private final ResourceLocation registryName;

  public CompatibleRecipeRegistry(ResourceLocation registryName) {
    this.registryName = registryName;
  }

  public CompatibleRecipeRegistry(String registryName) {
    this(new ResourceLocation(FL, registryName));
  }

  public ResourceLocation getRegistryName() {
    return registryName;
  }

  public void init(FMLInitializationEvent event) {

  }

  public void registerNutRecipes(IForgeRegistry<NutRecipe> r) {

  }
}
