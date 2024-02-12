package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;

import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariants;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

@Getter
public class BlockWoodStairs extends BlockStairs implements IWoodBlock {

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    public BlockWoodStairs(WoodBlockVariant blockVariant, WoodType type) {
        super(BlocksWood.getBlock(WoodBlockVariants.PLANKS, type).getDefaultState());

        this.blockVariant = blockVariant;
        this.type = type;
        this.useNeighborBrightness = true;
        setHarvestLevel("axe", 0);

        Blocks.FIRE.setFireInfo(this, 5, 20);

//            OreDictionaryHelper.register(this, variant.toString());
//            OreDictionaryHelper.register(this, variant.toString(), "wood");
//            OreDictionaryHelper.register(this, variant.toString(), "wood", type.toString());
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "inventory"));
    }
}
