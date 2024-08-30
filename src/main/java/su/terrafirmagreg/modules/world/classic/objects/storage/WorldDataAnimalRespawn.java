package su.terrafirmagreg.modules.world.classic.objects.storage;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Saves animal respawning data to world save
 */
public class WorldDataAnimalRespawn
        extends WorldSavedData {

    private static final String DATA_ID = ModUtils.localize("data.animal_respawn");

    private final Map<ResourceLocation, Map<ChunkPos, Long>> respawnMap;

    private WorldDataAnimalRespawn() {
        super(DATA_ID);
        respawnMap = new HashMap<>();
    }

    @NotNull
    public static WorldDataAnimalRespawn get(World world) {
        MapStorage mapStorage = world.getMapStorage();
        if (mapStorage != null) {
            WorldDataAnimalRespawn data = (WorldDataAnimalRespawn) mapStorage.getOrLoadData(WorldDataAnimalRespawn.class, DATA_ID);
            if (data == null) {
                data = new WorldDataAnimalRespawn();
                data.markDirty();
                mapStorage.setData(DATA_ID, data);
            }
            return data;
        }
        throw new IllegalStateException("Unable to access animal respawning data!");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList tag = nbt.getTagList("animal", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tag.tagCount(); i++) {
            NBTTagCompound animalNbt = tag.getCompoundTagAt(i);
            ResourceLocation name = new ResourceLocation(animalNbt.getString("name"));
            NBTTagList gridValues = nbt.getTagList("values", Constants.NBT.TAG_COMPOUND);
            Map<ChunkPos, Long> internal = new HashMap<>();
            for (int j = 0; j < gridValues.tagCount(); j++) {
                NBTTagCompound gridTag = gridValues.getCompoundTagAt(i);
                ChunkPos pos = new ChunkPos(gridTag.getInteger("x"), gridTag.getInteger("z"));
                long respawn = gridTag.getLong("respawn");
                internal.put(pos, respawn);
            }
            respawnMap.put(name, internal);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList tag = new NBTTagList();
        for (ResourceLocation animal : respawnMap.keySet()) {
            NBTTagCompound animalNbt = new NBTTagCompound();
            animalNbt.setString("name", animal.toString());
            NBTTagList gridValues = new NBTTagList();
            Map<ChunkPos, Long> internal = respawnMap.get(animal);
            for (ChunkPos gridPos : internal.keySet()) {
                NBTTagCompound gridTag = new NBTTagCompound();
                gridTag.setInteger("x", gridPos.x);
                gridTag.setInteger("z", gridPos.z);
                gridTag.setLong("respawn", internal.get(gridPos));
                gridValues.appendTag(gridTag);
            }
            animalNbt.setTag("values", gridValues);
            tag.appendTag(animalNbt);
        }
        nbt.setTag("animal", tag);
        return nbt;
    }

    /**
     * Returns the last respawn tick of this animal, based on the chunk grid
     *
     * @param entity   the animal Entity
     * @param chunkPos the ChunkPos this entity is trying to spawn on
     * @return 0 if never respawned, the calendar's player time tick it was last respawned on
     */
    public long getLastRespawnTick(Entity entity, ChunkPos chunkPos) {
        ResourceLocation entityKey = EntityList.getKey(entity);
        Map<ChunkPos, Long> internal = respawnMap.get(entityKey);
        if (internal == null) {
            return 0L;
        }
        ChunkPos grid = new ChunkPos(chunkPos.x - chunkPos.x % 16, chunkPos.z - chunkPos.z % 16);
        return internal.getOrDefault(grid, 0L);
    }

    /**
     * Set the last respawn tick of this animal, based on the chunk grid
     *
     * @param entity   the animal Entity
     * @param chunkPos the chunk pos this entity is being respawned
     * @param tick     the calendar's player time tick
     */
    public void setLastRespawnTick(Entity entity, ChunkPos chunkPos, long tick) {
        ResourceLocation entityKey = EntityList.getKey(entity);
        ChunkPos grid = new ChunkPos(chunkPos.x - chunkPos.x % 16, chunkPos.z - chunkPos.z % 16);
        respawnMap.computeIfAbsent(entityKey, k -> new HashMap<>()).put(grid, tick);
        this.markDirty();
    }
}
