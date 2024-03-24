package su.terrafirmagreg.modules.device.objects.items;

import lyeoj.tfcthings.items.ItemMetalSlingAmmo;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemReinforcedSling extends ItemSling {


	public ItemReinforcedSling() {
		this.setMaxDamage(256);
	}

	@Override
	public @NotNull String getName() {
		return "device/sling/reinforced";
	}

	@Override
	protected boolean isStone(ItemStack stack) {
		if (stack.getItem() instanceof ItemRock) {
			return true;
		} else if (stack.getItem() instanceof ItemIngot ingot) {
			return ingot.getMetal(stack) == Metal.UNKNOWN;
		} else return stack.getItem() instanceof ItemMetalSlingAmmo;
	}


}
