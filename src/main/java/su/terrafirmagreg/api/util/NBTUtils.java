package su.terrafirmagreg.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;


import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.function.Function;

@UtilityClass
@SuppressWarnings("unused")
public final class NBTUtils {

  /**
   * A Comparator used to compare NBTTagCompound.
   */
  public static final Comparator<NBTTagCompound> NBT_COMPARATOR = (firstTag, secondTag) ->
          firstTag != null ? firstTag.equals(secondTag) ? 0 : 1 : secondTag != null ? -1 : 0;

  public static void setGenericNBTValue(String tagName, Object value) {
    setGenericNBTValue(new NBTTagCompound(), tagName, value);
  }

  /**
   * Sets an unknown data type to an NBTTagCompound. If the type of the data can not be identified, and exception will be thrown. Current supported data types include
   * String, Integer, Float, Boolean, Double, Long, Short, Byte, ItemStack, Entity and Position.
   *
   * @param dataTag: An NBTTagCompound to write this unknown data to.
   * @param tagName: The name to save this unknown data under.
   * @param value:   The unknown data you wish to write to the dataTag.
   */
  public static void setGenericNBTValue(NBTTagCompound dataTag, String tagName, Object value) {

    if (value instanceof String stringValue) {
      dataTag.setString(tagName, stringValue);
    } else if (value instanceof Integer integerValue) {
      dataTag.setInteger(tagName, integerValue);
    } else if (value instanceof Float floatValue) {
      dataTag.setFloat(tagName, floatValue);
    } else if (value instanceof Boolean boleanValue) {
      dataTag.setBoolean(tagName, boleanValue);
    } else if (value instanceof Double doubleValue) {
      dataTag.setDouble(tagName, doubleValue);
    } else if (value instanceof Long longValue) {
      dataTag.setLong(tagName, longValue);
    } else if (value instanceof Short shortValue) {
      dataTag.setShort(tagName, shortValue);
    } else if (value instanceof Byte byteValue) {
      dataTag.setByte(tagName, byteValue);
    } else if (value instanceof UUID uuidValue) {
      dataTag.setUniqueId(tagName, uuidValue);
    } else if (value instanceof ItemStack itemStackValue) {
      dataTag.setTag(tagName, itemStackValue.writeToNBT(new NBTTagCompound()));
    } else if (value instanceof NBTBase nbtBase) {
      dataTag.setTag(tagName, nbtBase);
    } else if (value instanceof Entity entityValue) {
      final NBTTagCompound newTag = new NBTTagCompound();
      entityValue.writeToNBT(newTag);
      dataTag.setTag(tagName, newTag);
    } else {
      throw new RuntimeException(
              "The data type of " + value.getClass().getName() + " is currently not supported." + System.lineSeparator() + "Raw Data: " + value
      );
    }
  }

  /**
   * Writes an inventory to an NBTTagCompound. Can be used to save an inventory in a TileEntity, or perhaps an ItemStack.
   *
   * @param tag:       The NBTTagCompound to write the inventory to.
   * @param inventory: The inventory to write to the NBTTagCompound.
   * @return NBTTagCompound: The same NBTTagCompound that was passed to this method.
   */
  public static NBTTagCompound writeInventoryToNBT(NBTTagCompound tag, InventoryBasic inventory) {

    if (inventory.hasCustomName()) {
      NBTUtils.setGenericNBTValue(tag, "CustomName", inventory.getName());
    }

    final NBTTagList nbttaglist = new NBTTagList();

    for (int slotCount = 0; slotCount < inventory.getSizeInventory(); slotCount++) {

      final ItemStack stackInSlot = inventory.getStackInSlot(slotCount);

      if (!stackInSlot.isEmpty()) {

        final NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByte("Slot", (byte) slotCount);
        stackInSlot.writeToNBT(itemTag);
        nbttaglist.appendTag(itemTag);
      }
    }

    tag.setTag("Items", nbttaglist);

    return tag;
  }

  /**
   * Reads an inventory from an NBTTagCompound. Can be used to load an Inventory from a TileEntity or perhaps an ItemStak.
   *
   * @param tag:       The NBTTagCompound to read the inventory data from.
   * @param inventory: The inventory to set all of the inventory data to.
   * @return InventoryBasic: The same instance of InventoryBasic that was passed to this method.
   */
  public static InventoryBasic readInventoryFromNBT(NBTTagCompound tag, InventoryBasic inventory) {

    if (tag.hasKey("CustomName", 8)) {
      inventory.setCustomName(tag.getString("CustomName"));
    }

    final NBTTagList items = tag.getTagList("Items", 10);

    for (int storedCount = 0; storedCount < items.tagCount(); storedCount++) {

      final NBTTagCompound itemTag = items.getCompoundTagAt(storedCount);
      final int slotCount = itemTag.getByte("Slot") & 0xFF;

      if (slotCount >= 0 && slotCount < inventory.getSizeInventory()) {
        inventory.setInventorySlotContents(slotCount, new ItemStack(itemTag));
      }
    }

    return inventory;
  }

