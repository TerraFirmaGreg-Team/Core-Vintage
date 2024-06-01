package su.terrafirmagreg.api.capabilities.skill;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;


import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillType;

import org.jetbrains.annotations.Nullable;

public final class CapabilitySkill {

    public static final ResourceLocation KEY = ModUtils.resource("skill_capability");

    @CapabilityInject(ICapabilitySkill.class)
    public static final Capability<ICapabilitySkill> CAPABILITY = ModUtils.getNull();

    public static void register() {
        CapabilityManager.INSTANCE.register(ICapabilitySkill.class, new StorageSkill(), ProviderSkill::new);
    }

    public static ICapabilitySkill get(EntityPlayer player) {
        return player.getCapability(CAPABILITY, null);
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
        ICapabilitySkill skills = CapabilitySkill.get(player);
        if (skills != null) {
            return skills.getSkill(skillType);
        }
        return null;
    }
}
