package mod.acgaming.tfcpaths;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlockPlacedItemFlat;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PathEvent {
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

			if (PathConfig.general_settings.DEBUG) {
				if (player_debug_cooldown == 0) {
					System.out.println("[Player] Random: " + player_random);
					System.out.println("[Player] Speed: " + player_speed);
					player_debug_cooldown = 100;
				} else {
					player_debug_cooldown--;
				}
			}

			if (!world.isRemote && state.getBlock() instanceof BlockRockVariant && player_speed > 0.2) {
				BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();

				if (player_random < PathConfig.player_settings.GRASS_TO_DIRT && blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS) {
					world.setBlockState(posPlayer, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT)
					                                               .getDefaultState());
					if (PathConfig.general_settings.DESTROY_VEGETATION) {
						BlockPos upPos = posPlayer.up();
						Material upMaterial = world.getBlockState(upPos).getMaterial();
						if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos)
						                                                                         .getBlock() instanceof BlockPlacedItemFlat) {
							world.destroyBlock(upPos, true);
						}
					}
					return;
				}

				if (player_random < PathConfig.player_settings.DIRT_TO_PATH && blockRock.getType() == Rock.Type.DIRT) {
					world.setBlockState(posPlayer, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH)
					                                               .getDefaultState());
					return;
				}

				if (player_random < PathConfig.player_settings.PATH_TO_GRAVEL && blockRock.getType() == Rock.Type.PATH) {
					world.setBlockState(posPlayer, BlockRockVariant.get(blockRock.getRock(), Rock.Type.GRAVEL)
					                                               .getDefaultState());
					return;
				}
			}
		}

		// MOB PATHING
		if (PathConfig.general_settings.ALL_ENTITIES) {
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

				if (PathConfig.general_settings.DEBUG) {
					if (mob_debug_cooldown == 0) {
						System.out.println("[Mob] Random: " + mob_random);
						System.out.println("[Mob] Speed: " + mob_speed);
						mob_debug_cooldown = 100;
					} else {
						mob_debug_cooldown--;
					}
				}

				if (!world.isRemote && state.getBlock() instanceof BlockRockVariant && mob_speed > 0.08) {
					BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();

					if (mob_random < PathConfig.mob_settings.GRASS_TO_DIRT && blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS) {
						world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT)
						                                               .getDefaultState());
						if (PathConfig.general_settings.DESTROY_VEGETATION) {
							BlockPos upPos = posEntity.up();
							Material upMaterial = world.getBlockState(upPos).getMaterial();
							if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos)
							                                                                         .getBlock() instanceof BlockPlacedItemFlat) {
								world.destroyBlock(upPos, true);
							}
						}
						return;
					}

					if (mob_random < PathConfig.mob_settings.DIRT_TO_PATH && blockRock.getType() == Rock.Type.DIRT) {
						world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH)
						                                               .getDefaultState());
						return;
					}

					if (mob_random < PathConfig.mob_settings.PATH_TO_GRAVEL && blockRock.getType() == Rock.Type.PATH) {
						world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.GRAVEL)
						                                               .getDefaultState());
						return;
					}
				}
			}
		}
	}
}
