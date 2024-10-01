package se.gory_moon.horsepower.waila;

import su.terrafirmagreg.api.base.block.BaseBlockHorse;
import su.terrafirmagreg.modules.device.object.tile.TileChopperHorse;
import su.terrafirmagreg.modules.device.object.tile.TileChopperManual;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.SpecialChars;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.blocks.BlockChopperHorse;
import se.gory_moon.horsepower.blocks.BlockChopperManual;
import se.gory_moon.horsepower.blocks.BlockFiller;
import se.gory_moon.horsepower.blocks.BlockPress;
import se.gory_moon.horsepower.blocks.ModBlocks;
import se.gory_moon.horsepower.lib.Reference;
import se.gory_moon.horsepower.tileentity.TileFiller;
import se.gory_moon.horsepower.tileentity.TileHPBase;
import se.gory_moon.horsepower.util.Localization;

import java.util.List;

public class Provider implements IWailaDataProvider {

  public static void callbackRegister(IWailaRegistrar registrar) {
    Provider provider = new Provider();

    //registrar.registerStackProvider(provider, BlockFiller.class);
    registrar.registerBodyProvider(provider, BaseBlockHorse.class);
    registrar.registerBodyProvider(provider, BlockPress.class);
    registrar.registerBodyProvider(provider, BlockFiller.class);
    registrar.registerNBTProvider(provider, BlockChopperHorse.class);
    registrar.registerNBTProvider(provider, BlockChopperManual.class);
    registrar.registerNBTProvider(provider, BlockPress.class);
    registrar.registerNBTProvider(provider, BlockFiller.class);
    registrar.addConfig(Reference.NAME, "horsepower:showItems", Localization.WAILA.SHOW_ITEMS.translate());
  }

  @Override
  public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
    if (accessor.getBlock().equals(ModBlocks.BLOCK_CHOPPER_FILLER)) {
      return accessor.getBlock()
                     .getPickBlock(accessor.getBlockState(), accessor.getMOP(), accessor.getWorld(), accessor.getPosition(), accessor.getPlayer());
    }
    return null;
  }

  @Override
  public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    return null;
  }

  @Override
  public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    NBTTagCompound nbt = accessor.getNBTData();
    if (nbt.hasKey("horsepower:chopper", 10)) {
      nbt = nbt.getCompoundTag("horsepower:chopper");

      double totalWindup = Configs.general.pointsForWindup > 0 ? Configs.general.pointsForWindup : 1;
      double windup = nbt.getInteger("currentWindup");
      double current = nbt.getInteger("chopTime");
      double total = nbt.getInteger("totalChopTime");
      double progressWindup = Math.round(((windup / totalWindup) * 100D) * 100D) / 100D;
      double progressChopping = Math.round(((current / total) * 100D) * 100D) / 100D;

      if (accessor.getTileEntity() instanceof TileChopperHorse || accessor.getTileEntity() instanceof TileFiller) {
        currenttip.add(Localization.WAILA.WINDUP_PROGRESS.translate(progressWindup));
      }
      if (total > 1 || accessor.getTileEntity() instanceof TileChopperManual) {
        currenttip.add(Localization.WAILA.CHOPPING_PROGRESS.translate(progressChopping));
      }
    } else if (nbt.hasKey("horsepower:press")) {
      nbt = nbt.getCompoundTag("horsepower:press");
      double current = nbt.getInteger("currentPressStatus");
      double total = Configs.general.pointsForPress > 0 ? Configs.general.pointsForPress : 1;
      double progress = Math.round(((current / total) * 100D) * 100D) / 100D;
      currenttip.add(Localization.WAILA.PRESS_PROGRESS.translate(progress));
    }

    if (config.getConfig("horsepower:showItems")
        && (accessor.getTileEntity() instanceof TileHPBase
            || accessor.getTileEntity() instanceof TileFiller)
        && accessor.getPlayer().isSneaking()) {

      TileEntity tile = accessor.getTileEntity();
      if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        if (itemHandler != null) {
          {
            final ItemStack stack = itemHandler.getStackInSlot(0);
            final String name = stack.getItem().getRegistryName().toString();
            if (!stack.isEmpty()) {
              currenttip.add(SpecialChars.getRenderString("waila.stack", "1", name, String.valueOf(stack.getCount()),
                                                          String.valueOf(stack.getItemDamage()), stack.serializeNBT().toString()) + TextFormatting.WHITE
                             + stack.getDisplayName());
            }
          }
          {
            final ItemStack stack = itemHandler.getStackInSlot(1);
            final String name = stack.getItem().getRegistryName().toString();
            if (!stack.isEmpty()) {
              currenttip.add(SpecialChars.getRenderString("waila.stack", "1", name, String.valueOf(stack.getCount()),
                                                          String.valueOf(stack.getItemDamage()), stack.serializeNBT().toString()) + TextFormatting.WHITE
                             + stack.getDisplayName());
            }
          }
          {
            final ItemStack stack = itemHandler.getStackInSlot(2);
            final String name = stack.getItem().getRegistryName().toString();
            if (!stack.isEmpty()) {
              currenttip.add(SpecialChars.getRenderString("waila.stack", "1", name, String.valueOf(stack.getCount()),
                                                          String.valueOf(stack.getItemDamage()), stack.serializeNBT().toString()) + TextFormatting.WHITE
                             + stack.getDisplayName());
            }
          }
        }
      }
    }
    return currenttip;
  }

  @Override
  public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
    return null;
  }
  
}
