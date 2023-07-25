package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = OrePrefix.class, remap = false)
public class OrePrefixMixin implements IOrePrefixExtension {

    @Unique private boolean isHasMold;

    @Unique private boolean shouldHasMetalCapability;

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
}
