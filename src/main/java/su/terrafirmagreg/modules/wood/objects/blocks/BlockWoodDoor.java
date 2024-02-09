package su.terrafirmagreg.modules.wood.objects.blocks;

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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.blocks.itemblocks.ItemBlockWoodDoor;

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

        //OreDictionaryHelper.register(this, variant.toString());
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
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

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public Item getItemDropped(IBlockState state, @NotNull Random rand, int fortune) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public ItemStack getPickBlock(@NotNull IBlockState state, @NotNull RayTraceResult target, @NotNull World world, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this));
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
