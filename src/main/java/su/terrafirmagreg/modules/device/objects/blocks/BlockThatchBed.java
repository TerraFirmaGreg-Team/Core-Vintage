package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.model.ICustomState;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.core.init.BlocksCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.items.ItemAnimalHide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@MethodsReturnNonnullByDefault
public class BlockThatchBed extends BlockBed implements IAutoReg, ICustomState {

    public BlockThatchBed() {
        setSoundType(SoundType.PLANT);
        setHardness(0.6F);

        BlockUtils.setFireInfo(this, 60, 20);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn,
                                    @NotNull EnumHand hand, @NotNull EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.setSpawnPoint(pos, false);
            playerIn.sendMessage(new TextComponentTranslation(ModUtils.getIDName(".thatch_bed.spawnpoint")));
            if (!worldIn.isThundering()) {
                playerIn.sendStatusMessage(new TextComponentTranslation(ModUtils.getIDName(".thatch_bed.not_thundering")), true);
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void neighborChanged(IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
        EnumFacing enumfacing = state.getValue(FACING);
        if (state.getValue(PART) == EnumPartType.FOOT) {
            if (!(worldIn.getBlockState(pos.offset(enumfacing)).getBlock() instanceof BlockThatchBed)) {
                worldIn.setBlockToAir(pos);
            }
        } else if (!(worldIn.getBlockState(pos.offset(enumfacing)).getBlock() instanceof BlockThatchBed)) {
            if (!worldIn.isRemote) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }
            worldIn.setBlockToAir(pos);
        }

    }

    @Override
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return Item.getItemFromBlock(BlocksCore.THATCH);
    }

    @Override
    public void dropBlockAsItemWithChance(@NotNull World worldIn, @NotNull BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(PART) == EnumPartType.HEAD) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.LARGE)));
            spawnAsEntity(worldIn, pos, new ItemStack(BlocksCore.THATCH, 2));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public ItemStack getItem(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        return new ItemStack(BlocksCore.THATCH);
    }

    @Override
    public void harvestBlock(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull BlockPos pos, @NotNull IBlockState state,
                             @NotNull TileEntity te, @NotNull ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, null, stack); //Force vanilla to use #dropBlockAsItemWithChance
    }

    @Override
    public TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return null;
    }

    @Override
    public boolean hasTileEntity(@NotNull IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
        return null;
    }

    @Override
    public boolean isBed(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @Nullable Entity player) {
        return true;
    }

    @Override
    public void onStateMapperRegister() {
        ModelUtils.registerStateMapper(this, new StateMap.Builder().ignore(OCCUPIED).build());
    }

    @Override
    public @NotNull String getName() {
        return "device/thatch_bed";
    }
}
