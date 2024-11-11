package tfcflorae;

import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.registry.BlocksFL;
import com.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.objects.blocks.wood.bamboo.BlockBambooLeaves;
import net.dries007.tfc.objects.blocks.wood.cinnamon.BlockCassiaCinnamonLeaves;
import net.dries007.tfc.objects.blocks.wood.cinnamon.BlockCeylonCinnamonLeaves;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.util.calendar.Calendar;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCF;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_TFCF)
public final class CommonEventHandlerTFCF {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  @SubscribeEvent
  public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
    World world = event.getWorld();
    BlockPos pos = event.getPos();
    IBlockState state = event.getState();
    Block block = state.getBlock();
    Month month = Calendar.CALENDAR_TIME.getMonthOfYear();

    for (PlantType plant : PlantType.getTypes()) {
      if (plant == PlantTypes.BARREL_CACTUS &&
          (month == Month.SEPTEMBER || month == Month.OCTOBER || month == Month.NOVEMBER)) {
        int chance = MathUtils.RNG.nextInt(2);
        if (chance == 0) {
          event.getDrops().clear();
          event.getDrops().add(new ItemStack(ItemsTFCF.BARREL_CACTUS_FRUIT, 1 + MathUtils.RNG.nextInt(3)));
        }
      }
    }
    if (TFCFlorae.FirmaLifeAdded) {
      EntityPlayer playerHarvest = event.getHarvester();
      ItemStack held = playerHarvest == null ? ItemStack.EMPTY : playerHarvest.getHeldItemMainhand();

      if (block instanceof BlockCassiaCinnamonLeaves || block instanceof BlockCeylonCinnamonLeaves || block instanceof BlockBambooLeaves) {
        event.getDrops().add(new ItemStack(ItemsFL.FRUIT_LEAF, 2 + MathUtils.RNG.nextInt(4)));
      }
      if (block == BlocksFL.MELON_FRUIT && (held.getItem()
                                                .getHarvestLevel(held, "knife", playerHarvest, state) != -1)) {
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(ItemsFL.getFood(FoodFL.MELON), 2 + MathUtils.RNG.nextInt(4)));
      }
    }
  }
}
