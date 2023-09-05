package net.dries007.tfc.common.objects.blocks.tree;

import com.ferreusveritas.dynamictrees.blocks.BlockBranchBasic;
import net.dries007.tfc.api.types.tree.ITreeBlock;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.types.tree.variant.block.TreeBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.config.ConfigTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Set;

public class BlockTreeBranch extends BlockBranchBasic implements ITreeBlock {

    private final TreeBlockVariant variant;
    private final TreeType type;

    public BlockTreeBranch(TreeBlockVariant variant, TreeType type) {
        super(String.format("tree/%s/%s", variant, type));

        this.variant = variant;
        this.type = type;

        setTranslationKey(getTranslationName());
    }

    @Override
    public TreeBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public TreeType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return null;//new ItemBlockTFC(this);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos cutPos, EntityPlayer player, boolean canHarvest) {
        ItemStack tool = player.getHeldItemMainhand();
        // after BlockLogTFC#harvestBlock
        final Set<String> toolClasses = tool.getItem().getToolClasses(tool);
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
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getRegistryLocation())
                        .ignore(BlockBranchBasic.RADIUS)
                        .build());

//        ModelLoader.setCustomModelResourceLocation(
//                Item.getItemFromBlock(this), 0,
//                new ModelResourceLocation(getRegistryLocation(),
//                        "normal"));
    }
}
