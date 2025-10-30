package su.terrafirmagreg.framework.manager.content.base.item.spi;


import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.framework.manager.content.base.item.api.IItemEntry;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockPlacement;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class BaseItemBlock extends ItemBlock implements IItemEntry, IProviderBlockColor {

  protected final ItemSettings settings;
  protected final Block modelBlock;

  public BaseItemBlock(Block block) {
    super(block);

    this.settings = ItemSettings.of(block);
    this.modelBlock = block;
  }

  @Override
  public IBlockColor getBlockColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getBlockColor() : null;
  }

  @Override
  public IItemColor getItemColor() {
    return modelBlock instanceof IProviderBlockColor provider ? provider.getItemColor() : null;
  }


  @Override
  public CreativeTabs getCreativeTab() {
    return this.settings.getGroup();
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    Supplier<EnumActionResult> resultSupplier = () -> super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);

    if (block instanceof IProviderBlockPlacement provider) {
      provider.onItemUse(resultSupplier, player.getHeldItem(hand), player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
    return resultSupplier.get();
  }

  @Override
  public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
    Supplier<Boolean> resultSupplier = () -> super.canPlaceBlockOnSide(world, pos, side, player, stack);

    if (block instanceof IProviderBlockPlacement provider) {
      return provider.canPlaceBlockOnSide(resultSupplier, world, pos, side, player, stack);
    }
    return resultSupplier.get();
  }


  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    return TranslatorUtils.getItemStackDisplayName(stack);
  }

  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }

}
