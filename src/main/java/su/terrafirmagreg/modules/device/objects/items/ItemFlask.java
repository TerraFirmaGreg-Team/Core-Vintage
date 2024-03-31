package su.terrafirmagreg.modules.device.objects.items;

import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.capability.FluidWhitelistHandlerComplex;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.util.FluidTransferHelper;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.item.ICustomMesh;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.ModuleDeviceConfig;
import su.terrafirmagreg.modules.device.data.ItemsDevice;
import su.terrafirmagreg.modules.device.data.SoundDevice;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

import static net.dries007.tfc.api.capability.food.IFoodStatsTFC.MAX_PLAYER_THIRST;
import static su.terrafirmagreg.Tags.MOD_ID;

public abstract class ItemFlask extends ItemFluidContainer implements IItemSize, IAutoReg, ICustomMesh {
	private final int capacity;
	private final int drink;

	public ItemFlask(int capacity, int drink) {
		super(capacity);

		this.capacity = capacity;
		this.drink = drink;

		setHasSubtypes(true);

		if (ModuleDeviceConfig.ITEMS.WATER_FLASKS.damageFactor == 0) {
			setMaxDamage(Integer.MAX_VALUE);
		} else {
			setMaxDamage(capacity / ModuleDeviceConfig.ITEMS.WATER_FLASKS.damageFactor);
		}

	}

