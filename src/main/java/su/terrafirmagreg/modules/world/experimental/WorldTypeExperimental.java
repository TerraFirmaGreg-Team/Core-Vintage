package su.terrafirmagreg.modules.world.experimental;

import su.terrafirmagreg.modules.world.classic.BiomeProviderClassic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.client.gui.GuiCustomizeWorld;

public class WorldTypeExperimental extends WorldType {

    public static final int SEALEVEL = 144;
    public static final int ROCKLAYER2 = 110;
    public static final int ROCKLAYER3 = 55;

    public WorldTypeExperimental() {
        super("tfg:experimental");
    }

    @Override
    public BiomeProvider getBiomeProvider(World world) {
        return new BiomeProviderClassic(world);
    }

    //    @Override
    //    public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {
    //        return new ChunkGenExperimental(world, generatorOptions);
    //    }

    @Override
    public int getMinimumSpawnHeight(World world) {
        return SEALEVEL; // todo
    }

    @Override
    public double getHorizon(World world) {
        return SEALEVEL; // todo
    }

    //    @Override
    //    public int getSpawnFuzz(WorldServer world, MinecraftServer server) {
    //        if (world.getGameRules().hasRule("spawnRadius")) {
    //            return world.getGameRules().getInt("spawnRadius");
    //        }
    //
    //        return ((ChunkGenExperimental) world.getChunkProvider().chunkGenerator).settings.spawnFuzz;
    //    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onCustomizeButton(Minecraft mc, GuiCreateWorld guiCreateWorld) {
        mc.displayGuiScreen(new GuiCustomizeWorld(guiCreateWorld, guiCreateWorld.chunkProviderSettingsJson));
    }

    @Override
    public boolean isCustomizable() {
        return true;
    }

    @Override
    public float getCloudHeight() {
        return 2 * SEALEVEL;
    }
}
