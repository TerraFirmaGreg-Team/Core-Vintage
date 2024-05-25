package su.terrafirmagreg.api.registry.provider;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockStateProvider {

    @SideOnly(Side.CLIENT)
    IStateMapper getStateMapper();
}
