package net.dries007.tfc.api.capability.chunkdata;

import su.terrafirmagreg.data.lib.NBTBuilder;
import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.objects.generator.vein.Vein;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistry;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.ROCKLAYER2;
import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.ROCKLAYER3;

@SuppressWarnings("WeakerAccess")
public final class ChunkData {

    public static final int FISH_POP_MAX = 60;

    private static final ChunkData EMPTY = new ChunkData();

    static {
        Arrays.fill(EMPTY.drainageLayer, DataLayerClassic.ERROR);
        Arrays.fill(EMPTY.stabilityLayer, DataLayerClassic.ERROR);
        Arrays.fill(EMPTY.seaLevelOffset, -1);
    }

    private final int[] rockLayer1 = new int[256];
    private final int[] rockLayer2 = new int[256];
    private final int[] rockLayer3 = new int[256];
    private final DataLayerClassic[] drainageLayer = new DataLayerClassic[256]; // To be removed / replaced?
    private final DataLayerClassic[] stabilityLayer = new DataLayerClassic[256]; // To be removed / replaced?
    private final int[] seaLevelOffset = new int[256];
    @Getter
    private boolean initialized = false;
    @Getter
    private int fishPopulation = FISH_POP_MAX; // todo: Set this based on biome? temp? rng?
    @Getter
    private float rainfall;
    @Getter
    private float regionalTemp;
    private float averagTemp;
    @Getter
    private float floraDensity;
    @Getter
    private float floraDiversity;
    private Set<Vein> generatedVeins = new HashSet<>();
    private int chunkWorkage;
    private long protectedTicks; // Used for hostile spawn protection. Starts negative, increases by players in the area
    @Getter
    private long lastUpdateTick; // The last time this chunk was updated by world regen
    @Getter
    private long lastUpdateYear;

    @NotNull
    public static ChunkData get(World world, BlockPos pos) {
        return get(world.getChunk(pos));
    }

    @NotNull
    public static ChunkData get(Chunk chunk) {
        ChunkData data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
        return data == null ? EMPTY : data;
    }

    public static Rock getRock1(World world, BlockPos pos) {
        return get(world, pos).getRockLayer1(pos.getX() & 15, pos.getZ() & 15);
    }

    public static Rock getRock2(World world, BlockPos pos) {
        return get(world, pos).getRockLayer2(pos.getX() & 15, pos.getZ() & 15);
    }

    public static Rock getRock3(World world, BlockPos pos) {
        return get(world, pos).getRockLayer3(pos.getX() & 15, pos.getZ() & 15);
    }

    public static float getRainfall(World world, BlockPos pos) {
        return get(world, pos).getRainfall();
    }

    public static float getFloraDensity(World world, BlockPos pos) {
        return get(world, pos).getFloraDensity();
    }

    public static float getFloraDiversity(World world, BlockPos pos) {
        return get(world, pos).getFloraDiversity();
    }

    public static boolean isStable(World world, BlockPos pos) {
        return get(world, pos).getStabilityLayer(pos.getX() & 15, pos.getZ() & 15).valueInt == 0;
    }

    public static int getDrainage(World world, BlockPos pos) {
        return get(world, pos).getDrainageLayer(pos.getX() & 15, pos.getZ() & 15).valueInt;
    }

    public static int getSeaLevelOffset(World world, BlockPos pos) {
        return get(world, pos).getSeaLevelOffset(pos.getX() & 15, pos.getZ() & 15);
    }

    public static int getFishPopulation(World world, BlockPos pos) {
        return get(world, pos).getFishPopulation();
    }

