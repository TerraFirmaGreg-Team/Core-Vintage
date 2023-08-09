package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockWoodDoor extends BlockDoor implements IWoodBlock {
    private final WoodBlockVariant woodBlockVariant;
    private final WoodType woodType;

    public BlockWoodDoor(WoodBlockVariant woodBlockVariant, WoodType woodType) {
        super(Material.WOOD);

        this.woodBlockVariant = woodBlockVariant;
        this.woodType = woodType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.WOOD);
        setHardness(3.0F);
        disableStats();

        // No direct item, so no oredict.
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public WoodBlockVariant getWoodBlockVariant() {
        return woodBlockVariant;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, @Nonnull Random rand, int fortune) {
        var itemBlock = getItemBlock();

        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
            return Items.AIR;
        } else if (itemBlock != null) {
            return itemBlock;
        }

        return Items.AIR;
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        var itemBlock = getItemBlock();
        return itemBlock == null ? ItemStack.EMPTY : new ItemStack(getItemBlock());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(getResourceLocation()).ignore(BlockDoor.POWERED).build());

        for (IBlockState state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(), "normal"));
        }
    }
}
