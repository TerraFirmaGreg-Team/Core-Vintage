package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.Random;

public abstract class BlockRockFallable extends BlockRock {

    public BlockRockFallable(RockBlockVariant blockVariant, RockType type) {
        this(Material.ROCK, blockVariant, type);

    }

    public BlockRockFallable(Material material, RockBlockVariant blockVariant, RockType type) {
        super(material, blockVariant, type);

        setHardness(getFinalHardness() * 0.03f);

        //OreDictionaryHelper.register(this, blockVariant.toString(), type.toString());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull Random rand) {
        if (rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, getStateId(state));
        }
    }
}
