package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
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

@SuppressWarnings("WeakerAccess")
public class ItemOreTFC extends ItemTFC implements ICapabilityMetal {

    private static final Map<Ore, ItemOreTFC> MAP = new HashMap<>();
    public final Ore ore;

    public ItemOreTFC(Ore ore) {
        this.ore = ore;
        if (MAP.put(ore, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        if (ore.getMetal() != null) {
            setHasSubtypes(true);

            for (Ore.Grade grade : Ore.Grade.values()) {
                //noinspection ConstantConditions
                String name = ore.getMetal().getRegistryName().getPath();
                OreDictionaryHelper.registerMeta(this, grade.getMeta(), "ore", name, grade);
                OreDictionaryHelper.registerMeta(this, grade.getMeta(), "ore", grade, name);
                if (ore.getMetal() == Metal.WROUGHT_IRON && ConfigTFC.General.MISC.dictionaryIron) {
                    OreDictionaryHelper.registerMeta(this, grade.getMeta(), "ore", "iron", grade);
                    OreDictionaryHelper.registerMeta(this, grade.getMeta(), "ore", grade, "iron");
                }
            }
        } else // Mineral
        {
            //noinspection ConstantConditions
            String oreName = ore.getRegistryName().getPath();
            switch (oreName) {
                case "lapis_lazuli":
                    OreDictionaryHelper.register(this, "gem", "lapis");
                    break;
                case "bituminous_coal":
                    OreDictionaryHelper.register(this, "gem", "coal");
                    break;
                case "lignite":
                    OreDictionaryHelper.register(this, "gem", "lignite");
                    break;
                default:
                    OreDictionaryHelper.register(this, "gem", ore);
            }
        }
    }

    public static ItemOreTFC get(Ore ore) {
        return MAP.get(ore);
    }

    public static ItemStack get(Ore ore, Ore.Grade grade, int amount) {
        return new ItemStack(MAP.get(ore), amount, ore.isGraded() ? grade.getMeta() : 0);
    }

    public static ItemStack get(Ore ore, int amount) {
        return new ItemStack(MAP.get(ore), amount);
    }

    @NotNull
    public Ore.Grade getGradeFromStack(ItemStack stack) {
        return Ore.Grade.valueOf(stack.getItemDamage());
    }

    @Override
    @NotNull
    public String getTranslationKey(@NotNull ItemStack stack) {
        Ore.Grade grade = getGradeFromStack(stack);
        if (grade == Ore.Grade.NORMAL) return super.getTranslationKey(stack);
        return super.getTranslationKey(stack) + "." + grade.getName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        Metal metal = getMetal(stack);
        if (metal != null) {
            int smeltAmount = this.getSmeltAmount(stack);
            int meltTemp = (int) this.getMeltTemp(stack);
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
                        infoTotal = String.format("%s: %s", I18n.format(Helpers.getTypeName(metal)), I18n.format("tfc.tooltip.units", smeltAmount),
                                I18n.format("tfc.tooltip.melttemp", meltTemp));
                    }
                    tooltip.add(infoTotal);
                    break;
                case ADVANCED:
                    // All info: "Metal: xx units / xx total"
                    String advancedTotal;
                    if (stack.getCount() > 1) {
                        advancedTotal = String.format("%s: %s: %s", I18n.format(Helpers.getTypeName(metal)),
                                I18n.format("tfc.tooltip.units.info_total", smeltAmount, smeltAmount * stack.getCount()),
                                I18n.format("tfc.tooltip.melttemp", meltTemp));
                    } else {
                        advancedTotal = String.format("%s: %s: %s", I18n.format(Helpers.getTypeName(metal)),
                                I18n.format("tfc.tooltip.units", smeltAmount), I18n.format("tfc.tooltip.melttemp", meltTemp));
                    }
                    tooltip.add(advancedTotal);

            }
        }
    }

    @Override
    public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            if (ore.isGraded()) {
                for (Ore.Grade grade : Ore.Grade.values()) {
                    items.add(new ItemStack(this, 1, grade.getMeta()));
                }
            } else {
                items.add(new ItemStack(this));
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return ore.getMetal() != null ? new ProviderHeat(nbt, ore.getMetal().getSpecificHeat(), ore.getMetal().getMeltTemp()) : null;
    }

    @Override
    @Nullable
    public Metal getMetal(ItemStack stack) {
        return ore.getMetal();
    }

    @Override
    public int getSmeltAmount(ItemStack stack) {
        return getGradeFromStack(stack).getSmeltAmount();
    }

    @Override
    public boolean canMelt(ItemStack stack) {
        return ore.canMelt();
    }

    @Override
    public float getMeltTemp(ItemStack stack) {
        if (this.canMelt(stack)) {
            return ore.getMetal().getMeltTemp();
        }
        return 0f;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL; // Fits in Small Vessels
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.MEDIUM; // Stacksize = 16
    }
}
