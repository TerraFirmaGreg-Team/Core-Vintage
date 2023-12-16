package com.lumintorious.ambiental.api;

import com.lumintorious.ambiental.capability.TemperatureCapability;
//import com.lumintorious.ambiental.item.ClothesItem;
//import com.lumintorious.ambiental.item.TemperatureAlteringMaterial;
import com.lumintorious.ambiental.modifier.EnvironmentalModifier;
import com.lumintorious.ambiental.modifier.TempModifier;
import com.lumintorious.ambiental.modifier.TempModifierStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
//import net.minecraft.world.entity.EquipmentSlot;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.LightLayer;
//import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

@FunctionalInterface
public interface IEquipmentTemperatureProvider {
    Optional<TempModifier> getModifier(EntityPlayer player, ItemStack stack);

//    static Optional<TempModifier> handleClothes(EntityPlayer player, ItemStack stack) {
//        if(stack.getItem() instanceof ClothesItem clothesItem) {
//            if(clothesItem.getMaterial() instanceof TemperatureAlteringMaterial tempMaterial) {
//                return Optional.of(tempMaterial.getTempModifier(stack));
//            }
//        }
//        return TempModifier.none();
//    }
//
//    static Optional<TempModifier> handleSunlightCap(EntityPlayer player, ItemStack stack) {
//        float AVERAGE = TFCAmbientalConfig.GENERAL.averageTemperature;
//        if(stack.getItem() instanceof ClothesItem clothesItem) {
//            if(clothesItem.getEquivalentSlot() == EquipmentSlot.HEAD) {
//                if(player.level.getBrightness(LightLayer.SKY, player.getOnPos().above()) > 14) {
//                    float envTemp = EnvironmentalModifier.getEnvironmentTemperatureWithTimeOfDay(player);
//                    if(envTemp > AVERAGE) {
//                        float diff = envTemp - AVERAGE;
//                        Optional<TempModifier> helmetMod = handleClothes(player, stack);
//                        if(helmetMod.isPresent()) {
//                            diff = diff - helmetMod.get().getChange();
//                        };
//                        return TempModifier.defined("sunlight_protection", diff * -0.2f, -0.5f);
//                    }
//                }
//            }
//        }
//        return TempModifier.none();
//    }

    static void evaluateAll(EntityPlayer player, TempModifierStorage storage) {
//        CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent(c -> {
//            for(int i = 0; i < c.getSlots(); i++) {
//                ItemStack stack = c.getStackInSlot(i);
//                for(var fn : AmbientalRegistry.EQUIPMENT) {
//                    storage.add(fn.getModifier(player, stack));
//                }
//            }
//        });
//        Iterable<ItemStack> armor = player.getArmorInventoryList();
//        for(ItemStack stack : armor) {
//            if(stack.getItem() instanceof ItemArmor) {
//                ItemArmor thing = (ItemArmor)stack.getItem();
//                if(thing.armorType == EntityEquipmentSlot.HEAD) {
//                    if(player.world.getLight(player.getPosition()) > 14) {
//                        float envTemp = EnvironmentalModifier.getEnvironmentTemperature(player);
//                        if(envTemp > TemperatureCapability.AVERAGE + 3) {
//                            float diff = envTemp - TemperatureCapability.AVERAGE;
//                            storage.add(new EquipmentModifier("helmet", - diff / 3f, -0.5f));
//                        }else{
//                            storage.add(new EquipmentModifier("armor", 3f, -0.25f));
//                        }
//                    }
//                }else {
//                    float envTemp = EnvironmentalModifier.getEnvironmentTemperature(player);
//                    if(envTemp > TemperatureCapability.AVERAGE + 3){
//                        storage.add(new EquipmentModifier("armor", 3f, -0.25f));
//                    }
//                }
//            }
//        }
    }

    static boolean hasLeatherArmorProtection(EntityPlayer player) {
        Item head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
        Item chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
        Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
        Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

        Item leatherHelmet = Items.LEATHER_HELMET;
        Item leatherChestplate = Items.LEATHER_CHESTPLATE;
        Item leatherLeggings = Items.LEATHER_LEGGINGS;
        Item leatherBoots = Items.LEATHER_BOOTS;

        // Leather Armor
        return head.equals(leatherHelmet) &&
                chest.equals(leatherChestplate) &&
                legs.equals(leatherLeggings) &&
                feet.equals(leatherBoots);
    }
}
