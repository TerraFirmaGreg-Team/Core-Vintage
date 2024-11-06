package su.terrafirmagreg.core.mixin.tfctech.jei;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfctech.compat.jei.wrappers.UnmoldRecipeWrapper;
import tfctech.objects.items.ceramics.ItemTechMold;
import tfctech.objects.items.metal.ItemTechMetal;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(value = UnmoldRecipeWrapper.class, remap = false)
@ParametersAreNonnullByDefault
public class UnmoldRecipeWrapperMixin implements IRecipeWrapper {

  @Shadow
  @Final
  @Mutable
  private ItemStack mold;
  @Shadow
  @Final
  @Mutable
  private ItemStack output;

  private ItemTechMetal.ItemType type;
  private Metal metal;

  @Inject(method = "<init>(Lnet/dries007/tfc/api/types/Metal;Ltfctech/objects/items/metal/ItemTechMetal$ItemType;)V", at = @At(value = "TAIL"), remap = false)
  public void onUnmoldRecipeWrapper(Metal metal, ItemTechMetal.ItemType type, CallbackInfo ci) {
    this.type = type;
    this.metal = metal;

    mold = new ItemStack(ItemTechMold.get(type));
    IFluidHandler cap = mold.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
    if (cap instanceof IMoldHandler) {
      cap.fill(new FluidStack(FluidsTFC.getFluidFromMetal(metal), 144), true);
    }

    if (type == ItemTechMetal.ItemType.RACKWHEEL_PIECE) {
      switch (metal.toString()) {
        case "platinum":
        case "pig_iron":
        case "sterling_silver":
        case "tin":
        case "silver":
        case "rose_gold":
        case "lead":
        case "gold":
        case "copper":
        case "brass":
        case "bismuth_bronze": {
          output = ItemStack.EMPTY;
          break;
        }
        default: {
          //noinspection ConstantConditions
          output = new ItemStack(ItemTechMetal.get(metal, type));
        }
      }
    } else {
      //noinspection ConstantConditions
      output = new ItemStack(ItemTechMetal.get(metal, type));
    }
  }

  public void getIngredients(IIngredients ingredients) {
    if (type == ItemTechMetal.ItemType.RACKWHEEL_PIECE) {
      switch (metal.toString()) {
        case "platinum":
        case "pig_iron":
        case "sterling_silver":
        case "tin":
        case "silver":
        case "rose_gold":
        case "lead":
        case "gold":
        case "copper":
        case "brass":
        case "bismuth_bronze": {
          break;
        }
        default: {
          ingredients.setInput(VanillaTypes.ITEM, mold);
          ingredients.setOutput(VanillaTypes.ITEM, output);
        }
      }
    } else {
      ingredients.setInput(VanillaTypes.ITEM, mold);
      ingredients.setOutput(VanillaTypes.ITEM, output);
    }

  }
}
