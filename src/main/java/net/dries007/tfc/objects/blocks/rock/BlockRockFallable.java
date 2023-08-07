package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockRockFallable extends BlockRock {

    public BlockRockFallable(RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, RockType rockType) {
        this(Material.ROCK, rockBlockType, rockBlockVariant, rockType);
    }

    public BlockRockFallable(Material material, RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, RockType rockType) {
        super(material, rockBlockType, rockBlockVariant, rockType);

        // TODO: 07.08.2023
        /*
        if (rockVariant.canFall()) {
            FallingBlockManager.registerFallable(this, rockVariant.getFallingSpecification());
        }*/
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        var rockBlockVariant = getRockBlockVariant();

        if (rockBlockVariant != null && rockBlockVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }
}
