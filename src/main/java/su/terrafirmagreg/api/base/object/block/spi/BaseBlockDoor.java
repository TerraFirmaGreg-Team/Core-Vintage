package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.item.spi.BaseItemDoor;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import lombok.Getter;

import java.util.Random;

@Getter
public abstract class BaseBlockDoor extends BlockDoor implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockDoor(Settings settings) {
    super(settings.getMaterial());

    this.settings = settings;

    getSettings()
      .ignoresProperties(BlockDoor.POWERED)
      .itemBlock(BaseItemDoor::new)
      .capability(CapabilityProviderSize.of(Size.VERY_LARGE, Weight.HEAVY))
      .hardness(3.0F);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : Item.getItemFromBlock(this);
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(Item.getItemFromBlock(this));
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize("block", this.getRegistryName());
  }

  @Override
  public String getHarvestTool(IBlockState state) {
    return this.settings.getHarvestTool();
  }

  @Override
  public int getHarvestLevel(IBlockState state) {
    return this.settings.getHarvestLevel();
  }

}
