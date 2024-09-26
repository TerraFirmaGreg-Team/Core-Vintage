package com.eerussianguy.firmalife;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalCow;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalGoat;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalYak;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalZebu;

import net.minecraft.block.Block;
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
import net.minecraftforge.event.world.BlockEvent;
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

import com.eerussianguy.firmalife.entity.CombatGreenhouseTask;
import com.eerussianguy.firmalife.registry.BlocksFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.client.gui.FLGuiHandler;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ItemFruitPole;

import static su.terrafirmagreg.data.Constants.MODID_FL;
import static su.terrafirmagreg.data.MathConstants.RNG;

@Mod.EventBusSubscriber(modid = MODID_FL)
public class CommonEventHandlerFL {

  @SubscribeEvent
  public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
    final IBlockState state = event.getState();
    final Block block = state.getBlock();

    if (block instanceof BlockFruitTreeLeaves) {
      event.getDrops().add(new ItemStack(ItemsFL.FRUIT_LEAF, 2 + RNG.nextInt(4)));
    } else if (block instanceof BlockFruitTreeTrunk) {
      if (event.isCanceled()) {
        event.setCanceled(false);
      }
      IFruitTree tree = ((BlockFruitTreeTrunk) block).getTree();
      ItemFruitPole pole = ItemFruitPole.get(tree);
      if (pole != null) {
        event.getDrops().add(new ItemStack(pole));
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
    if (!ConfigFL.General.COMPAT.customMilk) {
      return;
    }
    if (event.getWorld().isRemote) {
      return;
    }
    Entity entity = event.getTarget();
    ItemStack item = event.getItemStack();
    EntityPlayer player = event.getEntityPlayer();
    if (!item.isEmpty()) {
      IFluidHandlerItem bucket = item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
      if (bucket != null) //checking if it can be filled
      {
        FluidActionResult fillResult = FluidUtil.tryFillContainer(item, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)),
                                                                  Fluid.BUCKET_VOLUME, player, false);
        if (fillResult.isSuccess() && entity instanceof EntityAnimalCow cow) {
          //we can just cast the entity to a cow to test familiarity etc
          Fluid fluid = FluidsTFC.MILK.get();
          boolean foundMilkable = false;
          if (entity instanceof EntityAnimalYak)//have to check the original entity to get the proper instanceof however
          {
            foundMilkable = true;
            fluid = FluidsTFC.YAK_MILK.get();
          } else if (entity instanceof EntityAnimalGoat) {
            foundMilkable = true;
            fluid = FluidsTFC.GOAT_MILK.get();
          } else if (entity instanceof EntityAnimalZebu) {
            foundMilkable = true;
            fluid = FluidsTFC.ZEBU_MILK.get();
          }
          if (foundMilkable) {
            if (cow.getFamiliarity() > 0.15f && cow.isReadyForAnimalProduct()) {
              FluidTank fluidHandler = new FluidTank(fluid, 1000, 1000);
              player.setHeldItem(player.getActiveHand(), FluidUtil
                .tryFillContainerAndStow(item, fluidHandler, new PlayerInvWrapper(player.inventory), Fluid.BUCKET_VOLUME, player,
                                         true)
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
    if (world.isRemote) {
      return;
    }
    Item item = event.getItemStack().getItem();

    EntityPlayer player = event.getEntityPlayer();
    BlockPos pos = event.getPos();
    if (item == Item.getItemFromBlock(BlocksFL.PUMPKIN_FRUIT) && OreDictUtils.playerHasItemMatchingOre(player.inventory, "knife")) {
      FLGuiHandler.openGui(world, pos, player, FLGuiHandler.Type.KNAPPING_PUMPKIN);
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    World world = event.getWorld();
    if (world.isRemote) {
      return;
    }
    ItemStack stack = event.getItemStack();
    Item item = stack.getItem();

    EntityPlayer player = event.getEntityPlayer();
    BlockPos pos = event.getPos();
    EnumFacing facing = event.getFace();
    if (item == ItemsAnimal.WOOL_YARN && player.isSneaking() && facing != null) {
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
