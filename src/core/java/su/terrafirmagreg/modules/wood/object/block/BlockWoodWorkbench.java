package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockWorkbench;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;
import su.terrafirmagreg.modules.wood.object.container.ContainerWoodWorkbench;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockWoodWorkbench extends BaseBlockWorkbench implements IWoodBlock {

  protected final WoodType type;

  public BlockWoodWorkbench(WoodType type) {
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("workbench"))
      .customResource(type.getResource("workbench"))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .renderLayer(BlockRenderLayer.CUTOUT)
      .hardness(2.0F)
      .resistance(5.0F)
      .fireInfo(5, 20)
      .oreDict("workbench");
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote && playerIn != null) {
      playerIn.displayGui(new InterfaceCraftingTable(this, worldIn, pos));
      playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
    }
    return true;
  }

  @SuppressWarnings("WeakerAccess")
  public static class InterfaceCraftingTable implements IInteractionObject {

    //todo: replace with proper workbench mechanics + normal forge gui code
    private final BlockWoodWorkbench workbench;
    private final World world;
    private final BlockPos position;

    public InterfaceCraftingTable(BlockWoodWorkbench workbench, World worldIn, BlockPos pos) {
      this.workbench = workbench;
      this.world = worldIn;
      this.position = pos;
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    @Override
    public String getName() {
      return "crafting_table";
    }

    /**
     * Returns true if this thing is named
     */
    @Override
    public boolean hasCustomName() {
      return false;
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    @Override
    public ITextComponent getDisplayName() {
      return new TextComponentTranslation(workbench.getTranslationKey() + ".name");
    }

    @Override
    public Container createContainer(InventoryPlayer inv, EntityPlayer player) {
      return new ContainerWoodWorkbench(inv, world, position, workbench);
    }

    @Override
    public String getGuiID() {
      return "minecraft:crafting_table";
    }
  }

}
