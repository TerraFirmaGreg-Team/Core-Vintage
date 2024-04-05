package pieman.caffeineaddon.items;

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;
import su.terrafirmagreg.api.model.ICustomModel;

import org.jetbrains.annotations.Nullable;

public class ItemCoffeeBerries extends ItemFood implements ICustomModel {

	public ItemCoffeeBerries(String name) {
		super(0, 0, false);
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabsTFC.CT_FOOD);
		this.setPotionEffect(new PotionEffect(MobEffects.SPEED, 800, 0), 1f);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void onModelRegister() {
		CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new FoodHandler(nbt, new FoodData(4, 0f, 0f, 0f, 0f, 0.75f, 0f, 0f, 4f));
	}
}
