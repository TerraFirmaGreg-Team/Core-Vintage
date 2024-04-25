package su.terrafirmagreg.modules.device.init.recipe;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.init.ItemsDevice;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;


import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.metal.ItemMetal;

import static net.dries007.tfc.api.types.Metal.ItemType.SHEET;
import static net.dries007.tfc.util.forge.ForgeRule.*;
import static net.dries007.tfc.util.skills.SmithingSkill.Type.GENERAL;

public class AnvilRecipeAdd {

    public static void register(RegistryEvent.Register<AnvilRecipe> event) {
        event.getRegistry().registerAll(
                new AnvilRecipe(
                        ModUtils.id("unfinished_iron_flask"),
                        IIngredient.of(new ItemStack(ItemMetal.get(Metal.WROUGHT_IRON, SHEET))),
                        new ItemStack(ItemsDevice.UNFINISHED_FLASK), Metal.WROUGHT_IRON.getTier(), GENERAL, PUNCH_LAST, BEND_SECOND_LAST,
                        BEND_THIRD_LAST)
        );
    }
}
