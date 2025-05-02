package su.terrafirmagreg.api.data;

import net.minecraft.util.DamageSource;

public final class DamageSources {

  public static final DamageSource BERRY_BUSH;
  public static final DamageSource PLUCKING;
  public static final DamageSource PARASITES;
  public static final DamageSource DEHYDRATION;
  public static final DamageSource GRILL;
  public static final DamageSource SOUP;
  public static final DamageSource HYPERTHERMIA;
  public static final DamageSource HYPOTHERMIA;
  public static final DamageSource BEAR_TRAP;
  public static final DamageSource PIGVIL;
  public static final DamageSource SWARM;

  static {
    BERRY_BUSH = new DamageSource("berry_bush");
    PLUCKING = new DamageSource("plucking").setDamageBypassesArmor();
    PARASITES = new DamageSource("parasites").setDamageBypassesArmor().setDamageIsAbsolute();
    DEHYDRATION = new DamageSource("dehydration").setDamageBypassesArmor().setDamageIsAbsolute();
    GRILL = new DamageSource("grill").setDamageBypassesArmor().setFireDamage();
    SOUP = new DamageSource("soup").setDamageBypassesArmor().setFireDamage();
    HYPERTHERMIA = new DamageSource("hyperthermia").setDamageBypassesArmor().setDamageIsAbsolute();
    HYPOTHERMIA = new DamageSource("hypothermia").setDamageBypassesArmor().setDamageIsAbsolute();
    BEAR_TRAP = new DamageSource("bear_trap");
    PIGVIL = new DamageSource("pigvil");
    SWARM = new DamageSource("swarm").setDifficultyScaled();
  }
}
