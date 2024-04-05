package net.dries007.tfc.api.capability.forge;

import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

public final class CapabilityForgeable {
	public static final ResourceLocation KEY = new ResourceLocation(MODID_TFC, "item_forge");
	public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>(); //Used inside CT, set custom IForgeable for items outside TFC
	@CapabilityInject(IForgeable.class)
	public static Capability<IForgeable> FORGEABLE_CAPABILITY;

	public static void preInit() {
		CapabilityManager.INSTANCE.register(IForgeable.class, new DumbStorage<>(), ForgeableHandler::new);
	}

	@Nullable
	public static ICapabilityProvider getCustomForgeable(ItemStack stack) {
		Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_ITEMS.keySet();
		for (IIngredient<ItemStack> ingredient : itemItemSet) {
			if (ingredient.testIgnoreCount(stack)) {
				return CUSTOM_ITEMS.get(ingredient).get();
			}
		}
		return null;
	}
}
