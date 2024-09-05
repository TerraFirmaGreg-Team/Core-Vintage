package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockRockButton extends BlockButtonStone implements IRockBlock {

  protected final Settings settings;
  private final RockBlockVariant variant;
  private final RockType type;

  public BlockRockButton(RockBlockVariant variant, RockType type) {
    this.variant = variant;
    this.type = type;
    this.settings = Settings.of(Material.CIRCUITS)
        .registryKey(variant.getRegistryKey(type))
        .hardness(variant.getHardness(type))
        .sound(SoundType.STONE)
        .hardness(0.5f)
        .oreDict(variant, "stone")
        .oreDict(variant, "stone", type);
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing,
      float hitX, float hitY, float hitZ, int meta,
      EntityLivingBase placer) {
    IBlockState state = getStateFromMeta(meta);
    return BlockButton.canPlaceBlock(worldIn, pos, facing) ? state.withProperty(
        BlockDirectional.FACING, facing) :
        state.withProperty(BlockDirectional.FACING, EnumFacing.DOWN);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
      ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": "
        + getType().getCategory().getLocalizedName());
  }
}
