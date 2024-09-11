package net.dries007.tfc.objects.items.tools;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ItemRockToolHead extends ItemTFC implements IRockObject {

  private static final EnumMap<Rock.ToolType, Map<RockCategory, ItemRockToolHead>> TABLE = new EnumMap<>(Rock.ToolType.class);
  private final RockCategory category;
  private final Rock.ToolType type;

  public ItemRockToolHead(RockCategory category, Rock.ToolType type) {
    this.type = type;
    this.category = category;
    if (!TABLE.containsKey(type)) {
      TABLE.put(type, new HashMap<>());
    }
    TABLE.get(type).put(category, this);

    OreDictionaryHelper.register(this, type, "head");
    OreDictionaryHelper.register(this, type, "head", category);
  }

  public static ItemRockToolHead get(RockCategory cat, Rock.ToolType type) {
    return TABLE.get(type).get(cat);
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.LIGHT; // Stacksize = 32
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.SMALL; // Stored everywhere
  }

  @Nullable
  @Override
  public Rock getRock(ItemStack stack) {
    return null;
  }

  @NotNull
  @Override
  public RockCategory getRockCategory(ItemStack stack) {
    return category;
  }
}
