package com.lumintorious.ambiental.modifiers;

import su.terrafirmagreg.modules.device.objects.tiles.TEBloomery;
import su.terrafirmagreg.modules.device.objects.tiles.TECharcoalForge;
import su.terrafirmagreg.modules.device.objects.tiles.TEFirePit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.lumintorious.ambiental.capability.TemperatureCapability;
import net.dries007.tfc.objects.te.TELamp;

public class TileEntityModifier extends BlockModifier {

    public TileEntityModifier(String unlocalizedName) {
        super(unlocalizedName);
    }

    public TileEntityModifier(String unlocalizedName, float change, float potency) {
        super(unlocalizedName, change, potency);
    }

    public TileEntityModifier(String unlocalizedName, float change, float potency, boolean affectedByDistance) {
        super(unlocalizedName, change, potency, affectedByDistance);
    }

    private static boolean hasProtection(EntityPlayer player) {
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        return stack != null && !stack.isEmpty();
    }

    public static TileEntityModifier handleCharcoalForge(TileEntity tile, EntityPlayer player) {
        if (tile instanceof TECharcoalForge forge) {
            float temp = forge.getField(TECharcoalForge.FIELD_TEMPERATURE);
            float change = temp / 140f;
            float potency = temp / 350f;
            if (hasProtection(player)) {
                change = 1.0F;
            }
            return new TileEntityModifier("charcoal_forge", change, potency);
        } else {
            return null;
        }
    }

    public static TileEntityModifier handleFirePit(TileEntity tile, EntityPlayer player) {
        if (tile instanceof TEFirePit pit) {
            float temp = pit.getField(TEFirePit.FIELD_TEMPERATURE);
            float change = temp / 100f;
            float potency = temp / 350f;
            if (hasProtection(player)) {
                change = 1.0F;
            }
            return new TileEntityModifier("fire_pit", Math.min(6f, change), potency);
        } else {
            return null;
        }
    }

    public static TileEntityModifier handleBloomery(TileEntity tile, EntityPlayer player) {
        if (tile instanceof TEBloomery bloomery) {
            float change = bloomery.getRemainingTicks() > 0 ? 4f : 0f;
            float potency = change;
            if (hasProtection(player)) {
                change = 1.0F;
            }
            return new TileEntityModifier("bloomery", change, potency);
        } else {
            return null;
        }
    }

    public static TileEntityModifier handleLamps(TileEntity tile, EntityPlayer player) {
        if (tile instanceof TELamp lamp) {
            if (EnvironmentalModifier.getEnvironmentTemperature(player) < TemperatureCapability.AVERAGE) {
                float change = (lamp.isPowered() && lamp.getFuel() > 0) ? 1f : 0f;
                float potency = 0f;
                return new TileEntityModifier("lamp", change, potency, false);
            }
        }
        return null;
    }
}
