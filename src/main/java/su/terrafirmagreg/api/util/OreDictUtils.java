package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypes;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoral;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoralBlock;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
@SuppressWarnings("unused")
public final class OreDictUtils {

  public static void register(Item item, Object... parts) {
    register(item, OreDictionary.WILDCARD_VALUE, parts);
  }

  public static void register(Item item, int meta, Object... parts) {
    register(new ItemStack(item, 1, meta), toString(parts));
  }

  public static void register(ItemStack itemStack, String oreName) {
    if (oreName == null) {
      return;
    }

    if (itemStack != null && !itemStack.isEmpty()) {
      OreDictionary.registerOre(oreName, itemStack);
    } else {
      TerraFirmaGreg.LOGGER.error(
        "Failed to register ore dict entry for {}. Another unknown mod is likely responsible.",
        oreName);
    }
  }

  /**
   * Преобразует массив объектов в строку в формате lowerCamelCase, разделяя элементы подчеркиванием.
   *
   * @param parts Массив объектов для преобразования в строку.
   * @return Строка, представляющая объединенные элементы массива в формате lowerCamelCase.
   */
  public static String toString(@NotNull Object... parts) {
    return CaseFormat.UPPER_UNDERSCORE
      .converterTo(CaseFormat.LOWER_CAMEL)
      .convert(Joiner.on('_').skipNulls().join(parts));
  }

  public static void register(Block block, Object... parts) {
    register(new ItemStack(block), toString(parts));
  }

  /**
   * Gets all of the ore dictionary names for an ItemStack.
   *
   * @param stack The ItemStack to look at.
   * @return A set of the ore names.
   */
  public static Set<String> getOreNames(ItemStack stack) {
    final Set<String> names = new HashSet<>();

    for (final int id : OreDictionary.getOreIDs(stack)) {
      names.add(OreDictionary.getOreName(id));
    }

    return names;
  }

  public static boolean playerHasItemMatchingOre(InventoryPlayer playerInv, String ore) {
    for (ItemStack stack : playerInv.mainInventory) {
      if (!stack.isEmpty() && contains(stack, ore)) {
        return true;
      }
    }
    for (ItemStack stack : playerInv.armorInventory) {
      if (!stack.isEmpty() && contains(stack, ore)) {
        return true;
      }
    }
    for (ItemStack stack : playerInv.offHandInventory) {
      if (!stack.isEmpty() && contains(stack, ore)) {
        return true;
      }
    }
    return false;
  }

  public static boolean contains(ItemStack itemStack, String oreDict) {
    if (!OreDictionary.doesOreNameExist(oreDict)) {
      TerraFirmaGreg.LOGGER.warn("Method called with non-existing name. stack: {} name: {}", itemStack, oreDict);
      return false;
    }

    if (itemStack.isEmpty()) {
      return false;
    }

    int needle = OreDictionary.getOreID(oreDict);
    int[] oreIDs = OreDictionary.getOreIDs(itemStack);

    //noinspection ForLoopReplaceableByForEach
    for (int i = 0; i < oreIDs.length; i++) {
      if (oreIDs[i] == needle) {
        return true;
      }
    }

    return false;
  }

}
