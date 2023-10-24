package pieman.caffeineaddon.blocks;

import java.util.Random;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves.EnumLeafState;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModBlocks;
import pieman.caffeineaddon.init.ModItems;
import pieman.caffeineaddon.util.IHasModel;

public class BlockCoffeeTreeLeaves extends BlockFruitTreeLeaves implements IHasModel{

	public BlockCoffeeTreeLeaves(IFruitTree tree) {
		super(tree);
		setTranslationKey(tree.getName() + "_leaves");
		setRegistryName(tree.getName() + "_leaves");
		//setCreativeTab(CreativeTabsTFC.CT_WOOD);

		//the super does this but passes itself so forge gives a warning and doesn't register it
        //OreDictionaryHelper.register(this, "tree", "leaves");
        //OreDictionaryHelper.register(this.to, "tree", "leaves", tree.getName());

		ModBlocks.BLOCKS.add(this);
		//ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(tree.getName() + "_leaves"));
	}    

	@Override
	public void registerModels() {
		CaffeineAddon.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random)
    {
    	
        TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
        if (te != null)
        {
            float temp = ClimateTFC.getActualTemp(world, pos);
            float rainfall = ChunkDataTFC.getRainfall(world, pos);
            long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
        	System.out.println("state.getValue(HARVESTABLE) " + state.getValue(HARVESTABLE));
        	System.out.println("tree.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear()) " + tree.isHarvestMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear()));
        	System.out.println("hours > tree.getGrowthTime() " + (hours > tree.getGrowthTime()));
        	System.out.println("tree.isValidForGrowth(temp, rainfall) " + tree.isValidForGrowth(temp, rainfall));
        }
        super.randomTick(world, pos, state, random);
    }
}
