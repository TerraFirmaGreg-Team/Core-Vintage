package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.containers.ContainerWoodWorkbench;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;


import gregtech.api.items.toolitem.ToolClasses;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockWoodWorkbench extends BlockWorkbench implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;
    protected Settings settings;

    public BlockWoodWorkbench(WoodBlockVariant variant, WoodType type) {
        this.variant = variant;
        this.type = type;

        this.settings = Settings.of(Material.WOOD)
                .sound(SoundType.WOOD)
                .renderLayer(BlockRenderLayer.CUTOUT)
                .hardness(2.0F)
                .resistance(5.0F)
                .oreDict(variant)
                .oreDict(variant, type);

        setHarvestLevel(ToolClasses.AXE, 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY,
                                    float hitZ) {
        if (!worldIn.isRemote && playerIn != null) {
            playerIn.displayGui(new InterfaceCraftingTable(this, worldIn, pos));
            playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        }
        return true;
    }

    @SuppressWarnings("WeakerAccess")
    public static class InterfaceCraftingTable implements IInteractionObject {

        //todo: replace with proper workbench mechanics + normal forge gui code
        private final BlockWoodWorkbench workbench;
        private final World world;
        private final BlockPos position;

        public InterfaceCraftingTable(BlockWoodWorkbench workbench, World worldIn, BlockPos pos) {
            this.workbench = workbench;
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
            return new TextComponentTranslation(workbench.getTranslationKey() + ".name");
        }

        @Override
        public Container createContainer(InventoryPlayer inv, EntityPlayer player) {
            return new ContainerWoodWorkbench(inv, world, position, workbench);
        }

        @Override
        public String getGuiID() {
            return "minecraft:crafting_table";
        }
    }

}
