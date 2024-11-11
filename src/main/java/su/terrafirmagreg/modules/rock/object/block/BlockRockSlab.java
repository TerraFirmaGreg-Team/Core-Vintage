package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockSlab;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class BlockRockSlab extends BaseBlockSlab implements IRockBlock {

  protected final RockBlockVariant variant;
  protected final RockType type;
  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockRockSlab(Block model, RockBlockVariant variant, RockType type) {
    super(Settings.of(Material.ROCK));

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .hardness(variant.getHardness(type))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .oreDict("slab")
      .oreDict("slab", "stone");

    setHarvestLevel(ToolClasses.PICKAXE, model.getHarvestLevel(model.getDefaultState()));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
                             ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(
      new TextComponentTranslation("rockcategory.name")
        .getFormattedText() + ": " + getType().getCategory().getLocalizedName());
  }

  public static class Double extends BlockRockSlab {

    public Double(Block model, RockBlockVariant variant, RockType type) {
      super(model, variant, type);

    }

    @Override
    public boolean isDouble() {
      return true;
    }

    @Override
    public Double getDoubleSlab() {
      return this;
    }
  }

  public static class Half extends BlockRockSlab {

    public Half(Block model, Block doubleSlab, RockBlockVariant variant, RockType type) {
      super(model, variant, type);

      this.doubleSlab = (Double) doubleSlab;
      this.doubleSlab.halfSlab = this;
    }

    @Override
    public boolean isDouble() {
      return false;
    }

    @Override
    public Half getHalfSlab() {
      return this;
    }
  }
}
