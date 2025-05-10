package su.terrafirmagreg.api.data;

import net.minecraft.util.DamageSource;

public class DamageSources extends DamageSource {

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
    BERRY_BUSH = new DamageSources("berry_bush");
    PLUCKING = new DamageSources("plucking").setDamageBypassesArmor();
    PARASITES = new DamageSources("parasites").setDamageBypassesArmor().setDamageIsAbsolute();
    DEHYDRATION = new DamageSources("dehydration").setDamageBypassesArmor().setDamageIsAbsolute();
    GRILL = new DamageSources("grill").setDamageBypassesArmor().setFireDamage();
    SOUP = new DamageSources("soup").setDamageBypassesArmor().setFireDamage();
    HYPERTHERMIA = new DamageSources("hyperthermia").setDamageBypassesArmor().setDamageIsAbsolute();
    HYPOTHERMIA = new DamageSources("hypothermia").setDamageBypassesArmor().setDamageIsAbsolute();
    BEAR_TRAP = new DamageSources("bear_trap");
    PIGVIL = new DamageSources("pigvil");
    SWARM = new DamageSources("swarm").setDifficultyScaled();
  }

  public DamageSources(String damageTypeIn) {
    super(damageTypeIn);
  }
}
