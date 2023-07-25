package net.dries007.tfc.mixins.gregtech;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = OrePrefix.class, remap = false)
public class OrePrefixMixin implements IOrePrefixExtension {

    @Unique private boolean isHasMold;

    @Override
    public void setHasMold(boolean boolValue) {
        isHasMold = boolValue;
    }

    @Override
    public boolean getHasMold() {
        return isHasMold;
    }
}
