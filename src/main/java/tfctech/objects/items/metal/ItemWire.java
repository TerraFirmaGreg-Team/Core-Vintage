package tfctech.objects.items.metal;

import net.dries007.tfc.api.types.Metal;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWire extends ItemTechMetal {
	public ItemWire(Metal metal, ItemType type) {
		super(metal, type);
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getMetadata() > 0) {
			String stage = (new TextComponentTranslation("tooltip.tfctech.metalitem.wire.stage", 5 - stack.getMetadata())).getFormattedText();
			tooltip.add(stage);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this));
			items.add(new ItemStack(this, 1, 4));
		}
	}

	@NotNull
	@Override
	public String getItemStackDisplayName(@NotNull ItemStack stack) {
		if (stack.getMetadata() > 0) {
			//noinspection ConstantConditions
			String metalName = (new TextComponentTranslation("tfc.types.metal." + metal.getRegistryName()
			                                                                           .getPath()
			                                                                           .toLowerCase())).getFormattedText();
			return (new TextComponentTranslation("item.tfctech.metalitem.wire.unfinished", metalName)).getFormattedText();
		}
		return super.getItemStackDisplayName(stack);
	}
}
