package su.terrafirmagreg.modules.core.capabilities.playerdata;

import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.feature.skill.Skill;
import su.terrafirmagreg.modules.core.feature.skill.SkillType;
import su.terrafirmagreg.modules.core.network.SCPacketPlayerDataUpdate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import net.dries007.tfc.api.recipes.ChiselRecipe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface for the capability attached to a player Holds an internal list of skill implementations
 *
 * @see SkillType
 */
public interface ICapabilityPlayerData extends ICapabilitySerializable<NBTTagCompound> {

  @Nullable
  <S extends Skill> S getSkill(SkillType<S> skillType);

  /*
   * Gets the tool that was used in the last {@link net.minecraftforge.event.world.BlockEvent.BreakEvent} event
   */
  @NotNull
  ItemStack getHarvestingTool();

  void setHarvestingTool(@NotNull ItemStack stack);

  /**
   * Gets the current chiseling mode.
   *
   * @return enum value of the chiseling mode
   */
  @NotNull
  ChiselRecipe.Mode getChiselMode();

  /**
   * Sets the current chiseling mode.
   *
   * @param chiselMode enum value for the new chiseling mode
   */
  void setChiselMode(ChiselRecipe.Mode chiselMode);

  /**
   * Makes the player intoxicated
   *
   * @param ticks Ticks for the player to be intoxicated
   */
  void addIntoxicatedTime(long ticks);

  /**
   * Gets the number of ticks the player is intoxicated for
   */
  long getIntoxicatedTime();

  /**
   * Sets the time the player last hit a nut tree to current time
   */
  void setNuttedTime();

  /**
   * Retrieves the time the player last hit a nut tree
   */
  long getNuttedTime();

  /**
   * @param pos The block position of the last hammering
   */
  void setNutPosition(BlockPos pos);

  /**
   * @return Distance in blocks of how far the last hammering was.
   */
  int getNutDistance(BlockPos pos);

  default void updateAndSync() {
    EntityPlayer player = getPlayer();
    if (player instanceof EntityPlayerMP entityPlayerMP) {
      ModuleCore.NETWORK.sendTo(new SCPacketPlayerDataUpdate(serializeNBT()), entityPlayerMP);
    }
  }

  @NotNull
  EntityPlayer getPlayer();
}
