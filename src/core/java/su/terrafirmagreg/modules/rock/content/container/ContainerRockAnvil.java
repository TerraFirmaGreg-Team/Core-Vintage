package su.terrafirmagreg.modules.rock.content.container;


import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.content.base.gui.button.api.IButtonHandler;
import su.terrafirmagreg.framework.manager.content.base.inventory.spi.container.BaseContainerTile;
import su.terrafirmagreg.modules.rock.content.recipe.anvil.AnvilRecipeManager;
import su.terrafirmagreg.modules.rock.content.recipe.anvil.IAnvilRecipe;
import su.terrafirmagreg.modules.rock.content.tile.TileRockAnvil;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.Nullable;

public class ContainerRockAnvil
  extends BaseContainerTile<TileRockAnvil> implements IButtonHandler {

  public ContainerRockAnvil(InventoryPlayer playerInv, TileRockAnvil tile) {
    super(playerInv, tile);

  }

  @Override
  public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
    if (extraNBT != null) {
      // Set the tile recipe
      String recipeName = extraNBT.getString("recipe");
      IAnvilRecipe recipe = AnvilRecipeManager.findMatchingRecipe(new ResourceLocation(recipeName));
      if (tile.setRecipe(recipe)) {
        tile.markForSync();
      }

      // Switch to anvil GUI
      GuiHandler.openGui(player.world, tile.getPos(), player);
    }
  }

  @Override
  protected void addContainerSlots() {
  }
}
