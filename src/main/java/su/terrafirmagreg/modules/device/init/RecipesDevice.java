package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.data.ToolOreDict;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;

import net.minecraft.item.ItemStack;

import com.cleanroommc.groovyscript.helper.ingredient.ItemsIngredient;
import com.cleanroommc.groovyscript.helper.ingredient.OreDictIngredient;

import static com.cleanroommc.groovyscript.compat.vanilla.VanillaModule.crafting;

import net.dries007.tfc.objects.items.ItemsTFC;

public class RecipesDevice {

  public static void onRegister() {

    addShaped();
    addShapeless();
  }


  private static void addShaped() {
    crafting.shapedBuilder()
      .row("LLL")
      .row("LRL")
      .key("R", new OreDictIngredient("rock"))
      .key("L", new OreDictIngredient("lumber"))
      .name("device/alloy_calculator")
      .output(new ItemStack(BlocksDevice.ALLOY_CALCULATOR.get()))
      .register();

    crafting.shapedBuilder()
      .row("XXX")
      .row("SSS")
      .row("XXX")
      .key("X", new OreDictIngredient("lumber"))
      .key("S", new OreDictIngredient("leather"))
      .name("device/bellows")
      .output(new ItemStack(BlocksDevice.BELLOWS.get()))
      .register();

    crafting.shapedBuilder()
      .row("XXX")
      .row("XZX")
      .row("XXX")
      .key("X", new OreDictIngredient("plateDoubleWroughtIron"))
      .key("Z", new OreDictIngredient("crucible"))
      .name("device/blast_furnace")
      .output(new ItemStack(BlocksDevice.BLAST_FURNACE.get()))
      .register();

    crafting.shapedBuilder()
      .row("XXX")
      .row("X X")
      .row("XXX")
      .key("X", new OreDictIngredient("plateDoubleAnyBronze"))
      .name("device/bloomery")
      .output(new ItemStack(BlocksDevice.BLOOMERY.get()))
      .register();

    crafting.shapedBuilder()
      .row(" SK")
      .row("CBC")
      .row("LIL")
      .key("I", new ItemsIngredient(new ItemStack(ItemsDevice.METAL_FLASK_UNFINISHED.get())))
      .key("K", new OreDictIngredient(ToolOreDict.toolKnife))
      .key("C", new ItemsIngredient(new ItemStack(ItemsTFC.BURLAP_CLOTH)))
      .key("S", new OreDictIngredient("string"))
      .key("L", new ItemsIngredient(new ItemStack(ItemsDevice.LEATHER_FLASK_UNFINISHED.get())))
      .key("B", new ItemsIngredient(new ItemStack(ItemsAnimal.BLADDER.get())))
      .name("device/flask/metal")
      .output(new ItemStack(ItemsDevice.METAL_FLASK.get()))
      .register();

    crafting.shapedBuilder()
      .row("FB")
      .row("CK")
      .key("F", new ItemsIngredient(new ItemStack(ItemsDevice.METAL_FLASK_BROKEN.get())))
      .key("K", new OreDictIngredient(ToolOreDict.toolKnife))
      .key("C", new ItemsIngredient(new ItemStack(ItemsTFC.BURLAP_CLOTH)))
      .key("B", new ItemsIngredient(new ItemStack(ItemsAnimal.BLADDER.get())))
      .name("device/flask/metal/broken_repair")
      .output(new ItemStack(ItemsDevice.METAL_FLASK.get()))
      .register();

    crafting.shapedBuilder()
      .row("FB")
      .row("CK")
      .key("F", new ItemsIngredient(new ItemStack(ItemsDevice.METAL_FLASK.get())))
      .key("K", new OreDictIngredient(ToolOreDict.toolKnife))
      .key("C", new ItemsIngredient(new ItemStack(ItemsTFC.BURLAP_CLOTH)))
      .key("B", new ItemsIngredient(new ItemStack(ItemsAnimal.BLADDER.get())))
      .name("device/flask/metal/repair")
      .output(new ItemStack(ItemsDevice.METAL_FLASK.get()))
      .register();

  }

  private static void addShapeless() {
    crafting.shapelessBuilder()
      .input(
        new OreDictIngredient("barrel"),
        new OreDictIngredient("dyeRed"),
        new OreDictIngredient("dustGunpowder"))
      .name("device/powderkeg")
      .output(new ItemStack(BlocksDevice.POWDERKEG.get()))
      .register();
  }
}
