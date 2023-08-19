package net.dries007.tfc.common.objects.blocks.crop;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.types.crop.ICropBlock;
import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.crop.variant.CropBlockVariant;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.skills.SimpleSkill;
import net.dries007.tfc.util.skills.SkillType;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
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

@ParametersAreNonnullByDefault
public class BlockCropDead extends BlockBush implements IGrowingPlant, ICropBlock {
    /* true if the crop spawned in the wild, means it ignores growth conditions i.e. farmland */
    public static final PropertyBool MATURE = PropertyBool.create("mature");

    // binary flags for state and metadata conversion
    private static final int META_MATURE = 1;

    private final CropType type;
    private final CropBlockVariant variant;

    public BlockCropDead(CropBlockVariant variant, CropType type) {
        super(Material.PLANTS);

        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.FLORA);
        setSoundType(SoundType.PLANT);
        setHardness(0.6f);
    }

    @Nonnull
    @Override
    public CropType getType() {
        return type;
    }

    @Nonnull
    @Override
    public CropBlockVariant getBlockVariant() {
        return variant;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(MATURE, (meta & META_MATURE) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(MATURE) ? META_MATURE : 0;
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return type.getDropSeed().getItem();
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MATURE);
    }

    @Override
    @Nonnull
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        // dead crops always drop at least 1 seed
        int count = 1;
        if (state.getValue(MATURE)) {
            // (mature and dead) crops always drop 1 extra seed
            count++;
            // mature crops have a chance to drop a bonus, dead or alive
            EntityPlayer player = harvesters.get();
            if (player != null) {
                SimpleSkill skill = CapabilityPlayerData.getSkill(player, SkillType.AGRICULTURE);
                if (skill != null) {
                    count += CropType.getSkillSeedBonus(skill, RANDOM);
                    skill.add(0.04f);
                }
            }
        }

        return count;
    }

//    @Override
//    @Nonnull
//    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
//        return new ItemStack(ItemCropSeeds.get(type));
//    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState soil = world.getBlockState(pos.down());
        return soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    }

    @Nonnull
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        return GrowthStatus.DEAD;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), this.getPropertyString(state.getProperties()));
            }
        });

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    this.getMetaFromState(state), new ModelResourceLocation(getResourceLocation(), "normal"));
        }
    }
}
