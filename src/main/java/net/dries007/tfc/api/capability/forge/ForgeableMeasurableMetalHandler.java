package net.dries007.tfc.api.capability.forge;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Extension of forgeable heatable handler for blooms
 */
public class ForgeableMeasurableMetalHandler extends ForgeableHeatableHandler implements IForgeableMeasurableMetal {
    private int metalAmount;
    private Material material;

    public ForgeableMeasurableMetalHandler(Material material, int metalAmount) {
        this.metalAmount = metalAmount;
        this.material = material;

        var property = material.getProperty(TFGPropertyKey.HEAT);
        if (property == null) throw new RuntimeException(String.format("No heat property for material: %s", material));

        this.heatCapacity = property.getSpecificHeat();
        this.meltTemp = property.getMeltTemp();

    }

    public ForgeableMeasurableMetalHandler(@Nonnull NBTTagCompound nbt) {
        var property = TFGMaterials.Unknown.getProperty(TFGPropertyKey.HEAT);

        if (property == null) throw new RuntimeException(String.format("No heat property for material: %s", material));

        this.metalAmount = 0;
        this.material = TFGMaterials.Unknown;
        this.heatCapacity = property.getSpecificHeat();
        this.meltTemp = property.getMeltTemp();
        deserializeNBT(nbt);
    }

    public int getMetalAmount() {
        return metalAmount;
    }

    public void setMetalAmount(int metalAmount) {
        this.metalAmount = metalAmount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    @Nonnull
    public NBTTagCompound serializeNBT() {
        var nbt = super.serializeNBT();
        nbt.setInteger("metalAmount", metalAmount);
        nbt.setString("metal", material.getName());
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nullable NBTTagCompound nbt) {
        if (nbt != null) {
            metalAmount = nbt.getInteger("metalAmount");
            var materialName = nbt.getString("metal");
            material = GregTechAPI.materialManager.getMaterial(materialName);
            if (material == null) {
                material = TFGMaterials.Unknown;
            }

            var property = material.getProperty(TFGPropertyKey.HEAT);

            if (property == null)
                throw new RuntimeException(String.format("No heat property for material: %s", material));

            this.meltTemp = property.getMeltTemp();
            this.heatCapacity = property.getSpecificHeat();
        }
        super.deserializeNBT(nbt);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addHeatInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
        String desc = TextFormatting.WHITE + I18n.format("tfc.tooltip.units", metalAmount);
        text.add(desc);
        super.addHeatInfo(stack, text);
    }
}
