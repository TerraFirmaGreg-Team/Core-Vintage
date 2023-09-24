package net.dries007.tfc.compat.dynamictrees.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockBranchBasic;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.core.config.ConfigTFC;
import net.dries007.tfc.module.wood.ModuleWood;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTreeBranch extends BlockBranchBasic implements IItemProvider, IHasModel {
    public BlockTreeBranch(WoodType type) {
        super(String.format("wood/branch/%s", type));

        setTranslationKey(String.format("wood.branch.%s", type));
        setCreativeTab(ModuleWood.WOOD_TAB);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return null;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos cutPos, EntityPlayer player, boolean canHarvest) {
        var tool = player.getHeldItemMainhand();
        // after BlockLogTFC#harvestBlock
        final var toolClasses = tool.getItem().getToolClasses(tool);
        if (toolClasses.contains("axe") || toolClasses.contains("saw") || player.isCreative()) {
            // success!
        } else if (toolClasses.contains("hammer") && ConfigTFC.General.TREE.enableHammerSticks) {
            // can't force only stick drop from here
            // so hammers only drop a few sticks from dynamic trees
            return false; //No wood for you!
        }
//        else if (ConfigTFC.General.TREE.requiresAxe) {
//            // Here, there was no valid tool used. Deny spawning any drops since logs require axes
//            return false; //Also no wood for you!
//        }
        return super.removedByPlayer(state, world, cutPos, player, canHarvest); // any other conditions, we can handle normally
    }

    @Override
    public void onModelRegister() {

    }
}
