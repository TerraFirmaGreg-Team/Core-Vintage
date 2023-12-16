package com.lumintorious.ambiental.compat;

import com.lumintorious.ambiental.capability.TemperatureCapability;
import com.lumintorious.ambiental.modifier.EnvironmentalModifier;
import com.lumintorious.ambiental.modifier.TempModifier;
import net.dries007.tfc.objects.te.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.Optional;

import static com.lumintorious.ambiental.api.ITileEntityTemperatureProvider.hasProtection;
import static com.lumintorious.ambiental.api.ITileEntityTemperatureProvider.mod;

public class TFC {

    public static Optional<TempModifier> handleCharcoalForge(EntityPlayer player, TileEntity tile) {
        if(tile instanceof TECharcoalForge) {
            TECharcoalForge forge = (TECharcoalForge)tile;

            float temp = forge.getField(TECharcoalForge.FIELD_TEMPERATURE);
            float change =  temp / 140f;
            if(hasProtection(player)){
                change = change * mod;
            }
            return TempModifier.defined("charcoal_forge", change, 0);
        }else {
            return TempModifier.none();
        }
    }

    public static Optional<TempModifier> handleFirePit(EntityPlayer player, TileEntity tile) {
        if(tile instanceof TEFirePit) {
            TEFirePit pit = (TEFirePit)tile;

            float temp = pit.getField(TEFirePit.FIELD_TEMPERATURE);
            float change =  temp / 100f;
            if(hasProtection(player)){
                change = change * mod;
            }
            return TempModifier.defined("fire_pit", Math.min(6f, change), 0);
        }else {
            return TempModifier.none();
        }
    }

    public static Optional<TempModifier> handleBloomery(EntityPlayer player, TileEntity tile) {
        if(tile instanceof TEBloomery) {
            TEBloomery bloomery = (TEBloomery)tile;

            float change = bloomery.getRemainingTicks() > 0 ? 4f : 0f;
            if(hasProtection(player)){
                change = change * mod;
            }
            return TempModifier.defined("bloomery", change, 0);
        }else {
            return TempModifier.none();
        }
    }

    public static Optional<TempModifier> handleCrucible(EntityPlayer player, TileEntity tile) {
        if (tile instanceof TECrucible) {
            TECrucible crucible = (TECrucible) tile;

            float temp = crucible.getField(TECrucible.FIELD_TEMPERATURE);
            float change =  temp / 100f;
            if (hasProtection(player)) {
                change = change * mod;
            }
            return TempModifier.defined("crucible", change, 0);
        } else {
            return TempModifier.none();
        }
    }

    public static Optional<TempModifier> handleLamps(EntityPlayer player, TileEntity tile) {
        if(tile instanceof TELamp) {
            TELamp lamp = (TELamp)tile;

            if(EnvironmentalModifier.getEnvironmentTemperature(player) < TemperatureCapability.AVERAGE) {
                float change = (lamp.isPowered() && lamp.getFuel() > 0) ? 1f : 0f;
                float potency = 0f;
                return TempModifier.defined("lamp", change, potency);
            }
        }
        return TempModifier.none();
    }
}
