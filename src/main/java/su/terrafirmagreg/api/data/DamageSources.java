package su.terrafirmagreg.api.data;

import net.minecraft.util.DamageSource;

public class DamageSources {

  public static final DamageSource BERRYBUSH = new DamageSource("berrybush");
  public static final DamageSource PLUCKING = new DamageSource("plucking").setDamageBypassesArmor();
  public static final DamageSource FOOD_POISON = new DamageSource("food_poison").setDamageBypassesArmor().setDamageIsAbsolute();
  public static final DamageSource DEHYDRATION = new DamageSource("dehydration").setDamageBypassesArmor().setDamageIsAbsolute();
  public static final DamageSource GRILL = new DamageSource("grill").setDamageBypassesArmor().setFireDamage();
  public static final DamageSource SOUP = new DamageSource("grill").setDamageBypassesArmor().setFireDamage();
  public static final DamageSource HYPERTHERMIA = new DamageSource("hyperthermia").setDamageBypassesArmor().setDamageIsAbsolute();
  public static final DamageSource HYPOTHERMIA = new DamageSource("hypothermia").setDamageBypassesArmor().setDamageIsAbsolute();
  public static final DamageSource BEAR_TRAP = new DamageSource("bear_trap");
  public static final DamageSource PIGVIL = new DamageSource("pigvil");
  public static final DamageSource SWARM = new DamageSource("swarm").setDifficultyScaled();
}
