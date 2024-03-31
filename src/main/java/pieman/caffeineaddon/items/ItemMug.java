package pieman.caffeineaddon.items;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemJug;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;
import su.terrafirmagreg.api.model.ICustomModel;

import javax.annotation.Nonnull;

public class ItemMug extends ItemJug implements ICustomModel {

	public ItemMug(String name) {
		super();
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabsTFC.CT_FOOD);

		ModItems.ITEMS.add(this);
	}

	//TODO: this is hardcoded as Mug should fix
	@Override
	@Nonnull
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
		if (bucketCap != null) {
			FluidStack fluidStack = bucketCap.drain(100, false);
			if (fluidStack != null) {
				String fluidname = fluidStack.getLocalizedName();
				return fluidname + " Mug";
			}
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public void onModelRegister() {
		CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
