package su.terrafirmagreg.api.data;

import lombok.Getter;

@Getter
public enum FluidVolume {

    NUGGET(16),
    RING(16),
    TINY_DUST(16),
    BARS(18),
    SCREW(32),
    POOR_CHUNK(21),
    CHUNK(31),
    RICH_CHUNK(42),
    BOLT(36),
    SMALL_DUST(36),
    CHAIN(44),
    ROD(72),
    IMPURE_DUST(120),
    DUST(144),
    INGOT(144),
    PLATE(144),
    LONG_ROD(144),
    TRAPDOOR(144),
    LAMP(144),
    RING_MESH(176),
    DOUBLE_INGOT(288),
    DOUBLE_PLATE(288),
    TUYERE(288),
    BOTTLE(333),
    TRIPLE_INGOT(432),
    TRIPLE_PLATE(432),
    QUADRUPLE_INGOT(576),
    QUADRUPLE_PLATE(576),
    GEAR(864),
    BUCKET(1000),
    BLOCK(1296),
    ANVIL(2016);

    /**
     * The amount of mb which make up this amount.
     */
    public final int amount;

    /**
     * A simple enumeration used to list how many milibuckets is in a given measurement.
     *
     * @param amount The amount of milibuckets in the measurement.
     */
    FluidVolume(int amount) {
        this.amount = amount;
    }
}
