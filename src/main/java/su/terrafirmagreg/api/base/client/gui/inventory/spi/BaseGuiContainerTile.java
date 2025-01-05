package su.terrafirmagreg.api.base.client.gui.inventory.spi;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BaseGuiContainerTile<T extends TileEntity> extends BaseGuiContainer {

  protected final T tile;

  public BaseGuiContainerTile(Container container, InventoryPlayer playerInv, T tile, ResourceLocation background) {
    super(container, playerInv, background);

    this.tile = tile;
  }
}
