package net.dries007.tfc.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.damage.CapabilityDamageResistance;
import net.dries007.tfc.module.core.common.objects.entity.animal.AnimalFood;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


public enum JsonConfigRegistry {
    INSTANCE;
    private static final String DEFAULT_ANIMAL_FOOD = "assets/tfc/config/animal_food_data.json";
    private static final String DEFAULT_DAMAGE_RESISTANCE = "assets/tfc/config/entity_resistance_data.json";

    private File tfcConfigDir;

    /**
     * Creates the TFC config directory, and populates it if empty
     *
     * @param dir the mod config directory root
     */
    public void preInit(File dir) {
        // Init base config dir
        TerraFirmaCraft.LOGGER.info("Loading or creating TFC config directory");
        tfcConfigDir = new File(dir, Tags.MOD_ID);
        if (!tfcConfigDir.exists() && !tfcConfigDir.mkdir()) {
            throw new Error("Problem creating TFC extra config directory.");
        }

        File defaultFile = new File(tfcConfigDir, "entity_resistance_data.json");
        try {
            if (defaultFile.createNewFile()) {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(JsonConfigRegistry.class.getClassLoader().getResourceAsStream(DEFAULT_DAMAGE_RESISTANCE)), defaultFile);
            }
        } catch (IOException e) {
            throw new Error("Problem creating default entity resistance config file.", e);
        }
        defaultFile = new File(tfcConfigDir, "animal_food_data.json");
        try {
            if (defaultFile.createNewFile()) {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(JsonConfigRegistry.class.getClassLoader().getResourceAsStream(DEFAULT_ANIMAL_FOOD)), defaultFile);
            }
        } catch (IOException e) {
            throw new Error("Problem creating default animal food config file.", e);
        }
    }

    public void postInit() {
        Path[] recursivePathList;
        try {
            recursivePathList = Files.walk(tfcConfigDir.toPath()).filter(f -> Files.isRegularFile(f) && f.toString().endsWith(".json")).toArray(Path[]::new);
        } catch (IOException e) {
            TerraFirmaCraft.LOGGER.error("Unable to read files in the config directory! TFC will not generate any ore veins or load any damage resitances!");
            TerraFirmaCraft.LOGGER.error("Error: ", e);
            return;
        }
        for (Path path : recursivePathList) {
            try {
                // Read each file, then json parse each file individually into a map, so each vein can be parsed by GSON independently
                String fileContents = new String(Files.readAllBytes(path), Charset.defaultCharset());
                JsonObject jsonObject = new JsonParser().parse(fileContents).getAsJsonObject();
                JsonElement loader = jsonObject.get("#loader");
                if (loader != null && "tfc:damage_resistance".equals(loader.getAsString())) {
                    CapabilityDamageResistance.readFile(jsonObject.entrySet());
                } else if (loader != null && "tfc:animal_food".equals(loader.getAsString())) {
                    AnimalFood.readFile(jsonObject.entrySet());
                }

            } catch (IOException e) {
                // Don't crash the game if one of the files error-ed, just show it in log
                TerraFirmaCraft.LOGGER.error("There was an error reading a json file at: " + path);
                TerraFirmaCraft.LOGGER.error("Error: ", e);
            }
        }
        CapabilityDamageResistance.postInit();
    }

}
