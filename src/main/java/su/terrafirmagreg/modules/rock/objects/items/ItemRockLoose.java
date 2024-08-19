package su.terrafirmagreg.modules.rock.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;
import su.terrafirmagreg.modules.rock.client.gui.GuiContainerKnappingRock;
import su.terrafirmagreg.modules.rock.objects.containers.ContainerKnappingRock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public class ItemRockLoose extends ItemRock implements IProviderContainer<ContainerKnappingRock, GuiContainerKnappingRock> {

    public ItemRockLoose(RockItemVariant variant, RockType type) {
        super(variant, type);

        getSettings()
                .size(Size.SMALL)
                .weight(Weight.VERY_LIGHT)
                .addOreDict("rock")
                .addOreDict("rock", type)
                .addOreDict("rock", type.getRockCategory());

        if (type.isFlux()) getSettings().addOreDict("rock", "flux");
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation(
                "rockcategory.name").getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());

        if (getType().isFlux())
            tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("is_flux_rock.name").getFormattedText());
    }

    @Override
    public ContainerKnappingRock getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerKnappingRock(inventoryPlayer, inventoryPlayer.player.getHeldItemMainhand());
    }

    @Override
    public GuiContainerKnappingRock getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiContainerKnappingRock(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, getType().getTexture());
    }
}
