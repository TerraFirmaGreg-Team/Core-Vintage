package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockChest;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.content.inventory.InventoryWoodLargeChest;
import su.terrafirmagreg.modules.wood.content.render.TESRWoodChest;
import su.terrafirmagreg.modules.wood.content.tile.TileWoodChest;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockWoodChest extends BaseBlockChest implements IWoodEntry {

  protected final WoodType type;

  public BlockWoodChest(Type chestType, WoodType type) {
    super(chestType);

    this.type = type;
  }

  public BlockWoodChest(WoodType type) {
    super(Type.BASIC);

    this.type = type;

    getSettings()
      .customResource(type.getResource("chest"))
      .ignoresProperties(BlockChest.FACING)
      .sound(SoundType.WOOD)
      .hardness(2.5f)
      .tile(TileWoodChest.class, new TESRWoodChest())
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.MEDIUM))
      .fireInfo(5, 20)
      .addOreDict("chest")
      .addOreDict("chest", type)
      .addOreDict("chest", "wood");
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
      GuiHandler.openGui(worldIn, pos, playerIn);
    }
    return true;
  }

  /**
   * This and the following methods are copied from vanilla to allow us to hook into vanilla's chest stuff Hoppers are hardcoded for vanilla chest insertions, which means we need to block them (to stop inserting items that aren't the
   * correct size)
   */
  @Nullable
  @Override
  public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {
    return TileUtils.getTile(worldIn, pos, TileEntityChest.class)
      .map(tile -> {
        ILockableContainer ilockablecontainer = tile;

        if (!allowBlocking && isBlocked(worldIn, pos)) {
          return null;
        } else {
          for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = worldIn.getBlockState(blockpos).getBlock();

            if (block == this) {
              if (!allowBlocking && isBlocked(worldIn, blockpos)) // Forge: fix MC-99321
              {
                return null;
              }

              TileEntity worldInTileEntity = worldIn.getTileEntity(blockpos);

              if (worldInTileEntity instanceof TileEntityChest tileEntityChest1) {
                if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                  ilockablecontainer = new InventoryWoodLargeChest("container.chestDouble", ilockablecontainer, tileEntityChest1);
                } else {
                  ilockablecontainer = new InventoryWoodLargeChest("container.chestDouble", tileEntityChest1, ilockablecontainer);
                }
              }
            }
          }

          return ilockablecontainer;
        }
      }).orElse(null);
  }


  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodChest();
  }


}
