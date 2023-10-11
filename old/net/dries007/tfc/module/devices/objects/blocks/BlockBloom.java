package net.dries007.tfc.module.devices.objects.blocks;

import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.module.core.api.objects.block.BlockBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.core.init.ItemsCore;
import net.dries007.tfc.module.devices.objects.tile.TEBloom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
public class BlockBloom extends BlockBase implements IHasModel {

    public static final String NAME = "device.bloom";

    public BlockBloom() {
        super(Material.IRON);
        setHardness(3.0f);
        setHarvestLevel("pickaxe", 0);
        setSoundType(SoundType.STONE);
    }

//    @Nullable
//    @Override
//    public ItemBlock getItemBlock() {
//        return null;
//    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TEBloom te = Helpers.getTE(worldIn, pos, TEBloom.class);
        if (te != null) {
            te.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, @Nullable EntityPlayer player, boolean willHarvest) {
        if (player != null && player.canHarvestBlock(state) && !player.isCreative()) {
            // Try to give the contents of the TE directly to the player if possible
            TEBloom tile = Helpers.getTE(world, pos, TEBloom.class);
            if (tile != null) {
                IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                if (cap != null) {
                    ItemStack contents = cap.extractItem(0, 64, false);
                    ItemHandlerHelper.giveItemToPlayer(player, contents);
                }
            }
        }
        //noinspection ConstantConditions
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEBloom();
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        TEBloom tile = Helpers.getTE(world, pos, TEBloom.class);
        if (tile != null) {
            IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (cap != null) {
                ItemStack stack = cap.extractItem(0, 1, true);
                if (!stack.isEmpty()) {
                    return stack;
                }
            }
        }
        return new ItemStack(ItemsCore.UNREFINED_BLOOM);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        var resourceLocation = Helpers.getID(NAME.replaceAll("\\.", "/"));

        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder().customPath(resourceLocation).build());

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(resourceLocation, "normal"));
    }
}
