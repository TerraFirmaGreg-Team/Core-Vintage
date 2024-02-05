package su.terrafirmagreg.modules.soil.objects.blocks.peat;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.objects.block.BlockBase;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.api.util.IHasModel;


public class BlockPeat extends BlockBase  {

    public BlockPeat() {
        super(Material.GROUND);

        setSoundType(SoundType.GROUND);
        setHardness(0.6F);
        setHarvestLevel("shovel", 0);

        OreDictionaryHelper.register(this, getName());
        Blocks.FIRE.setFireInfo(this, 5, 10);

        //DirtHelper.registerSoil(this.getDefaultState().getBlock(), DirtHelper.GRAVELLIKE);
    }

    @Override
    public @NotNull String getName() {
        return "soil/peat";
    }
}
