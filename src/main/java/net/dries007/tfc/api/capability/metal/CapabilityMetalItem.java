package net.dries007.tfc.api.capability.metal;

import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.material.Materials;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.util.Helpers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class CapabilityMetalItem {
    public static final ResourceLocation KEY = TerraFirmaCraft.identifier("metal_object");
    public static final Map<ItemStack, ICapabilityProvider> CUSTOM_METAL_ITEMS = new HashMap<>();
    @CapabilityInject(IMaterialItem.class)
    public static Capability<IMaterialItem> METAL_OBJECT_CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(IMaterialItem.class, new DumbStorage<>(), MetalItemHandler::new);
    }

    public static void init() {
        CUSTOM_METAL_ITEMS.put(new ItemStack(Blocks.IRON_BARS), new MetalItemHandler(Materials.Iron, 25, true));
        CUSTOM_METAL_ITEMS.put(new ItemStack(Items.IRON_INGOT), new MetalItemHandler(Materials.Iron, 144, true));
        CUSTOM_METAL_ITEMS.put(new ItemStack(TFCItems.UNREFINED_BLOOM), new MetalItemHandler(Materials.Iron, 144, true));
        CUSTOM_METAL_ITEMS.put(new ItemStack(TFCItems.REFINED_BLOOM), new MetalItemHandler(Materials.Iron, 144, true));
    }

    /**
     * Gets the IMetalItem instance from an itemstack, either via capability or via interface
     *
     * @param stack The stack
     * @return The IMetalItem if it exists, or null if it doesn't
     */
    @Nullable
    public static IMaterialItem getMaterialItem(ItemStack stack) {
        if (!stack.isEmpty()) {
            return stack.getCapability(METAL_OBJECT_CAPABILITY, null);
        }
        return null;
    }

    @Nullable
    public static ICapabilityProvider getCustomMetalItem(ItemStack stack) {
        var customMetalProperty = CUSTOM_METAL_ITEMS.entrySet().stream().filter(s -> s.getKey().getItem() == stack.getItem()).findFirst().orElse(null);

        if (customMetalProperty != null)
            return customMetalProperty.getValue();

        if (stack.getItem() instanceof MetaPrefixItem metaPrefixItem) {
            var orePrefix = metaPrefixItem.getOrePrefix();
            var material = metaPrefixItem.getMaterial(stack);

            if (material == null) return null;
            if (!material.hasProperty(TFGPropertyKey.HEAT)) return null;

            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getShouldHasMetalCapability()) {
                return new MetalItemHandler(material, Helpers.getOrePrefixMaterialAmount(orePrefix), true);
            }
        }

        return null;
    }
}
