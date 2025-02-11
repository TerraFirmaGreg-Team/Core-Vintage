package net.dries007.tfc.objects.items.tools;

import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemAxeTFCF extends ItemAxe implements ICapabilitySize {

  public final ToolMaterial material;

  public ItemAxeTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
    super(material, material.getAttackDamage(), AttackSpeed);
    this.material = material;
    this.attackDamage = (AttackDamage);
    this.attackSpeed = (AttackSpeed);
    this.setMaxDamage(Durability);
    this.setHarvestLevel(ToolClasses.AXE, material.getHarvestLevel());

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        net.dries007.tfc.util.OreDictionaryHelper.register(this, obj);
      }
    }
    OreDictionaryHelper.registerDamageType(this, DamageType.SLASHING);
  }

  @Override
  public boolean canHarvestBlock(IBlockState state) {
    Material material = state.getMaterial();
    return material == Material.WOOD || material == Material.GOURD || material == Material.PLANTS || material == Material.VINE;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.LARGE; // Stored only in chests
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
