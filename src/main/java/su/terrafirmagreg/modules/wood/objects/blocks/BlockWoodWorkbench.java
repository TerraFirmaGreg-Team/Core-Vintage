package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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

import mcp.MethodsReturnNonnullByDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.container.ContainerWoodWorkbench;


public class BlockWoodWorkbench extends BlockWorkbench implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodWorkbench(WoodBlockVariant variant, WoodType type) {

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        //OreDictionaryHelper.register(this, variant.toString());
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Override
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && playerIn != null) {
            playerIn.displayGui(new InterfaceCraftingTable(this, worldIn, pos));
            playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder().customPath(getResourceLocation()).build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "inventory"));
    }

    @SuppressWarnings("WeakerAccess")

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
        public Container createContainer(@NotNull InventoryPlayer inv, @NotNull EntityPlayer player) {
            return new ContainerWoodWorkbench(inv, world, position, workbenchTFC);
        }

        @Override
        public String getGuiID() {
            return "minecraft:crafting_table";
        }
    }

}
