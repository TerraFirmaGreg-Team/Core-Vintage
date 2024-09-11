package net.dries007.tfc.objects.items.tools;

import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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

public class ItemRockKnife extends ItemTool implements ICapabilitySize, IRockObject {

  private static final Map<RockCategory, ItemRockKnife> MAP = new HashMap<>();
  public final RockCategory category;

  public ItemRockKnife(RockCategory category) {
    // Vanilla ItemTool constructor actually treats this as "bonus attack damage", and as a result, adds + getAttackDamage(). So for our purposes, this is 0.54 * attack damage.
    super(-0.46f * category.getToolMaterial()
            .getAttackDamage(), -1.5f, category.getToolMaterial(), ImmutableSet.of());
    this.category = category;
    if (MAP.put(category, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setHarvestLevel("knife", category.getToolMaterial().getHarvestLevel());

    OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
    OreDictionaryHelper.register(this, "knife");
    OreDictionaryHelper.register(this, "knife", "stone");
    OreDictionaryHelper.register(this, "knife", "stone", category);
  }

  public static ItemRockKnife get(RockCategory category) {
    return MAP.get(category);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Rock type: " + OreDictionaryHelper.toString(category));
  }

  @Override
  public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
    return true;
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.MEDIUM;
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.NORMAL; // Stored in large vessels
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

  @Override
  public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
    // Knives always take damage
    if (!worldIn.isRemote) {
      stack.damageItem(1, entityLiving);
    }
    return true;
  }
}
