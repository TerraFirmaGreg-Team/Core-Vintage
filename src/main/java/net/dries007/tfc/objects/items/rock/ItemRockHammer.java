package net.dries007.tfc.objects.items.rock;

import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.collect.ImmutableSet;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MethodsReturnNonnullByDefault
public class ItemRockHammer extends ItemTool implements ICapabilitySize, IRockObject {

  private static final Map<RockCategory, ItemRockHammer> MAP = new HashMap<>();
  public final RockCategory category;

  public ItemRockHammer(RockCategory category) {
    // Vanilla ItemTool constructor actually treats this as "bonus attack damage", and as a result, adds + getAttackDamage(). So for our purposes, this is 1.0 * attack damage.
    super(0f, -3f, category.getToolMaterial(), ImmutableSet.of());
    this.category = category;
    if (MAP.put(category, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setHarvestLevel("hammer", category.getToolMaterial().getHarvestLevel());
    OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
    OreDictionaryHelper.register(this, "hammer");
    OreDictionaryHelper.register(this, "hammer", "stone");
    OreDictionaryHelper.register(this, "hammer", "stone", category);
  }

  public static ItemRockHammer get(RockCategory category) {
    return MAP.get(category);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Rock type: " + OreDictionaryHelper.toString(category));
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.LARGE;  // Stored only in chests
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.MEDIUM;
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return false;
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
