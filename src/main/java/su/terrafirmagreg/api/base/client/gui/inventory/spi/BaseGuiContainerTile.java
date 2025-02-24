package su.terrafirmagreg.api.base.client.gui.inventory.spi;

import su.terrafirmagreg.api.library.MCColor;

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

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    this.fontRenderer.drawString(this.getLocalizedTileName(), xSize / 2 - fontRenderer.getStringWidth(this.getLocalizedTileName()) / 2, 5, MCColor.BLACK.getRGB());
    // this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 92, 0);
  }

  public String getLocalizedTileName() {
    return tile.getBlockType().getLocalizedName();
  }
}
