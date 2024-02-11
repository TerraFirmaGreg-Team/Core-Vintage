package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.IHasModel;
import su.terrafirmagreg.api.registry.RegistryModel;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public abstract class BlockWood extends BlockBase implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    protected BlockWood(WoodBlockVariant variant, WoodType type) {
        super(Material.WOOD);

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);

        //OreDictionaryHelper.register(this, variant.toString());
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Override
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        RegistryModel.registerBlockModel(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .build());

        RegistryModel.registerItemModel(Item.getItemFromBlock(this), getResourceLocation().toString());
    }
}
