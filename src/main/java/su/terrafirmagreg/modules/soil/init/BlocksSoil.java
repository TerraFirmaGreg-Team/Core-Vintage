package su.terrafirmagreg.modules.soil.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.soil.StorageSoil;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeat;
import su.terrafirmagreg.modules.soil.objects.blocks.peat.BlockPeatGrass;

public final class BlocksSoil {

    public static BlockPeatGrass PEAT_GRASS;
    public static BlockPeat PEAT;

    public static void onRegister(Registry registry) {

        for (var block : StorageSoil.SOIL_BLOCKS.values()) registry.registerAutoBlock((Block) block);

		registry.registerAutoBlock(PEAT_GRASS = new BlockPeatGrass());
		registry.registerAutoBlock(PEAT = new BlockPeat());


    }
}
