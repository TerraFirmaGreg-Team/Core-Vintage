package net.dries007.tfc.compat.gregtech.oreprefix;

public interface IOrePrefixExtension {

    boolean getHasMold();

    void setHasMold(boolean value);

    boolean getShouldHasMetalCapability();

    void setShouldHasMetalCapability(boolean value);

    int getMetalAmount();

    void setMetalAmount(int value);

    String[] getClayKnappingPattern();

    void setClayKnappingPattern(String... value);

    String[] getRockKnappingPattern();

    void setRockKnappingPattern(String... value);

}
