package net.dries007.tfc.config;

public enum DecayTooltipMode {
    HIDE("Hide decay information"),
    EXPIRATION_ONLY("Show only the expiration date"),
    TIME_REMAINING_ONLY("Show only the time remaining to expire"),
    ALL_INFO("Show All");

    private final String name;

    DecayTooltipMode(String name) {
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
