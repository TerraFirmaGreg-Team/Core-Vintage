package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.block.type.RockType;
import net.dries007.tfc.api.types.rock.block.variant.RockVariant;
import net.dries007.tfc.api.types.rock.type.Rock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Пока это почти полная копия {@link BlockRock}
 * Этот клас в будущем планируется использовать для механики распространения мха
 */
public class BlockRockMossy extends BlockRock {

    public BlockRockMossy(RockType rockType, RockVariant rockVariant, Rock rock) {
        super(rockType, rockVariant, rock);
    }

    //@Override
    //public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
    //    if (!world.isRemote) {
    //        ItemStack heldItemStack = player.getHeldItem(EnumHand.MAIN_HAND);
    //        Item heldItem = heldItemStack.getItem();
    //
    //        // Проверяем, можно ли игроку собрать блок с использованием текущего инструмента
    //        if (player.canHarvestBlock(state)) {
    //            // Проверяем, является ли удерживаемый предмет инструментом с классом инструмента pickaxe и кроме инструмента HARD_HAMMER
    //            if ((heldItem.getToolClasses(heldItemStack).contains("pickaxe")) && !(heldItem == HARD_HAMMER.get())) {
    //                Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек мха
    //                switch (rockVariant) {
    //                    case COBBLE:
    //                        //Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(3) + 4));
    //                        break;
    //                    case BRICK:
    //                        //Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 4));
    //                        break;
    //                }
    //            } else if (heldItem == HARD_HAMMER.get()) {
    //                Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек мха
    //                switch (rockVariant) {
    //                    case COBBLE:
    //                        //Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(3) + 4));
    //                        Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек глины?
    //                        break;
    //                    case BRICK:
    //                        //Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get("brick/" + rockType.getName()), new Random().nextInt(3) + 3));
    //                        break;
    //                }
    //            }
    //        }
    //    }
    //    return super.removedByPlayer(state, world, pos, player, willHarvest);
    //}

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
