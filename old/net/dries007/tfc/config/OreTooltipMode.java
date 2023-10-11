package net.dries007.tfc.config;

public enum OreTooltipMode {
    HIDE("Hide ore information"),
    UNIT_ONLY("Show only the ore units"),
    TOTAL_ONLY("Show only the stack total units"),
    ALL_INFO("Show All"),
    ADVANCED("Show numeric heat/melt data");

    private final String name;

    OreTooltipMode(String name) {
        this.name = name;
    }

    /**
     * Shows this text in config instead of the enum name
     */
    @Override
    public String toString() {
        return name;
    }
}
