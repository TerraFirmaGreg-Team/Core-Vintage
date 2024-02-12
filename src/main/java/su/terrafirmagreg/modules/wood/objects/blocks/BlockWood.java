package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.api.util.ModelRegistrationHelper;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

@Getter
public abstract class BlockWood extends BlockBase implements IWoodBlock {

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    protected BlockWood(WoodBlockVariant blockVariant, WoodType type) {
        super(Material.WOOD);

        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.WOOD);

        //OreDictionaryHelper.register(this, variant.toString());
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelRegistrationHelper.registerBlockModel(this, new CustomStateMap.Builder().customPath(getResourceLocation()).build());
        ModelRegistrationHelper.registerItemModel(Item.getItemFromBlock(this), this.getResourceLocation().toString());
    }
}
