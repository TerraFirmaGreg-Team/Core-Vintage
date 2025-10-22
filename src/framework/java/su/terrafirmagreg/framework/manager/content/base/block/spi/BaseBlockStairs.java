package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;

@Getter
public class BaseBlockStairs extends BlockStairs implements IBlockEntry, IProviderBlockColor {

  protected final Block modelBlock;
  protected final BlockSettings settings;


  public BaseBlockStairs(Block block) {
    this(BlockSettings.of(block));

  }

  public BaseBlockStairs(BlockSettings settings) {
    super(settings.getBlock().getDefaultState());

    this.settings = settings;
    this.modelBlock = settings.getBlock();
    getSettings()
      .renderLayer(BlockRenderLayer.CUTOUT)
      .customResource(settings.getResource(), "_stairs")
      .addOreDict("stairs");

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();

    BlockUtils.BLOCK_TO_STAIRS.put(settings.getBlock(), this);
  }


  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.settings.getRenderLayer();
  }

  @Override
  public String getHarvestTool(IBlockState state) {
    return this.settings.getHarvestTool();
  }

  @Override
  public int getHarvestLevel(IBlockState state) {
    return this.settings.getHarvestLevel();
  }

//  @Override
//  public String getLocalizedName() {
//    return I18n.translateToLocal(this.getTranslationKey() + ".name");
//  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    // Prevents cobble stairs from falling
  }


  @Override
  public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state) {
    // Prevents chiseled smooth stone stairs from collapsing
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    // Prevents cobble stairs from falling
  }

  @Override
  public IBlockColor getBlockColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getBlockColor() : null;
  }

  @Override
  public IItemColor getItemColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getItemColor() : null;
  }
}
