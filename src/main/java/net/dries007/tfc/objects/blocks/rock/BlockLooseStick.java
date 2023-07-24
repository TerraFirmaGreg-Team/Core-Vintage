//package net.dries007.tfc.objects.blocks.rock;
//
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.block.model.ModelResourceLocation;
//import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Items;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.NonNullList;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.client.model.ModelLoader;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import su.terrafirmagreg.TFG;
//import su.terrafirmagreg.modules.stonelayers.api.types.FossilType;
//import su.terrafirmagreg.modules.stonelayers.objects.creativetabs.StoneLayersCreativeTabs;
//
//import javax.annotation.Nonnull;
//import javax.annotation.ParametersAreNonnullByDefault;
//
//import static su.terrafirmagreg.TFG.MOD_ID;
//import static su.terrafirmagreg.modules.stonelayers.api.types.BlockType.LOOSE;
//import static su.terrafirmagreg.modules.stonelayers.objects.blocks.StoneTypeBlocks.FOSSIL_BLOCK_MAP;
//
//@ParametersAreNonnullByDefault
//public class BlockLooseStick extends BlockLooseTFG {
//
//    private final FossilType fossilType;
//    private final ResourceLocation modelLocation;
//
//    public BlockLooseStick(FossilType fossilType) {
//        super(Material.WOOD);
//
//        if (FOSSIL_BLOCK_MAP.put(fossilType, this) != null)
//            throw new RuntimeException("Duplicate in loose registry!");
//
//        this.fossilType = fossilType;
//        this.modelLocation = new ResourceLocation(TFG.MOD_ID, LOOSE + "/" + fossilType);
//
//        String blockRegistryName = String.format("%s/%s", LOOSE, fossilType);
//        setCreativeTab(StoneLayersCreativeTabs.TFG_STONELAYERS_ITEMS);
//        setRegistryName(MOD_ID, blockRegistryName);
//        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
//        setSoundType(SoundType.WOOD);
//    }
//
//    @Override
//    @SuppressWarnings("ConstantConditions")
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
//                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        ItemStack itemStack = new ItemStack(Items.STICK);
//
//        if (playerIn.addItemStackToInventory(itemStack)) {
//            worldIn.setBlockToAir(pos);
//
//            playerIn.swingArm(EnumHand.MAIN_HAND);
//            playerIn.playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f);
//        }
//
//        return true;
//    }
//
//
//    @Nonnull
//    @Override
//    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
//        return new ItemStack(Items.STICK);
//    }
//
//    @Override
//    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
//        drops.add(new ItemStack(Items.STICK));
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void onModelRegister() {
//        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
//            @Nonnull
//            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
//                return new ModelResourceLocation(modelLocation, fossilType.getName());
//
//            }
//        });
//    }
//
//}
