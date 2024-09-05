package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockRockPressurePlate extends BlockPressurePlate implements IRockBlock {

  protected final Settings settings;
  private final RockBlockVariant variant;
  private final RockType type;

  public BlockRockPressurePlate(RockBlockVariant variant, RockType type) {
    super(Material.ROCK, Sensitivity.MOBS);

    this.variant = variant;
    this.type = type;
    this.settings = Settings.of(Material.ROCK)
        .registryKey(variant.getRegistryKey(type))
        .hardness(variant.getHardness(type))
        .sound(SoundType.STONE)
        .hardness(0.5f)
        .oreDict(variant)
        .oreDict(variant, "stone")
        .oreDict(variant, "stone", type);

  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
      ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    tooltip.add(new TextComponentTranslation("rockcategory.name")
        .getFormattedText() + ": " + type.getCategory().getLocalizedName());
  }
}
