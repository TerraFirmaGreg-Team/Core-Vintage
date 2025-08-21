package su.terrafirmagreg.modules.rock.object.recipe.anvil;


import su.terrafirmagreg.framework.manager.registry.base.recipe.api.IBaseRecipe;
import su.terrafirmagreg.modules.core.feature.playerdata.spi.SmithingSkill;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.forge.ForgeSteps;

public interface IAnvilRecipe extends IBaseRecipe {

  ForgeRule[] getRules();

  Metal.Tier getTier();

  SmithingSkill.Type getSkillBonusType();

  NonNullList<ItemStack> getOutputItem(ItemStack stack);

  boolean isValidInput(ItemStack inputItem);

  boolean matches(ForgeSteps steps);

  int getTarget(long worldSeed);

}
