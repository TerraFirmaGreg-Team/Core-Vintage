package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockRockStairs extends BlockStairs implements IRockBlock {

    private final RockBlockVariant blockVariant;
    private final RockType type;

    public BlockRockStairs(RockBlockVariant modelBlock, RockBlockVariant blockVariant, RockType type) {
        super(modelBlock.get(type).getDefaultState());

        this.blockVariant = blockVariant;
        this.type = type;
        this.useNeighborBrightness = true;

        var block = modelBlock.get(type);

        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", block.getHarvestLevel(block.getDefaultState()));
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "stairs");
        OreDictUtils.register(this, "stairs", "stone");
    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull Block blockIn,
                                @NotNull BlockPos fromPos) {
        // Prevents cobble stairs from falling
    }

    @Override
    public void onPlayerDestroy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        // Prevents chiseled smooth stone stairs from collapsing
    }

    @Override
    public void onBlockAdded(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        // Prevents cobble stairs from falling
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(
                new TextComponentTranslation("rockcategory.name")
                        .getFormattedText() + ": " + this.getCategory().getLocalizedName());
    }

}
