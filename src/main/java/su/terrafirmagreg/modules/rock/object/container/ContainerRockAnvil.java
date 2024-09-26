package su.terrafirmagreg.modules.rock.object.container;

import su.terrafirmagreg.api.base.container.BaseContainerTile;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.AnvilRecipeManager;
import su.terrafirmagreg.modules.metal.objects.recipe.anvil.IAnvilRecipe;
import su.terrafirmagreg.modules.rock.object.tile.TileRockAnvil;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.objects.container.IButtonHandler;

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
