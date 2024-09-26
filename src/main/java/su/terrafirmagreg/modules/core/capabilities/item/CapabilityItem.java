package su.terrafirmagreg.modules.core.capabilities.item;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

import static net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;

public final class CapabilityItem {

  public static final ResourceLocation KEY = ModUtils.resource("item_capability");


  public static IItemHandler get(TileEntity tile) {
    return tile.getCapability(ITEM_HANDLER_CAPABILITY, null);
  }

  public static boolean has(TileEntity tile) {
    return tile.hasCapability(ITEM_HANDLER_CAPABILITY, null);
  }

}
