package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Set;

@UtilityClass
@SuppressWarnings("unused")
public final class OreDictUtils {

  public static void register(Block block) {
    if (block instanceof IBlockSettings provider) {
      var settings = provider.getSettings();
      OreDictUtils.register(block, settings.getRegistryKey());
      settings.getOreDict().forEach(oreDict -> {
        if (oreDict != null && oreDict.length > 0) {
          OreDictUtils.register(block, oreDict);
        }
      });

    }
  }

  public static void register(Item item) {
    if (item instanceof IItemSettings provider) {
      var settings = provider.getSettings();
      OreDictUtils.register(item, settings.getRegistryKey());
      settings.getOreDict().forEach(oreDict -> {
        if (oreDict != null && oreDict.length > 0) {
          OreDictUtils.register(item, oreDict);
        }
      });
    }
  }

  public static void register(Block block, Object... parts) {
    register(new ItemStack(block), toString(parts));
  }

  public static void register(Item item, Object... parts) {
    register(new ItemStack(item), toString(parts));
  }

  public static void register(ItemStack itemStack, String oreName) {
    if (oreName == null) {
      return;
    }

    if (itemStack != null && !itemStack.isEmpty()) {
      OreDictionary.registerOre(oreName, itemStack);
    } else {
      TerraFirmaGreg.LOGGER.error("Failed to register ore dict entry for {}. Another unknown mod is likely responsible.", oreName);
    }
  }

  /**
   * Преобразует массив объектов в строку в формате lowerCamelCase, разделяя элементы подчеркиванием.
   *
   * @param parts Массив объектов для преобразования в строку.
   * @return Строка, представляющая объединенные элементы массива в формате lowerCamelCase.
   */
  public static String toString(@NotNull Object... parts) {
    if (parts == null) {
      return null;
    }
    Object[] modifiedParts = Arrays.stream(parts)
      .map(part -> part.toString().replace("/", "_"))
      .toArray();

    return CaseFormat.UPPER_UNDERSCORE
      .converterTo(CaseFormat.LOWER_CAMEL)
      .convert(Joiner.on('_').skipNulls().join(modifiedParts));
  }

  /**
   * Gets all of the ore dictionary names for an ItemStack.
   *
   * @param stack The ItemStack to look at.
   * @return A set of the ore names.
   */
  public static Set<String> getOreNames(ItemStack stack) {
    final Set<String> names = new ObjectOpenHashSet<>();

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

    return CollectionUtils.containsInt(oreIDs, needle);
  }

}
