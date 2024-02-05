package su.terrafirmagreg.modules.soil.objects.blocks;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.util.OreDictionaryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.objects.block.BlockBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import java.util.Random;

public abstract class BlockSoil extends BlockBase implements ISoilBlockVariant {

    private final SoilBlockVariant variant;
    private final SoilType type;


    public BlockSoil(SoilBlockVariant variant, SoilType type) {
        super(Material.GROUND);

        if (variant.canFall()) FallingBlockManager.registerFallable(this, variant.getSpecification());

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.GROUND);
        setHardness(2.0F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @NotNull
    @Override
    public SoilBlockVariant getBlockVariant() {
        return variant;
    }

    @NotNull
    @Override
    public SoilType getType() {
        return type;
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        return 0;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull Random rand) {
        if (variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    public int damageDropped(@NotNull IBlockState state) {
        return getMetaFromState(state);
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
