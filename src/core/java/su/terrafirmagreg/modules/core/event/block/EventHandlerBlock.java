package su.terrafirmagreg.modules.core.event.block;

import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Month;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class EventHandlerBlock {

  @SubscribeEvent
  public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
    final List<ItemStack> drops = event.getDrops();
    final EntityPlayer player = event.getHarvester();
    final ItemStack heldItem = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();
    final IBlockState state = event.getState();
    final Block block = state.getBlock();
    final ItemStack stackAt = new ItemStack(Item.getItemFromBlock(block), 1, block.damageDropped(state));
    final Month month = Calendar.CALENDAR_TIME.getMonthOfYear();

//    if (OreDictUtils.contains(stackAt, "blockPackedIce")) {
//      if (OreDictUtils.contains(heldItem, "iceSaw")) {
//        drops.clear();
//        drops.add(new ItemStack(ItemsCore.ICE_SHARD, 3 + MathUtils.RNG.nextInt(4)));
//      }
//    }
//
//    if (OreDictUtils.contains(stackAt, "blockGlass")) {
//      if (!event.isSilkTouching()) {
//        // Drop shards from glass
//        drops.clear();
//        drops.add(new ItemStack(ItemsCore.GLASS_SHARD));
//      }
//
//    }
//
//    if (OreDictUtils.contains(stackAt, "chest")) {
//      drops.clear();
//      drops.add(new ItemStack(BlocksWood.CHEST.get(WoodTypes.OAK)));
//    }
//
//    if (OreDictUtils.contains(stackAt, "chestTrapped")) {
//      drops.clear();
//      drops.add(new ItemStack(BlocksWood.CHEST_TRAPPED.get(WoodTypes.OAK)));
//    }
  }

}
