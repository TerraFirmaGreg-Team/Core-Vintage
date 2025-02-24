package net.dries007.tfctech.client;

import su.terrafirmagreg.modules.device.client.gui.GuiElectricForge;
import su.terrafirmagreg.modules.device.client.gui.GuiInductionCrucible;
import su.terrafirmagreg.modules.device.object.container.ContainerCrucible;
import su.terrafirmagreg.modules.device.object.container.ContainerElectricForge;
import su.terrafirmagreg.modules.device.object.tile.TileElectricForge;
import su.terrafirmagreg.modules.device.object.tile.TileInductionCrucible;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import net.dries007.tfc.client.gui.GuiGlassworking;
import net.dries007.tfc.objects.container.ContainerGlassworking;
import net.dries007.tfc.objects.items.glassworking.ItemBlowpipe;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfctech.TFCTech;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCTECH;

public class TechGuiHandler implements IGuiHandler {

  public static final ResourceLocation GUI_ELEMENTS = new ResourceLocation(TFCTECH, "textures/gui/elements.png");

  public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
    player.openGui(TFCTech.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
  }

  @Nullable
  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    ItemStack stack = player.getHeldItemMainhand();
    Type type = Type.valueOf(ID);
    switch (type) {
      case ELECTRIC_FORGE:
        TileElectricForge teElectricForge = Helpers.getTE(world, pos, TileElectricForge.class);
        return teElectricForge == null ? null : new ContainerElectricForge(player.inventory, teElectricForge);
      case INDUCTION_CRUCIBLE:
        TileInductionCrucible teInductionCrucible = Helpers.getTE(world, pos, TileInductionCrucible.class);
        return teInductionCrucible == null ? null : new ContainerCrucible(player.inventory, teInductionCrucible);
      case GLASSWORKING:
        return new ContainerGlassworking(player.inventory, stack.getItem() instanceof ItemBlowpipe ? stack : player.getHeldItemOffhand());
      default:
        return null;
    }

  }

  @Override
  @Nullable
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    Type type = Type.valueOf(ID);
    BlockPos pos = new BlockPos(x, y, z);
    switch (type) {
      case ELECTRIC_FORGE:
        return new GuiElectricForge(container, player.inventory, Helpers.getTE(world, pos, TileElectricForge.class));
      case INDUCTION_CRUCIBLE:
        return new GuiInductionCrucible(container, player.inventory, Helpers.getTE(world, pos, TileInductionCrucible.class));
      case GLASSWORKING:
        return new GuiGlassworking(container, player);
      default:
        return null;
    }
  }

  public enum Type {
    ELECTRIC_FORGE,
    INDUCTION_CRUCIBLE,
    GLASSWORKING;

    private static final Type[] values = values();

    @Nonnull
    public static Type valueOf(int id) {
      while (id >= values.length) {id -= values.length;}
      while (id < 0) {id += values.length;}
      return values[id];
    }
  }

}
