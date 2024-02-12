package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;

import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.api.util.ModelRegistrationHelper;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

@Getter
public class BlockWoodBookshelf extends BlockBookshelf implements IWoodBlock, IColorfulBlock {

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    public BlockWoodBookshelf(WoodBlockVariant blockVariant, WoodType type) {
        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        Blocks.FIRE.setFireInfo(this, 30, 20);

        //OreDictionaryHelper.register(this, variant.toString());
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public float getEnchantPowerBonus(@NotNull World world, @NotNull BlockPos pos) {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelRegistrationHelper.registerBlockModel(this, new CustomStateMap.Builder().customPath(getResourceLocation()).build());
        ModelRegistrationHelper.registerItemModel(Item.getItemFromBlock(this), getResourceLocation());
    }

    @Override
    public IBlockColor getColorHandler() {
        return (s, w, p, i) ->  this.getType().getColor();
    }

    @Override
    public IItemColor getItemColorHandler() {
        return (s, i) -> this.getType().getColor();
    }
}
