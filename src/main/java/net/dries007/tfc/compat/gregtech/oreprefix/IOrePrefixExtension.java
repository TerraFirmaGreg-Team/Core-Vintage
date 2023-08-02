package net.dries007.tfc.compat.gregtech.oreprefix;

public interface IOrePrefixExtension {

    void setHasMold(boolean value);

    boolean getHasMold();

    void setShouldHasMetalCapability(boolean value);

    boolean getShouldHasMetalCapability();

    void setMetalAmount(int value);

    int getMetalAmount();

    void setClayKnappingPattern(String... value);

    String[] getClayKnappingPattern();

    void setRockKnappingPattern(String... value);

    String[] getRockKnappingPattern();

}
