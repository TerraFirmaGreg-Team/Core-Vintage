package net.dries007.tfc.objects.items.food;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityProviderFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.util.agriculture.Food;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemDynamicBowlFood extends ItemFoodTFC {

  public ItemDynamicBowlFood(Food food) {
    super(food);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new DynamicFoodHandler(nbt, food.getData());
  }

  @Nonnull
  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    ICapabilityFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
    if (food instanceof DynamicFoodHandler) {
      ItemStack bowlStack = ((DynamicFoodHandler) food).getBowlStack();
      if (entityLiving instanceof EntityPlayer) {
        ItemHandlerHelper.giveItemToPlayer((EntityPlayer) entityLiving, bowlStack);
      }
    }
    return super.onItemUseFinish(stack, worldIn, entityLiving);
  }

  public static class DynamicFoodHandler extends CapabilityProviderFood {

    private ItemStack bowlStack;

    public DynamicFoodHandler(@Nullable NBTTagCompound nbt, FoodData data) {
      super(nbt, data);
      this.bowlStack = ItemStack.EMPTY;
    }

    public void initCreationDataAndBowl(ItemStack bowlStack, FoodData data) {
      this.bowlStack = bowlStack;
      this.data = data;
    }

    @Override
    public NBTTagCompound serializeNBT() {
      NBTTagCompound nbt = super.serializeNBT();
      nbt.setTag("bowl", bowlStack.serializeNBT());
      return nbt;
    }

    @Override
    public void deserializeNBT(@Nullable NBTTagCompound nbt) {
      super.deserializeNBT(nbt);
      if (nbt != null) {
        bowlStack = new ItemStack(nbt.getCompoundTag("bowl"));
      }
    }

    @Override
    protected boolean isDynamic() {
      return true;
    }

    @Nonnull
    public ItemStack getBowlStack() {
      return bowlStack.copy();
    }
  }
}
