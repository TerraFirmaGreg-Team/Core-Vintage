package su.terrafirmagreg.modules.core.event;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.init.BlocksCore;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import java.util.Iterator;
import java.util.Random;

@SuppressWarnings("unused")
public class EventHandlerPuddles {

  @SubscribeEvent
  public void placePuddles(TickEvent.ServerTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      WorldServer world = DimensionManager.getWorld(0);
      try {
        if (world.getTotalWorldTime() % 10 == 0) {
          Iterator<Chunk> iterator = world.getPlayerChunkMap().getChunkIterator();

          while (iterator.hasNext()) {
            Random random = world.rand;
            ChunkPos chunkPos = iterator.next().getPos();

            int x = random.nextInt(8) - random.nextInt(8);
            int z = random.nextInt(8) - random.nextInt(8);
            BlockPos pos = chunkPos.getBlock(8 + x, 0, 8 + z);

            int y = world.getHeight(pos).getY() + random.nextInt(4) - random.nextInt(4);
            BlockPos puddlePos = pos.add(0, y, 0);

            if (this.canSpawnPuddle(world, puddlePos)) {
              if (random.nextInt(100) < ConfigCore.BLOCK.PUDDLE.puddleRate) {
                world.setBlockState(puddlePos.up(), BlocksCore.PUDDLE.getDefaultState(), 2);
              }
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  // TODO: PuddlesMixin +
  public boolean canSpawnPuddle(World world, BlockPos pos) {
    if (!world.isSideSolid(pos, EnumFacing.UP)) {
      return false;
    }
    if (!world.isAirBlock(pos.up())) {
      return false;
    }
    if (!world.isRaining()) {
      return false;
    }

    Biome biome = world.getBiomeForCoordsBody(pos);
    if (biome.canRain() && !biome.getEnableSnow() && (Climate.getActualTemp(pos) > 0)) {
      for (int y = pos.getY() + 1; y < world.getHeight(); y++) {
        BlockPos up = new BlockPos(pos.getX(), y, pos.getZ());
        if (!world.isAirBlock(up)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @SubscribeEvent
  public void puddleInteract(PlayerInteractEvent.RightClickBlock event) {
    ItemStack stack = event.getItemStack();
    World world = event.getWorld();
    BlockPos pos = event.getPos().up();
    EntityPlayer player = event.getEntityPlayer();
    if (world.getBlockState(pos).getBlock() == BlocksCore.PUDDLE) {
      if (stack.getItem() == Items.GLASS_BOTTLE && ConfigCore.BLOCK.PUDDLE.canUseGlassBottle) {
        if (event.getFace() == EnumFacing.UP) {
          if (!world.isRemote) {
            stack.shrink(1);
            if (!player.inventory.addItemStackToInventory(
              PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER))) {
              player.dropItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), false);
            }
            world.setBlockToAir(pos);
          } else {
            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
          }
        }
      }
      if (stack.getItem() instanceof ItemHoe hoe) {
        world.setBlockToAir(pos);
        hoe.onItemUse(player, world, pos.down(), event.getHand(), event.getFace(), 0, 0, 0);
      }
      if (stack.getItem() instanceof ItemSpade shovel) {
        world.setBlockToAir(pos);
        shovel.onItemUse(player, world, pos.down(), event.getHand(), event.getFace(), 0, 0, 0);
      }
    }
  }

  @SubscribeEvent
  public void makeBigSplash(LivingFallEvent event) {
    EntityLivingBase entity = event.getEntityLiving();
    BlockPos pos = entity.getPosition();
    World world = entity.getEntityWorld();

    if (!world.isRemote) {
      if (world.getBlockState(pos).getBlock() == BlocksCore.PUDDLE) {
        float distance = event.getDistance();
        if (distance < 3.0F) {
          ((WorldServer) world).spawnParticle(
            EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, 15, 0.0D, 0.0D,
            0.0D, 0.13D,
            Block.getStateId(BlocksCore.PUDDLE.getDefaultState()));
          ((WorldServer) world).spawnParticle(
            EnumParticleTypes.WATER_SPLASH, entity.posX, entity.posY, entity.posZ, 15, 0.0D, 0.0D,
            0.0D, 0.13D,
            Block.getStateId(BlocksCore.PUDDLE.getDefaultState()));
        } else {
          float f = (float) MathHelper.ceil(distance - 3.0F);

          double d0 = Math.min(0.2F + f / 15.0F, 2.5D);
          int i = (int) (200.0D * d0);

          for (int a = 0; a < 20; a++) {
            double x = 0.8 * (world.rand.nextDouble() - world.rand.nextDouble());
            double z = 0.8 * (world.rand.nextDouble() - world.rand.nextDouble());
            ((WorldServer) world).spawnParticle(
              EnumParticleTypes.WATER_SPLASH,
              entity.posX + x, entity.posY, entity.posZ + z, i / 2,
              0.0D, 0.0D, 0.0D, 0.25D
            );
          }
          ((WorldServer) world).spawnParticle(
            EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, i,
            0.0D, 0.0D, 0.0D, 0.4D, Block.getStateId(BlocksCore.PUDDLE.getDefaultState()));

          world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
        }
      }
    }
  }
}
