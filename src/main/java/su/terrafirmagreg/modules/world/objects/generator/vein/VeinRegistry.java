package su.terrafirmagreg.modules.world.objects.generator.vein;

import su.terrafirmagreg.data.Constants;
import su.terrafirmagreg.data.lib.collection.WeightedCollection;
import su.terrafirmagreg.modules.world.objects.generator.GeneratorOreVeins;


import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.dries007.tfc.TerraFirmaCraft;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum VeinRegistry {
    INSTANCE;

    private final WeightedCollection<VeinType> weightedVeinTypes = new WeightedCollection<>();
    private final Map<String, VeinType> veinTypeRegistry = new HashMap<>();

    @NotNull
    public WeightedCollection<VeinType> getVeins() {
        return weightedVeinTypes;
    }

    @NotNull
    public Set<String> keySet() {
        return veinTypeRegistry.keySet();
    }

    @Nullable
    public VeinType getVein(String name) {
        return veinTypeRegistry.get(name);
    }

    /**
     * Wraps things up and output to log
     */
    public void postInit() {
        // Reset max chunk radius and number of rolls for veins
        int maxRadius = 0;
        for (VeinType type : veinTypeRegistry.values()) {
            if (type.getWidth() > maxRadius) {
                maxRadius = type.getWidth();
            }
        }
        GeneratorOreVeins.CHUNK_RADIUS = 1 + (maxRadius >> 4);
        TerraFirmaCraft.getLog()
                .info("Vein Registry Initialized, with {} veins, {} max radius, {} total weight", veinTypeRegistry.size(), maxRadius,
                        weightedVeinTypes.getTotalWeight());
    }

    /**
     * Read file and load valid veins into registry
     *
     * @param jsonElements the json elements to read
     * @param subfolder    the current subfolder, relative to TFC config directory (used to differentiate veins in case of more than one file registers a vein with the same name)
     */
    public void readFile(Set<Map.Entry<String, JsonElement>> jsonElements, String subfolder) {
        for (Map.Entry<String, JsonElement> entry : jsonElements) {
            try {
                String properVeinName = entry.getKey();
                if ("#loader".equals(properVeinName)) continue; // Skip loader
                if (!subfolder.isEmpty()) {
                    properVeinName = subfolder + "/" + properVeinName;
                }
                VeinType vein = Constants.GSON.fromJson(entry.getValue(), VeinType.class);
                vein.setRegistryName(properVeinName);

                veinTypeRegistry.put(properVeinName, vein);
                weightedVeinTypes.add(vein.getWeight(), vein);

                TerraFirmaCraft.getLog().debug("Registered new vein " + vein);
            } catch (JsonParseException e) {
                TerraFirmaCraft.getLog().error("An ore vein is specified incorrectly! Skipping.");
                TerraFirmaCraft.getLog().error("Error: ", e);
            }
        }
    }
}
