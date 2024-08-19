package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.api.lib.model.CustomStateMap;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodChest;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.objects.inventory.capability.InventoryWoodLargeChest;
import su.terrafirmagreg.modules.wood.objects.tiles.TileWoodChest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
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


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static net.minecraft.block.BlockChest.Type.BASIC;
import static net.minecraft.block.BlockChest.Type.TRAP;

@Getter
public class BlockWoodChest extends BlockChest implements IWoodBlock, IProviderTile {

    protected final Settings settings;
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodChest(WoodBlockVariant variant, WoodType type) {
        super(variant == BlocksWood.CHEST ? BASIC : TRAP);

        this.variant = variant;
        this.type = type;

        this.settings = Settings.of(Material.WOOD)
                .soundType(SoundType.WOOD)
                .hardness(2.5f)
                .size(Size.LARGE)
                .weight(Weight.MEDIUM)
                .addOreDict("chest")
                .addOreDict("chest", "wood")
                .addOreDict("chest", "wood", type);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            GuiHandler.openGui(worldIn, pos, playerIn);
        }
        return true;
    }

    /**
     * This and the following methods are copied from vanilla to allow us to hook into vanilla's chest stuff Hoppers are hardcoded for vanilla chest insertions, which means we need
     * to block them (to stop inserting items that aren't the correct size)
     */
    @Nullable
    @Override
    public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking) {

        ILockableContainer ilockablecontainer = TileUtils.getTile(worldIn, pos, TileEntityChest.class);

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
    public IStateMapper getStateMapper() {
        return new CustomStateMap.Builder()
                .ignore(BlockChest.FACING)
                .customResource(getResourceLocation())
                .build();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileWoodChest();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileWoodChest.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRWoodChest();
    }
}
