package su.terrafirmagreg.modules.soil.object.block.spi;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
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

import static su.terrafirmagreg.api.data.Properties.BoolProp.CLAY;
import static su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;

@Getter
public abstract class BlockSoil extends BaseBlock implements ISoilEntry {

  protected final SoilType type;

  public BlockSoil(SoilType type) {
    this(Settings.of(Material.GROUND), type);
  }

  public BlockSoil(Settings settings, SoilType type) {
    super(settings);

    this.type = type;

    getSettings()
      .sound(SoundType.GROUND)
      .harvestLevel(ToolClasses.SHOVEL, 0)
      .hardness(2.0F);

    FallingBlockManager.registerFallable(this, VERTICAL_AND_HORIZONTAL);
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
    if (settings.isCanFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
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
