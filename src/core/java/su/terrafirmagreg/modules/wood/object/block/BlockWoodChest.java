package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockChest;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodChest;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodChest;

import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class BlockWoodChest extends BaseBlockChest implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodChest(WoodType type) {

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("chest"))
      .customResource(type.getResource("chest"))
      .ignoresProperties(BlockChest.FACING)
      .sound(SoundType.WOOD)
      .hardness(2.5f)
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.MEDIUM))
      .fireInfo(5, 20)
      .oreDict("chest")
      .oreDict("chest", "wood")
      .oreDict("chest", "wood", type);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      GuiHandler.openGui(worldIn, pos, playerIn);
    }
    return true;
  }


  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodChest();
  }


  @Override
  public Class<TileWoodChest> getTileClass() {
    return TileWoodChest.class;
  }

  @Override
  public TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRWoodChest();
  }
}
