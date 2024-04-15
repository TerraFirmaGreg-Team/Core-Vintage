package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodChest;
import su.terrafirmagreg.modules.wood.objects.inventory.capability.InventoryWoodLargeChest;
import su.terrafirmagreg.modules.wood.objects.tiles.TEWoodChest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static net.minecraft.block.BlockChest.Type.BASIC;
import static net.minecraft.block.BlockChest.Type.TRAP;
import static su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariants.CHEST;

@Getter
public class BlockWoodChest extends BlockChest implements IWoodBlock, ITEBlock {

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    public BlockWoodChest(WoodBlockVariant blockVariant, WoodType type) {
        super(blockVariant == CHEST ? BASIC : TRAP);

        this.blockVariant = blockVariant;
        this.type = type;

        setHardness(2.5F);
        setSoundType(SoundType.WOOD);

        BlockUtils.setFireInfo(this, blockVariant.getEncouragement(), blockVariant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "chest", "wood");
        OreDictUtils.register(this, blockVariant);
        OreDictUtils.register(this, blockVariant, type);
    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn,
                                    @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            GuiHandler.openGui(worldIn, pos, playerIn, GuiHandler.Type.WOOD_CHEST);
        }
        return true;
    }

    /**
     * This and the following methods are copied from vanilla to allow us to hook into vanilla's chest stuff Hoppers are hardcoded for vanilla chest
     * insertions, which means we need to block them (to stop inserting items that aren't the correct size)
     */
    @Nullable
    @Override
    public ILockableContainer getContainer(World worldIn, @NotNull BlockPos pos, boolean allowBlocking) {
        var tile = worldIn.getTileEntity(pos);

        if (!(tile instanceof TileEntityChest tileEntityChest)) {
            return null;
        } else {
            ILockableContainer ilockablecontainer = tileEntityChest;

            if (!allowBlocking && isBlocked(worldIn, pos)) {
                return null;
            } else {
                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                    BlockPos blockpos = pos.offset(enumfacing);
                    Block block = worldIn.getBlockState(blockpos).getBlock();

                    if (block == this) {
                        // Forge: fix MC-99321
                        if (!allowBlocking && isBlocked(worldIn, blockpos)) {
                            return null;
                        }

                        TileEntity tileentity1 = worldIn.getTileEntity(blockpos);

                        if (tileentity1 instanceof TileEntityChest tileEntityChest1) {
                            if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
                                ilockablecontainer = new InventoryWoodLargeChest("container.chestDouble", ilockablecontainer, tileEntityChest1);
                            } else {
                                ilockablecontainer = new InventoryWoodLargeChest("container.chestDouble", tileEntityChest1, ilockablecontainer);
                            }
                        }
                    }
                }

                return ilockablecontainer;
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(@NotNull World worldIn, int meta) {
        return new TEWoodChest();
    }

    @NotNull
    @Override
    public Size getSize(@NotNull ItemStack stack) {
        return Size.LARGE;
    }

    @NotNull
    @Override
    public Weight getWeight(@NotNull ItemStack stack) {
        return Weight.LIGHT;
    }

    private boolean isBlocked(World worldIn, BlockPos pos) {
        return this.isBelowSolidBlock(worldIn, pos) || this.isOcelotSittingOnChest(worldIn, pos);
    }

    private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).doesSideBlockChestOpening(worldIn, pos.up(), EnumFacing.DOWN);
    }

    private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos) {
        for (EntityOcelot entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class,
                new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1))) {
            if (entity.isSitting()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onStateMapperRegister() {
        ModelUtils.registerStateMapper(this, new CustomStateMap.Builder().ignore(BlockChest.FACING).customResource(getResourceLocation()).build());
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TEWoodChest.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRWoodChest();
    }
}
