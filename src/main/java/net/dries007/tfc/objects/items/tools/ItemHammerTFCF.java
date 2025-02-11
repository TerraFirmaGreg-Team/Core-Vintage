package net.dries007.tfc.objects.items.tools;

import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.ImmutableSet;
import mcp.MethodsReturnNonnullByDefault;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHammerTFCF extends ItemTool implements ICapabilitySize {

  public final ToolMaterial material;

  public ItemHammerTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
    super(AttackDamage, AttackSpeed, material, ImmutableSet.of());
    this.material = material;
    this.attackDamage = (AttackDamage);
    this.attackSpeed = (AttackSpeed);
    this.setMaxDamage(Durability);
    this.setHarvestLevel(ToolClasses.HARD_HAMMER, material.getHarvestLevel());

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, obj);
      }
    }
    OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.LARGE;  // Stored only in chests
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.MEDIUM;
  }

  @Override
  public boolean canStack(ItemStack stack) {
    return false;
  }
}