  /**
   * Retrieves an array of ItemStack from an NBTTagCompound. This method is intended to be used with the NBT version of an IInventory and can be used when parsing
   * things like TileEntity NBT data.
   *
   * @param tag:     The tag to retrieve all of the item data from.
   * @param invSize: The projected size of the inventory stored to the tag. It is critical that this never be smaller then the actual amount.
   * @return ItemStack[]: An array of ItemStack stored on the NBTTagCompound.
   */
  public static ItemStack[] getStoredItems(NBTTagCompound tag, int invSize) {

    ItemStack[] inventory = null;

    if (tag.hasKey("Items")) {

      final NBTTagList list = tag.getTagList("Items", 10);
      inventory = new ItemStack[invSize];

      for (int i = 0; i < list.tagCount(); i++) {
        if (!(i > list.tagCount())) {

          final NBTTagCompound currentTag = list.getCompoundTagAt(i);
          inventory[currentTag.getByte("Slot")] = new ItemStack(currentTag);
        }
      }
    }

    return inventory;
  }

  /**
   * Provides a way to access an NBTTagCompound that is very deep within another NBTTagCompound. This will allow you to use an array of strings which represent the
   * different steps to get to the deep NBTTagCompound.
   *
   * @param tag:  An NBTTagCompound to search through.
   * @param tags: An array containing the various steps to get to the desired deep NBTTagCompound.
   * @return NBTTagCompound: This method will return the deepest possible NBTTagCompound. In some cases, this may be the tag you provide, or only a few steps deep,
   * rather than all of the way.
   */
  public static NBTTagCompound getDeepTagCompound(NBTTagCompound tag, String[] tags) {

    NBTTagCompound deepTag = tag;

    if (tag != null) {
      for (final String tagName : tags) {
        if (deepTag.hasKey(tagName)) {
          deepTag = deepTag.getCompoundTag(tagName);
        }
      }
    }

    return deepTag;
  }

  public static void resetNBT(ItemStack stack) {
    NBTTagCompound nbt = new NBTTagCompound();
    stack.setTagCompound(nbt);
  }

  /**
   * Increments the value of an integer tag on an ItemStack.
   *
   * @param stack  The ItemStack to increment.
   * @param key    The key of the tag.
   * @param amount The amount to add.
   * @return The amount after adding.
   */
  public static int increment(ItemStack stack, String key, int amount) {

    return incriment(StackUtils.prepareStackTag(stack), key, amount);
  }

  /**
   * Increments the value of an integer tag.
   *
   * @param tag    The tag to modify.
   * @param key    The key of the tag.
   * @param amount The amount to add.
   * @return The amount after adding.
   */
  public static int incriment(NBTTagCompound tag, String key, int amount) {

    final int result = tag.getInteger(key) + amount;
    tag.setInteger(key, result);
    return result;
  }

  /**
   * Gets the value of an integer tag on an ItemStack.
   *
   * @param stack The ItemStack to check.
   * @param key   The key to get.
   * @return The value of the tag.
   */
  public static int getAmount(ItemStack stack, String key) {

    return StackUtils.prepareStackTag(stack).getInteger(key);
  }

  /**
   * Checks if one nbt tag contains all of the data in the second nbt tag.
   *
   * @param first The first tag, this is the one being checked.
   * @param two   The second tag, this is the one being checked for.
   * @return Whether or not the first tag contains entries of the second tag.
   */
  public static boolean containsAllTags(NBTTagCompound first, NBTTagCompound two) {

    for (final String key : two.getKeySet()) {

      if (!first.hasKey(key) || !first.getTag(key).equals(two.getTag(key))) {

        return false;
      }
    }

    return true;
  }

  /**
   * Reads a collection from an NBTTagList by deserializing strings into the elements.
   *
   * @param collection The collection to populate.
   * @param list       The NBTTagList to read.
   * @param func       The function used to deserialize elements.
   * @return A collection containing all deserialized elements.
   */
  public static <T extends Object> Collection<T> readCollection(Collection<T> collection,
          NBTTagList list, Function<String, T> func) {

    for (int index = 0; index < list.tagCount(); index++) {

      final String string = list.getStringTagAt(index);
      collection.add(func.apply(string));
    }

    return collection;
  }

  /**
   * Writes a collection to an NBTTagList by serializing elements to a string.
   *
   * @param collection The collection to write.
   * @param func       The function used to serialize the elements.
   * @return An NBTTagList containing the serialized string values.
   */
  public static <T extends Object> NBTTagList writeCollection(Collection<T> collection,
          Function<T, String> func) {

    final NBTTagList tagList = new NBTTagList();

    for (final T t : collection) {

      tagList.appendTag(new NBTTagString(func.apply(t)));
    }

    return tagList;
  }
}
