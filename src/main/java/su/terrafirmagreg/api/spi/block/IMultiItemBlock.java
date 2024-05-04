package su.terrafirmagreg.api.spi.block;

public interface IMultiItemBlock {

    boolean getHasItemSubtypes();

    default int getMaxItemDamage() {
        return 0;
    }

    String getTranslationKey(int meta);
}
