package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockStairs;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.Block;
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

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
@SuppressWarnings("deprecation")
public class BlockRockStairs extends BaseBlockStairs implements IRockBlock {

  private final RockBlockVariant variant;
  private final RockType type;

  public BlockRockStairs(Block model, RockBlockVariant variant, RockType type) {
    super(model);

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .hardness(variant.getHardness(type))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .oreDict("stairs")
      .oreDict("stairs", model);
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
                              BlockPos fromPos) {
    // Prevents cobble stairs from falling
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
                             ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(
      new TextComponentTranslation("rockcategory.name")
        .getFormattedText() + ": " + this.getType().getCategory().getLocalizedName());
  }

  @Override
  public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
    // Prevents chiseled smooth stone stairs from collapsing
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    // Prevents cobble stairs from falling
  }

}
