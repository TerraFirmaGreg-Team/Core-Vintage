package su.terrafirmagreg.modules.core.objects.blocks;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;

import org.jetbrains.annotations.NotNull;

public class BlockAggregate extends BlockGravel implements IAutoReg {

    public BlockAggregate() {
        setSoundType(SoundType.SAND);
        setHardness(0.4f);
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "aggregate");
    }

    @Override
    public @NotNull String getName() {
        return "core/aggregate";
    }
}
