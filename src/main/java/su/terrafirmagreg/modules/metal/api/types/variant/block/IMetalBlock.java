package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.api.registry.provider.IProviderModel;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.model.CustomStateMap;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IType<MetalType>, IVariant<MetalBlockVariant>, IBlockSettings,
    IProviderModel, IProviderBlockColor {

  /**
   * Возвращает местоположение регистрации блока почвы.
   *
   * @return Местоположение регистрации блока почвы.
   */
  @NotNull
  default String getRegistryKey() {
    return String.format("metal/%s/%s", getVariant(), getType());
  }

  /**
   * Возвращает расположение ресурса для данного деревянного блока.
   *
   * @return расположение ресурса
   */
  @NotNull
  default ResourceLocation getResourceLocation() {
    return ModUtils.resource(String.format("metal/%s", getVariant()));
  }

  @Override
  @SideOnly(Side.CLIENT)
  default @NotNull IStateMapper getStateMapper() {
    return new CustomStateMap.Builder().customResource(getResourceLocation()).build();
  }

  @Override
  default IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
