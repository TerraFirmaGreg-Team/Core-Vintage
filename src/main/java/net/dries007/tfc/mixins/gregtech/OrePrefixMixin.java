package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("all")
@Mixin(value = OrePrefix.class, remap = false)
public class OrePrefixMixin implements IOrePrefixExtension {

    @Unique private boolean isHasMold;
    @Unique private boolean shouldHasMetalCapability;
    @Unique private int metalAmount;
    @Unique private String[] clayKnappingPattern;
    @Unique private String[] rockKnappingPattern;

    @Override
    public void setHasMold(boolean value) {
        isHasMold = value;
    }

    @Override
    public boolean getHasMold() {
        return isHasMold;
    }

    @Override
    public void setShouldHasMetalCapability(boolean value) {
        shouldHasMetalCapability = value;
    }

    @Override
    public boolean getShouldHasMetalCapability() {
        return shouldHasMetalCapability;
    }

    @Override
    public void setMetalAmount(int value) {
        metalAmount = value;
    }

    @Override
    public int getMetalAmount() {
        return metalAmount;
    }

    @Override
    public void setClayKnappingPattern(String... value) {
        clayKnappingPattern = value;
    }

    @Override
    public String[] getClayKnappingPattern() {
        return clayKnappingPattern;
    }

    @Override
    public void setRockKnappingPattern(String... value) {
        rockKnappingPattern = value;
    }

    @Override
    public String[] getRockKnappingPattern() {
        return rockKnappingPattern;
    }
}
