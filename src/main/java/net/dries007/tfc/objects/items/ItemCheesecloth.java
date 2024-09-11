package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemHandlerHelper;


import com.eerussianguy.firmalife.ConfigFL;
import com.eerussianguy.firmalife.recipe.StrainingRecipe;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.fluid.FluidWhitelistHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraftforge.fluids.BlockFluidBase.LEVEL;

@MethodsReturnNonnullByDefault

public class ItemCheesecloth extends ItemTFC {

  private static final int CAPACITY = 500;

  public ItemCheesecloth() {
    setHasSubtypes(true);
    setContainerItem(this);

    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DispenseFluidContainer.getInstance());
  }

  @SuppressWarnings("Nullpointerexception")
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack stack = playerIn.getHeldItem(handIn);
    if (!worldIn.isRemote && !stack.isEmpty()) {
      IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
      RayTraceResult rayTrace = this.rayTrace(worldIn, playerIn, true);

      //noinspection ConstantConditions - ray trace can be null
      if (rayTrace != null && bucketCap != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
        BlockPos pos = rayTrace.getBlockPos();
        if (bucketCap.drain(CAPACITY, false) == null) {
          ItemStack single = stack.copy();
          single.setCount(1);
          FluidActionResult result = FluidUtil.tryPickUpFluid(single, playerIn, worldIn, pos, rayTrace.sideHit);
          if (result.isSuccess()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
              return new ActionResult<>(EnumActionResult.SUCCESS, result.getResult());
            }

            if (!playerIn.isCreative()) {
              ItemHandlerHelper.giveItemToPlayer(playerIn, result.getResult());
            }

            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
          }
        } else {
          IBlockState stateAt = worldIn.getBlockState(pos);
          if (!(stateAt.getBlock().isReplaceable(worldIn, pos) || !stateAt.getMaterial().isSolid())) {
            pos = pos.offset(rayTrace.sideHit);
          }

          stateAt = worldIn.getBlockState(pos);
          if (stateAt.getBlock().isReplaceable(worldIn, pos) || !stateAt.getMaterial().isSolid()) {
            if (!stateAt.getMaterial().isLiquid()) {
              worldIn.destroyBlock(pos, true);
            }

            FluidStack fluidStack = bucketCap.drain(CAPACITY, !playerIn.isCreative());
            if (fluidStack != null) {
              Fluid fluid = fluidStack.getFluid();

              if (fluid.getBlock() instanceof BlockFluidBase) {
                worldIn.setBlockState(pos, fluid.getBlock().getDefaultState().withProperty(LEVEL, 1));
              } else if (fluid.getBlock() instanceof BlockLiquid) {
                try {
                  BlockLiquid flowingBlock = BlockStaticLiquid.getFlowingBlock(fluid.getBlock()
                          .getDefaultState()
                          .getMaterial());
                  worldIn.setBlockState(pos, flowingBlock.getDefaultState()
                          .withProperty(BlockLiquid.LEVEL, 1));
                } catch (IllegalArgumentException ignored) {
                }
              }
              if (recipeExists(fluidStack)) {
                ItemStack item = StrainingRecipe.get(fluidStack).getOutputItem();
                if (item != null) {
                  StackUtils.spawnItemStack(worldIn, pos, item);
                }
              }
            }
            worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 1.0F, 1.0F);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
          }
        }
      }
    }
    return new ActionResult<>(EnumActionResult.PASS, stack);
  }

  private boolean recipeExists(FluidStack inputFluid) {
    return StrainingRecipe.get(inputFluid) != null;
  }

  @Override
  @NotNull
  public String getItemStackDisplayName(@NotNull ItemStack stack) {
    IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (bucketCap != null) {
      FluidStack fluidStack = bucketCap.drain(CAPACITY, false);
      if (fluidStack != null) {
        String fluidName = fluidStack.getLocalizedName();
        return new TextComponentTranslation(getTranslationKey() + ".filled.name", fluidName).getFormattedText();
      }
    }
    return super.getItemStackDisplayName(stack);
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab)) {
      items.add(new ItemStack(this));
      for (String fluidName : ConfigFL.General.COMPAT.cheeseclothWhitelist) {
        Fluid fluid = FluidRegistry.getFluid(fluidName);
        if (fluid != null) {
          ItemStack stack = new ItemStack(this);
          IFluidHandlerItem cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
          if (cap != null) {
            cap.fill(new FluidStack(fluid, CAPACITY), true);
          }
          items.add(stack);
        }
      }
    }
  }

  @Override
  public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new FluidWhitelistHandler(stack, CAPACITY, ConfigFL.General.COMPAT.cheeseclothWhitelist);
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return Weight.LIGHT;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return Size.NORMAL;
  }

  @Override
  public boolean canStack(@NotNull ItemStack stack) {
    IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (bucketCap != null) {
      return bucketCap.drain(CAPACITY, false) == null;
    }
    return true;
  }
}
