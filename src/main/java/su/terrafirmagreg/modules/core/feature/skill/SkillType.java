package su.terrafirmagreg.modules.core.feature.skill;

import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.capabilities.playerdata.ICapabilityPlayerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The instance (i.e. Block or Item) of a skill. This holds the name, class, and supplier for the skill capability To create a new skill all you need to do is
 * create a new instance of this class. Registration will be automatically handled for you.
 *
 * @param <S> the skill class
 */
@SuppressWarnings("WeakerAccess")
public final class SkillType<S extends Skill> {

  public static final SkillType<ProspectingSkill> PROSPECTING;
  public static final SkillType<SmithingSkill> SMITHING;
  public static final SkillType<SimpleSkill> AGRICULTURE;
  public static final SkillType<SimpleSkill> BUTCHERING;

  private static final Map<String, SkillType<? extends Skill>> SKILL_TYPES = new LinkedHashMap<>(4);
  private static final List<SkillType<? extends Skill>> SKILL_ORDER = new ArrayList<>(4);

  static {
    // This needs to happen after SKILL_TYPES and SKILL_ORDER are initialized, otherwise it causes an NPE
    PROSPECTING = new SkillType<>("prospecting", ProspectingSkill::new);
    SMITHING = new SkillType<>("smithing", SmithingSkill::new);
    AGRICULTURE = new SkillType<>("agriculture", SimpleSkill::new);
    BUTCHERING = new SkillType<>("butchering", SimpleSkill::new);
  }

  private final String name;
  private final Function<ICapabilityPlayerData, S> skillSupplier;

  public SkillType(String name, Function<ICapabilityPlayerData, S> skillSupplier) {
    this.name = name;
    this.skillSupplier = skillSupplier;

    if (SKILL_TYPES.containsKey(name)) {
      throw new IllegalArgumentException("Can't register multiple skills with the same name!");
    }

    SKILL_TYPES.put(name, this);
    SKILL_ORDER.add(this);
  }

  @Nonnull
  public static List<SkillType<? extends Skill>> getSkills() {
    return SKILL_ORDER;
  }

  @Nonnull
  public static Map<String, Skill> createSkillMap(ICapabilityPlayerData rootInstance) {
    return SKILL_TYPES.values().stream().collect(Collectors.toMap(SkillType::getName, e -> e.skillSupplier.apply(rootInstance)));
  }

  @Nullable
  @SuppressWarnings("unchecked")
  public static <S extends Skill> SkillType<S> get(String name, Class<S> returnClass) {
    SkillType<? extends Skill> skill = SKILL_TYPES.get(name);
    try {
      return ((SkillType<S>) skill);
    } catch (ClassCastException e) {
      ModuleCore.LOGGER.warn("Tried to cast skill '" + skill + "' to an incorrect instance type: " + name + " / " + returnClass);
      return null;
    }
  }

  @Nonnull
  public String getName() {
    return name;
  }
}
