package su.terrafirmagreg.modules.core.feature.falling;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.feature.falling.capability.CapabilityWorldTracker;
import su.terrafirmagreg.modules.core.feature.falling.capability.ProviderWorldTracker;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FeatureFalling extends BaseFeature {

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilityWorldTracker.register();
  }


  @SubscribeEvent
  public static void onWorldTick(TickEvent.WorldTickEvent event) {
    var phase = event.phase;
    var world = event.world;

    if (phase == TickEvent.Phase.START) {

      CapabilityUtils.getOptional(world, CapabilityWorldTracker.CAPABILITY).ifPresent(cap -> cap.tick(world));
    }

  }

  @SubscribeEvent
  public static void onAttachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
    World world = event.getObject();
    if (world == null) {
      return;
    }

    // TODO проверить
    if (!CapabilityUtils.has(world, CapabilityWorldTracker.CAPABILITY)) {
      event.addCapability(CapabilityWorldTracker.KEY, new ProviderWorldTracker());
    }

  }

  /**
   * Update harvesting tool before it takes damage
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void breakEvent(BlockEvent.BreakEvent event) {
    final EntityPlayer player = event.getPlayer();
    final ItemStack heldItem = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();

    FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(event.getState());
    if (spec != null && spec.isCollapsable()) {
      FallingBlockManager.checkCollapsingArea(event.getWorld(), event.getPos());
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
    if (ConfigCore.FEATURE.FALLABLE.explosionCausesCollapse) {
      for (BlockPos pos : event.getAffectedBlocks()) {
        if (FallingBlockManager.checkCollapsingArea(event.getWorld(), pos)) {
          break;
        }
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
    final var world = event.getWorld();
    if (world.isRemote) {
      return;
    }
    IBlockState state = event.getPlacedBlock();
    FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(state);
    if (spec != null && !spec.isCollapsable()) {
      if (FallingBlockManager.checkFalling(world, event.getPos(), state)) {
        world.playSound(null, event.getPos(), spec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
    IBlockState state = event.getState();
    FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(state);
    if (spec != null && !spec.isCollapsable()) {
      if (FallingBlockManager.checkFalling(event.getWorld(), event.getPos(), state)) {
        event.getWorld().playSound(null, event.getPos(), spec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    } else {
      for (EnumFacing notifiedSide : event.getNotifiedSides()) {
        BlockPos offsetPos = event.getPos().offset(notifiedSide);
        IBlockState notifiedState = event.getWorld().getBlockState(offsetPos);
        FallingBlockManager.Specification notifiedSpec = FallingBlockManager.getSpecification(notifiedState);
        if (notifiedSpec != null && !notifiedSpec.isCollapsable()) {
          if (FallingBlockManager.checkFalling(event.getWorld(), offsetPos, notifiedState)) {
            event.getWorld().playSound(null, offsetPos, notifiedSpec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          }
        }
      }
    }
  }
}
