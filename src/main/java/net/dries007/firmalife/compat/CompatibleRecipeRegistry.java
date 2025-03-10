package net.dries007.firmalife.compat;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.objects.recipes.NutRecipe;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

public class CompatibleRecipeRegistry {

  private final ResourceLocation registryName;

  public CompatibleRecipeRegistry(ResourceLocation registryName) {
    this.registryName = registryName;
  }

  public CompatibleRecipeRegistry(String registryName) {
    this(new ResourceLocation(MOD_ID, registryName));
  }

  public ResourceLocation getRegistryName() {
    return registryName;
  }

  public void init(FMLInitializationEvent event) {

  }

  public void registerNutRecipes(IForgeRegistry<NutRecipe> r) {

  }
}
