package su.terrafirmagreg.modules.metal.objects.container;

import su.terrafirmagreg.api.spi.container.BaseContainerTile;
import su.terrafirmagreg.modules.metal.objects.tiles.TEMetalAnvil;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.container.IButtonHandler;

import org.jetbrains.annotations.Nullable;

public class ContainerAnvilPlan extends BaseContainerTile<TEMetalAnvil> implements IButtonHandler {

    public ContainerAnvilPlan(InventoryPlayer playerInv, TEMetalAnvil tile) {
        super(playerInv, tile);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable NBTTagCompound extraNBT) {
        if (extraNBT != null) {
            // Set the tile recipe
            String recipeName = extraNBT.getString("recipe");
            AnvilRecipe recipe = TFCRegistries.ANVIL.getValue(new ResourceLocation(recipeName));
            if (tile.setRecipe(recipe)) {
                tile.markForSync();
            }

            // Switch to anvil GUI
            TFCGuiHandler.openGui(player.world, tile.getPos(), player, TFCGuiHandler.Type.ANVIL);
        }
    }

    @Override
    protected void addContainerSlots() {
    }
}
