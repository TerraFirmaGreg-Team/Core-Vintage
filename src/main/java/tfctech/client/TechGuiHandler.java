package tfctech.client;

import su.terrafirmagreg.modules.metal.client.gui.GuiGlassworking;
import su.terrafirmagreg.modules.metal.objects.container.ContainerGlassworking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import net.dries007.tfc.objects.items.glassworking.ItemBlowpipe;
import tfctech.TFCTech;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCTECH;

public class TechGuiHandler implements IGuiHandler {

  public static final ResourceLocation GUI_ELEMENTS = new ResourceLocation(MODID_TFCTECH, "textures/gui/glassworking.png");

  public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
    player.openGui(TFCTech.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
  }

  @Nullable
  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    ItemStack stack = player.getHeldItemMainhand();
    Type type = Type.valueOf(ID);
    if (type == Type.GLASSWORKING) {
      return new ContainerGlassworking(player.inventory, stack.getItem() instanceof ItemBlowpipe ? stack : player.getHeldItemOffhand());
    }
    return null;

  }

  @Override
  @Nullable
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    Type type = Type.valueOf(ID);
    BlockPos pos = new BlockPos(x, y, z);
    if (type == Type.GLASSWORKING) {
      return new GuiGlassworking(container, player);
    }
    return null;
  }

  public enum Type {
    GLASSWORKING;

    private static final Type[] values = values();

    @NotNull
    public static Type valueOf(int id) {
      while (id >= values.length) {
        id -= values.length;
      }
      while (id < 0) {
        id += values.length;
      }
      return values[id];
    }
  }


}
