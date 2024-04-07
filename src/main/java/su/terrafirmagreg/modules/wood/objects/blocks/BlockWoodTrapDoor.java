package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

import lombok.Getter;

import org.jetbrains.annotations.Nullable;

@Getter
public class BlockWoodTrapDoor extends BlockTrapDoor implements IWoodBlock {

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    public BlockWoodTrapDoor(WoodBlockVariant blockVariant, WoodType type) {
        super(Material.WOOD);
        this.blockVariant = blockVariant;
        this.type = type;

        setHardness(0.5F);
        setSoundType(SoundType.WOOD);

        //OreDictUtils.register(this, variant.toString());
        //OreDictUtils.register(this, variant.toString(), type.toString());
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }
}
