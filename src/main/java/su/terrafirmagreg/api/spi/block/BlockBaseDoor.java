package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.model.ICustomState;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockDoor;
import su.terrafirmagreg.api.util.ModelUtils;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class BlockBaseDoor extends BlockDoor implements IAutoReg, ICustomState {

    protected BlockBaseDoor(Material material) {
        super(material);

        setHardness(3.0F);

    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        return new ItemBlockDoor(this);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public Item getItemDropped(IBlockState state, @NotNull Random rand, int fortune) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, @NotNull RayTraceResult target, @NotNull World world, @NotNull BlockPos pos,
                                  @NotNull EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onStateMapperRegister() {
        ModelUtils.registerStateMapper(this, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
    }
}
