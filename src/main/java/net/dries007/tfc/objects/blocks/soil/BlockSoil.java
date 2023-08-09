package net.dries007.tfc.objects.blocks.soil;

import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class BlockSoil extends Block implements ISoilBlock {

    // Used for connected textures only.
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    private final SoilBlockVariant soilBlockVariant;
    private final SoilType soilType;


    public BlockSoil(SoilBlockVariant soilBlockVariant, SoilType soilType) {
        super(Material.GROUND);

        if (soilBlockVariant.canFall())
            FallingBlockManager.registerFallable(this, soilBlockVariant.getFallingSpecification());

        this.soilBlockVariant = soilBlockVariant;
        this.soilType = soilType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.EARTH);
        setSoundType(SoundType.GROUND);
        setHardness(2.0F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, soilBlockVariant.name(), soilType.name());
    }

    @Nonnull
    @Override
    public SoilBlockVariant getSoilBlockVariant() {
        return soilBlockVariant;
    }

    @Nonnull
    @Override
    public SoilType getSoilType() {
        return soilType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        return 0;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, @Nonnull BlockPos pos) {
        pos = pos.add(0, -1, 0);
        return state.withProperty(NORTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
                .withProperty(EAST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
                .withProperty(SOUTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
                .withProperty(WEST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(@Nonnull IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (soilBlockVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
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
                        "soiltype=" + soilType.toString());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(),
                        "soiltype=" + soilType.toString()));
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
