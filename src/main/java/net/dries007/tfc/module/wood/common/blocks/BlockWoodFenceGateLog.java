package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWoodFenceGateLog extends BlockWoodFenceGate {

    public BlockWoodFenceGateLog(WoodBlockVariant variant, WoodType type) {
        super(variant, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .ignore(IN_WALL)
                        .ignore(POWERED)
                        .customVariant("woodtype=" + getType())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(),
                        "facing=south," +
                                "open=false," +
                                "woodtype=" + getType()));
    }
}
