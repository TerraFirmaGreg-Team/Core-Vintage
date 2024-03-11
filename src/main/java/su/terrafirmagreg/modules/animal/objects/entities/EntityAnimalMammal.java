package su.terrafirmagreg.modules.animal.objects.entities;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Implements pregnancy for mammals in TFC
 */
@ParametersAreNonnullByDefault
public abstract class EntityAnimalMammal extends EntityAnimalBase {
	// The time(in days) this entity became pregnant
	private static final DataParameter<Long> PREGNANT_TIME = EntityDataManager.createKey(EntityAnimalMammal.class, TFCEntities.getLongDataSerializer());

	@SuppressWarnings("unused")
	public EntityAnimalMammal(World worldIn) {
		super(worldIn);
	}

	public EntityAnimalMammal(World worldIn, IAnimal.Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		setPregnantTime(-1);
	}

	public long getPregnantTime() {
		return dataManager.get(PREGNANT_TIME);
	}

	private void setPregnantTime(long day) {
		dataManager.set(PREGNANT_TIME, day);
	}

	@Override
	public void onFertilized(IAnimal male) {
		//Mark the day this female became pregnant
		setPregnantTime(CalendarTFC.PLAYER_TIME.getTotalDays());
	}

	@Override
	public Type getType() {
		return Type.MAMMAL;
	}

	/**
	 * Spawns children of this animal
	 */
	public abstract void birthChildren();

	/**
	 * Return the number of days for a full gestation
	 *
	 * @return long value in days
	 */
	public abstract long gestationDays();

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataManager().register(PREGNANT_TIME, -1L);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.world.isRemote) {
			if (this.isFertilized() && CalendarTFC.PLAYER_TIME.getTotalDays() >= getPregnantTime() + gestationDays()) {
				birthChildren();
				this.setFertilized(false);
			}
		}
	}

	@Override
	public void writeEntityToNBT(@Nonnull NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setLong("pregnant", getPregnantTime());
	}

	@Override
	public void readEntityFromNBT(@Nonnull NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setPregnantTime(nbt.getLong("pregnant"));
	}
}
