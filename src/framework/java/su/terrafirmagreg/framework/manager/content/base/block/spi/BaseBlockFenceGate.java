package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Optional;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;

import lombok.Getter;

@Getter
@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggable", modid = ModIDs.FLUIDLOGGED)
public abstract class BaseBlockFenceGate extends BlockFenceGate implements IBlockEntry, IFluidloggable {

  protected final BlockSettings settings;

  public BaseBlockFenceGate() {
    this(BlockSettings.of()
      .material(Material.WOOD));
  }

  public BaseBlockFenceGate(BlockSettings settings) {
    super(BlockPlanks.EnumType.OAK);

    this.settings = settings;

    getSettings()
      .nonOpaque()
      .nonFullCube();

    this.fullBlock = this.settings.isOpaque();
    this.lightOpacity = this.fullBlock ? 255 : 0;
    this.translucent = this.settings.isTranslucent();
    this.useNeighborBrightness = this.settings.isUseNeighborBrightness();
  }
}
