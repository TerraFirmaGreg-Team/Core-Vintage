package net.dries007.tfc.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import net.dries007.eerussianguy.firmalife.FirmaLife;
import net.dries007.eerussianguy.firmalife.registry.BlocksFL;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.objects.container.ContainerKnapping;

public class FLGuiHandler implements IGuiHandler {

  public static final ResourceLocation PUMPKIN_TEXTURE = new ResourceLocation("minecraft", "textures/blocks/pumpkin_side.png");

  public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
    player.openGui(FirmaLife.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
  }

  @Override
  public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    ItemStack stack = player.getHeldItemMainhand();
    Type type = Type.valueOf(ID);
    switch (type) {
      case KNAPPING_PUMPKIN:
        return new ContainerKnapping(KnappingTypes.PUMPKIN, player.inventory,
                                     stack.getItem() == Item.getItemFromBlock(BlocksFL.PUMPKIN_FRUIT) ? stack : player.getHeldItemOffhand());
      default:
        return null;
    }
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    Container container = getServerGuiElement(ID, player, world, x, y, z);
    Type type = Type.valueOf(ID);
    switch (type) {
      case KNAPPING_PUMPKIN:
        return new GuiKnapping(container, player, KnappingTypes.LEATHER, PUMPKIN_TEXTURE);
      default:
        return null;
    }
  }

  public enum Type {
    KNAPPING_PUMPKIN;

    private static final Type[] values = values();

    public static Type valueOf(int id) {
      return values[id % values.length];
    }

  }


}
