package su.terrafirmagreg.api.base.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class BaseGuiContainerTile<T extends TileEntity> extends BaseGuiContainer {

  protected final T tile;

  public BaseGuiContainerTile(Container container, InventoryPlayer playerInv, T tile, ResourceLocation background) {
    super(container, playerInv, background);

    this.tile = tile;
  }
}
