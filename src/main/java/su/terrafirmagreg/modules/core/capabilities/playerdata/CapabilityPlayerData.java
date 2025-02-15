package su.terrafirmagreg.modules.core.capabilities.playerdata;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.feature.skill.Skill;
import su.terrafirmagreg.modules.core.feature.skill.SkillType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import org.jetbrains.annotations.Nullable;

public final class CapabilityPlayerData {

  public static final ResourceLocation KEY = ModUtils.resource("skill_capability");

  @CapabilityInject(ICapabilityPlayerData.class)
  public static final Capability<ICapabilityPlayerData> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityPlayerData.class, new StoragePlayerData(), ProviderPlayerData::new);
  }

  public static boolean has(EntityPlayer player) {
    return player.hasCapability(CAPABILITY, null);
  }

  /**
   * Helper method to get a skill instance
   *
   * @param player    The player to get skills fromm
   * @param skillType The skill type
   * @param <S>       The skill class
   */
  @Nullable
  public static <S extends Skill> S getSkill(EntityPlayer player, SkillType<S> skillType) {
    var cap = CapabilityPlayerData.get(player);
    if (cap != null) {
      return cap.getSkill(skillType);
    }
    return null;
  }

  public static ICapabilityPlayerData get(EntityPlayer player) {
    return player.getCapability(CAPABILITY, null);
  }
}
