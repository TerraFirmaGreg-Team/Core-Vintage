package net.dries007.tfcflorae;

import su.terrafirmagreg.modules.core.feature.skill.SmithingSkill;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.dries007.tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import net.dries007.tfcflorae.types.BlockTypesTFCF.RockTFCF;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCF;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCF)
public final class CommonEventHandlerTFCF {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  @SubscribeEvent
  public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
    ItemStack stack = event.getObject();
    Item item = stack.getItem();
    if (!stack.isEmpty())
      ;
  }

  @SubscribeEvent
  public static void onBreakProgressEvent(BreakSpeed event) {
    EntityPlayer player = event.getEntityPlayer();
    if (player != null) {
      ItemStack stack = player.getHeldItemMainhand();
      float skillModifier = SmithingSkill.getSkillBonus(stack, SmithingSkill.Type.TOOLS);
      if (skillModifier > 0) {
        // Up to 2x modifier for break speed for skill bonuses on tools
        // New speed, so it will take into account other mods' modifications
        event.setNewSpeed(event.getNewSpeed() + (event.getNewSpeed() * skillModifier));
      }
    }
    if (event.getState().getBlock() instanceof BlockRockVariantTFCF) {
      event.setNewSpeed((float) (event.getNewSpeed() / ConfigTFC.General.MISC.rockMiningTimeModifier));
    }
  }

  @SubscribeEvent
  public static void onUseHoe(UseHoeEvent event) {
    if (ConfigTFCF.General.WORLD.enableAllBlockTypes) {
      World world = event.getWorld();
      BlockPos pos = event.getPos();
      IBlockState state = world.getBlockState(pos);

      if (ConfigTFC.General.OVERRIDES.enableHoeing) {
        if (state.getBlock() instanceof BlockRockVariantTFCF) {
          BlockRockVariantTFCF blockRock = (BlockRockVariantTFCF) state.getBlock();
          if
          (
            blockRock.getType() == RockTFCF.PODZOL ||
            blockRock.getType() == RockTFCF.SPARSE_GRASS
          ) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if
          (
            blockRock.getType() == RockTFCF.COARSE_DIRT
          ) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.LOAMY_SAND ||
                       blockRock.getType() == RockTFCF.LOAMY_SAND_GRASS ||
                       blockRock.getType() == RockTFCF.LOAMY_SAND_PODZOL ||
                       blockRock.getType() == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                       blockRock.getType() == RockTFCF.SPARSE_LOAMY_SAND_GRASS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAMY_SAND_FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.SANDY_LOAM ||
                       blockRock.getType() == RockTFCF.SANDY_LOAM_GRASS ||
                       blockRock.getType() == RockTFCF.SANDY_LOAM_PODZOL ||
                       blockRock.getType() == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                       blockRock.getType() == RockTFCF.SPARSE_SANDY_LOAM_GRASS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SANDY_LOAM_FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.LOAM ||
                       blockRock.getType() == RockTFCF.LOAM_GRASS ||
                       blockRock.getType() == RockTFCF.LOAM_PODZOL ||
                       blockRock.getType() == RockTFCF.DRY_LOAM_GRASS ||
                       blockRock.getType() == RockTFCF.SPARSE_LOAM_GRASS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAM_FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.SILT_LOAM ||
                       blockRock.getType() == RockTFCF.SILT_LOAM_GRASS ||
                       blockRock.getType() == RockTFCF.SILT_LOAM_PODZOL ||
                       blockRock.getType() == RockTFCF.DRY_SILT_LOAM_GRASS ||
                       blockRock.getType() == RockTFCF.SPARSE_SILT_LOAM_GRASS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_LOAM_FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.SILT ||
                       blockRock.getType() == RockTFCF.SILT_GRASS ||
                       blockRock.getType() == RockTFCF.SILT_PODZOL ||
                       blockRock.getType() == RockTFCF.DRY_SILT_GRASS ||
                       blockRock.getType() == RockTFCF.SPARSE_SILT_GRASS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.HUMUS ||
                       blockRock.getType() == RockTFCF.HUMUS_GRASS ||
                       blockRock.getType() == RockTFCF.DRY_HUMUS_GRASS ||
                       blockRock.getType() == RockTFCF.SPARSE_HUMUS_GRASS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.HUMUS_FARMLAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.COARSE_LOAMY_SAND
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAMY_SAND).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.COARSE_SANDY_LOAM
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SANDY_LOAM).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.COARSE_LOAM
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAM).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.COARSE_SILT_LOAM
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_LOAM).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.COARSE_SILT
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          } else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                     (
                       blockRock.getType() == RockTFCF.COARSE_HUMUS
                     )) {
            if (!world.isRemote) {
              world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
              world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.HUMUS).getDefaultState());
            }
            event.setResult(Event.Result.ALLOW);
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void onContainerOpen(PlayerContainerEvent.Open event) {
    if (event.getEntityPlayer() instanceof EntityPlayerMP) {
      // Capability Sync Handler
      CapabilityContainerListener.addTo(event.getContainer(), (EntityPlayerMP) event.getEntityPlayer());
    }
  }

}
