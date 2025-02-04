package net.dries007.firmalife;


import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalCow;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalGoat;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalYak;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalZebu;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import net.dries007.firmalife.entity.CombatGreenhouseTask;
import net.dries007.firmalife.gui.FLGuiHandler;
import net.dries007.firmalife.registry.BlocksFL;
import net.dries007.tfc.util.Helpers;

@Mod.EventBusSubscriber(modid = FirmaLife.MOD_ID)
public class CommonEventHandlerFL {

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
    if (!ConfigFL.General.COMPAT.customMilk) {return;}
    if (event.getWorld().isRemote) {return;}
    Entity entity = event.getTarget();
    ItemStack item = event.getItemStack();
    EntityPlayer player = event.getEntityPlayer();
    if (!item.isEmpty()) {
      IFluidHandlerItem bucket = item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
      if (bucket != null) //checking if it can be filled
      {
        FluidActionResult fillResult = FluidUtil.tryFillContainer(item, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), Fluid.BUCKET_VOLUME, player, false);
        if (fillResult.isSuccess() && entity instanceof EntityAnimalCow cow) {
          //we can just cast the entity to a cow to test familiarity etc
          Fluid fluid = FluidsCore.MILK.get();
          boolean foundMilkable = false;
          if (entity instanceof EntityAnimalYak)//have to check the original entity to get the proper instanceof however
          {
            foundMilkable = true;
            fluid = FluidsCore.YAK_MILK.get();
          } else if (entity instanceof EntityAnimalGoat) {
            foundMilkable = true;
            fluid = FluidsCore.GOAT_MILK.get();
          } else if (entity instanceof EntityAnimalZebu) {
            foundMilkable = true;
            fluid = FluidsCore.ZEBU_MILK.get();
          }
          if (foundMilkable) {
            if (cow.getFamiliarity() > 0.15f && cow.isReadyForAnimalProduct()) {
              FluidTank fluidHandler = new FluidTank(fluid, 1000, 1000);
              player.setHeldItem(player.getActiveHand(), FluidUtil.tryFillContainerAndStow(item, fluidHandler, new PlayerInvWrapper(player.inventory), Fluid.BUCKET_VOLUME, player, true)
                .getResult());
              cow.setProductsCooldown();
              event.setCanceled(true);
            }
          }
        }

      }
    }
  }


  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
    World world = event.getWorld();
    if (world.isRemote) {return;}
    Item item = event.getItemStack().getItem();

    EntityPlayer player = event.getEntityPlayer();
    BlockPos pos = event.getPos();
    if (item == Item.getItemFromBlock(BlocksFL.PUMPKIN_FRUIT) && Helpers.playerHasItemMatchingOre(player.inventory, "knife")) {
      FLGuiHandler.openGui(world, pos, player, FLGuiHandler.Type.KNAPPING_PUMPKIN);
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    World world = event.getWorld();
    if (world.isRemote) {return;}
    ItemStack stack = event.getItemStack();
    Item item = stack.getItem();

    EntityPlayer player = event.getEntityPlayer();
    BlockPos pos = event.getPos();
    EnumFacing facing = event.getFace();
    if (item == ItemsAnimal.WOOL_YARN.get() && player.isSneaking() && facing != null) {
      BlockPos offsetPos = pos.offset(facing);
      if (world.isAirBlock(offsetPos)) {
        IBlockState string = BlocksFL.WOOL_STRING.getStateForPlacement(world, player, facing, offsetPos);
        world.setBlockState(offsetPos, string);
        stack.shrink(1);
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void onEntitySpawn(EntityJoinWorldEvent event) {
    Entity entity = event.getEntity();
    if (entity instanceof EntityZombie zombie) {
      zombie.tasks.addTask(4, new CombatGreenhouseTask(zombie));
    }
  }
}
