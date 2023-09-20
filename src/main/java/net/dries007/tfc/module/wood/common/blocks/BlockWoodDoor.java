package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.core.client.util.CustomStateMap;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.module.wood.common.blocks.itemblocks.ItemBlockWoodDoor;
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
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockWoodDoor extends BlockDoor implements IWoodBlock {
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodDoor(WoodBlockVariant variant, WoodType type) {
        super(Material.WOOD);

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHardness(3.0F);
        disableStats();

        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockWoodDoor(this);
    }

    /**
     * Вызов из ForgeRegistries сопряжен с тем, что при вызове getItemBlock возвращается null.
     * Я так и не разобрался почему так происходит.
     */
    @SuppressWarnings("ConstantConditions")
    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, @Nonnull Random rand, int fortune) {
        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
            return Items.AIR;
        } else {
            return ForgeRegistries.ITEMS.getValue(getRegistryLocation());
        }
    }

    /**
     * Вызов из ForgeRegistries сопряжен с тем, что при вызове getItemBlock возвращается null.
     * Я так и не разобрался почему так происходит.
     */
    @SuppressWarnings("ConstantConditions")
    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, @Nonnull RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {

        return new ItemStack(ForgeRegistries.ITEMS.getValue(getRegistryLocation()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .ignore(BlockDoor.POWERED).build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "normal"));
    }
}
