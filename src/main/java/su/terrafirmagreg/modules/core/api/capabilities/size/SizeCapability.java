package su.terrafirmagreg.modules.core.api.capabilities.size;

import net.dries007.tfc.api.capability.ItemStickCapability;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import su.terrafirmagreg.api.util.ModUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class SizeCapability {
	public static final ResourceLocation KEY = ModUtils.getID("size_capability");
	public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new LinkedHashMap<>(); //Used inside CT, set custom IItemSize for items outside TFC
	@CapabilityInject(ISizeCapability.class)
	public static Capability<ISizeCapability> SIZE_CAPABILITY;

	public static void preInit() {
		// Register the capability
		CapabilityManager.INSTANCE.register(ISizeCapability.class, new SizeStorage(), SizeProvider::getDefault);
	}

	public static void init() {
		// Add hardcoded size values for vanilla items
		CUSTOM_ITEMS.put(IIngredient.of(Items.COAL), () -> SizeProvider.get(Size.SMALL, Weight.LIGHT, true)); // Store anywhere stacksize = 32
		CUSTOM_ITEMS.put(IIngredient.of(Items.STICK), ItemStickCapability::new); // Store anywhere stacksize = 64
		CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> SizeProvider.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64
		CUSTOM_ITEMS.put(IIngredient.of(Items.BED), () -> SizeProvider.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1
		CUSTOM_ITEMS.put(IIngredient.of(Items.MINECART), () -> SizeProvider.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Store only in chests stacksize = 1
		CUSTOM_ITEMS.put(IIngredient.of(Items.ARMOR_STAND), () -> SizeProvider.get(Size.LARGE, Weight.HEAVY, true)); // Store only in chests stacksize = 4
		CUSTOM_ITEMS.put(IIngredient.of(Items.CAULDRON), () -> SizeProvider.get(Size.LARGE, Weight.LIGHT, true)); // Store only in chests stacksize = 32
		CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK), () -> SizeProvider.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Store anywhere stacksize = 64
	}

	/**
	 * Checks if an item is of a given size and weight
	 */
	public static boolean checkItemSize(ItemStack stack, Size size, Weight weight) {
		ISizeCapability cap = getIItemSize(stack);
		if (cap != null) {
			return cap.getWeight(stack) == weight && cap.getSize(stack) == size;
		}
		return false;
	}

	/**
	 * Gets the IItemSize instance from an itemstack, either via capability or via interface
	 *
	 * @param stack The stack
	 * @return The IItemSize if it exists, or null if it doesn't
	 */
	@Nullable
	public static ISizeCapability getIItemSize(ItemStack stack) {
		if (!stack.isEmpty()) {
			ISizeCapability size = stack.getCapability(SIZE_CAPABILITY, null);
			if (size != null) {
				return size;
			} else if (stack.getItem() instanceof ISizeCapability item) {
				return item;
			} else if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof ISizeCapability) {
				return (ISizeCapability) ((ItemBlock) stack.getItem()).getBlock();
			}
		}
		return null;
	}

	@Nonnull
	public static ICapabilityProvider getCustomSize(ItemStack stack) {
		for (Map.Entry<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> entry : CUSTOM_ITEMS.entrySet()) {
			if (entry.getKey().testIgnoreCount(stack)) {
				return entry.getValue().get();
			}
		}
		// Check for generic item types
		Item item = stack.getItem();
		if (item instanceof ItemTool || item instanceof ItemSword) {
			return SizeProvider.get(Size.LARGE, Weight.MEDIUM, true); // Stored only in chests, stacksize should be limited to 1 since it is a tool
		} else if (item instanceof ItemArmor) {
			return SizeProvider.get(Size.LARGE, Weight.VERY_HEAVY, true); // Stored only in chests and stacksize = 1
		} else if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockLadder) {
			return SizeProvider.get(Size.SMALL, Weight.VERY_LIGHT, true); // Fits small vessels and stacksize = 64
		} else if (item instanceof ItemBlock) {
			return SizeProvider.get(Size.SMALL, Weight.LIGHT, true); // Fits small vessels and stacksize = 32
		} else {
			return SizeProvider.get(Size.VERY_SMALL, Weight.VERY_LIGHT, true); // Stored anywhere and stacksize = 64
		}
	}
}
