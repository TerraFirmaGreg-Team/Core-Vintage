package su.terrafirmagreg.modules.soil.event;

import su.terrafirmagreg.modules.core.ModuleCoreConfig;
import su.terrafirmagreg.modules.soil.ModuleSoilConfig;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.dries007.tfc.objects.blocks.BlockPlacedItemFlat;

import static su.terrafirmagreg.api.lib.Constants.MOD_ID;
import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class PathEventHandler {

    private static int player_debug_cooldown = 100;
    private static int mob_debug_cooldown = 1000;
    private static double player_speed = 0.001D;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void createPath(LivingEvent.LivingUpdateEvent event) {
        // PLAYER PATHING
        if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer) {
            // GET PLAYER INFORMATION
            EntityLivingBase player = event.getEntityLiving();
            int posX = MathHelper.floor(player.posX);
            int posY = MathHelper.floor(player.getEntityBoundingBox().minY - 1);
            int posZ = MathHelper.floor(player.posZ);
            BlockPos posPlayer = new BlockPos(posX, posY, posZ);
            World world = player.getEntityWorld();
            IBlockState state = world.getBlockState(posPlayer);

            // PLAYER MATH
            double player_random = Math.random() * 1000.0D + 1.0D;
            double player_distance_X = player.posX - player.prevPosX;
            double player_distance_Z = player.posZ - player.prevPosZ;
            double player_speed_current = MathHelper.sqrt(player_distance_X * player_distance_X + player_distance_Z * player_distance_Z);

            // UPDATE PLAYER SPEED ON UPDATE PACKET
            if (player_speed_current > 0.0) {
                player_speed = player_speed_current;
            }

            if (ModuleCoreConfig.MISC.DEBUG) {
                if (player_debug_cooldown == 0) {
                    System.out.println("[Player] Random: " + player_random);
                    System.out.println("[Player] Speed: " + player_speed);
                    player_debug_cooldown = 100;
                } else {
                    player_debug_cooldown--;
                }
            }

            if (!world.isRemote && state.getBlock() instanceof ISoilBlock soil && player_speed > 0.2) {

                if (player_random < ModuleSoilConfig.BLOCKS.PATH.PLAYER_GRASS_TO_DIRT && soil.getBlockVariant() == GRASS ||
                        soil.getBlockVariant() == DRY_GRASS) {
                    world.setBlockState(posPlayer, DIRT.get(soil.getType()).getDefaultState());
                    if (ModuleSoilConfig.BLOCKS.PATH.DESTROY_VEGETATION) {
                        BlockPos upPos = posPlayer.up();
                        Material upMaterial = world.getBlockState(upPos).getMaterial();
                        if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos)
                                .getBlock() instanceof BlockPlacedItemFlat) {
                            world.destroyBlock(upPos, true);
                        }
                    }
                    return;
                }

                if (player_random < ModuleSoilConfig.BLOCKS.PATH.PLAYER_DIRT_TO_PATH && soil.getBlockVariant() == DIRT) {
                    world.setBlockState(posPlayer, GRASS_PATH.get(soil.getType()).getDefaultState());
                    return;
                }
            }
        }

        // MOB PATHING
        if (ModuleSoilConfig.BLOCKS.PATH.ALL_ENTITIES) {
            if (event.getEntity() != null && !(event.getEntity() instanceof EntityPlayer)) {
                // GET MOB INFORMATION
                EntityLivingBase entity = event.getEntityLiving();
                int posX = MathHelper.floor(entity.posX);
                int posY = MathHelper.floor(entity.getEntityBoundingBox().minY - 1);
                int posZ = MathHelper.floor(entity.posZ);
                BlockPos posEntity = new BlockPos(posX, posY, posZ);
                World world = entity.getEntityWorld();
                IBlockState state = world.getBlockState(posEntity);

                // MOB MATH
                double mob_random = Math.random() * 1000.0D + 1.0D;
                double mob_speed = Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);

                if (ModuleCoreConfig.MISC.DEBUG) {
                    if (mob_debug_cooldown == 0) {
                        System.out.println("[Mob] Random: " + mob_random);
                        System.out.println("[Mob] Speed: " + mob_speed);
                        mob_debug_cooldown = 100;
                    } else {
                        mob_debug_cooldown--;
                    }
                }

                if (!world.isRemote && state.getBlock() instanceof ISoilBlock soil && mob_speed > 0.08) {

                    if (mob_random < ModuleSoilConfig.BLOCKS.PATH.MOB_GRASS_TO_DIRT && soil.getBlockVariant() == GRASS ||
                            soil.getBlockVariant() == DRY_GRASS) {
                        world.setBlockState(posEntity, DIRT.get(soil.getType()).getDefaultState());
                        if (ModuleSoilConfig.BLOCKS.PATH.DESTROY_VEGETATION) {
                            BlockPos upPos = posEntity.up();
                            Material upMaterial = world.getBlockState(upPos).getMaterial();
                            if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos)
                                    .getBlock() instanceof BlockPlacedItemFlat) {
                                world.destroyBlock(upPos, true);
                            }
                        }
                        return;
                    }

                    if (mob_random < ModuleSoilConfig.BLOCKS.PATH.MOB_DIRT_TO_PATH && soil.getBlockVariant() == DIRT ||
                            soil.getBlockVariant() == COARSE_DIRT) {
                        world.setBlockState(posEntity, GRASS_PATH.get(soil.getType()).getDefaultState());
                    }
                }
            }
        }
    }
}
