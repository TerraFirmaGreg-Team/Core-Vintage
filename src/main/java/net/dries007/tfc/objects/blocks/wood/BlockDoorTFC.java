package net.dries007.tfc.objects.blocks.wood;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.items.wood.ItemDoorTFC;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockDoorTFC extends BlockDoor {
    private static final Map<Tree, BlockDoorTFC> MAP = new HashMap<>();
    public final Tree wood;

    public BlockDoorTFC(Tree wood) {
        super(Material.WOOD);
        if (MAP.put(wood, this) != null) throw new IllegalStateException("There can only be one.");
        this.wood = wood;
        setSoundType(SoundType.WOOD);
        setHardness(3.0F);
        disableStats();
        // No direct item, so no oredict.
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    public static BlockDoorTFC get(Tree wood) {
        return MAP.get(wood);
    }

    // todo: Is private, but it might be worth it making protected/public
    // @Override
    public Item getItem() {
        return ItemDoorTFC.get(wood);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(getItem());
    }
}
