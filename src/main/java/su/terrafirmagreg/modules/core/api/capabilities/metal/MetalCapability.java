package su.terrafirmagreg.modules.core.api.capabilities.metal;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.oredict.OreDictionary;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class MetalCapability {

    public static final ResourceLocation KEY = ModUtils.getID("metal_capability");
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_METAL_ITEMS = new HashMap<>(); //Used inside CT, set custom IMetalItem for items outside TFC
    public static final Map<String, Metal.ItemType> ORE_DICT_METAL_ITEMS = new LinkedHashMap<>();
    @CapabilityInject(IMetalCapability.class)
    public static Capability<IMetalCapability> METAL_CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(IMetalCapability.class, new MetalStorage(), MetalProvider::new);
    }

    public static void init() {
        CUSTOM_METAL_ITEMS.put(IIngredient.of(Blocks.IRON_BARS), () -> new MetalProvider(Metal.WROUGHT_IRON, 25, true));
    }

    /**
     * Gets the IMetalItem instance from an itemstack, either via capability or via interface
     *
     * @param stack The stack
     * @return The IMetalItem if it exists, or null if it doesn't
     */
    @Nullable
    public static IMetalCapability getMetalItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            IMetalCapability metal = stack.getCapability(METAL_CAPABILITY, null);
            if (metal != null) {
                return metal;
            } else if (stack.getItem() instanceof IMetalCapability) {
                return (IMetalCapability) stack.getItem();
            } else if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof IMetalCapability) {
                return (IMetalCapability) ((ItemBlock) stack.getItem()).getBlock();
            }
        }
        return null;
    }

    @Nullable
    public static ICapabilityProvider getCustomMetalItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_METAL_ITEMS.keySet();
            for (IIngredient<ItemStack> ingredient : itemItemSet) {
                if (ingredient.testIgnoreCount(stack)) {
                    return CUSTOM_METAL_ITEMS.get(ingredient).get();
                }
            }
            // Try using ore dict prefix-suffix common values (ie: ingotCopper)
            int[] ids = OreDictionary.getOreIDs(stack);
            for (int id : ids) {
                ICapabilityProvider handler = getMetalItemFromOreDict(OreDictionary.getOreName(id));
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }

    @Nullable
    private static ICapabilityProvider getMetalItemFromOreDict(String oreDict) {
        for (String oreName : ORE_DICT_METAL_ITEMS.keySet()) {
            if (oreDict.startsWith(oreName)) {
                //noinspection ConstantConditions
                return TFCRegistries.METALS.getValuesCollection().stream()
                        .filter(metal -> oreDict.equals(OreDictUtils.toString(oreName, metal.getRegistryName().getPath())))
                        .findFirst()
                        .map(metal -> {
                            Metal.ItemType type = ORE_DICT_METAL_ITEMS.get(oreName);
                            return new MetalProvider(metal, type.getSmeltAmount(), true);
                        }).orElse(null);
            }
        }
        return null;
    }
}
