package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.data.EnumColor;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;


public class BlockAlabaster extends BlockBase implements IItemSize {

    private final RockBlockVariant variant;
    private final EnumColor color;

    public BlockAlabaster(RockBlockVariant variant, EnumColor color) {
        super(Material.ROCK);

        this.variant = variant;
        this.color = color;

        setCreativeTab(ModuleCore.MISC_TAB);
        setSoundType(SoundType.STONE);
        setHardness(1.0F);

        OreDictionaryHelper.register(this, "alabaster");
        OreDictionaryHelper.register(this, "alabaster", variant.toString());
        OreDictionaryHelper.register(this, "alabaster", variant.toString(), color.getName());
    }

	@Override
    public @NotNull String getName() {
        return String.format("rock/alabaster/%s/%s", variant, color.getName());
    }


}
