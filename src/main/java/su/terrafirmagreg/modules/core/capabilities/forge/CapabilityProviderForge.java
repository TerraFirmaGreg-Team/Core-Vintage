package su.terrafirmagreg.modules.core.capabilities.forge;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import net.dries007.tfc.util.forge.ForgeStep;
import net.dries007.tfc.util.forge.ForgeSteps;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderForge implements ICapabilitySerializable<NBTTagCompound>, ICapabilityForge {

  protected final ForgeSteps steps;
  protected int work;
  protected ResourceLocation recipeName;

  public CapabilityProviderForge(@Nullable NBTTagCompound nbt) {
    steps = new ForgeSteps();
    deserializeNBT(nbt);
  }

  public CapabilityProviderForge() {
    // for custom implementations
    steps = new ForgeSteps();
  }

  @Override
  public int getWork() {
    return work;
  }

  @Override
  public void setWork(int work) {
    this.work = work;
  }

  @Override
  @Nullable
  public ResourceLocation getRecipeName() {
    return recipeName;
  }

  @Override
  public void setRecipe(@Nullable ResourceLocation recipeName) {
    this.recipeName = recipeName;
  }

  @Override
  @Nonnull
  public ForgeSteps getSteps() {
    return steps;
  }

  @Override
  public void addStep(ForgeStep step) {
    steps.addStep(step);
    work += step.getStepAmount();
  }

  @Override
  public void reset() {
    steps.reset();
    recipeName = null;
    work = 0;
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityForgeable.CAPABILITY;
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }

  @Override
  @Nonnull
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = new NBTTagCompound();

    nbt.setInteger("work", work);
    nbt.setTag("steps", steps.serializeNBT());
    if (recipeName != null) {
      nbt.setString("recipe", recipeName.toString());
    }

    return nbt;
  }

  @Override
  public void deserializeNBT(@Nullable NBTTagCompound nbt) {
    if (nbt != null) {
      work = nbt.getInteger("work");
      recipeName = nbt.hasKey("recipe") ? new ResourceLocation(nbt.getString("recipe")) : null; // stops defaulting to empty string
      steps.deserializeNBT(nbt.getCompoundTag("steps"));
    }
  }
}
