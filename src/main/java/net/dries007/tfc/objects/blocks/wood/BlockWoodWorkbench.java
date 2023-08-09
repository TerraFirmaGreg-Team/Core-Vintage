package net.dries007.tfc.objects.blocks.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.container.ContainerWorkbenchTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockWoodWorkbench extends BlockWorkbench implements IWoodBlock {
    private final WoodBlockVariant woodBlockVariant;
    private final WoodType woodType;

    public BlockWoodWorkbench(WoodBlockVariant woodBlockVariant, WoodType woodType) {

        this.woodBlockVariant = woodBlockVariant;
        this.woodType = woodType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.WOOD);
        setHardness(2.0F).setResistance(5.0F);
        setHarvestLevel("axe", 0);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Override
    public WoodBlockVariant getWoodBlockVariant() {
        return woodBlockVariant;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nullable EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && playerIn != null) {
            playerIn.displayGui(new InterfaceCraftingTable(this, worldIn, pos));
            playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), getPropertyString(state.getProperties()));
            }
        });

        for (IBlockState state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(), "normal"));
        }
    }

    @SuppressWarnings("WeakerAccess")
    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public static class InterfaceCraftingTable implements IInteractionObject {
        //todo: replace with proper workbench mechanics + normal forge gui code
        private final BlockWoodWorkbench workbenchTFC;
        private final World world;
        private final BlockPos position;

        public InterfaceCraftingTable(BlockWoodWorkbench workbenchTFC, World worldIn, BlockPos pos) {
            this.workbenchTFC = workbenchTFC;
            this.world = worldIn;
            this.position = pos;
        }

        /**
         * Get the name of this object. For players this returns their username
         */
        @Override
        public String getName() {
            return "crafting_table";
        }

        /**
         * Returns true if this thing is named
         */
        @Override
        public boolean hasCustomName() {
            return false;
        }

        /**
         * Get the formatted ChatComponent that will be used for the sender's username in chat
         */
        @Override
        public ITextComponent getDisplayName() {
            return new TextComponentTranslation(workbenchTFC.getTranslationKey() + ".name");
        }

        @Override
        public Container createContainer(InventoryPlayer inv, EntityPlayer player) {
            return new ContainerWorkbenchTFC(inv, world, position, workbenchTFC);
        }

        @Override
        public String getGuiID() {
            return "minecraft:crafting_table";
        }
    }

}
