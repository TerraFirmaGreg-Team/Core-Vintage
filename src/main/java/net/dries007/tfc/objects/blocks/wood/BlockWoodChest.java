package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.variant.WoodVariant_old;
import net.dries007.tfc.api.types.wood.type.Wood;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.inventory.capability.TFCInventoryLargeChest;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TEChestTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class BlockWoodChest extends BlockChest implements IItemSize, IWoodBlock {
    private final WoodVariant_old woodVariant;
    private final Wood wood;
    private final ResourceLocation modelLocation;

    public BlockWoodChest(WoodVariant_old woodVariant, Wood wood) {
        super(Type.BASIC);
        this.woodVariant = woodVariant;
        this.wood = wood;
        this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

        var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.WOOD);
        setHardness(2.5F);
        setSoundType(SoundType.WOOD);
//        if (type == TFCBASIC) {
//            if (MAP_BASIC.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
//            OreDictionaryHelper.register(this, "chest");
//            OreDictionaryHelper.register(this, "chest", "wood");
//            //noinspection ConstantConditions
//            OreDictionaryHelper.register(this, "chest", wood.getRegistryName().getPath());
//        } else if (type == TFCTRAP) {
//            if (MAP_TRAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
//            OreDictionaryHelper.register(this, "chest", "trapped");
//            OreDictionaryHelper.register(this, "chest", "wood");
//            //noinspection ConstantConditions
//            OreDictionaryHelper.register(this, "chest", "trapped", wood.getRegistryName().getPath());
//        } else {
//            throw new IllegalStateException("TFC Chest must use TFC chest type");
//        }
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public WoodVariant_old getWoodVariant() {
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TFCGuiHandler.openGui(worldIn, pos, playerIn, TFCGuiHandler.Type.CHEST);
        }
        return true;
    }

    /**
     * This and the following methods are copied from vanilla to allow us to hook into vanilla's chest stuff
     * Hoppers are hardcoded for vanilla chest insertions, which means we need to block them (to stop inserting items that aren't the correct size)
     */
    @Nullable
    public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityChest)) {
            return null;
        } else {
            ILockableContainer ilockablecontainer = (TileEntityChest) tileentity;

            if (!allowBlocking && isBlocked(worldIn, pos)) {
                return null;
            } else {
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                    BlockPos blockpos = pos.offset(enumfacing);
                    Block block = worldIn.getBlockState(blockpos).getBlock();

                    if (block == this) {
                        if (!allowBlocking && isBlocked(worldIn, blockpos)) // Forge: fix MC-99321
                        {
                            return null;
                        }

                        TileEntity tileentity1 = worldIn.getTileEntity(blockpos);

                        if (tileentity1 instanceof TileEntityChest) {
                            if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                                ilockablecontainer = new TFCInventoryLargeChest("container.chestDouble", ilockablecontainer, (TileEntityChest) tileentity1);
                            } else {
                                ilockablecontainer = new TFCInventoryLargeChest("container.chestDouble", (TileEntityChest) tileentity1, ilockablecontainer);
                            }
                        }
                    }
                }

                return ilockablecontainer;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEChestTFC();
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.LARGE; // Can only be stored in itself (and since this can't be carried with items, makes sense
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

    private boolean isBlocked(World worldIn, BlockPos pos) {
        return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
    }

    private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), EnumFacing.DOWN);
    }

    private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos) {
        for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1))) {
            EntityOcelot entityocelot = (EntityOcelot) entity;

            if (entityocelot.isSitting()) {
                return true;
            }
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(modelLocation).ignore(BlockChest.FACING).build());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(modelLocation, "normal"));

//				for (IBlockState state : this.getBlockState().getValidStates()) {
//						ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
//										this.getMetaFromState(state),
//										new ModelResourceLocation(modelLocation, "normal"));
//				}
    }
}
