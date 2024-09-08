package tfcflorae.util;

import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;


import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import org.jetbrains.annotations.NotNull;

import static net.dries007.tfc.util.OreDictionaryHelper.MAP;
import static net.dries007.tfc.util.OreDictionaryHelper.Thing;

public class OreDictionaryHelper {

  private static final Converter<String, String> UPPER_UNDERSCORE_TO_LOWER_CAMEL = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
  private static final Joiner JOINER_UNDERSCORE = Joiner.on('_').skipNulls();
  private static final boolean done = false;

  public static String toString(Object... parts) {
    return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
  }

  public static String toString(Iterable<Object> parts) {
    return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
  }

  public static String toString(Object[] prefix, Object... parts) {
    return toString(ImmutableList.builder().add(prefix).add(parts).build());
  }

  public static void register(Block thing, Object... parts) {
    register(new Thing(thing), parts);
  }

  public static void register(Item thing, Object... parts) {
    register(new Thing(thing), parts);
  }

  public static void registerMeta(Item thing, int meta, Object... parts) {
    register(new Thing(thing, meta), parts);
  }

  public static void registerDamageType(Item thing, DamageType type) {
    register(thing, "damage", "type", type.name().toLowerCase());
  }

  /**
   * Checks if an ItemStack has an OreDictionary entry that matches 'name'.
   */
  public static boolean doesStackMatchOre(@NotNull ItemStack stack, String name) {
    if (!OreDictionary.doesOreNameExist(name)) {
      //TFCFlorae.getLog().warn("doesStackMatchOre called with non-existing name. stack: {} name: {}", stack, name);
      return false;
    }
    if (stack.isEmpty()) {
      return false;
    }
    int needle = OreDictionary.getOreID(name);
    for (int id : OreDictionary.getOreIDs(stack)) {
      if (id == needle) {
        return true;
      }
    }
    return false;
  }

  private static void register(Thing thing, Object... parts) {
    if (done) {
      throw new IllegalStateException("Cannot use the helper to register after postInit");
    }
    MAP.put(thing, toString(parts));
  }

}
