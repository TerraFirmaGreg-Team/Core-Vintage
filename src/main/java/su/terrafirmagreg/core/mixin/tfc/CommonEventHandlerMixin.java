package su.terrafirmagreg.core.mixin.tfc;

import net.dries007.tfc.CommonEventHandler;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.devices.BlockQuern;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.blocks.stone.BlockStoneAnvil;
import net.dries007.tfc.objects.blocks.wood.BlockSupport;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import puddles.Puddles;

@Mixin(value = CommonEventHandler.class, remap = false)
public class CommonEventHandlerMixin {

	@Inject(method = "onRightClickBlock", at = @At(value = "HEAD"), remap = false, cancellable = true)
	private static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event, CallbackInfo ci) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		IBlockState state = world.getBlockState(pos);
		ItemStack stack = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		if (state.getBlock() instanceof BlockAnvilTFC || state.getBlock() instanceof BlockStoneAnvil || state.getBlock() instanceof BlockQuern || state.getBlock() instanceof BlockSupport) {
			event.setUseBlock(Event.Result.ALLOW);
		}

		if (!player.isCreative() && stack.isEmpty() && player.getFoodStats() instanceof IFoodStatsTFC foodStats && event.getHand() == EnumHand.MAIN_HAND) {
			RayTraceResult result = Helpers.rayTrace(event.getWorld(), player, true);
			if (result != null && result.typeOfHit == net.minecraft.util.math.RayTraceResult.Type.BLOCK) {
				IBlockState waterState = world.getBlockState(result.getBlockPos());
				IBlockState puddleState = world.getBlockState(result.getBlockPos().add(0, 1, 0));
				boolean isFreshWater = BlocksTFC.isFreshWater(waterState);
				boolean isSaltWater = BlocksTFC.isSaltWater(waterState);
				boolean isPuddle = isPuddle(puddleState);
				if (isFreshWater && foodStats.attemptDrink(10.0F, true) || isSaltWater && foodStats.attemptDrink(-1.0F, true) || isPuddle && foodStats.attemptDrink(5.0F, true)) {
					if (!world.isRemote) {
						player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0F, 1.0F);
						if (isFreshWater) {
							foodStats.attemptDrink(10.0F, false);
						} else if (isSaltWater) {
							foodStats.attemptDrink(-1.0F, false);
						} else {
							foodStats.attemptDrink(5.0F, false);
							world.setBlockToAir(result.getBlockPos().add(0, 1, 0));
						}
					} else {
						foodStats.resetCooldown();
					}

					event.setCancellationResult(EnumActionResult.SUCCESS);
					event.setCanceled(true);
				}
			}
		}

		ci.cancel();
	}

	private static boolean isPuddle(IBlockState current) {
		return current == Puddles.puddle.getDefaultState();
	}

	@Inject(method = "onPlayerRespawn", at = @At(value = "INVOKE", target = "Lnet/dries007/tfc/api/capability/food/FoodStatsTFC;replaceFoodStats(Lnet/minecraft/entity/player/EntityPlayer;)V", shift = At.Shift.AFTER), remap = false, cancellable = true)
	private static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event, CallbackInfo ci) {
		final EntityPlayerMP player = (EntityPlayerMP) event.player;

		FoodStatsTFC foodStatsTFC = (FoodStatsTFC) player.getFoodStats();
		foodStatsTFC.setFoodLevel(4);
		foodStatsTFC.setThirst(25F);
		player.setHealth(5);
	}
}
