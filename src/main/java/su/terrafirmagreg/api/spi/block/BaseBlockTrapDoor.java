package su.terrafirmagreg.api.spi.block;

import net.minecraft.block.BlockTrapDoor;


import lombok.Getter;

@Getter
public abstract class BaseBlockTrapDoor extends BlockTrapDoor implements ISettingsBlock {

    protected final Settings settings;

    protected BaseBlockTrapDoor(Settings settings) {
        super(settings.material);

        this.settings = settings;

        setTranslationKey(settings.translationKey);
    }
}
