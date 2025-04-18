package su.terrafirmagreg.modules.device.object.item.spi;

import su.terrafirmagreg.api.base.object.item.spi.BaseItemFluid;
import su.terrafirmagreg.api.library.MeshDefinitionFix;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderItemMesh;
import su.terrafirmagreg.modules.core.capabilities.fluid.CapabilityFluidItem;
import su.terrafirmagreg.modules.core.capabilities.fluid.CapabilityProviderFluid;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.util.FluidTransferHelper;

import lombok.Getter;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.modules.food.api.IFoodStatsTFC.MAX_PLAYER_THIRST;

@Getter
public abstract class ItemFlask extends BaseItemFluid implements IProviderItemMesh {

  private final int drink;

  public ItemFlask(int capacity, int drink) {
    super(capacity);

    this.drink = drink;

    getSettings()
      .maxStackSize(1)
      .oreDict("flask")
      .capability(
        CapabilityProviderFluid.of(capacity, true, getValidFluids())
      );

    setHasSubtypes(true);
    if (ConfigDevice.ITEM.WATER_FLASKS.damageFactor == 0) {
      getSettings()
        .maxDamage(Integer.MAX_VALUE);
    } else {
      getSettings()
        .maxDamage(capacity / ConfigDevice.ITEM.WATER_FLASKS.damageFactor);
    }

  }

  public static Set<Fluid> getValidFluids() {
    return FluidsTFC.getAllWrappers()
      .stream()
      .filter(x -> x.get(DrinkableProperty.DRINKABLE) != null)
      .map(FluidWrapper::get)
      .collect(Collectors.toSet());
  }

  @Override
  public ModelResourceLocation[] getModelLocations() {
    var name = Objects.requireNonNull(getRegistryName());
    return new ModelResourceLocation[]{
      new ModelResourceLocation(name + "/0", "inventory"),
      new ModelResourceLocation(name + "/1", "inventory"),
      new ModelResourceLocation(name + "/2", "inventory"),
      new ModelResourceLocation(name + "/3", "inventory"),
      new ModelResourceLocation(name + "/4", "inventory"),
      new ModelResourceLocation(name, "inventory")
    };
  }

  @Override
  public ItemMeshDefinition getItemMesh() {
    return MeshDefinitionFix.create(stack ->
      switch ((int) Math.floor(getLiquidAmount(stack) / (double) capacity * 5F)) {
        case 5 -> getModelLocations()[5];
        case 4 -> getModelLocations()[4];
        case 3 -> getModelLocations()[3];
        case 2 -> getModelLocations()[2];
        case 1 -> getModelLocations()[1];
        default -> getModelLocations()[0];
      });
  }

  public int getLiquidAmount(ItemStack stack) {
    int content = 0;
    IFluidHandler flaskCap = CapabilityFluidItem.get(stack);
    if (flaskCap != null) {
      FluidStack drained = flaskCap.drain(capacity, false);
      if (drained != null) {
        content = drained.amount;
      }
    }
    return content;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty()) {
      // Do not use in creative game mode
      if (player.isCreative()) {
        return new ActionResult<>(EnumActionResult.PASS, stack);
      }

      IFluidHandlerItem flaskCap = CapabilityFluidItem.get(stack);
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
          FluidActionResult result = FluidTransferHelper.tryPickUpFluidGreedy(single, player, world,
            rayTrace.getBlockPos(),
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
        if (stats instanceof FoodStatsTFC foodStatsTFC && foodStatsTFC.getThirst() >= MAX_PLAYER_THIRST) {
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
    IFluidHandlerItem flaskCap = CapabilityFluidItem.get(stack);
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
              worldIn.playSound(null, entityLiving.getPosition(), SoundsDevice.FLASK_BREAK.get(), SoundCategory.PLAYERS, 1.0f, 1.0f);
              if (name.toString().contains("leather")) {
                ItemHandlerHelper.giveItemToPlayer((EntityPlayer) entityLiving, new ItemStack(ItemsDevice.LEATHER_FLASK_BROKEN.get()));
              } else {
                ItemHandlerHelper.giveItemToPlayer((EntityPlayer) entityLiving,
                  new ItemStack(ItemsDevice.METAL_FLASK_BROKEN.get()));
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
    IFluidHandlerItem bucketCap = CapabilityFluidItem.get(stack);
    if (bucketCap != null) {
      FluidStack fluidStack = bucketCap.drain(capacity, false);
      if (fluidStack != null) {
        String fluidname = fluidStack.getLocalizedName();
        return new TextComponentTranslation(ModUtils.localize("item", ModUtils.localize(getRegistryName()), "filled.name"), fluidname).getFormattedText();
      }
    }
    return super.getItemStackDisplayName(stack);
  }

  /**
   * Returns the packed int RGB value used to render the durability bar in the GUI. Retrieves no-alpha RGB color from liquid to use in durability bar
   *
   * @param stack Stack to get color from
   * @return A packed RGB value for the durability colour (0x00RRGGBB)
   */
  @Override
  public int getRGBDurabilityForDisplay(ItemStack stack) {
    IFluidHandler flaskCap = CapabilityFluidItem.get(stack);
    if (flaskCap != null) {
      FluidStack drained = flaskCap.drain(capacity, false);
      if (drained != null) {
        Fluid fluid = drained.getFluid();
        return fluid.getColor();
      }
    }
    return super.getRGBDurabilityForDisplay(stack);
  }

}
