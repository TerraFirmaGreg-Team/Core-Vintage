package net.dries007.tfc.module.wood.objects.blocks;

import net.dries007.tfc.module.wood.api.types.type.WoodType;
import net.dries007.tfc.module.wood.api.types.variant.block.WoodBlockVariant;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockWoodFenceLog extends BlockWoodFence {
    public BlockWoodFenceLog(WoodBlockVariant variant, WoodType type) {
        super(variant, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(),
                        "east=" + state.getValue(BlockFence.EAST) + "," +
                                "normal=false" + "," +
                                "north=" + state.getValue(BlockFence.NORTH) + "," +
                                "south=" + state.getValue(BlockFence.SOUTH) + "," +
                                "west=" + state.getValue(BlockFence.WEST) + "," +
                                "woodtype=" + getType());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(),
                        "east=false," +
                                "normal=true," +
                                "north=false," +
                                "south=false," +
                                "west=false," +
                                "woodtype=" + getType()));
    }
}
