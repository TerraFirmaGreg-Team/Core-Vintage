package su.terrafirmagreg.modules.core.capabilities.player;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.skills.Skill;
import su.terrafirmagreg.modules.core.feature.skills.SkillType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import net.dries007.tfc.api.recipes.ChiselRecipe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ProviderPlayer implements ICapabilityPlayer, ICapabilitySerializable<NBTTagCompound> {

  public static final int MAX_INTOXICATED_TICKS =
    36 * ICalendar.TICKS_IN_HOUR; // A day and a half. Each drink gives you 4 hours of time

  private final Map<String, Skill> skills;
  private final EntityPlayer player;
  private ItemStack harvestingTool;
  private long intoxicatedTime;

  private ChiselRecipe.Mode chiselMode = ChiselRecipe.Mode.SMOOTH;
  private long nutted;
  private BlockPos nutPosition;

  public ProviderPlayer() {
    this(null);
  }

  public ProviderPlayer(EntityPlayer player) {
    this.skills = SkillType.createSkillMap(this);
    this.player = player;
    this.harvestingTool = ItemStack.EMPTY;
    this.intoxicatedTime = 0;
    this.nutted = 0;
    this.nutPosition = new BlockPos(0, 0, 0);
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound nbt = new NBTTagCompound();
    skills.forEach((k, v) -> nbt.setTag(k, v.serializeNBT()));
    nbt.setTag("chiselMode", new NBTTagByte((byte) chiselMode.ordinal()));
    nbt.setTag("harvestingTool", harvestingTool.serializeNBT());
    nbt.setLong("intoxicatedTime", intoxicatedTime);
    nbt.setLong("nutted", nutted);
    nbt.setLong("pos", nutPosition.toLong());
    return nbt;
  }

  @Override
  public void deserializeNBT(@Nullable NBTTagCompound nbt) {
    if (nbt != null) {
      skills.forEach((k, v) -> v.deserializeNBT(nbt.getCompoundTag(k)));
      chiselMode = ChiselRecipe.Mode.valueOf(nbt.getByte("chiselMode"));
      harvestingTool = new ItemStack(nbt.getCompoundTag("harvestingTool"));
      intoxicatedTime = nbt.getLong("intoxicatedTime");
      nutted = nbt.getLong("nutted");
      nutPosition = BlockPos.fromLong(nbt.getLong("pos"));
    }
  }

  @Override
  @Nullable
  @SuppressWarnings("unchecked")
  public <S extends Skill> S getSkill(SkillType<S> skillType) {
    return (S) skills.get(skillType.getName());
  }

  @NotNull
  @Override
  public ItemStack getHarvestingTool() {
    return harvestingTool;
  }

  @Override
  public void setHarvestingTool(@NotNull ItemStack stack) {
    this.harvestingTool = stack.copy();
  }

  @Override
  @NotNull
  public ChiselRecipe.Mode getChiselMode() {
    return chiselMode;
  }

  @Override
  public void setChiselMode(ChiselRecipe.Mode chiselMode) {
    this.chiselMode = chiselMode;
  }

  @Override
  public void addIntoxicatedTime(long ticks) {
    long currentTicks = Calendar.PLAYER_TIME.getTicks();
    if (this.intoxicatedTime < currentTicks) {
      this.intoxicatedTime = currentTicks;
    }
    this.intoxicatedTime += ticks;
    if (this.intoxicatedTime > currentTicks + MAX_INTOXICATED_TICKS) {
      this.intoxicatedTime = currentTicks + MAX_INTOXICATED_TICKS;
    }
  }

  @Override
  public long getIntoxicatedTime() {
    return Math.max(0, intoxicatedTime - Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  public void setNuttedTime() {
    nutted = Calendar.CALENDAR_TIME.getTicks();
  }

  @Override
  public long getNuttedTime() {
    return nutted;
  }

  @Override
  public void setNutPosition(BlockPos pos) {
    nutPosition = pos;
  }

  @Override
  public int getNutDistance(BlockPos pos) {
    return (int) Math.sqrt(nutPosition.distanceSq(pos));
  }

  @NotNull
  @Override
  public EntityPlayer getPlayer() {
    return player;
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityPlayer.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) this : null;
  }
}
