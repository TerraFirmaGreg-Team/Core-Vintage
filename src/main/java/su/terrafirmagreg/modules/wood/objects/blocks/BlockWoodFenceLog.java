package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWoodFenceLog extends BlockWoodFence {

  public BlockWoodFenceLog(WoodBlockVariant variant, WoodType type) {
    super(variant, type);

  }

  @Override
  public ResourceLocation getResourceLocation() {
    return ModUtils.resource(getRegistryKey());
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IStateMapper getStateMapper() {
    return new StateMap.Builder().build();
  }
}
