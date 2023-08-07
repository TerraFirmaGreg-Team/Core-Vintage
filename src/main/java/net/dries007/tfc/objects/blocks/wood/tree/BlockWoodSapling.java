package net.dries007.tfc.objects.blocks.wood.tree;

import net.dries007.tfc.api.types.agriculture.util.IGrowingPlant;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.block.variant.WoodVariant;
import net.dries007.tfc.api.types.wood.type.Wood;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class BlockWoodSapling extends BlockBush implements IGrowable, IGrowingPlant, IWoodBlock {
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 4);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);
    private final WoodVariant woodVariant;
    private final Wood wood;
    private final ResourceLocation modelLocation;

    public BlockWoodSapling(WoodVariant woodVariant, Wood wood) {

        this.woodVariant = woodVariant;
        this.wood = wood;
        this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant + "/" + wood);

        var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.WOOD);

        setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
        setSoundType(SoundType.PLANT);
        setHardness(0.0F);
        OreDictionaryHelper.register(this, "tree", "sapling");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "tree", "sapling", wood.toString());
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public WoodVariant getWoodVariant() {
        return woodVariant;
    }

    @Override
    public Wood getWood() {
        return wood;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }


    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(STAGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STAGE);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (te != null) {
            te.resetCounter();
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STAGE);
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TETickCounter();
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);

        if (!world.isRemote) {
            TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
            if (te != null) {
                long days = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
                if (days > 7) { //woodType.getMinGrowthTime()
                    grow(world, random, pos, state);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAPLING_AABB;
    }

    @Override
    @Nonnull
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
        return false;
    }

    @Override
    public void grow(World world, Random random, BlockPos blockPos, IBlockState blockState) {
//        woodType.makeTree(world, blockPos, random, false);
    }
//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//        woodType.addInfo(stack, worldIn, tooltip, flagIn);
//    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        return GrowthStatus.GROWING;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(modelLocation).ignore(BlockWoodSapling.STAGE).build());

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(modelLocation, "inventory"));
        }
    }
}
