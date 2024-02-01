package su.terrafirmagreg.core.mixin.puddles;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.util.climate.ClimateTFC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import puddles.Puddles;

@Mixin(value = Puddles.class, remap = false)
public class PuddlesMixin {

    /**
     * @author SpeeeDCraft
     * @reason Fix the spawn of puddles in winter
     */
    @Overwrite
    public boolean canSpawnPuddle(World world, BlockPos pos) {
        if (!world.isSideSolid(pos, EnumFacing.UP)) {
            return false;
        }
        if (!world.isAirBlock(pos.up())) {
            return false;
        }
        if (!world.isRaining()) {
            return false;
        }

        Biome biome = world.getBiomeForCoordsBody(pos);
        if (biome.canRain() && !biome.getEnableSnow() && (ClimateTFC.getActualTemp(pos) > 0)) {
            for (int y = pos.getY() + 1; y < world.getHeight(); y++) {
                BlockPos up = new BlockPos(pos.getX(), y, pos.getZ());
                if (!world.isAirBlock(up)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
