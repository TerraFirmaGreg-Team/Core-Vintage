package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.forge.CapabilityForgeable;
import su.terrafirmagreg.modules.core.capabilities.forge.ForgeableMeasurableMetalHandler;
import su.terrafirmagreg.modules.core.capabilities.forge.ICapabilityForge;
import su.terrafirmagreg.modules.core.capabilities.forge.IForgeableMeasurableMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ItemBloom extends ItemTFC implements ICapabilityMetal {

  private final boolean meltable;

  public ItemBloom(boolean meltable) {
    this.meltable = meltable;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack stack) {
    return Size.LARGE; // Stored in chests
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack stack) {
    return Weight.HEAVY; // Stacksize = 4
  }

  @Nullable
  @Override
  public Metal getMetal(ItemStack stack) {
    ICapabilityForge cap = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap instanceof IForgeableMeasurableMetal handler) {
      return handler.getMetal();
    }
    return Metal.UNKNOWN;
  }

  @Override
  public int getSmeltAmount(ItemStack stack) {
    ICapabilityForge cap = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap instanceof IForgeableMeasurableMetal handler) {
      int amount = handler.getMetalAmount();
      if (amount > 144) {
        amount = 144;
      }
      return amount;
    }
    return 0;
  }

  @Override
  public boolean canMelt(ItemStack stack) {
    return meltable;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addMetalInfo(ItemStack stack, List<String> text) {
    ICapabilityForge cap = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
    if (cap instanceof IForgeableMeasurableMetal forgeableMeasurableMetal) {
      text.add("");
      text.add(I18n.format("tfc.tooltip.metal", I18n.format(Helpers.getTypeName(forgeableMeasurableMetal.getMetal()))));
      text.add(I18n.format("tfc.tooltip.units", forgeableMeasurableMetal.getMetalAmount()));
      text.add(I18n.format(Helpers.getEnumName(forgeableMeasurableMetal.getMetal().getTier())));
    }
  }

  @Override
  @Nonnull
  public String getTranslationKey(ItemStack stack) {
    //noinspection ConstantConditions
    return super.getTranslationKey(stack) + "." + getMetal(stack).getRegistryName().getPath();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab)) {
      for (int i = 144; i <= 576; i += 144) {
        ItemStack stack = new ItemStack(this);
        ICapabilityForge cap = stack.getCapability(CapabilityForgeable.CAPABILITY, null);
        if (cap instanceof IForgeableMeasurableMetal handler) {
          handler.setMetal(Metal.WROUGHT_IRON);
          handler.setMetalAmount(i);
          items.add(stack);
        }
      }
    }
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (nbt == null) {
      return new ForgeableMeasurableMetalHandler(Metal.WROUGHT_IRON, 144);
    } else {
      return new ForgeableMeasurableMetalHandler(nbt);
    }
  }
}
