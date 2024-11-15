package su.terrafirmagreg.mixin.minecraft.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = Block.class, remap = false)
public abstract class MixinBlock extends IForgeRegistryEntry.Impl<Block> implements IBlockSettings {

  @Mutable
  @Final
  protected Settings settings;

  @Shadow
  @Final
  public Material material;

  @Shadow
  protected boolean fullBlock;

  @Inject(method = "<init>(Lnet/minecraft/block/material/Material;Lnet/minecraft/block/material/MapColor;)V", at = @At(value = "TAIL"), remap = false)
  public void onConstruct(Material blockMaterialIn, MapColor blockMapColorIn, CallbackInfo ci) {
    this.settings = Settings.of(blockMaterialIn, blockMapColorIn);
  }

  @Override
  public Settings getSettings() {
    return settings;
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */
  @Overwrite
  public boolean isOpaqueCube(IBlockState state) {
    return getSettings() != null && getSettings().isOpaque();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */
  @Overwrite
  public boolean getUseNeighborBrightness(IBlockState state) {
    return getSettings().isUseNeighborBrightness();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public boolean isFullCube(IBlockState state) {
    return fullBlock = getSettings().isFullCube();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public boolean isCollidable() {
    return getSettings().isCollidable();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public SoundType getSoundType() {
    return getSettings().getSoundType();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
    return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return getSettings().getRenderLayer();
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
    return getSettings().getHardness().apply(blockState, worldIn, pos);
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public float getExplosionResistance(Entity exploder) {
    return getSettings().getResistance() / 5.0F;
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
    return getSettings().getSlipperiness().apply(state, world, pos);
  }

  /**
   * @author Xikaro
   * @reason Адаптация под ISettingsBlock
   */

  @Overwrite
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return getSettings().getLightValue().apply(state, world, pos);
  }


  public Item asItem() {
    return Item.getItemFromBlock((Block) (Object) this);
  }


  public boolean getHasItemSubtypes() {
    return getSettings().isHasItemSubtypes();
  }


  public Size getSize(ItemStack stack) {
    return getSettings().getSize();
  }


  public Weight getWeight(ItemStack stack) {
    return getSettings().getWeight();
  }


  public boolean canStack(ItemStack stack) {
    return getSettings().isCanStack();
  }

}
