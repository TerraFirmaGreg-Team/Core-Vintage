package net.dries007.tfcfarming;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.objects.blocks.BlockHangingPlanter;
import net.dries007.tfc.objects.blocks.BlockLargePlanter;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.te.TECropBase;
import net.dries007.tfc.objects.te.TEHangingPlanter;
import net.dries007.tfc.objects.te.TEPlanter;
import net.dries007.tfc.objects.te.TEStemCrop;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfcfarming.firmalife.TEHangingPlanterN;
import net.dries007.tfcfarming.firmalife.TEPlanterN;
import net.dries007.tfcfarming.firmalife.TEStemCropN;
import net.dries007.tfc.objects.blocks.blocktype.farmland.FarmlandTFCF;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CommonProxy {

  /**
   * Each plant uses all the nutrient of its type from the soil block on which it is place in a complete growth phase
   * <p>
   * The average plant in TFC takes ~4 months to mature The passive nutrient replenish parameters are set such that a nutrient goes from empty to full in a
   * period of 32 months.
   * <p>
   * Going a bit more in depth, if we exclude the usage of fertilizers, considering that a plant takes 4 months to mature (as that is the average time) the 32
   * month come into play like this:
   * <p>
   * 4 growth months + 8 waiting months = 12 month = 1 year 24 waiting months            = 2 years
   * <p>
   * Thus, a plant can be planted again on the same spot as it first grew after exactly 3 years (3 years after it started first growing). This is done to keep
   * growing seasons in sync, otherwise if it were to be 3 years after the plant matured then if you planted a crop in June, and it matured in September then
   * the soonest you will be able to plant it again would be in September after 3 years not in June.
   * <p>
   * The period is of 3 years because there are 3 types of nutrients and this makes the strategy of splitting farmland into 3/6/9/... slices (depending on how
   * many times the temperatures allow you to grow a crop every year) the best way of growing stuff without using fertilizers
   * <p>
   * Using a number that is not a multiple of three would break the symmetry of the rotation and using a multiple of 3 would invalidate the simple 3 slice split
   * solution, fact which can make it harder or discourage others to understand the system.
   */

  private final List<Tuple<BlockPos, World>> awaiting = new ArrayList<>();
  private final long month = Calendar.CALENDAR_TIME.getDaysInMonth() * 24000;

  public void init() {}

  /**
   * Sets the tile entity to the blocks
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
  @SuppressWarnings("deprecated")
  public void blockPlaced(BlockEvent.PlaceEvent event) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    if (!event.getWorld().isRemote) {
      setTileEntity(event.getWorld(), event.getPos());

      TEPlanter pte = Helpers.getTE(event.getWorld(), event.getPos(), TEPlanter.class);
      if (pte != null) {
        event.getWorld().setTileEntity(event.getPos(), TEPlanterN.class.newInstance());
        return;
      }

      Block b = event.getWorld().getBlockState(event.getPos()).getBlock();
      if (Config.hangingPlanters && b instanceof BlockHangingPlanter hangingPlanter) {

        Supplier<? extends Item> supplier = hangingPlanter.getSeed();
        Item i = supplier.get();

        if (i instanceof ItemSeedsTFC seeds) {
          TEHangingPlanter hpte = Helpers.getTE(event.getWorld(), event.getPos(), TEHangingPlanter.class);
          if (hpte != null) {
            ICrop crop = seeds.getCrop();
            if (crop != null) {
              TETickCounter teHangingPlanter = TEHangingPlanterN.class.getConstructor(ICrop.class).newInstance(crop);
              teHangingPlanter.resetCounter();
              event.getWorld().setTileEntity(event.getPos(), teHangingPlanter);
            }
          }
        }

      }
    }
  }

  /**
   * sets tile entity to awaited blocks, nutrient map cleanup, passive nutrient growth
   */
  @SubscribeEvent
  public void tickEvent(TickEvent.ServerTickEvent event)
    throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    if (!awaiting.isEmpty()) {
      synchronized (awaiting) {
        for (Tuple<BlockPos, World> t : awaiting) {
          World world = t.getSecond();
          BlockPos pos = t.getFirst();
          setTileEntity(world, pos);
        }
        awaiting.clear();
      }
    }

    // cleanup and passive growth logic
    World w = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0);
    TETickCounter t = TFCFarming.INSTANCE.worldStorage.teTickCounter;
    FarmingWorldStorage worldStorage = TFCFarming.INSTANCE.worldStorage;

    // 255 units / 8 units / month = 32 months for a full replenish
    if (t.getTicksSinceUpdate() > month) {
      worldStorage.performCleanup();
      worldStorage.globalIncreaseUpdate(w, NutrientClass.NITROGEN, Config.nPassive);
      worldStorage.globalIncreaseUpdate(w, NutrientClass.PHOSPHORUS, Config.pPassive);
      worldStorage.globalIncreaseUpdate(w, NutrientClass.POTASSIUM, Config.kPassive);
      TFCFarming.INSTANCE.worldStorage.resetCounter();
    }

  }

  private void setTileEntity(World w, BlockPos pos) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    TECropBase te = Helpers.getTE(w, pos, TECropBase.class);
    if (te == null) {return;}
    if (te instanceof TEStemCrop && !(te instanceof TEStemCropN)) {
      TETickCounter teStemCropN = TEStemCropN.class.getConstructor(TECropBase.class).newInstance(te);
      teStemCropN.resetCounter();
      w.setTileEntity(pos, teStemCropN);
    } else if (!(te instanceof TECropBaseN)) {
      TECropBaseN teCropBaseN = new TECropBaseN(te);
      teCropBaseN.resetCounter();
      w.setTileEntity(pos, teCropBaseN);
    }
  }

  public boolean canSeeSky(BlockPos pos, World world) {
    BlockPos up = pos.up();
    for (; !world.getBlockState(up).isOpaqueCube() && up.getY() < 256; up = up.up())
      ;
    return up.getY() == 256;
  }

  /**
   * puts blocks put on TFCF farmland in await list, cancels rice put on TFCF farmland, fertilizer logic
   */
  @SubscribeEvent
  public void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
    Block b = event.getWorld().getBlockState(event.getPos()).getBlock();
    if (!event.getWorld().isRemote) {
      boolean farmlandTFC = b instanceof BlockFarmlandTFC;
      boolean farmlandTFCF = b instanceof FarmlandTFCF;
      boolean planter = b instanceof BlockLargePlanter;
      boolean hangingPlanter = Config.hangingPlanters && b instanceof BlockHangingPlanter;
      if (farmlandTFC || farmlandTFCF || planter || hangingPlanter) {

        // fertilizer logic
        if (hangingPlanter || planter || canSeeSky(event.getPos(), event.getWorld())) {
          if (TFCFarmingContent.isFertilizer(event.getItemStack())) {
            NutrientClass nutrientClass = TFCFarmingContent.getFertilizerClass(event.getItemStack());
            int value = TFCFarmingContent.getFertilizerValue(event.getItemStack());

            if (!planter && TFCFarming.INSTANCE.worldStorage.fertilizerBlock(event.getPos().getX(), event.getPos().getZ(), nutrientClass, value)) {
              event.getItemStack().setCount(event.getItemStack().getCount() - 1);

            } else if (planter) {
              TEPlanterN tePlanterN = Helpers.getTE(event.getWorld(), event.getPos(), TEPlanterN.class);

              if (tePlanterN != null && tePlanterN.fertilize(nutrientClass, value)) {
                event.getItemStack().setCount(event.getItemStack().getCount() - 1);
              }

            } else if (hangingPlanter) {
              TEHangingPlanterN teHangingPlanterN = Helpers.getTE(event.getWorld(), event.getPos(), TEHangingPlanterN.class);

              if (teHangingPlanterN != null && teHangingPlanterN.fertilize(nutrientClass, value)) {
                event.getItemStack().setCount(event.getItemStack().getCount() - 1);
              }
            }
          }
        }
      }
      // put block in await list, if it's tile entity isn't already set and should be set, then set it
      if (b instanceof FarmlandTFCF) {
        synchronized (awaiting) {
          awaiting.add(new Tuple<>(event.getPos().add(0, 1, 0), event.getWorld()));
        }
      }
    }
  }
}
