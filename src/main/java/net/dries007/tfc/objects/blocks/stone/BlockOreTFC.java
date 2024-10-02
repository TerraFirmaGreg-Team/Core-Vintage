package net.dries007.tfc.objects.blocks.stone;

import su.terrafirmagreg.data.enums.EnumGradeOre;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.data.Properties.EnumProp.GRADE_ORE;

public class BlockOreTFC extends Block {

  private static final Map<Ore, Map<RockType, BlockOreTFC>> TABLE = new HashMap<>();
  public final Ore ore;
  public final RockType type;

  public BlockOreTFC(Ore ore, RockType type) {
    super(Material.ROCK);

    if (!TABLE.containsKey(ore)) {
      TABLE.put(ore, new HashMap<>());
    }
    TABLE.get(ore).put(type, this);

    this.ore = ore;
    this.type = type;
    setDefaultState(blockState.getBaseState().withProperty(GRADE_ORE, EnumGradeOre.NORMAL));
    setSoundType(SoundType.STONE);
    setHardness(10.0F).setResistance(10.0F);
    setHarvestLevel("pickaxe", 0);
  }

  public static BlockOreTFC get(Ore ore, RockType rock) {
    return TABLE.get(ore).get(rock);
  }

  public static IBlockState get(Ore ore, RockType rock, EnumGradeOre grade) {
    IBlockState state = TABLE.get(ore).get(rock).getDefaultState();
    if (!ore.isGraded()) {
      return state;
    }
    return state.withProperty(GRADE_ORE, grade);
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(GRADE_ORE, EnumGradeOre.valueOf(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(GRADE_ORE).getMeta();
  }

  @Override
  @NotNull
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemOreTFC.get(ore);
  }

  @Override
  public int damageDropped(IBlockState state) {
    return getMetaFromState(state);
  }

  @Override
  @NotNull
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  /**
   * Handle drops separately, so will always drop
   */
  @Override
  public boolean canDropFromExplosion(Explosion explosionIn) {
    return false;
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, GRADE_ORE);
  }

  /**
   * Ore blocks should always drop from explosions, see #1325
   */
  @Override
  public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
    if (!world.isRemote) {
      dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
    }
    super.onBlockExploded(world, pos, explosion);
  }

  @Override
  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(state.getBlock());
  }
}
