package su.terrafirmagreg.modules.rock.content.item;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItem;
import su.terrafirmagreg.framework.manager.content.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.feature.heat.capability.CapabilityProviderHeat;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.content.container.ContainerRockKnapping;
import su.terrafirmagreg.modules.rock.content.gui.GuiContainerKnappingRock;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class ItemRockLoose extends BaseItem
  implements IRockEntry, IProviderContainer<ContainerRockKnapping, GuiContainerKnappingRock> {

  protected final RockType type;

  public ItemRockLoose(RockType type) {
    super(ItemSettings.of()
      .capability(
        CapabilityProviderSize.of(Size.SMALL, Weight.VERY_LIGHT),
        stack -> CapabilityProviderHeat.of(stack.getTagCompound(), 0.2f, 2000f))  // Since this is technically still a pottery item, despite being a block
      .addOreDict("rock")
      .addOreDict("rock", type)
      .addOreDict(type::isFlux, "rock", "flux")
      .addOreDict("rock", type.getCategory())
    );

    this.type = type;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1) {
      GuiHandler.openGui(world, player.getPosition(), player);
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
  }


  @Override
  public ContainerRockKnapping getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerRockKnapping(inventoryPlayer, inventoryPlayer.player.getHeldItemMainhand());
  }

  @Override
  public GuiContainerKnappingRock getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiContainerKnappingRock(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, type.getTexture("raw"));
  }
}
