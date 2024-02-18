package su.terrafirmagreg.api.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHasItemModel {

	@SideOnly(Side.CLIENT)
	void onModelRegister(Item item);
}
