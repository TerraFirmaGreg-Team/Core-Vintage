package su.terrafirmagreg.modules.device.plugin.jei;

import su.terrafirmagreg.modules.device.client.gui.GuiDryingMat;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.plugin.jei.dryingmat.DryingMatRecipeCategory;
import su.terrafirmagreg.modules.device.plugin.jei.dryingmat.DryingMatRecipeMaker;
import su.terrafirmagreg.modules.device.plugin.jei.quern.QuernRecipeCategory;
import su.terrafirmagreg.modules.device.plugin.jei.quern.QuernRecipeMaker;

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
public final class PluginJustEnoughItems implements IModPlugin {

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

    registry.addRecipeCategories(new QuernRecipeCategory(guiHelper));
    registry.addRecipeCategories(new DryingMatRecipeCategory(guiHelper));
  }

  @Override
  public void register(IModRegistry registry) {

    // ==== QUERN ====
    registry.addRecipes(QuernRecipeMaker.getRecipes(), QuernRecipeCategory.UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.QUERN_MANUAL), QuernRecipeCategory.UID);

    // ==== DRYING_MAT ====
    registry.addRecipes(DryingMatRecipeMaker.getRecipes(), DryingMatRecipeCategory.UID);
    registry.addRecipeCatalyst(new ItemStack(BlocksDevice.DRYING_MAT), DryingMatRecipeCategory.UID);
    registry.addRecipeClickArea(GuiDryingMat.class, 103, 35, 9, 14, DryingMatRecipeCategory.UID);

  }
}