    public static Rock getRockHeight(World world, BlockPos pos) {
        return get(world, pos).getRockLayerHeight(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
    }

    /**
     * INTERNAL USE ONLY. No need to mark as dirty, since this will only ever be called on worldgen, before the first chunk save.
     */
    public void setGenerationData(int[] rockLayer1, int[] rockLayer2, int[] rockLayer3, DataLayerClassic[] stabilityLayer, DataLayerClassic[] drainageLayer,
                                  int[] seaLevelOffset, float rainfall, float regionalTemp, float avgTemp, float floraDensity, float floraDiversity) {
        this.initialized = true;
        System.arraycopy(rockLayer1, 0, this.rockLayer1, 0, 256);
        System.arraycopy(rockLayer2, 0, this.rockLayer2, 0, 256);
        System.arraycopy(rockLayer3, 0, this.rockLayer3, 0, 256);
        System.arraycopy(stabilityLayer, 0, this.stabilityLayer, 0, 256);
        System.arraycopy(drainageLayer, 0, this.drainageLayer, 0, 256);
        System.arraycopy(seaLevelOffset, 0, this.seaLevelOffset, 0, 256);

        this.rainfall = rainfall;
        this.regionalTemp = regionalTemp;
        this.averagTemp = avgTemp;
        this.floraDensity = floraDensity;
        this.floraDiversity = floraDiversity;

        this.chunkWorkage = 0;

        this.lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
        this.lastUpdateYear = Calendar.CALENDAR_TIME.getTotalYears();
    }

    /**
     * Adds generated ores to this chunk list of ores Should be used by ore vein generators to save in this chunk which ores generated here
     *
     * @param vein the ore added by ore vein generator
     */
    public void markVeinGenerated(@NotNull Vein vein) {
        generatedVeins.add(vein);
    }

    /**
     * @return the veins generated in this chunk. Note: the veins here are soft (non-functional) copies. They are used for data markers, not for actual world generation
     */
    public Set<Vein> getGeneratedVeins() {
        return generatedVeins;
    }

    public boolean canWork(int amount) {
        return ConfigTFC.Devices.SLUICE.maxWorkChunk == 0 || chunkWorkage <= ConfigTFC.Devices.SLUICE.maxWorkChunk + amount;
    }

    public void addWork(int amount) {
        chunkWorkage += amount;
    }

    public void addWork() {
        addWork(1);
    }

    public void setWork(int amount) {
        chunkWorkage = amount;
    }

    public Rock getRock1(BlockPos pos) {
        return getRock1(pos.getX() & 15, pos.getY() & 15);
    }

    public Rock getRock1(int x, int z) {
        return getRockLayer1(x, z);
    }

    public Rock getRock2(BlockPos pos) {
        return getRock2(pos.getX() & 15, pos.getY() & 15);
    }

    public Rock getRock2(int x, int z) {
        return getRockLayer2(x, z);
    }

    public Rock getRock3(BlockPos pos) {
        return getRock3(pos.getX() & 15, pos.getY() & 15);
    }

    public Rock getRock3(int x, int z) {
        return getRockLayer3(x, z);
    }

    public boolean isStable(int x, int z) {
        return getStabilityLayer(x, z).valueInt == 0;
    }

    public int getDrainage(int x, int z) {
        return getDrainageLayer(x, z).valueInt;
    }

    public Rock getRockHeight(BlockPos pos) {
        return getRockHeight(pos.getX(), pos.getY(), pos.getZ());
    }

    public Rock getRockHeight(int x, int y, int z) {
        return getRockLayerHeight(x & 15, y, z & 15);
    }

    public int getSeaLevelOffset(BlockPos pos) {
        return getSeaLevelOffset(pos.getX() & 15, pos.getY() & 15);
    }

    public int getSeaLevelOffset(int x, int z) {
        return seaLevelOffset[z << 4 | x];
    }

    public float getAverageTemp() {
        return averagTemp;
    }

    public void addSpawnProtection(int multiplier) {
        if (protectedTicks < Calendar.PLAYER_TIME.getTicks()) {
            protectedTicks = Calendar.PLAYER_TIME.getTicks();
        }
        protectedTicks += multiplier * 600L;
    }

    public long getSpawnProtection() {
        return protectedTicks - (24 * ICalendar.TICKS_IN_HOUR) - Calendar.PLAYER_TIME.getTicks();
    }

    public boolean isSpawnProtected() {
        return getSpawnProtection() > 0L;
    }

    public void resetLastUpdateTick() {
        this.lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    }

    public void resetLastUpdateYear() {
        this.lastUpdateYear = Calendar.CALENDAR_TIME.getTotalYears();
    }

    public List<Tree> getValidTrees() {
        return TFCRegistries.TREES.getValuesCollection().stream()
                .filter(t -> t.isValidLocation(averagTemp, rainfall, floraDensity))
                .sorted((s, t) -> (int) (t.getDominance() - s.getDominance()))
                .collect(Collectors.toList());
    }

    @Nullable
    public Tree getSparseGenTree() {
        return TFCRegistries.TREES.getValuesCollection().stream()
                .filter(t -> t.isValidLocation(0.5f * averagTemp + 10f, 0.5f * rainfall + 120f, 0.5f))
                .min((s, t) -> (int) (t.getDominance() - s.getDominance()))
                .orElse(null);
    }

    // Directly accessing the DataLayer is discouraged (except for getting the name). It's easy to use the wrong value.
    public Rock getRockLayer1(int x, int z) {
        return ((ForgeRegistry<Rock>) TFCRegistries.ROCKS).getValue(rockLayer1[z << 4 | x]);
    }

    public Rock getRockLayer2(int x, int z) {
        return ((ForgeRegistry<Rock>) TFCRegistries.ROCKS).getValue(rockLayer2[z << 4 | x]);
    }

    public Rock getRockLayer3(int x, int z) {
        return ((ForgeRegistry<Rock>) TFCRegistries.ROCKS).getValue(rockLayer3[z << 4 | x]);
    }

    public DataLayerClassic getStabilityLayer(int x, int z) {
        return stabilityLayer[z << 4 | x];
    }

    public DataLayerClassic getDrainageLayer(int x, int z) {
        return drainageLayer[z << 4 | x];
    }

    public Rock getRockLayerHeight(int x, int y, int z) {
        int offset = getSeaLevelOffset(x, z);
        if (y <= ROCKLAYER3 + offset) return getRockLayer3(x, z);
        if (y <= ROCKLAYER2 + offset) return getRockLayer2(x, z);
        return getRockLayer1(x, z);
    }

    public static final class ChunkDataStorage implements Capability.IStorage<ChunkData> {

        public static NBTTagByteArray write(DataLayerClassic[] layers) {
            return new NBTTagByteArray(Arrays.stream(layers).map(x -> (byte) x.layerID).collect(Collectors.toList()));
        }

        public static void read(DataLayerClassic[] layers, byte[] bytes) {
            for (int i = bytes.length - 1; i >= 0; i--) {
                layers[i] = DataLayerClassic.get(bytes[i]);
            }
        }

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<ChunkData> capability, ChunkData instance, EnumFacing side) {
            if (instance == null || !instance.isInitialized()) {
                return new NBTBuilder().setBoolean("valid", false).build();
            }
            NBTTagCompound root = new NBTTagCompound();
            root.setBoolean("valid", true);

            root.setTag("rockLayer1", new NBTTagIntArray(instance.rockLayer1));
            root.setTag("rockLayer2", new NBTTagIntArray(instance.rockLayer2));
            root.setTag("rockLayer3", new NBTTagIntArray(instance.rockLayer3));
            root.setTag("seaLevelOffset", new NBTTagIntArray(instance.seaLevelOffset));

            root.setTag("stabilityLayer", write(instance.stabilityLayer));
            root.setTag("drainageLayer", write(instance.drainageLayer));

            root.setInteger("fishPopulation", instance.fishPopulation);

            root.setFloat("rainfall", instance.rainfall);
            root.setFloat("regionalTemp", instance.regionalTemp);
            root.setFloat("avgTemp", instance.averagTemp);
            root.setFloat("floraDensity", instance.floraDensity);
            root.setFloat("floraDiversity", instance.floraDiversity);

            root.setInteger("chunkWorkage", instance.chunkWorkage);
            root.setLong("protectedTicks", instance.protectedTicks);
            root.setLong("lastUpdateTick", instance.lastUpdateTick);
            root.setLong("lastUpdateYear", instance.lastUpdateYear);

            NBTTagList veinList = new NBTTagList();
            for (Vein vein : instance.generatedVeins) {
                veinList.appendTag(Vein.serialize(vein));
            }
            root.setTag("veins", veinList);

            return root;
        }

        @Override
        public void readNBT(Capability<ChunkData> capability, ChunkData instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound root = (NBTTagCompound) nbt;
            if (nbt != null && root.getBoolean("valid")) {
                System.arraycopy(root.getIntArray("rockLayer1"), 0, instance.rockLayer1, 0, 256);
                System.arraycopy(root.getIntArray("rockLayer2"), 0, instance.rockLayer2, 0, 256);
                System.arraycopy(root.getIntArray("rockLayer3"), 0, instance.rockLayer3, 0, 256);
                System.arraycopy(root.getIntArray("seaLevelOffset"), 0, instance.seaLevelOffset, 0, 256);

                read(instance.stabilityLayer, root.getByteArray("stabilityLayer"));
                read(instance.drainageLayer, root.getByteArray("drainageLayer"));

                instance.fishPopulation = root.getInteger("fishPopulation");

                instance.rainfall = root.getFloat("rainfall");
                instance.regionalTemp = root.getFloat("regionalTemp");
                instance.averagTemp = root.getFloat("avgTemp");
                instance.floraDensity = root.getFloat("floraDensity");
                instance.floraDiversity = root.getFloat("floraDiversity");

                instance.chunkWorkage = root.getInteger("chunkWorkage");
                instance.protectedTicks = root.getLong("protectedTicks");
                instance.lastUpdateTick = root.getLong("lastUpdateTick");
                instance.lastUpdateYear = root.getLong("lastUpdateYear");

                instance.generatedVeins = new HashSet<>();

                NBTTagList veinList = root.getTagList("veins", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < veinList.tagCount(); i++) {
                    instance.generatedVeins.add(Vein.deserialize(veinList.getCompoundTagAt(i)));
                }

                instance.initialized = true;
            }
        }
    }
}
