package net.dries007.tfc.module.rock.common.blocks;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class BlockRockFallable extends BlockRock {

    public BlockRockFallable(RockBlockVariant variant, RockType type) {
        this(Material.ROCK, variant, type);
    }

    public BlockRockFallable(Material material, RockBlockVariant variant, RockType type) {
        super(material, variant, type);

        setHardness(getFinalHardness() * 0.03f);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, getStateId(state));
        }
    }
}
