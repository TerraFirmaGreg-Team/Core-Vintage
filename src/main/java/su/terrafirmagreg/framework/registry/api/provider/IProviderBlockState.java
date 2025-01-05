package su.terrafirmagreg.framework.registry.api.provider;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IProviderBlockState {

  @SideOnly(Side.CLIENT)
  IStateMapper getStateMapper();
}
