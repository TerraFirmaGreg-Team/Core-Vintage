package net.dries007.tfc.objects.items.glassworking;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfctech.client.TechGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemBlowpipe extends ItemGlassMolder implements ICapabilityMetal {

  private static final Map<Metal, ItemBlowpipe> TABLE = new HashMap<>();
  private final Metal metal;

  public ItemBlowpipe(Metal metal) {
    super(ItemGlassMolder.BLOWPIPE_TANK);
    this.metal = metal;
    if (!TABLE.containsKey(metal)) {
      TABLE.put(metal, this);
    }
    setMaxStackSize(1);
    //noinspection ConstantConditions
    setMaxDamage(metal.getToolMetal().getMaxUses() / 2);
  }

  @Nullable
  public static ItemBlowpipe get(Metal metal) {
    return TABLE.get(metal);
  }

  @Override
  @Nonnull
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote && !player.isSneaking()) {
      ICapabilityHeat cap = stack.getCapability(CapabilityHeat.CAPABILITY, null);
      if (cap instanceof GlassMolderCapability && ((GlassMolderCapability) cap).canWork()) {
        TechGuiHandler.openGui(world, player.getPosition(), player, TechGuiHandler.Type.GLASSWORKING);
      }
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
  }

  @Override
  @Nonnull
  public String getItemStackDisplayName(@Nonnull ItemStack stack) {
    //noinspection ConstantConditions
    String metalName = (new TextComponentTranslation("tfc.types.metal." + metal.getRegistryName().getPath().toLowerCase())).getFormattedText();
    return (new TextComponentTranslation("item.tfctech.metalitem.blowpipe.name", metalName)).getFormattedText();
  }

  @Nullable
  @Override
  public Metal getMetal(ItemStack itemStack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack itemStack) {
    return 288;
  }
}
