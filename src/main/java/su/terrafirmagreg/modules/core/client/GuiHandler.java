package su.terrafirmagreg.modules.core.client;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.spi.gui.IContainerProvider;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuiHandler implements IGuiHandler {

    // use this instead of player.openGui() -> avoids magic numbers
    public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
        player.openGui(TerraFirmaGreg.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());

    }

    // Only use this for things that don't need a BlockPos to identify TE's!!!
    public static void openGui(World world, EntityPlayer player, Type type) {
        player.openGui(TerraFirmaGreg.getInstance(), type.ordinal(), world, 0, 0, 0);

    }

    @Override
    @org.jetbrains.annotations.Nullable
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        ItemStack stack = player.getHeldItemMainhand();
        IBlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = TileUtils.getTile(world, blockPos);
        Entity entity = world.getEntityByID(x);
        Type type = Type.valueOf(ID);
        return switch (type) {
            case NULL -> null;

            default -> {
                if (tileEntity instanceof IContainerProvider<?, ?> containerProvider) {
                    yield containerProvider.getContainer(player.inventory, world, blockState, blockPos);

                } else if (entity instanceof IContainerProvider<?, ?> containerProvider) {
                    yield containerProvider.getContainer(player.inventory, world, blockState, blockPos);

                } else {
                    yield null;
                }
            }
        };
    }

    @Override
    @Nullable
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Container container = getServerGuiElement(ID, player, world, x, y, z);
        BlockPos blockPos = new BlockPos(x, y, z);
        IBlockState blockState = world.getBlockState(blockPos);
        TileEntity tileEntity = TileUtils.getTile(world, blockPos);
        Entity entity = world.getEntityByID(x);
        Type type = Type.valueOf(ID);
        return switch (type) {
            case NULL -> null;

            default -> {
                if (tileEntity instanceof IContainerProvider<?, ?> containerProvider) {
                    yield containerProvider.getGuiContainer(player.inventory, world, blockState, blockPos);

                } else if (entity instanceof IContainerProvider<?, ?> containerProvider) {
                    yield containerProvider.getGuiContainer(player.inventory, world, blockState, blockPos);

                } else {
                    yield null;
                }
            }
        };
    }

    public enum Type {
        WOOD_SUPPLY_CART,
        WOOD_PLOW,
        WOOD_BARREL,
        WOOD_CHEST,
        ALLOY_CALCULATOR,
        CRATE,
        CHARCOAL_FORGE,
        BLAST_FURNACE,
        FIRE_PIT,
        NEST_BOX,
        LOG_PILE,
        CRUCIBLE,
        CELLAR_SHELF,
        ICE_BUNKER,
        FREEZE_DRYER,
        POWDERKEG,
        NULL;

        private static final Type[] values = values();

        @NotNull
        public static Type valueOf(int id) {
            return id < 0 || id >= values.length ? NULL : values[id];
        }
    }
}
