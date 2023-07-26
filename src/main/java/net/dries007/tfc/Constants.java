/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dries007.tfc.api.capability.damage.DamageResistance;
import net.dries007.tfc.objects.entity.animal.AnimalFood;
import net.dries007.tfc.util.json.AnimalFoodJson;
import net.dries007.tfc.util.json.DamageResistanceJson;
import net.dries007.tfc.util.json.LowercaseEnumTypeAdapterFactory;
import net.dries007.tfc.util.json.ResourceLocationJson;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public final class Constants
{
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping()
        .registerTypeAdapter(ResourceLocation.class, new ResourceLocationJson())
        .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory())
        .registerTypeAdapter(DamageResistance.class, new DamageResistanceJson())
        .registerTypeAdapter(AnimalFood.class, new AnimalFoodJson())
        .create();


    public static final Random RNG = new Random();
}