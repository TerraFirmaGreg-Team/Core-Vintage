package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.CLAY;

@Getter
public abstract class BlockSoil extends BaseBlock implements ISoilBlock {

  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoil(SoilBlockVariant variant, SoilType type) {
    this(Settings.of(Material.GROUND), variant, type);
  }

  public BlockSoil(Settings settings, SoilBlockVariant variant, SoilType type) {
    super(settings);

    this.variant = variant;
    this.type = type;

    getSettings()
            .registryKey(variant.getRegistryKey(type))
            .sound(SoundType.GROUND)
            .harvestLevel(ToolClasses.SHOVEL, 0)
            .fallable(this, variant.getSpecification())
            .hardness(2.0F)
            .oreDict(variant)
            .oreDict(variant, type);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @Override
  public int damageDropped(IBlockState state) {
    return getMetaFromState(state);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    if (variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
      double d0 = (float) pos.getX() + rand.nextFloat();
      double d1 = (double) pos.getY() - 0.05D;
      double d2 = (float) pos.getZ() + rand.nextFloat();
      world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
    }
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {
    return state.getValue(CLAY) ? random.nextInt(4) : super.quantityDropped(state, fortune, random);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(CLAY) ? Items.CLAY_BALL : ItemsSoil.PILE.get(type);
  }
}
