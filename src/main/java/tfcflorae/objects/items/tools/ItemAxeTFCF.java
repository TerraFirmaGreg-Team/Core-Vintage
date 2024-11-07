package tfcflorae.objects.items.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import tfcflorae.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemAxeTFCF extends ItemAxe implements IItemSize {

  public final ToolMaterial material;

  public ItemAxeTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
    super(material, material.getAttackDamage(), AttackSpeed);
    this.material = material;
    this.attackDamage = (AttackDamage);
    this.attackSpeed = (AttackSpeed);
    this.setMaxDamage(Durability);
    this.setHarvestLevel("axe", material.getHarvestLevel());

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {OreDictionaryHelper.register(this, (Object[]) obj);} else {OreDictionaryHelper.register(this, obj);}
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
