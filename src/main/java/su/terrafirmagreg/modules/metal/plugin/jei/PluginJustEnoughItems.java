package su.terrafirmagreg.modules.metal.plugin.jei;

import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.plugin.jei.anvil.MetalAnvilRecipeCategory;
import su.terrafirmagreg.modules.metal.plugin.jei.anvil.MetalAnvilRecipeMaker;
import su.terrafirmagreg.modules.metal.plugin.jei.welding.WeldingRecipeCategory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

@JEIPlugin
@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public final class PluginJustEnoughItems
        implements IModPlugin {

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

    registry.addRecipeCategories(new MetalAnvilRecipeCategory(guiHelper));
    registry.addRecipeCategories(new WeldingRecipeCategory(guiHelper));
  }

  @Override
  public void register(IModRegistry registry) {

    // ==== ANVIL ====
    registry.addRecipes(MetalAnvilRecipeMaker.getRecipes(), MetalAnvilRecipeCategory.UID);

    MetalType.getTypes().forEach(type -> {
      registry.addRecipeCatalyst(new ItemStack(BlocksMetal.ANVIL.get(type)),
              MetalAnvilRecipeCategory.UID);
      registry.addRecipeCatalyst(new ItemStack(BlocksMetal.ANVIL.get(type)),
              WeldingRecipeCategory.UID);
    });

  }
}
