package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.IBlockSettings;
import su.terrafirmagreg.api.spi.block.provider.IBlockStateProvider;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.core.init.BlocksCore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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


import net.dries007.tfc.objects.items.ItemAnimalHide;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public class BlockThatchBed extends BlockBed implements IBlockSettings, IBlockStateProvider {

    protected final Settings settings;

    public BlockThatchBed() {

        this.settings = Settings.of(Material.CLOTH)
                .registryKey("device/thatch_bed")
                .soundType(SoundType.CLOTH)
                .hardness(0.6F);

        BlockUtils.setFireInfo(this, 60, 20);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.setSpawnPoint(pos, false);
            playerIn.sendMessage(new TextComponentTranslation(ModUtils.localize("thatch_bed.spawnpoint")));
            if (!worldIn.isThundering()) {
                playerIn.sendStatusMessage(new TextComponentTranslation(ModUtils.localize("thatch_bed.not_thundering")), true);
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlocksCore.THATCH);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(PART) == EnumPartType.HEAD) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.LARGE)));
            spawnAsEntity(worldIn, pos, new ItemStack(BlocksCore.THATCH, 2));
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(BlocksCore.THATCH);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, null, stack); //Force vanilla to use #dropBlockAsItemWithChance
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player) {
        return true;
    }

    @Override
    public void onRegisterState() {
        ModelUtils.registerStateMapper(this, new StateMap.Builder().ignore(OCCUPIED).build());
    }

}
