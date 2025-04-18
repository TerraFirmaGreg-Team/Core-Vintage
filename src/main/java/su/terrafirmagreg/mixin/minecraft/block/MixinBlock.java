package su.terrafirmagreg.mixin.minecraft.block;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class)
public abstract class MixinBlock extends IForgeRegistryEntry.Impl<Block> implements IBlockSettings {

  @Unique
  @Mutable
  @Final
  protected Settings terraFirmaGreg$settings;


  @Inject(
    method = "<init>(Lnet/minecraft/block/material/Material;Lnet/minecraft/block/material/MapColor;)V",
    at = @At(
      value = "TAIL"
    )
  )
  public void onConstruct(Material blockMaterialIn, MapColor blockMapColorIn, CallbackInfo ci) {
    this.terraFirmaGreg$settings = Settings.of(blockMaterialIn, blockMapColorIn);
  }


  @Override
  public Settings getSettings() {
    return terraFirmaGreg$settings;
  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//  @Overwrite
//  public boolean causesSuffocation(IBlockState state) {
//    return this.getSettings().getIsSuffocating().test(state);
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//  @Overwrite
//  public boolean isOpaqueCube(IBlockState state) {
//    return getSettings() == null || getSettings().isOpaque();
//  }
//
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite
//  public boolean isFullCube(IBlockState state) {
//    return getSettings().isFullCube();
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite
//  public boolean isCollidable() {
//    return getSettings().isCollidable();
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite
//  public SoundType getSoundType() {
//    return getSettings().getSoundType();
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite
//  public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
//    return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite
//  @SideOnly(Side.CLIENT)
//  public BlockRenderLayer getRenderLayer() {
//    return getSettings().getRenderLayer();
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//  @Overwrite
//  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
//    return getSettings().getHardness().apply(blockState, worldIn, pos);
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//  @Overwrite
//  public float getExplosionResistance(Entity exploder) {
//    return getSettings().getResistance() / 5.0F;
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite(remap = false)
//  public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
//    return getSettings().isAir();
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite(remap = false)
//  public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
//    return getSettings().getSlipperiness().apply(state, world, pos);
//  }
//
//  /**
//   * @author Xikaro
//   * @reason Адаптация под ISettingsBlock
//   */
//
//  @Overwrite(remap = false)
//  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
//    return getSettings().getLightValue().apply(state, world, pos);
//  }

}
