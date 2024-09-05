package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

import java.util.Random;

@Getter
public abstract class BlockSoil extends BaseBlock implements ISoilBlock {

  protected final SoilBlockVariant variant;
  protected final SoilType type;

  public BlockSoil(SoilBlockVariant variant, SoilType type) {
    super(Settings.of(Material.GROUND));

    this.variant = variant;
    this.type = type;

    getSettings()
        .registryKey(variant.getRegistryKey(type))
        .sound(SoundType.GROUND)
        .harvestLevel(ToolClasses.SHOVEL, 0)
        .fallable(this, variant.getSpecification())
        .hardness(2.0F)
        .oreDict(variant);
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
    if (variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos,
        pos, state, false)) {
      double d0 = (float) pos.getX() + rand.nextFloat();
      double d1 = (double) pos.getY() - 0.05D;
      double d2 = (float) pos.getZ() + rand.nextFloat();
      world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D,
          Block.getStateId(state));
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.PILE.get(this.getType());
  }
}
