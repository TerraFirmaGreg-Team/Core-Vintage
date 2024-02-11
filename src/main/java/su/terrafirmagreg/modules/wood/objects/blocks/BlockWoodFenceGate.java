package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
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

public class BlockWoodFenceGate extends BlockFenceGate implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodFenceGate(WoodBlockVariant variant, WoodType type) {
        super(BlockPlanks.EnumType.OAK);

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(15.0F);

        Blocks.FIRE.setFireInfo(this, 5, 20);

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
                        .ignore(BlockFenceGate.IN_WALL)
                        .ignore(BlockFenceGate.POWERED)
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "inventory"));
    }
}
