package net.dries007.tfc.common.objects.blocks.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
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
                        "east=" + state.getValue(EAST) + "," +
                                "normal=false" + "," +
                                "north=" + state.getValue(NORTH) + "," +
                                "south=" + state.getValue(SOUTH) + "," +
                                "type=" + getType() + "," +
                                "west=" + state.getValue(WEST));
            }
        });

        for (var state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(),
                            "east=false," +
                                    "normal=true," +
                                    "north=false," +
                                    "south=false," +
                                    "type=" + getType() + "," +
                                    "west=false"));
        }
    }
}
