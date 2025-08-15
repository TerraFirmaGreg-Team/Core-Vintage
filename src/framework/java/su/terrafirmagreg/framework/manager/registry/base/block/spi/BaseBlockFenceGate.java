package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

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
    this(BlockSettings.of(Material.WOOD));
  }

  public BaseBlockFenceGate(BlockSettings settings) {
    super(BlockPlanks.EnumType.OAK);

    this.settings = settings;

    getSettings()
      .nonOpaque()
      .nonFullCube();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
