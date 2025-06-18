package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Optional;

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggable", modid = ModIDs.FLUIDLOGGED)
public abstract class BaseBlockFenceGate extends BlockFenceGate implements IBlockEntry, IFluidloggable {

  protected final Settings settings;

  public BaseBlockFenceGate() {
    this(Settings.of(Material.WOOD));
  }

  public BaseBlockFenceGate(Settings settings) {
    super(BlockPlanks.EnumType.OAK);

    this.settings = settings;

    getSettings()
      .nonOpaque()
      .nonFullCube();
  }
}
