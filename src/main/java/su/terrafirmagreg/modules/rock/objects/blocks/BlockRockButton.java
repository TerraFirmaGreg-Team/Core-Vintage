package su.terrafirmagreg.modules.rock.objects.blocks;

import lombok.Getter;

import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.util.OreDictionaryHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;

@Getter
public class BlockRockButton extends BlockButtonStone implements IRockBlock {

    private final RockBlockVariant blockVariant;
    private final RockType type;

    public BlockRockButton(RockBlockVariant blockVariant, RockType type) {
        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.STONE);
        setHardness(0.5f);

        //OreDictionaryHelper.register(this, blockVariant.toString(), type.toString());
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }


    @NotNull
    @Override
    public IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer) {
        IBlockState state = getStateFromMeta(meta);
        return BlockButton.canPlaceBlock(worldIn, pos, facing) ? state.withProperty(BlockDirectional.FACING, facing) : state.withProperty(BlockDirectional.FACING, EnumFacing.DOWN);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation(
                "rockcategory.name").getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
    }
}
