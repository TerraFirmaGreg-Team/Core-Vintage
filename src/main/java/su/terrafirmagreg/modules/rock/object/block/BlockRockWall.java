package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockWall;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
public class BlockRockWall extends BaseBlockWall implements IRockBlock {

  private final RockBlockVariant variant;
  private final RockType type;

  public BlockRockWall(Block model, RockBlockVariant variant, RockType type) {
    super(model);

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .hardness(variant.getHardness(type))
      .sound(SoundType.STONE)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .oreDict("wall")
      .oreDict("wall", "stone");

    setHarvestLevel(ToolClasses.PICKAXE, 0);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
                             ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": "
                + type.getCategory().getLocalizedName());
  }
}
