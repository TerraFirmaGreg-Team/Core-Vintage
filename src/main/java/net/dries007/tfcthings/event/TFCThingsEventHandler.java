package net.dries007.tfcthings.event;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.animal.content.entity.livestock.EntityAnimalSheep;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.objects.items.ItemRopeJavelin;
import net.dries007.tfc.util.Helpers;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Mods.ModIDs.TFCTHINGS)
public class TFCThingsEventHandler {


  @SubscribeEvent
  public static void onItemToss(ItemTossEvent event) {
    if (event.getEntityItem().getItem().getItem() instanceof ItemRopeJavelin javelin) {
      javelin.retractJavelin(event.getEntityItem().getItem(), event.getEntity().getEntityWorld());
    }
  }

//  @SubscribeEvent
//  public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {
//    var world = event.getWorld();
//    var itemStack = event.getItemStack();
//    var item = itemStack.getItem();
//    var block = world.getBlockState(event.getPos()).getBlock();
//    if (item instanceof ItemRopeJavelin itemRopeJavelin) {
//      if (RegistryUtils.isTag(block, Tags.TOOL_RACK)) {
//        itemRopeJavelin.retractJavelin(itemStack, world);
//      }
//    }
//  }

  @SubscribeEvent
  public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
    if (event.getTarget() instanceof EntityAnimalSheep sheep) {
      var stack = event.getItemStack();
      final Set<String> toolClasses = stack.getItem().getToolClasses(stack);
      if ((toolClasses.contains(ToolClasses.SHEARS) || toolClasses.contains(ToolClasses.KNIFE)) && sheep.hasWool() && sheep.getFamiliarity() == 1.0F) {
        if (!sheep.world.isRemote) {
          ItemStack woolStack = new ItemStack(ItemsAnimal.WOOL);
          Helpers.spawnItemStack(sheep.world, new BlockPos(sheep.posX, sheep.posY, sheep.posZ), woolStack);
        }
      }
    }
//        if(event.getTarget() instanceof EntityCowTFC) {
//            EntityCowTFC cow = (EntityCowTFC) event.getTarget();
//            if(cow.getFamiliarity() == 1.0F && cow.isReadyForAnimalProduct()) {
//                event.setCanceled(true);
//                ItemStack itemstack = event.getEntityPlayer().getHeldItem(event.getHand());
//                FluidActionResult fillResult = FluidUtil.tryFillContainer(itemstack, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), 1000, event.getEntityPlayer(), false);
//                if (!fillResult.isSuccess()) {
//                    return;
//                }
//                event.getEntityPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
//                event.getEntityPlayer().setHeldItem(event.getHand(), FluidUtil.tryFillContainerAndStow(itemstack, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), new PlayerInvWrapper(event.getEntityPlayer().inventory), 1000, (EntityPlayer)null, true).getResult());
//            }
//        }
  }


}
