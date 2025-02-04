package su.terrafirmagreg.modules.animal.object.entity;

import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.framework.network.spi.datasync.DataSerializers;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

/**
 * Implements pregnancy for mammals in TFC
 */

public abstract class EntityAnimalMammal extends EntityAnimalBase {

  // The time(in days) this entity became pregnant
  private static final DataParameter<Long> PREGNANT_TIME = EntityDataManager.createKey(
    EntityAnimalMammal.class, DataSerializers.LONG);

  @SuppressWarnings("unused")
  public EntityAnimalMammal(World worldIn) {
    super(worldIn);
  }

  public EntityAnimalMammal(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    setPregnantTime(-1);
  }

  @Override
  public void onFertilized(@NotNull IAnimal male) {
    //Mark the day this female became pregnant
    setPregnantTime(Calendar.PLAYER_TIME.getTotalDays());
  }

  @Override
  public Type getType() {
    return Type.MAMMAL;
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(PREGNANT_TIME, -1L);
  }

  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (!this.world.isRemote) {
      if (this.isFertilized()
          && Calendar.PLAYER_TIME.getTotalDays() >= getPregnantTime() + gestationDays()) {
        birthChildren();
        this.setFertilized(false);
      }
    }
  }

  public long getPregnantTime() {
    return dataManager.get(PREGNANT_TIME);
  }

  private void setPregnantTime(long day) {
    dataManager.set(PREGNANT_TIME, day);
  }

  /**
   * Return the number of days for a full gestation
   *
   * @return long value in days
   */
  public abstract long gestationDays();

  /**
   * Spawns children of this animal
   */
  public abstract void birthChildren();

  @Override
  public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "pregnant", getPregnantTime());
  }

  @Override
  public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    this.setPregnantTime(nbt.getLong("pregnant"));
  }
}
