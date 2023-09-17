package net.dries007.tfc.common.objects.blocks.soil;

import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.block.ISoilBlock;
import net.dries007.tfc.api.types.soil.variant.block.SoilBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlock;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class BlockSoil extends TFCBlock implements ISoilBlock {

    private final SoilBlockVariant variant;
    private final SoilType type;


    public BlockSoil(SoilBlockVariant variant, SoilType type) {
        super(Material.GROUND);

        if (variant.canFall())
            FallingBlockManager.registerFallable(this, variant.getFallingSpecification());

        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.SOIL);
        setSoundType(SoundType.GROUND);
        setHardness(2.0F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Nonnull
    @Override
    public SoilBlockVariant getBlockVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public SoilType getType() {
        return type;
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return 0;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (variant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    public int damageDropped(@Nonnull IBlockState state) {
        return getMetaFromState(state);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(),
                        "soiltype=" + type.toString());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(),
                        "soiltype=" + type.toString()));
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
