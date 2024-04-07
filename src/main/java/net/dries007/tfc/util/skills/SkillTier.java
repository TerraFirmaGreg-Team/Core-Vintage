package net.dries007.tfc.util.skills;

import org.jetbrains.annotations.NotNull;

public enum SkillTier {
    NOVICE,
    ADEPT,
    EXPERT,
    MASTER;

    private static final SkillTier[] VALUES = values();

    @NotNull
    public static SkillTier valueOf(int index) {
        return index < 0 ? NOVICE : index >= VALUES.length ? MASTER : VALUES[index];
    }

    @NotNull
    public SkillTier next() {
        return this == MASTER ? MASTER : VALUES[this.ordinal() + 1];
    }

    public boolean isAtLeast(SkillTier otherInclusive) {
        return this.ordinal() >= otherInclusive.ordinal();
    }
}
