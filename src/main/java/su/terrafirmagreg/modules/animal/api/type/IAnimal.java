package su.terrafirmagreg.modules.animal.api.type;

import su.terrafirmagreg.modules.animal.api.util.AnimalFood;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Interface for animals with gender, familiarity and aging
 */
public interface IAnimal extends ICreature {

  /**
   * Get this animal gender, female or male
   *
   * @return Gender of this animal
   */
  Gender getGender();

  /**
   * Set this animal gender, used on spawn/birth
   *
   * @param gender the Gender to set to
   */
  void setGender(Gender gender);

  /**
   * What is the maximum familiarity obtainable for adults of this animal?
   *
   * @return 0 if not familiarizable at all, [0, 1] for a cap
   */
  default float getAdultFamiliarityCap() {
    return 0;
  }

  /**
   * Event: Do things on fertilization of females (ie: save the male genes for some sort of genetic selection)
   */
  default void onFertilized(@NotNull IAnimal male) {
    setFertilized(true);
  }

  /**
   * Used by model renderer to scale the size of the animal
   *
   * @return double value between 0(birthday) to 1(full grown adult)
   */
  default double getPercentToAdulthood() {
    long deltaDays = Calendar.PLAYER_TIME.getTotalDays() - this.getBirthDay();
    long adulthoodDay = this.getDaysToAdulthood();
    return Math.max(0, Math.min(1, (double) deltaDays / adulthoodDay));
  }

  /**
   * Returns the birth day of this animal. Determines how old this animal is
   *
   * @return returns the day this animal has been birth
   */
  int getBirthDay();

  /**
   * Sets the birth day of this animal. Used to determine how old this animal is
   *
   * @param value the day this animal has been birth. Used when this animal spawns.
   */
  void setBirthDay(int value);

  /**
   * Get the number of days needed for this animal to be adult
   *
   * @return number of days
   */
  int getDaysToAdulthood();

  /**
   * Check if this animal is ready to mate
   *
   * @return true if ready
   */
  default boolean isReadyToMate() {
    return this.getAge() == Age.ADULT && !(this.getFamiliarity() < 0.3f) && !this.isFertilized()
           && !this.isHungry();
  }

  /**
   * Get this entity age, based on birth
   *
   * @return the Age enum of this entity
   */
  default Age getAge() {
    long deltaDays = Calendar.PLAYER_TIME.getTotalDays() - this.getBirthDay();
    long adulthoodDay = this.getDaysToAdulthood();
    long elderlyDay = this.getDaysToElderly() + this.getDaysToAdulthood();
    if (getCreatureType() == CreatureType.LIVESTOCK && this.getDaysToElderly() > 0
        && deltaDays > elderlyDay) {
      return Age.OLD; // if enabled, only for familiarizable animals
    } else if (deltaDays > adulthoodDay) {
      return Age.ADULT;
    } else {
      return Age.CHILD;
    }
  }

  /**
   * Returns the familiarity of this animal
   *
   * @return float value between 0-1.
   */
  float getFamiliarity();

  /**
   * Set this animal familiarity
   *
   * @param value float value between 0-1.
   */
  void setFamiliarity(float value);

  /**
   * Returns true if this female is pregnant, or the next time it ovulates, eggs are fertilized.
   *
   * @return true if this female has been fertilized.
   */
  boolean isFertilized();

  /**
   * Set if this female is fertilized
   *
   * @param value true on fertilization (mating)
   */
  void setFertilized(boolean value);

  /**
   * Is this animal hungry?
   *
   * @return true if this animal can be fed by player
   */
  boolean isHungry();

  /**
   * Get the number of days past adulthood needed for this animal to be an elder
   *
   * @return number of days, 0 to disable
   */
  int getDaysToElderly();

  /**
   * Check if said item can feed this animal
   *
   * @param stack the itemstack to check
   * @return true if item is used to feed this animal (entice and increase familiarity)
   */
  default boolean isFood(@NotNull ItemStack stack) {
    //noinspection unchecked
    Supplier<AnimalFood> food = AnimalFood.get((Class<? extends Entity>) this.getClass());
    if (food != null) {
      return food.get().isFood(stack);
    }
    return false;
  }

  /**
   * Which animal type is this? Do this animal lay eggs or give birth to it's offspring?
   *
   * @return the enum Type of this animal.
   */
  Type getType();

  /**
   * Some animals can give products (eg: Milk, Wool and Eggs) This function returns if said animal is ready to be worked upon (or if it is ready to lay eggs on
   * it's own)
   * <p>
   * ** Check for everything ** this function should return only true if the animal will give it's products upon work (so TOP integration could show this animal
   * is ready)
   *
   * @return true if it is ready for product production
   */
  default boolean isReadyForAnimalProduct() {
    return false;
  }

  /**
   * Get the products of this animal Can return more than one item itemstack fortune and other behaviour should not be handled here Suggestion:
   * EntityLiving#processInteract() for right clicking handling
   * <p>
   * (This function should be implemented with TOP integration in mind ie: what would you like for the tooltip to show when #isReadyForAnimalProduct returns
   * true?)
   *
   * @return a list of itemstack
   */
  default List<ItemStack> getProducts() {
    return Collections.emptyList();
  }

  /**
   * Set this animal on produce cooldown This means that you just sheared a sheep, your chicken just laiyed eggs or you just milked your cow
   */
  default void setProductsCooldown() {
  }

  /**
   * Returns the number of ticks remaining for this animal to finish it's produce cooldown
   *
   * @return ticks remaining to finish cooldown
   */
  default long getProductsCooldown() {
    return 0;
  }

  /**
   * Get the tooltip for ** Why this animal is not ready? ** Common usages: Cows not having milk, chickens already layed eggs today, sheeps' fleece not grown,
   * not enough familiarity
   *
   * @return null if you don't want for a tooltip to be shown, any TextComponentTranslation object if you want it to.
   */
  default TextComponentTranslation getTooltip() {
    return null;
  }

  /**
   * Get the animal name, which can be determined by male / female (eg: bull or cow, rooster or chicken)
   *
   * @return the TextComponentTranslation for localized name
   */
  TextComponentTranslation getAnimalName();

  enum Age {
    CHILD,
    ADULT,
    OLD
  }

  enum Gender {
    MALE,
    FEMALE;

    public static Gender valueOf(boolean value) {
      return value ? MALE : FEMALE;
    }

    public boolean toBool() {
      return this == MALE;
    }
  }

  enum Type {
    MAMMAL,
    OVIPAROUS
  }
}
