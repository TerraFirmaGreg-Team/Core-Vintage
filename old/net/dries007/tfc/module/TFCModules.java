package net.dries007.tfc.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.dries007.tfc.module.agriculture.ModuleAgriculture;
import net.dries007.tfc.module.animal.ModuleAnimal;
import net.dries007.tfc.module.devices.ModuleDevice;
import net.dries007.tfc.module.food.ModuleFood;
import net.dries007.tfc.module.metal.ModuleMetal;
import net.dries007.tfc.module.plant.ModulePlant;
import net.dries007.tfc.module.rock.ModuleRock;
import net.dries007.tfc.module.soil.ModuleSoil;
import net.dries007.tfc.module.wood.ModuleWood;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BooleanSupplier;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

@Config(modid = Tags.MOD_ID, name = Tags.MOD_NAME + "/modules")
public class TFCModules {

    public static Map<String, Boolean> MODULES = new TreeMap<>();

    static {
        MODULES.put(ModuleRock.MODULE_ID, true);
        MODULES.put(ModuleSoil.MODULE_ID, true);
        MODULES.put(ModuleWood.MODULE_ID, true);
        MODULES.put(ModuleMetal.MODULE_ID, true);
        MODULES.put(ModuleFood.MODULE_ID, true);
        MODULES.put(ModulePlant.MODULE_ID, true);
        MODULES.put(ModuleAnimal.MODULE_ID, true);
        MODULES.put(ModuleDevice.MODULE_ID, true);
        MODULES.put(ModuleAgriculture.MODULE_ID, true);

    }

    @SuppressWarnings("unused")
    public static class ConditionConfig implements IConditionFactory {

        @Override
        public BooleanSupplier parse(JsonContext context, JsonObject json) {

            if (json.has("include")) {
                JsonArray include = JsonUtils.getJsonArray(json, "include");

                for (JsonElement element : include) {
                    String module = element.getAsString();

                    if (!MODULES.containsKey(module)) {
                        throw new JsonSyntaxException("Unknown module id: [" + module + "]");
                    }

                    if (!MODULES.get(module)) {
                        return () -> false;
                    }
                }
            }

            if (json.has("exclude")) {
                JsonArray exclude = JsonUtils.getJsonArray(json, "exclude");

                for (JsonElement element : exclude) {
                    String module = element.getAsString();

                    if (!MODULES.containsKey(module)) {
                        throw new JsonSyntaxException("Unknown module id: [" + module + "]");
                    }

                    if (MODULES.get(module)) {
                        return () -> false;
                    }
                }
            }

            return () -> true;
        }
    }
}
