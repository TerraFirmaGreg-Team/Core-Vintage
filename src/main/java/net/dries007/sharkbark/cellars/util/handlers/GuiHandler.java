package net.dries007.sharkbark.cellars.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import net.dries007.sharkbark.cellars.blocks.container.ContainerCellarShelf;
import net.dries007.sharkbark.cellars.blocks.container.ContainerFreezeDryer;
import net.dries007.sharkbark.cellars.blocks.container.ContainerIceBunker;
import net.dries007.sharkbark.cellars.blocks.gui.GuiCellarShelf;
import net.dries007.sharkbark.cellars.blocks.gui.GuiFreezeDryer;
import net.dries007.sharkbark.cellars.blocks.gui.GuiIceBunker;
import net.dries007.sharkbark.cellars.blocks.tileentity.TECellarShelf;
import net.dries007.sharkbark.cellars.blocks.tileentity.TEFreezeDryer;
import net.dries007.sharkbark.cellars.blocks.tileentity.TEIceBunker;
import net.dries007.sharkbark.cellars.util.Reference;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

  public static void registerGUIs() {

  }

  @Nullable
  @Override
  public Object getServerGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
    if (ID == Reference.GUI_CELLAR_SHELF) {
      return new ContainerCellarShelf(entityPlayer.inventory, (TECellarShelf) world.getTileEntity(new BlockPos(x, y, z)), entityPlayer);
    }
    if (ID == Reference.GUI_ICE_BUNKER) {
      return new ContainerIceBunker(entityPlayer.inventory, (TEIceBunker) world.getTileEntity(new BlockPos(x, y, z)), entityPlayer);
    }
    if (ID == Reference.GUI_FREEZE_DRYER) {
      return new ContainerFreezeDryer(entityPlayer.inventory, (TEFreezeDryer) world.getTileEntity(new BlockPos(x, y, z)), entityPlayer);
    }
    return null;
  }

  @Nullable
  @Override
  public Object getClientGuiElement(int ID, EntityPlayer entityPlayer, World world, int x, int y, int z) {
    Container container = (Container) getServerGuiElement(ID, entityPlayer, world, x, y, z);

    if (ID == Reference.GUI_CELLAR_SHELF) {
      TECellarShelf te = (TECellarShelf) world.getTileEntity(new BlockPos(x, y, z));
      //te.isOpen += 1;
      return new GuiCellarShelf(container, entityPlayer.inventory, te, world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
    }
    if (ID == Reference.GUI_ICE_BUNKER) {
      TEIceBunker te = (TEIceBunker) world.getTileEntity(new BlockPos(x, y, z));
      return new GuiIceBunker(container, entityPlayer.inventory, te, world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
    }

    if (ID == Reference.GUI_FREEZE_DRYER) {
      TEFreezeDryer te = (TEFreezeDryer) world.getTileEntity(new BlockPos(x, y, z));
      return new GuiFreezeDryer(container, entityPlayer.inventory, te, world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
    }
    return null;
  }

}
