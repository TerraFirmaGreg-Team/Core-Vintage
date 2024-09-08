package su.terrafirmagreg.modules.soil.object.item;

import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;
import su.terrafirmagreg.modules.soil.client.gui.GuiContainerKnappingMud;
import su.terrafirmagreg.modules.soil.object.container.ContainerKnappingMud;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSoilMud extends ItemSoil
        implements IProviderContainer<ContainerKnappingMud, GuiContainerKnappingMud> {

  public ItemSoilMud(SoilItemVariant variant, SoilType type) {
    super(variant, type);

    getSettings()
            .weight(Weight.VERY_LIGHT);
  }


  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote && !player.isSneaking() && stack.getCount() > 2) {
      GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.SOIL_MUD);
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
  }


  @Override
  public ContainerKnappingMud getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerKnappingMud(inventoryPlayer, inventoryPlayer.player.getHeldItemMainhand());
  }

  @Override
  public GuiContainerKnappingMud getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiContainerKnappingMud(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, type.getTexture());
  }
}
