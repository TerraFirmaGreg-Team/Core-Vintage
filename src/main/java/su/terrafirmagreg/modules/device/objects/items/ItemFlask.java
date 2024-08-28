package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.base.item.spi.IItemSettings;
import su.terrafirmagreg.api.registry.provider.IProviderItemMesh;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
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


import net.dries007.tfc.api.capability.fluid.FluidWhitelistHandlerComplex;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.util.FluidTransferHelper;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.stream.Collectors;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.modules.food.api.IFoodStatsTFC.MAX_PLAYER_THIRST;

@Getter
public abstract class ItemFlask extends ItemFluidContainer implements IItemSettings, IProviderItemMesh {

    protected final Settings settings;
    private final int capacity;
    private final int drink;

    public ItemFlask(String name, int capacity, int drink) {
        super(capacity);

        this.capacity = capacity;
        this.drink = drink;

        this.settings = Settings.of()
                .registryKey("device/flask/" + name)
                .notCanStack();

        setHasSubtypes(true);

        if (ConfigDevice.ITEMS.WATER_FLASKS.damageFactor == 0) {
            setMaxDamage(Integer.MAX_VALUE);
        } else {
            setMaxDamage(capacity / ConfigDevice.ITEMS.WATER_FLASKS.damageFactor);
        }

    }

    // Fix #12 by actually implementing the MC function that limits stack sizes
    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidWhitelistHandlerComplex(stack, capacity, FluidsTFC.getAllWrappers()
                .stream()
                .filter(x -> x.get(DrinkableProperty.DRINKABLE) != null)
                .map(FluidWrapper::get)
                .collect(Collectors.toSet()));
    }

    @Override
    public ItemMeshDefinition getItemMesh() {
        var modelFull = new ModelResourceLocation(ModUtils.resource(getRegistryKey()), "inventory");
        var model4 = new ModelResourceLocation(ModUtils.resource(getRegistryKey() + "/4"), "inventory");
        var model3 = new ModelResourceLocation(ModUtils.resource(getRegistryKey() + "/3"), "inventory");
        var model2 = new ModelResourceLocation(ModUtils.resource(getRegistryKey() + "/2"), "inventory");
        var model1 = new ModelResourceLocation(ModUtils.resource(getRegistryKey() + "/1"), "inventory");
        var model0 = new ModelResourceLocation(ModUtils.resource(getRegistryKey() + "/0"), "inventory");

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
     * Returns the packed int RGB value used to render the durability bar in the GUI. Retrieves no-alpha RGB color from liquid to use in durability bar
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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
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
                if (!world.isRemote && (cont == null || cont.amount < capacity) && rayTrace != null &&
                        rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
                    ItemStack single = stack.copy();
                    single.setCount(1);
                    FluidActionResult result = FluidTransferHelper.tryPickUpFluidGreedy(single, player, world, rayTrace.getBlockPos(),
                            rayTrace.sideHit, Fluid.BUCKET_VOLUME, false);
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
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
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
                            worldIn.playSound(null, entityLiving.getPosition(), SoundsDevice.FLASK_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
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
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (bucketCap != null) {
            FluidStack fluidStack = bucketCap.drain(capacity, false);
            if (fluidStack != null) {
                String fluidname = fluidStack.getLocalizedName();
                return new TextComponentTranslation("item." + MOD_ID + this.getRegistryKey().replaceAll("/", ".") + ".filled.name",
                        fluidname).getFormattedText();
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
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
