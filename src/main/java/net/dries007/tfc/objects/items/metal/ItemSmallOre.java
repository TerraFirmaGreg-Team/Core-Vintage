package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSmallOre extends ItemTFC implements ICapabilityMetal {

  private static final Map<Ore, ItemSmallOre> MAP = new HashMap<>();
  private final Ore ore;

  public ItemSmallOre(Ore ore) {
    this.ore = ore;
    if (MAP.put(ore, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setMaxDamage(0);
    if (ore.getMetal() != null) {
      //noinspection ConstantConditions
      String name = ore.getMetal().getRegistryName().getPath();
      OreDictionaryHelper.register(this, "ore", name, "small");
      OreDictionaryHelper.register(this, "ore", "small", name);
      if (ore.getMetal() == Metal.WROUGHT_IRON && ConfigTFC.General.MISC.dictionaryIron) {
        OreDictionaryHelper.register(this, "ore", "iron", "small");
        OreDictionaryHelper.register(this, "ore", "small", "iron");
      }
    } else {
      //noinspection ConstantConditions
      String name = ore.getMetal().getRegistryName().getPath();
      OreDictionaryHelper.register(this, "ore", name, "small");
      OreDictionaryHelper.register(this, "ore", "small", name);
    }
  }

  public static ItemSmallOre get(Ore ore) {
    return MAP.get(ore);
  }

  public static ItemStack get(Ore ore, int amount) {
    return new ItemStack(MAP.get(ore), amount);
  }

  @Override
  public boolean canMelt(ItemStack stack) {
    return ore.canMelt();
  }

  @Override
  public Metal getMetal(ItemStack stack) {
    return ore.getMetal();
  }

  @Override
  public int getSmeltAmount(ItemStack stack) {
    return ConfigTFC.General.MISC.smallOreMetalAmount;
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.MEDIUM; // Stacksize = 16
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.SMALL; // Fits in Small vessels
  }

  @NotNull
  public Ore getOre() {
    return ore;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
    Metal metal = getMetal(stack);
    if (metal != null) {
      int smeltAmount = this.getSmeltAmount(stack);
      switch (ConfigCore.MISC.HEAT.oreTooltipMode) {
        case HIDE:
          break;
        case UNIT_ONLY:
          // Like classic, "Metal: xx units"
          String info = String.format("%s: %s", I18n.format(Helpers.getTypeName(metal)), I18n.format("tfc.tooltip.units", smeltAmount));
          tooltip.add(info);
          break;
        case TOTAL_ONLY:
          // not like Classic, "Metal: xx total units" Adds the whole stacks worth up.
          String stackTotal = String.format("%s: %s", I18n.format(Helpers.getTypeName(metal)),
                  I18n.format("tfc.tooltip.units.total", smeltAmount * stack.getCount()));
          tooltip.add(stackTotal);
          break;
        case ALL_INFO:
          // All info: "Metal: xx units / xx total"
          String infoTotal;
          if (stack.getCount() > 1) {
            infoTotal = String.format("%s: %s", I18n.format(Helpers.getTypeName(metal)),
                    I18n.format("tfc.tooltip.units.info_total", smeltAmount, smeltAmount * stack.getCount()));
          } else {
            infoTotal = String.format("%s: %s", I18n.format(Helpers.getTypeName(metal)), I18n.format("tfc.tooltip.units", smeltAmount));
          }
          tooltip.add(infoTotal);
          break;
        case ADVANCED:
          // All info: "Metal: xx units / xx total"
          String advancedTotal;
          if (stack.getCount() > 1) {
            advancedTotal = String.format("%s: %s: %s", I18n.format(Helpers.getTypeName(metal)),
                    I18n.format("tfc.tooltip.units.info_total", smeltAmount, smeltAmount * stack.getCount()),
                    I18n.format("tfc.tooltip.melttemp", (int) metal.getMeltTemp()));
          } else {
            advancedTotal = String.format("%s: %s", I18n.format(Helpers.getTypeName(metal)),
                    I18n.format("tfc.tooltip.units", smeltAmount));
          }
          tooltip.add(advancedTotal);
      }
    }
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return ore.getMetal() != null ? new ProviderHeat(nbt, ore.getMetal().getSpecificHeat(), ore.getMetal().getMeltTemp()) : null;
  }
}