	// Fix #12 by actually implementing the MC function that limits stack sizes
	@Override
	public int getItemStackLimit(@NotNull ItemStack stack) {
		return getStackSize(stack);
	}

	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack stack) {
		return Size.SMALL;
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack stack) {
		return Weight.MEDIUM;
	}

	@Override
	public boolean canStack(@Nonnull ItemStack stack) {return false;}

	@Override
	public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FluidWhitelistHandlerComplex(stack, capacity, FluidsTFC.getAllWrappers()
				.stream()
				.filter(x -> x.get(DrinkableProperty.DRINKABLE) != null)
				.map(FluidWrapper::get)
				.collect(Collectors.toSet()));
	}

	@Override
	public ItemMeshDefinition getCustomMesh() {
		var modelFull = new ModelResourceLocation(ModUtils.getID(getName()), "inventory");
		var model4 = new ModelResourceLocation(ModUtils.getID(getName() + "/4"), "inventory");
		var model3 = new ModelResourceLocation(ModUtils.getID(getName() + "/3"), "inventory");
		var model2 = new ModelResourceLocation(ModUtils.getID(getName() + "/2"), "inventory");
		var model1 = new ModelResourceLocation(ModUtils.getID(getName() + "/1"), "inventory");
		var model0 = new ModelResourceLocation(ModUtils.getID(getName() + "/0"), "inventory");

		return stack -> switch ((int) Math.floor(getLiquidAmount(stack) / (double) capacity * 5F)) {
			case 5 -> modelFull;
			case 4 -> model4;
			case 3 -> model3;
			case 2 -> model2;
			case 1 -> model1;
			default -> model0;
		};
	}

	public int getLiquidAmount(ItemStack stack) {
		int content = 0;
		IFluidHandler flaskCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (flaskCap != null) {
			FluidStack drained = flaskCap.drain(capacity, false);
			if (drained != null) {
				content = drained.amount;
			}
		}
		return content;
	}

	/**
	 * Returns the packed int RGB value used to render the durability bar in the GUI.
	 * Retrieves no-alpha RGB color from liquid to use in durability bar
	 *
	 * @param stack Stack to get color from
	 * @return A packed RGB value for the durability colour (0x00RRGGBB)
	 */
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		IFluidHandler flaskCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (flaskCap != null) {
			FluidStack drained = flaskCap.drain(capacity, false);
			if (drained != null) {
				Fluid fluid = drained.getFluid();
				return fluid.getColor();
			}
		}
		return super.getRGBDurabilityForDisplay(stack);
	}


	@SuppressWarnings("ConstantConditions")
	@Override
	public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World world, EntityPlayer player, @NotNull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!stack.isEmpty()) {
			// Do not use in creative game mode
			if (player.isCreative())
				return new ActionResult<>(EnumActionResult.PASS, stack);

			IFluidHandler flaskCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (flaskCap != null) {
				// If contains fluid, allow emptying with shift-right-click
				if (player.isSneaking()) {
					flaskCap.drain(capacity, true);

					return new ActionResult<>(EnumActionResult.SUCCESS, stack);
				}
				// Try to Fill
				RayTraceResult rayTrace = rayTrace(world, player, true);
				FluidStack cont = flaskCap.drain(capacity, false);
				if (!world.isRemote && (cont == null || cont.amount < capacity) && rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
					ItemStack single = stack.copy();
					single.setCount(1);
					FluidActionResult result = FluidTransferHelper.tryPickUpFluidGreedy(single, player, world, rayTrace.getBlockPos(), rayTrace.sideHit, Fluid.BUCKET_VOLUME, false);
					if (result.isSuccess()) {
						stack.shrink(1);
						ItemStack res = result.getResult();
						if (stack.isEmpty()) {
							return new ActionResult<>(EnumActionResult.SUCCESS, res);
						}
						ItemHandlerHelper.giveItemToPlayer(player, res);
						return new ActionResult<>(EnumActionResult.SUCCESS, stack);
					}
				}
				//Try to Drink
				FoodStats stats = player.getFoodStats();
				if (stats instanceof FoodStatsTFC && ((FoodStatsTFC) stats).getThirst() >= MAX_PLAYER_THIRST) {
					// Don't drink if not thirsty
					return new ActionResult<>(EnumActionResult.FAIL, stack);
				} else if (cont != null && cont.amount >= drink) {
					player.setActiveHand(hand);
				}
				return new ActionResult<>(EnumActionResult.SUCCESS, stack);
			}
		}
		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving) {
		IFluidHandler flaskCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (flaskCap != null) {
			FluidStack total = flaskCap.drain(capacity, false);
			if (total != null && total.amount > 0) {
				FluidStack fluidConsumed = flaskCap.drain(drink, true);
				if (fluidConsumed != null) {
					DrinkableProperty drinkable = FluidsTFC.getWrapper(fluidConsumed.getFluid())
							.get(DrinkableProperty.DRINKABLE);
					if (drinkable != null) {
						drinkable.onDrink((EntityPlayer) entityLiving);
						if (stack.getItemDamage() == stack.getMaxDamage()) {
							ResourceLocation name = stack.getItem().getRegistryName();
							//break item, play sound
							worldIn.playSound(null, entityLiving.getPosition(), SoundDevice.FLASK_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
							assert name != null;
							if (name.toString().contains("leather")) {
								ItemHandlerHelper.giveItemToPlayer((EntityPlayer) entityLiving, new ItemStack(ItemsDevice.BROKEN_LEATHER_FLASK));
							} else {
								ItemHandlerHelper.giveItemToPlayer((EntityPlayer) entityLiving, new ItemStack(ItemsDevice.BROKEN_IRON_FLASK));
							}
							stack.shrink(1); //race condition here, seems to only sometimes work if done before giving broken flask
						} else {
							stack.damageItem(1, entityLiving);
						}
					}
				}
			}
		}
		return stack;
	}

	@Override
	@Nonnull
	public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(@NotNull ItemStack stack) {
		return 32;
	}

	@Override
	@Nonnull
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (bucketCap != null) {
			FluidStack fluidStack = bucketCap.drain(capacity, false);
			if (fluidStack != null) {
				String fluidname = fluidStack.getLocalizedName();
				return new TextComponentTranslation("item." + MOD_ID + this.getName().replaceAll("/", ".") + ".filled.name", fluidname).getFormattedText();
			}
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			items.add(new ItemStack(this));
//			for (FluidWrapper wrapper : FluidsTFC.getAllWrappers()) {
//				if (wrapper.get(DrinkableProperty.DRINKABLE) != null) {
//					ItemStack stack = new ItemStack(this);
//					IFluidHandlerItem cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
//					if (cap != null) {
//						cap.fill(new FluidStack(wrapper.get(), capacity), true);
//					}
//					items.add(stack);
//				}
//			}
		}
	}


}
