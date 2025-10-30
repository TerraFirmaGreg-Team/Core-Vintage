package su.terrafirmagreg.framework.manager.content.provider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public interface IProviderBlockPlacement {

  default EnumActionResult onItemUse(Supplier<EnumActionResult> resultSupplier, ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    return resultSupplier.get();
  }

  default boolean canPlaceBlockOnSide(Supplier<Boolean> resultSupplier, World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
    return resultSupplier.get();
  }
}
