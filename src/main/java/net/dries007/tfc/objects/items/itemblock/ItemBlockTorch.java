package net.dries007.tfc.objects.items.itemblock;

import net.dries007.tfc.objects.te.TELogPile;
import net.dries007.tfc.objects.te.TEPitKiln;
import net.dries007.tfc.test.blocks.TFCBlocks;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.dries007.tfc.api.util.property.ILightableBlock.LIT;

public class ItemBlockTorch extends ItemBlockTFC {
    public ItemBlockTorch(Block block) {
        super(block);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        BlockPos pos = entityItem.getPosition().down();
        World world = entityItem.getEntityWorld();
        IBlockState state = entityItem.getEntityWorld().getBlockState(pos);

        if (state.getBlock() == TFCBlocks.LOG_PILE || state.getBlock() == TFCBlocks.PIT_KILN) {
            int count = entityItem.getEntityData().getInteger("torchCount");
            if (count > 160) {
                if (state.getBlock() == TFCBlocks.LOG_PILE) {
                    world.setBlockState(pos, state.withProperty(LIT, true));
                    TELogPile te = Helpers.getTE(world, pos, TELogPile.class);
                    if (te != null) {
                        te.light();
                    }
                    if (Blocks.FIRE.canPlaceBlockAt(world, pos.up())) {
                        world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
                    }
                } else if (state.getBlock() == TFCBlocks.PIT_KILN) {
                    TEPitKiln te = Helpers.getTE(world, pos, TEPitKiln.class);
                    if (te != null) {
                        te.tryLight();
                    }
                }
                entityItem.setDead();
            } else {
                if (Math.random() <= 0.1) {
                    world.spawnParticle(EnumParticleTypes.LAVA, entityItem.posX, entityItem.posY, entityItem.posZ,
                            -0.5F + Math.random(), -0.5F + Math.random(), -0.5F + Math.random());
                }
                entityItem.getEntityData().setInteger("torchCount", count + 1);
            }
        }
        return super.onEntityItemUpdate(entityItem);
    }
}
