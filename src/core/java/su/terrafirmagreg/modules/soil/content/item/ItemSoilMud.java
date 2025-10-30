package su.terrafirmagreg.modules.soil.content.item;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.ISoilEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class ItemSoilMud extends BaseItem implements ISoilEntry {//implements IProviderContainer<ContainerKnappingMud, GuiContainerKnappingMud> {

  protected final SoilType type;

  public ItemSoilMud(SoilType type) {
    super(ItemSettings.of()
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT))
    );

    this.type = type;
  }


  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote && !player.isSneaking() && stack.getCount() > 2) {
      GuiHandler.openGui(world, player.getPosition(), player);
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
  }

//  @Override
//  public ContainerKnappingMud getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
//    return new ContainerKnappingMud(inventoryPlayer, inventoryPlayer.player.getHeldItemMainhand());
//  }
//
//  @Override
//  public GuiContainerKnappingMud getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
//    return new GuiContainerKnappingMud(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, type.getTexture(variant));
//  }
}
