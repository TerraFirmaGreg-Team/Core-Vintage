package tfcflorae.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

public class BlockBale extends BlockRotatedPillar implements IItemSize {

    public BlockBale() {
        super(new Material(MapColor.FOLIAGE));
        this.setSoundType(SoundType.PLANT);
        setHardness(0.6F);
        OreDictionaryHelper.register(this, "thatch");
        OreDictionaryHelper.register(this, "bale");
        BlockUtils.setFireInfo(this, 60, 20);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.VERY_SMALL;
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.HEAVY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.fall(fallDistance, 0.2F);
    }
}
