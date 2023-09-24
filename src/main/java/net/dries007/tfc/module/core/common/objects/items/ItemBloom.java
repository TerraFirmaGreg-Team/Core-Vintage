package net.dries007.tfc.module.core.common.objects.items;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.ForgeableMeasurableMetalHandler;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.capability.metal.IMaterialItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.module.core.common.objects.CreativeTabsTFC;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;


@ParametersAreNonnullByDefault
public class ItemBloom extends TFCItem implements IMaterialItem {
    private final boolean meltable;

    public ItemBloom(boolean meltable) {
        this.meltable = meltable;
        if (meltable) {
            setRegistryName(Tags.MOD_ID, "bloom/refined");
            setTranslationKey(Tags.MOD_ID + ".bloom.refined");
        } else {
            setRegistryName(Tags.MOD_ID, "bloom/unrefined");
            setTranslationKey(Tags.MOD_ID + ".bloom.unrefined");
        }
        setCreativeTab(CreativeTabsTFC.MISC_TAB);
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
    public Material getMaterial(ItemStack stack) {
        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap instanceof IForgeableMeasurableMetal) {
            return ((IForgeableMeasurableMetal) cap).getMaterial();
        }
        return TFGMaterials.Unknown;
    }

    @Override
    public int getSmeltAmount(ItemStack stack) {
        IForgeable cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
        if (cap instanceof IForgeableMeasurableMetal) {
            int amount = ((IForgeableMeasurableMetal) cap).getMetalAmount();
            if (amount > 144) amount = 144;
            return amount;
        }
        return 0;
    }

    @Override
    public boolean canMelt(ItemStack stack) {
        return meltable;
    }

    @Override
    @Nonnull
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this);
            var cap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            if (cap instanceof IForgeableMeasurableMetal handler) {
                handler.setMaterial(Materials.Iron);
                handler.setMetalAmount(144);
                items.add(stack);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addMetalInfo(ItemStack stack, List<String> text) {
        var forgeableCap = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);

        if (forgeableCap instanceof IForgeableMeasurableMetal measurableMetal) {

            var material = measurableMetal.getMaterial();
            var property = material.getProperty(TFGPropertyKey.HEAT);

            if (property == null) throw new RuntimeException(String.format("No heat property for %s", material));

            text.add("");
            text.add(I18n.format("tfc.tooltip.containsmetal"));
            text.add(I18n.format("tfc.tooltip.metalname", measurableMetal.getMaterial().getLocalizedName()));
            text.add(I18n.format("tfc.tooltip.units", getSmeltAmount(stack) * stack.getCount()));
            text.add(I18n.format("tfc.tooltip.melttemp", measurableMetal.getMeltTemp()));
            text.add(I18n.format("tfc.tooltip.tier", property.getTier()));
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (nbt == null) {
            return new ForgeableMeasurableMetalHandler(Materials.Iron, 144);
        } else {
            return new ForgeableMeasurableMetalHandler(nbt);
        }
    }
}
