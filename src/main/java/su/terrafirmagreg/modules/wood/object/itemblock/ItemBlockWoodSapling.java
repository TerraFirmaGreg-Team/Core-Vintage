package su.terrafirmagreg.modules.wood.object.itemblock;

import su.terrafirmagreg.api.base.itemblock.BaseItemBlock;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSapling;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBlockWoodSapling extends BaseItemBlock {

  private final BlockWoodSapling block;

  public ItemBlockWoodSapling(BlockWoodSapling block) {
    super(block);

    this.block = block;
  }

  @Override
  public boolean onEntityItemUpdate(EntityItem entityItem) {
    if (!entityItem.world.isRemote && entityItem.age >= entityItem.lifespan && !entityItem.getItem().isEmpty()) {
      final BlockPos pos = entityItem.getPosition();
      if (placeAndDecreaseCount(entityItem, pos)) {
        entityItem.setDead();
        return true;
      }
      for (EnumFacing face : EnumFacing.HORIZONTALS) {
        final BlockPos offsetPos = pos.offset(face);
        if (placeAndDecreaseCount(entityItem, offsetPos)) {
          entityItem.setDead();
          return true;
        }
      }
    }
    return false;
  }

  private boolean placeAndDecreaseCount(EntityItem entityItem, BlockPos pos) {
    if (entityItem.world.mayPlace(block, pos, false, EnumFacing.UP, null) && entityItem.world.setBlockState(pos, block.getDefaultState())) {
      entityItem.getItem().shrink(1);
    }
    return entityItem.getItem().isEmpty();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);

    var type = block.getType();

    if (GuiScreen.isShiftKeyDown()) {
      tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
      tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) type.getMinRain(), (int) type.getMaxRain()));
      tooltip.add(TextFormatting.GOLD
                  + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", type.getMinRain()), String.format("%.1f", type.getMaxRain())));
    } else {
      tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
    }
  }

}
