package su.terrafirmagreg.api.plugin.jei.category;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.Constants;

import net.minecraft.util.ResourceLocation;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;

public abstract class BaseRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

  protected static final ResourceLocation ICONS = ModUtils.resource("textures/gui/icons/jei.png");

  private final IDrawable background;
  private final String localizedName;
  private final String uid;

  public BaseRecipeCategory(IDrawable background, String uid) {
    this.background = background;
    this.localizedName = Translator.translateToLocal("jei.category." + uid);
    this.uid = uid;
  }

  @Override
  public String getUid() {
    return uid;
  }

  @Override
  public String getTitle() {
    return localizedName;
  }

  @Override
  public String getModName() {
    return Constants.MOD_NAME;
  }

  @Override
  public IDrawable getBackground() {
    return background;
  }

}
