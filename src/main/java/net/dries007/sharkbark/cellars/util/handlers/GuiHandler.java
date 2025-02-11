package net.dries007.sharkbark.cellars.util.handlers;

import su.terrafirmagreg.modules.device.client.gui.GuiCellarShelf;
import su.terrafirmagreg.modules.device.client.gui.GuiFreezeDryer;
import su.terrafirmagreg.modules.device.client.gui.GuiIceBunker;
import su.terrafirmagreg.modules.device.object.container.ContainerCellarShelf;
import su.terrafirmagreg.modules.device.object.container.ContainerFreezeDryer;
import su.terrafirmagreg.modules.device.object.container.ContainerIceBunker;
import su.terrafirmagreg.modules.device.object.tile.TileCellarShelf;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;
import su.terrafirmagreg.modules.device.object.tile.TileIceBunker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import net.dries007.sharkbark.cellars.util.Reference;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

  public static void registerGUIs() {

  }

  @Nullable
  @Override
  public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
    if (ID == Reference.GUI_CELLAR_SHELF) {
      return new ContainerCellarShelf(entityPlayer.inventory, (TileCellarShelf) world.getTileEntity(new BlockPos(x, y, z)), entityPlayer);
    }
    if (ID == Reference.GUI_ICE_BUNKER) {
      return new ContainerIceBunker(entityPlayer.inventory, (TileIceBunker) world.getTileEntity(new BlockPos(x, y, z)), entityPlayer);
    }
    if (ID == Reference.GUI_FREEZE_DRYER) {
      return new ContainerFreezeDryer(entityPlayer.inventory, (TileFreezeDryer) world.getTileEntity(new BlockPos(x, y, z)), entityPlayer);
    }
    return null;
  }

  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
    Container container = (Container) getServerGuiElement(ID, entityPlayer, world, x, y, z);

    if (ID == Reference.GUI_CELLAR_SHELF) {
      TileCellarShelf te = (TileCellarShelf) world.getTileEntity(new BlockPos(x, y, z));
      //te.isOpen += 1;
      return new GuiCellarShelf(container, entityPlayer.inventory, te, world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
    }
    if (ID == Reference.GUI_ICE_BUNKER) {
      TileIceBunker te = (TileIceBunker) world.getTileEntity(new BlockPos(x, y, z));
      return new GuiIceBunker(container, entityPlayer.inventory, te, world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
    }

    if (ID == Reference.GUI_FREEZE_DRYER) {
      TileFreezeDryer te = (TileFreezeDryer) world.getTileEntity(new BlockPos(x, y, z));
      return new GuiFreezeDryer(container, entityPlayer.inventory, te, world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
    }
    return null;
  }

}
