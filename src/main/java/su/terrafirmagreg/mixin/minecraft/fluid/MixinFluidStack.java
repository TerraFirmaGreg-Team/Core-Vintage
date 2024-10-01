package su.terrafirmagreg.mixin.minecraft.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import org.jetbrains.annotations.NotNull;

@Mixin(value = FluidStack.class, remap = false)
public abstract class MixinFluidStack {

  @Shadow
  public int amount;

  @Shadow
  public NBTTagCompound tag;

  @NotNull
  @Shadow
  public abstract FluidStack copy();

  @Shadow
  public abstract boolean isFluidEqual(FluidStack other);

  @Shadow
  public abstract Fluid getFluid();
}
