package net.dries007.tfc.util.interaction;

import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.object.tile.TileLogPile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.recipes.knapping.KnappingTypes;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;
import static su.terrafirmagreg.api.data.Properties.IntProp.TYPE;

@Mod.EventBusSubscriber(modid = MODID_TFC)
public final class InteractionManager {

  private static final Map<Predicate<ItemStack>, IRightClickBlockAction> USE_ACTIONS = new HashMap<>();
  private static final Map<Predicate<ItemStack>, IRightClickItemAction> RIGHT_CLICK_ACTIONS = new HashMap<>();
  private static final ThreadLocal<Boolean> PROCESSING_INTERACTION = ThreadLocal.withInitial(
    () -> false); // avoids stack overflows where some mods post interaction events during onBlockActivated (see #1321)

  static {
    // Clay knapping
    putBoth(stack -> OreDictionaryHelper.doesStackMatchOre(stack, "clay") && stack.getCount() >= KnappingTypes.CLAY.getAmountToConsume(),
            (worldIn, playerIn, handIn) -> {
              if (!worldIn.isRemote) {
                TFCGuiHandler.openGui(worldIn, playerIn, TFCGuiHandler.Type.KNAPPING_CLAY);
              }
              return EnumActionResult.SUCCESS;
            });

    // Fire clay knapping
    putBoth(stack -> OreDictionaryHelper.doesStackMatchOre(stack, "fireClay") && stack.getCount() >= KnappingTypes.FIRE_CLAY.getAmountToConsume(),
            ((worldIn, playerIn, handIn) -> {
              if (!worldIn.isRemote) {
                TFCGuiHandler.openGui(worldIn, playerIn, TFCGuiHandler.Type.KNAPPING_FIRE_CLAY);
              }
              return EnumActionResult.SUCCESS;
            }));

    // Leather knapping
    putBoth(stack -> OreDictionaryHelper.doesStackMatchOre(stack, "leather"), ((worldIn, playerIn, handIn) -> {
      if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "knife")) {
        if (!worldIn.isRemote) {
          TFCGuiHandler.openGui(worldIn, playerIn, TFCGuiHandler.Type.KNAPPING_LEATHER);
        }
        return EnumActionResult.SUCCESS;
      }
      return EnumActionResult.FAIL;
    }));

    putBoth(stack -> OreDictionaryHelper.doesStackMatchOre(stack, "bowl"), ((worldIn, playerIn, handIn) -> {
      // Offhand must contain a knife - avoids opening the salad gui whenever you empty a bowl form eating
      if (OreDictionaryHelper.doesStackMatchOre(playerIn.getHeldItem(handIn == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND),
                                                "knife")) {
        if (!worldIn.isRemote) {
          TFCGuiHandler.openGui(worldIn, playerIn, TFCGuiHandler.Type.SALAD);
        }
        return EnumActionResult.SUCCESS;
      }
      return EnumActionResult.PASS;
    }));

    // Logs -> Log Piles (placement + insertion)
    USE_ACTIONS.put(stack -> OreDictionaryHelper.doesStackMatchOre(stack, "logWood"),
                    (stack, player, worldIn, pos, hand, direction, hitX, hitY, hitZ) -> {
                      if (direction != null) {
                        IBlockState stateAt = worldIn.getBlockState(pos);
                        if (stateAt.getBlock() == BlocksDevice.LOG_PILE) {
                          // Clicked on a log pile, so try to insert into the original
                          // This is called first when player is sneaking, otherwise the call chain is passed to the BlockLogPile#onBlockActivated
                          var tile = TileUtils.getTile(worldIn, pos, TileLogPile.class);
                          if (tile.isPresent()) {
                            if (!player.isSneaking()) {
                              if (tile.get().insertLog(stack)) {
                                if (!worldIn.isRemote) {
                                  worldIn.playSound(null, pos.offset(direction), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F,
                                                    1.0F);
                                  stack.shrink(1);
                                  player.setHeldItem(hand, stack);
                                }
                                return EnumActionResult.SUCCESS;
                              }
                            } else {
                              int inserted = tile.get().insertLogs(stack.copy());
                              if (inserted > 0) {
                                if (!worldIn.isRemote) {
                                  worldIn.playSound(null, pos.offset(direction), SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F,
                                                    1.0F);
                                  stack.shrink(inserted);
                                  player.setHeldItem(hand, stack);
                                }
                                return EnumActionResult.SUCCESS;
                              }
                            }
                          }
                        }
                        // Try and place a log pile - if you were sneaking
                        if (ConfigTFC.General.OVERRIDES.enableLogPiles && player.isSneaking()) {
                          BlockPos posAt = pos;
                          if (!stateAt.getBlock().isReplaceable(worldIn, pos)) {
                            posAt = posAt.offset(direction);
                          }
                          if (worldIn.getBlockState(posAt.down()).isNormalCube()
                              && worldIn.mayPlace(BlocksDevice.LOG_PILE, posAt, false, direction, null)) {
                            // Place log pile
                            if (!worldIn.isRemote) {
                              worldIn.setBlockState(posAt, BlocksDevice.LOG_PILE.getStateForPlacement(worldIn, posAt, direction, 0, 0, 0, 0, player));

                              TileUtils.getTile(worldIn, posAt, TileLogPile.class).ifPresent(tile -> tile.insertLog(stack.copy()));

                              worldIn.playSound(null, posAt, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                              stack.shrink(1);
                              player.setHeldItem(hand, stack);
                            }
                            return EnumActionResult.SUCCESS;
                          }
                        }
                      }
                      // Pass to allow the normal `onItemUse` to get called, which handles item block placement
                      return EnumActionResult.PASS;
                    });

    // Charcoal -> charcoal piles
    // This is also where charcoal piles grow
    USE_ACTIONS.put(stack -> OreDictionaryHelper.doesStackMatchOre(stack, "charcoal"),
                    (stack, player, worldIn, pos, hand, direction, hitX, hitY, hitZ) -> {
                      if (direction != null) {
                        IBlockState state = worldIn.getBlockState(pos);
                        if (state.getBlock() == BlocksDevice.CHARCOAL_PILE && state.getValue(TYPE) < 8) {
                          // Check the player isn't standing inside the placement area for the next layer
                          IBlockState stateToPlace = state.withProperty(TYPE, state.getValue(TYPE) + 1);
                          if (worldIn.checkNoEntityCollision(stateToPlace.getBoundingBox(worldIn, pos).offset(pos))) {
                            if (!worldIn.isRemote) {
                              worldIn.setBlockState(pos, state.withProperty(TYPE, state.getValue(TYPE) + 1));
                              worldIn.playSound(null, pos, TFCSounds.CHARCOAL_PILE.getPlaceSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
                              stack.shrink(1);
                              player.setHeldItem(hand, stack);
                            }
                            return EnumActionResult.SUCCESS;
                          }
                        }
                        BlockPos posAt = pos;
                        if (!state.getBlock().isReplaceable(worldIn, pos)) {
                          posAt = posAt.offset(direction);
                        }
                        if (worldIn.getBlockState(posAt.down())
                                   .isSideSolid(worldIn, posAt.down(), EnumFacing.UP) && worldIn.getBlockState(posAt)
                                                                                                .getBlock()
                                                                                                .isReplaceable(worldIn, pos)) {
                          IBlockState stateToPlace = BlocksDevice.CHARCOAL_PILE.getDefaultState().withProperty(TYPE, 1);
                          if (worldIn.checkNoEntityCollision(stateToPlace.getBoundingBox(worldIn, posAt).offset(posAt))) {
                            // Create a new charcoal pile
                            if (!worldIn.isRemote) {
                              worldIn.setBlockState(posAt, stateToPlace);
                              worldIn.playSound(null, posAt, TFCSounds.CHARCOAL_PILE.getPlaceSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
                              stack.shrink(1);
                              player.setHeldItem(hand, stack);
                            }
                            return EnumActionResult.SUCCESS;
                          }
                        }
                      }
                      return EnumActionResult.FAIL;
                    });
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    if (!PROCESSING_INTERACTION.get()) {
      PROCESSING_INTERACTION.set(true);
      IRightClickBlockAction action = InteractionManager.findItemUseAction(event.getItemStack());
      if (action != null) {
        // Use alternative handling
        EnumActionResult result;
        if (event.getSide() == Side.CLIENT) {
          result = ClientInteractionManager.processRightClickBlock(event, action);
        } else {
          result = ServerInteractionManager.processRightClickBlock(event, action);
        }
        event.setCancellationResult(result);
        event.setCanceled(true);
      }
      PROCESSING_INTERACTION.set(false);
    }
  }

  @Nullable
  private static IRightClickBlockAction findItemUseAction(ItemStack stack) {
    return USE_ACTIONS.entrySet()
                      .stream()
                      .filter(e -> e.getKey().test(stack))
                      .findFirst()
                      .map(Map.Entry::getValue)
                      .orElse(null);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
    IRightClickItemAction action = InteractionManager.findItemRightClickAction(event.getItemStack());
    if (action != null) {
      // Use alternative handling
      EnumActionResult result;
      if (event.getSide() == Side.CLIENT) {
        result = ClientInteractionManager.processRightClickItem(event, action);
      } else {
        result = ServerInteractionManager.processRightClickItem(event, action);
      }
      event.setCancellationResult(result);
      event.setCanceled(true);
    }
  }

  @Nullable
  private static IRightClickItemAction findItemRightClickAction(ItemStack stack) {
    return RIGHT_CLICK_ACTIONS.entrySet()
                              .stream()
                              .filter(e -> e.getKey().test(stack))
                              .findFirst()
                              .map(Map.Entry::getValue)
                              .orElse(null);
  }

  private static void putBoth(Predicate<ItemStack> predicate, IRightClickItemAction minorAction) {
    USE_ACTIONS.put(predicate, minorAction);
    RIGHT_CLICK_ACTIONS.put(predicate, minorAction);
  }
}
