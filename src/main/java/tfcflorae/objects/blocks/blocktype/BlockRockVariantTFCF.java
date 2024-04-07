package tfcflorae.objects.blocks.blocktype;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemRock;
import tfcflorae.objects.blocks.blocktype.farmland.BlockHumusFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockLoamFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockLoamySandFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockSandyLoamFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockSiltFarmland;
import tfcflorae.objects.blocks.blocktype.farmland.BlockSiltLoamFarmland;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

@MethodsReturnNonnullByDefault

public class BlockRockVariantTFCF extends Block implements IItemSize {

    private static final Map<RockTFCF, BlockRockVariantTFCF> TABLETFCF = new HashMap<>();
    private static final Map<Rock, EnumMap<RockTFCF, BlockRockVariantTFCF>> TABLE = new HashMap<>();
    protected final RockTFCF rockTFCF;
    protected final Rock rock;

    public BlockRockVariantTFCF(RockTFCF rockTFCF, Rock rock) {
        super(rockTFCF.material);

        if (rock != null) {
            if (!TABLE.containsKey(rock)) {
                TABLE.put(rock, new EnumMap<>(RockTFCF.class));
            }
            TABLE.get(rock).put(rockTFCF, this);
        } else
            TABLETFCF.put(rockTFCF, this);

        this.rockTFCF = rockTFCF;
        this.rock = rock;
        if (rockTFCF.isGrass) setTickRandomly(true);
        switch (rockTFCF) {
            case MOSSY_RAW:
            case MUD_BRICKS:
                setSoundType(SoundType.STONE);
                setHardness(rock.getRockCategory().getHardness()).setResistance(rock.getRockCategory().getResistance());
                setHarvestLevel("pickaxe", 0);
                break;
            case COARSE_DIRT:
            case MUD:
                setSoundType(SoundType.GROUND);
                setHardness(rock.getRockCategory().getHardness() * 0.15F);
                setHarvestLevel("shovel", 0);
                break;
            case LOAMY_SAND:
            case SANDY_LOAM:
            case LOAM:
            case SILT_LOAM:
            case SILT:
            case HUMUS:
            case LOAMY_SAND_FARMLAND:
            case SANDY_LOAM_FARMLAND:
            case LOAM_FARMLAND:
            case SILT_LOAM_FARMLAND:
            case SILT_FARMLAND:
            case HUMUS_FARMLAND:
                setSoundType(SoundType.GROUND);
                setHardness(1.5F * 0.15F);
                setHarvestLevel("shovel", 0);
                break;
            case COARSE_CLAY:
            case EARTHENWARE_CLAY:
            case COARSE_EARTHENWARE_CLAY:
            case KAOLINITE_CLAY:
            case COARSE_KAOLINITE_CLAY:
            case STONEWARE_CLAY:
            case COARSE_STONEWARE_CLAY:
                setSoundType(SoundType.GROUND);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
            case BOG_IRON:
            case SANDY_CLAY_LOAM:
            case SANDY_CLAY:
            case CLAY_LOAM:
            case SILTY_CLAY_LOAM:
            case SILTY_CLAY:
            case COARSE_LOAMY_SAND:
            case COARSE_SANDY_LOAM:
            case COARSE_SANDY_CLAY_LOAM:
            case COARSE_SANDY_CLAY:
            case COARSE_LOAM:
            case COARSE_CLAY_LOAM:
            case COARSE_SILTY_CLAY:
            case COARSE_SILTY_CLAY_LOAM:
            case COARSE_SILT_LOAM:
            case COARSE_SILT:
            case COARSE_HUMUS:
            case COARSE_CLAY_HUMUS:
            case CLAY_HUMUS:
            case SANDY_EARTHENWARE_CLAY_LOAM:
            case COARSE_SANDY_EARTHENWARE_CLAY_LOAM:
            case SANDY_EARTHENWARE_CLAY:
            case COARSE_SANDY_EARTHENWARE_CLAY:
            case EARTHENWARE_CLAY_LOAM:
            case COARSE_EARTHENWARE_CLAY_LOAM:
            case SILTY_EARTHENWARE_CLAY:
            case COARSE_SILTY_EARTHENWARE_CLAY:
            case SILTY_EARTHENWARE_CLAY_LOAM:
            case COARSE_SILTY_EARTHENWARE_CLAY_LOAM:
            case EARTHENWARE_CLAY_HUMUS:
            case COARSE_EARTHENWARE_CLAY_HUMUS:
            case SANDY_KAOLINITE_CLAY_LOAM:
            case COARSE_SANDY_KAOLINITE_CLAY_LOAM:
            case SANDY_KAOLINITE_CLAY:
            case COARSE_SANDY_KAOLINITE_CLAY:
            case KAOLINITE_CLAY_LOAM:
            case COARSE_KAOLINITE_CLAY_LOAM:
            case SILTY_KAOLINITE_CLAY:
            case COARSE_SILTY_KAOLINITE_CLAY:
            case SILTY_KAOLINITE_CLAY_LOAM:
            case COARSE_SILTY_KAOLINITE_CLAY_LOAM:
            case KAOLINITE_CLAY_HUMUS:
            case COARSE_KAOLINITE_CLAY_HUMUS:
            case SANDY_STONEWARE_CLAY_LOAM:
            case COARSE_SANDY_STONEWARE_CLAY_LOAM:
            case SANDY_STONEWARE_CLAY:
            case COARSE_SANDY_STONEWARE_CLAY:
            case STONEWARE_CLAY_LOAM:
            case COARSE_STONEWARE_CLAY_LOAM:
            case SILTY_STONEWARE_CLAY:
            case COARSE_SILTY_STONEWARE_CLAY:
            case SILTY_STONEWARE_CLAY_LOAM:
            case COARSE_SILTY_STONEWARE_CLAY_LOAM:
            case STONEWARE_CLAY_HUMUS:
            case COARSE_STONEWARE_CLAY_HUMUS:
                setSoundType(SoundType.GROUND);
                setHardness(1.5F * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
            case PODZOL:
            case CLAY_PODZOL:
            case DRY_CLAY_GRASS:
            case EARTHENWARE_CLAY_GRASS:
            case EARTHENWARE_CLAY_PODZOL:
            case DRY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_EARTHENWARE_CLAY_GRASS:
            case KAOLINITE_CLAY_GRASS:
            case KAOLINITE_CLAY_PODZOL:
            case DRY_KAOLINITE_CLAY_GRASS:
            case SPARSE_KAOLINITE_CLAY_GRASS:
            case STONEWARE_CLAY_GRASS:
            case STONEWARE_CLAY_PODZOL:
            case DRY_STONEWARE_CLAY_GRASS:
            case SPARSE_STONEWARE_CLAY_GRASS:
            case SPARSE_GRASS:
            case SPARSE_CLAY_GRASS:
                setSoundType(SoundType.PLANT);
                setHardness(rock.getRockCategory().getHardness() * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
            case BOG_IRON_GRASS:
            case DRY_BOG_IRON_GRASS:
            case SPARSE_BOG_IRON_GRASS:
            case BOG_IRON_PODZOL:
            case LOAMY_SAND_GRASS:
            case LOAMY_SAND_PODZOL:
            case SANDY_LOAM_GRASS:
            case SANDY_LOAM_PODZOL:
            case SANDY_CLAY_LOAM_GRASS:
            case SANDY_CLAY_LOAM_PODZOL:
            case SANDY_CLAY_GRASS:
            case SANDY_CLAY_PODZOL:
            case LOAM_GRASS:
            case LOAM_PODZOL:
            case CLAY_LOAM_GRASS:
            case CLAY_LOAM_PODZOL:
            case SILTY_CLAY_GRASS:
            case SILTY_CLAY_PODZOL:
            case SILTY_CLAY_LOAM_GRASS:
            case SILTY_CLAY_LOAM_PODZOL:
            case SILT_LOAM_GRASS:
            case SILT_LOAM_PODZOL:
            case SILT_GRASS:
            case SILT_PODZOL:
            case DRY_LOAMY_SAND_GRASS:
            case DRY_SANDY_LOAM_GRASS:
            case DRY_SANDY_CLAY_LOAM_GRASS:
            case DRY_SANDY_CLAY_GRASS:
            case DRY_LOAM_GRASS:
            case DRY_CLAY_LOAM_GRASS:
            case DRY_SILTY_CLAY_GRASS:
            case DRY_SILTY_CLAY_LOAM_GRASS:
            case DRY_SILT_LOAM_GRASS:
            case DRY_SILT_GRASS:
            case HUMUS_GRASS:
            case DRY_HUMUS_GRASS:
            case CLAY_HUMUS_GRASS:
            case DRY_CLAY_HUMUS_GRASS:
            case SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SANDY_EARTHENWARE_CLAY_LOAM_PODZOL:
            case SANDY_EARTHENWARE_CLAY_GRASS:
            case SANDY_EARTHENWARE_CLAY_PODZOL:
            case EARTHENWARE_CLAY_LOAM_GRASS:
            case EARTHENWARE_CLAY_LOAM_PODZOL:
            case SILTY_EARTHENWARE_CLAY_GRASS:
            case SILTY_EARTHENWARE_CLAY_PODZOL:
            case SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SILTY_EARTHENWARE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case DRY_SANDY_EARTHENWARE_CLAY_GRASS:
            case DRY_EARTHENWARE_CLAY_LOAM_GRASS:
            case DRY_SILTY_EARTHENWARE_CLAY_GRASS:
            case DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case EARTHENWARE_CLAY_HUMUS_GRASS:
            case DRY_EARTHENWARE_CLAY_HUMUS_GRASS:
            case SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case SANDY_KAOLINITE_CLAY_LOAM_PODZOL:
            case SANDY_KAOLINITE_CLAY_GRASS:
            case SANDY_KAOLINITE_CLAY_PODZOL:
            case KAOLINITE_CLAY_LOAM_GRASS:
            case KAOLINITE_CLAY_LOAM_PODZOL:
            case SILTY_KAOLINITE_CLAY_GRASS:
            case SILTY_KAOLINITE_CLAY_PODZOL:
            case SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case SILTY_KAOLINITE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case DRY_SANDY_KAOLINITE_CLAY_GRASS:
            case DRY_KAOLINITE_CLAY_LOAM_GRASS:
            case DRY_SILTY_KAOLINITE_CLAY_GRASS:
            case DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case KAOLINITE_CLAY_HUMUS_GRASS:
            case DRY_KAOLINITE_CLAY_HUMUS_GRASS:
            case SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case SANDY_STONEWARE_CLAY_LOAM_PODZOL:
            case SANDY_STONEWARE_CLAY_GRASS:
            case SANDY_STONEWARE_CLAY_PODZOL:
            case STONEWARE_CLAY_LOAM_GRASS:
            case STONEWARE_CLAY_LOAM_PODZOL:
            case SILTY_STONEWARE_CLAY_GRASS:
            case SILTY_STONEWARE_CLAY_PODZOL:
            case SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case SILTY_STONEWARE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case DRY_SANDY_STONEWARE_CLAY_GRASS:
            case DRY_STONEWARE_CLAY_LOAM_GRASS:
            case DRY_SILTY_STONEWARE_CLAY_GRASS:
            case DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case STONEWARE_CLAY_HUMUS_GRASS:
            case DRY_STONEWARE_CLAY_HUMUS_GRASS:
            case SPARSE_LOAMY_SAND_GRASS:
            case SPARSE_SANDY_LOAM_GRASS:
            case SPARSE_SANDY_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_CLAY_GRASS:
            case SPARSE_LOAM_GRASS:
            case SPARSE_CLAY_LOAM_GRASS:
            case SPARSE_SILTY_CLAY_GRASS:
            case SPARSE_SILTY_CLAY_LOAM_GRASS:
            case SPARSE_SILT_LOAM_GRASS:
            case SPARSE_SILT_GRASS:
            case SPARSE_HUMUS_GRASS:
            case SPARSE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_SILTY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_KAOLINITE_CLAY_GRASS:
            case SPARSE_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_SILTY_KAOLINITE_CLAY_GRASS:
            case SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_KAOLINITE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_STONEWARE_CLAY_GRASS:
            case SPARSE_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_SILTY_STONEWARE_CLAY_GRASS:
            case SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_STONEWARE_CLAY_HUMUS_GRASS:
                setSoundType(SoundType.PLANT);
                setHardness(1.5F * 0.2F);
                setHarvestLevel("shovel", 0);
                break;
        }
        //if (rockTFCF != Rock.Type.SPIKE && rockTFCF != Rock.Type.ANVIL) //since spikes and anvils don't generate ItemBlocks
        {
            if (rock != null)
                OreDictionaryHelper.registerRockType(this, rockTFCF, rock);
            else
                OreDictionaryHelper.registerRockType(this, rockTFCF);
        }
    }

    public static BlockRockVariantTFCF get(Rock rock, RockTFCF rockTFCF) {
        if (TABLE.containsKey(rock) && TABLE.get(rock).containsKey(rockTFCF))
            return TABLE.get(rock).get(rockTFCF);
        else if (TABLETFCF.containsKey(rockTFCF))
            return TABLETFCF.get(rockTFCF);

        return TABLE.get(Rock.GRANITE).get(RockTFCF.MUD);
    }

    public static BlockRockVariantTFCF create(Rock rock, RockTFCF rockTFCF) {
        return switch (rockTFCF) {
            case MOSSY_RAW -> new BlockRockRawTFCF(rockTFCF, rock);
            case MUD -> new BlockRockMud(rockTFCF, rock);
            case COARSE_DIRT, BOG_IRON, BOG_IRON_GRASS, DRY_BOG_IRON_GRASS, SPARSE_BOG_IRON_GRASS, BOG_IRON_PODZOL, LOAMY_SAND, COARSE_LOAMY_SAND,
                 SANDY_LOAM,
                 COARSE_SANDY_LOAM, SANDY_CLAY_LOAM, COARSE_SANDY_CLAY_LOAM, SANDY_CLAY, COARSE_SANDY_CLAY, LOAM, COARSE_LOAM, CLAY_LOAM,
                 COARSE_CLAY_LOAM,
                 COARSE_CLAY, SILTY_CLAY, COARSE_SILTY_CLAY, SILTY_CLAY_LOAM, COARSE_SILTY_CLAY_LOAM, SILT_LOAM, COARSE_SILT_LOAM, SILT, COARSE_SILT,
                 HUMUS,
                 COARSE_HUMUS, CLAY_HUMUS, COARSE_CLAY_HUMUS, EARTHENWARE_CLAY, SANDY_EARTHENWARE_CLAY_LOAM, COARSE_SANDY_EARTHENWARE_CLAY_LOAM,
                 SANDY_EARTHENWARE_CLAY, COARSE_SANDY_EARTHENWARE_CLAY, EARTHENWARE_CLAY_LOAM, COARSE_EARTHENWARE_CLAY_LOAM, COARSE_EARTHENWARE_CLAY,
                 SILTY_EARTHENWARE_CLAY, COARSE_SILTY_EARTHENWARE_CLAY, SILTY_EARTHENWARE_CLAY_LOAM, COARSE_SILTY_EARTHENWARE_CLAY_LOAM,
                 EARTHENWARE_CLAY_HUMUS,
                 COARSE_EARTHENWARE_CLAY_HUMUS, KAOLINITE_CLAY, SANDY_KAOLINITE_CLAY_LOAM, COARSE_SANDY_KAOLINITE_CLAY_LOAM, SANDY_KAOLINITE_CLAY,
                 COARSE_SANDY_KAOLINITE_CLAY, KAOLINITE_CLAY_LOAM, COARSE_KAOLINITE_CLAY_LOAM, COARSE_KAOLINITE_CLAY, SILTY_KAOLINITE_CLAY,
                 COARSE_SILTY_KAOLINITE_CLAY, SILTY_KAOLINITE_CLAY_LOAM, COARSE_SILTY_KAOLINITE_CLAY_LOAM, KAOLINITE_CLAY_HUMUS,
                 COARSE_KAOLINITE_CLAY_HUMUS,
                 STONEWARE_CLAY, SANDY_STONEWARE_CLAY_LOAM, COARSE_SANDY_STONEWARE_CLAY_LOAM, SANDY_STONEWARE_CLAY, COARSE_SANDY_STONEWARE_CLAY,
                 STONEWARE_CLAY_LOAM, COARSE_STONEWARE_CLAY_LOAM, COARSE_STONEWARE_CLAY, SILTY_STONEWARE_CLAY, COARSE_SILTY_STONEWARE_CLAY,
                 SILTY_STONEWARE_CLAY_LOAM, COARSE_SILTY_STONEWARE_CLAY_LOAM, STONEWARE_CLAY_HUMUS, COARSE_STONEWARE_CLAY_HUMUS ->
                    new BlockRockVariantFallableTFCF(rockTFCF, rock);
            case PODZOL, LOAMY_SAND_GRASS, LOAMY_SAND_PODZOL, SANDY_LOAM_GRASS, SANDY_LOAM_PODZOL, SANDY_CLAY_LOAM_GRASS, SANDY_CLAY_LOAM_PODZOL,
                 SANDY_CLAY_GRASS, SANDY_CLAY_PODZOL, LOAM_GRASS, LOAM_PODZOL, CLAY_LOAM_GRASS, CLAY_LOAM_PODZOL, CLAY_PODZOL, SILTY_CLAY_GRASS,
                 SILTY_CLAY_PODZOL, SILTY_CLAY_LOAM_GRASS, SILTY_CLAY_LOAM_PODZOL, SILT_LOAM_GRASS, SILT_LOAM_PODZOL, SILT_GRASS, SILT_PODZOL,
                 DRY_LOAMY_SAND_GRASS, DRY_SANDY_LOAM_GRASS, DRY_SANDY_CLAY_LOAM_GRASS, DRY_SANDY_CLAY_GRASS, DRY_LOAM_GRASS, DRY_CLAY_LOAM_GRASS,
                 DRY_CLAY_GRASS, DRY_SILTY_CLAY_GRASS, DRY_SILTY_CLAY_LOAM_GRASS, DRY_SILT_LOAM_GRASS, DRY_SILT_GRASS, HUMUS_GRASS, DRY_HUMUS_GRASS,
                 CLAY_HUMUS_GRASS, DRY_CLAY_HUMUS_GRASS, EARTHENWARE_CLAY_GRASS, SANDY_EARTHENWARE_CLAY_LOAM_GRASS,
                 SANDY_EARTHENWARE_CLAY_LOAM_PODZOL,
                 SANDY_EARTHENWARE_CLAY_GRASS, SANDY_EARTHENWARE_CLAY_PODZOL, EARTHENWARE_CLAY_LOAM_GRASS, EARTHENWARE_CLAY_LOAM_PODZOL,
                 EARTHENWARE_CLAY_PODZOL, SILTY_EARTHENWARE_CLAY_GRASS, SILTY_EARTHENWARE_CLAY_PODZOL, SILTY_EARTHENWARE_CLAY_LOAM_GRASS,
                 SILTY_EARTHENWARE_CLAY_LOAM_PODZOL, DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS, DRY_SANDY_EARTHENWARE_CLAY_GRASS,
                 DRY_EARTHENWARE_CLAY_LOAM_GRASS,
                 DRY_EARTHENWARE_CLAY_GRASS, DRY_SILTY_EARTHENWARE_CLAY_GRASS, DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS, EARTHENWARE_CLAY_HUMUS_GRASS,
                 DRY_EARTHENWARE_CLAY_HUMUS_GRASS, KAOLINITE_CLAY_GRASS, SANDY_KAOLINITE_CLAY_LOAM_GRASS, SANDY_KAOLINITE_CLAY_LOAM_PODZOL,
                 SANDY_KAOLINITE_CLAY_GRASS, SANDY_KAOLINITE_CLAY_PODZOL, KAOLINITE_CLAY_LOAM_GRASS, KAOLINITE_CLAY_LOAM_PODZOL,
                 KAOLINITE_CLAY_PODZOL,
                 SILTY_KAOLINITE_CLAY_GRASS, SILTY_KAOLINITE_CLAY_PODZOL, SILTY_KAOLINITE_CLAY_LOAM_GRASS, SILTY_KAOLINITE_CLAY_LOAM_PODZOL,
                 DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS, DRY_SANDY_KAOLINITE_CLAY_GRASS, DRY_KAOLINITE_CLAY_LOAM_GRASS, DRY_KAOLINITE_CLAY_GRASS,
                 DRY_SILTY_KAOLINITE_CLAY_GRASS, DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS, KAOLINITE_CLAY_HUMUS_GRASS, DRY_KAOLINITE_CLAY_HUMUS_GRASS,
                 STONEWARE_CLAY_GRASS, SANDY_STONEWARE_CLAY_LOAM_GRASS, SANDY_STONEWARE_CLAY_LOAM_PODZOL, SANDY_STONEWARE_CLAY_GRASS,
                 SANDY_STONEWARE_CLAY_PODZOL, STONEWARE_CLAY_LOAM_GRASS, STONEWARE_CLAY_LOAM_PODZOL, STONEWARE_CLAY_PODZOL,
                 SILTY_STONEWARE_CLAY_GRASS,
                 SILTY_STONEWARE_CLAY_PODZOL, SILTY_STONEWARE_CLAY_LOAM_GRASS, SILTY_STONEWARE_CLAY_LOAM_PODZOL, DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS,
                 DRY_SANDY_STONEWARE_CLAY_GRASS, DRY_STONEWARE_CLAY_LOAM_GRASS, DRY_STONEWARE_CLAY_GRASS, DRY_SILTY_STONEWARE_CLAY_GRASS,
                 DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS, STONEWARE_CLAY_HUMUS_GRASS, DRY_STONEWARE_CLAY_HUMUS_GRASS, SPARSE_GRASS, SPARSE_CLAY_GRASS,
                 SPARSE_LOAMY_SAND_GRASS, SPARSE_SANDY_LOAM_GRASS, SPARSE_SANDY_CLAY_LOAM_GRASS, SPARSE_SANDY_CLAY_GRASS, SPARSE_LOAM_GRASS,
                 SPARSE_CLAY_LOAM_GRASS, SPARSE_SILTY_CLAY_GRASS, SPARSE_SILTY_CLAY_LOAM_GRASS, SPARSE_SILT_LOAM_GRASS, SPARSE_SILT_GRASS,
                 SPARSE_HUMUS_GRASS,
                 SPARSE_CLAY_HUMUS_GRASS, SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS, SPARSE_SANDY_EARTHENWARE_CLAY_GRASS,
                 SPARSE_EARTHENWARE_CLAY_LOAM_GRASS,
                 SPARSE_EARTHENWARE_CLAY_GRASS, SPARSE_SILTY_EARTHENWARE_CLAY_GRASS, SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS,
                 SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS, SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS, SPARSE_SANDY_KAOLINITE_CLAY_GRASS,
                 SPARSE_KAOLINITE_CLAY_LOAM_GRASS, SPARSE_KAOLINITE_CLAY_GRASS, SPARSE_SILTY_KAOLINITE_CLAY_GRASS,
                 SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS,
                 SPARSE_KAOLINITE_CLAY_HUMUS_GRASS, SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS, SPARSE_SANDY_STONEWARE_CLAY_GRASS,
                 SPARSE_STONEWARE_CLAY_LOAM_GRASS,
                 SPARSE_STONEWARE_CLAY_GRASS, SPARSE_SILTY_STONEWARE_CLAY_GRASS, SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS,
                 SPARSE_STONEWARE_CLAY_HUMUS_GRASS -> new BlockRockVariantConnectedTFCF(rockTFCF, rock);
            case LOAMY_SAND_FARMLAND -> new BlockLoamySandFarmland(rockTFCF, rock);
            case SANDY_LOAM_FARMLAND -> new BlockSandyLoamFarmland(rockTFCF, rock);
            case LOAM_FARMLAND -> new BlockLoamFarmland(rockTFCF, rock);
            case SILT_LOAM_FARMLAND -> new BlockSiltLoamFarmland(rockTFCF, rock);
            case SILT_FARMLAND -> new BlockSiltFarmland(rockTFCF, rock);
            case HUMUS_FARMLAND -> new BlockHumusFarmland(rockTFCF, rock);
            default -> new BlockRockVariantTFCF(rockTFCF, rock);
        };
    }

    public RockTFCF getRockType() {
        return rockTFCF;
    }

    public BlockRockVariantTFCF getVariant(RockTFCF t) {
        if (rock == null)
            return TABLETFCF.get(t);
        return TABLE.get(rock).get(t);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        switch (this.rockTFCF) {
            case LOAMY_SAND_FARMLAND:
            case SANDY_LOAM_FARMLAND:
            case LOAM_FARMLAND:
            case SILT_LOAM_FARMLAND:
            case SILT_FARMLAND:
            case HUMUS_FARMLAND:
                switch (side) {
                    case UP:
                        return true;
                    case NORTH:
                    case SOUTH:
                    case WEST:
                    case EAST:
                        IBlockState state = world.getBlockState(pos.offset(side));
                        Block block = state.getBlock();
                        if (state.isOpaqueCube()) return false;
                        if (block instanceof BlockLoamySandFarmland || block instanceof BlockSandyLoamFarmland ||
                                block instanceof BlockLoamFarmland || block instanceof BlockSiltLoamFarmland || block instanceof BlockSiltFarmland ||
                                block instanceof BlockHumusFarmland)
                            return false;
                        if (block instanceof BlockRockVariantTFCF) {
                            switch (((BlockRockVariantTFCF) block).rockTFCF) {
                                case LOAMY_SAND_FARMLAND:
                                case SANDY_LOAM_FARMLAND:
                                case LOAM_FARMLAND:
                                case SILT_LOAM_FARMLAND:
                                case SILT_FARMLAND:
                                case HUMUS_FARMLAND:
                                    return false;
                                default:
                                    return true;
                            }
                        }
                        return true;
                    case DOWN:
                        return super.shouldSideBeRendered(blockState, world, pos, side);
                }
            default:
                return super.shouldSideBeRendered(blockState, world, pos, side);
        }
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) return;
        if (rockTFCF.isGrass) BlockRockVariantConnectedTFCF.spreadGrass(world, pos, state, rand);
        super.randomTick(world, pos, state, rand);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return switch (rockTFCF) {
            case MOSSY_RAW -> ItemRock.get(rock);
            case MUD -> ItemMud.get(rock);
            case BOG_IRON, BOG_IRON_GRASS, DRY_BOG_IRON_GRASS, SPARSE_BOG_IRON_GRASS, BOG_IRON_PODZOL -> ItemSmallOre.get(Ore.LIMONITE);
            case SPARSE_LOAMY_SAND_GRASS, DRY_LOAMY_SAND_GRASS, LOAMY_SAND_GRASS, LOAMY_SAND_PODZOL ->
                    Item.getItemFromBlock(get(null, RockTFCF.LOAMY_SAND));
            case SPARSE_SANDY_LOAM_GRASS, DRY_SANDY_LOAM_GRASS, SANDY_LOAM_GRASS, SANDY_LOAM_PODZOL ->
                    Item.getItemFromBlock(get(null, RockTFCF.SANDY_LOAM));
            case SPARSE_LOAM_GRASS, DRY_LOAM_GRASS, LOAM_GRASS, LOAM_PODZOL -> Item.getItemFromBlock(get(null, RockTFCF.LOAM));
            case SPARSE_SILT_LOAM_GRASS, DRY_SILT_LOAM_GRASS, SILT_LOAM_GRASS, SILT_LOAM_PODZOL ->
                    Item.getItemFromBlock(get(null, RockTFCF.SILT_LOAM));
            case SPARSE_SILT_GRASS, DRY_SILT_GRASS, SILT_GRASS, SILT_PODZOL -> Item.getItemFromBlock(get(null, RockTFCF.SILT));
            case SPARSE_HUMUS_GRASS, HUMUS, HUMUS_GRASS, DRY_HUMUS_GRASS -> Item.getItemFromBlock(get(null, RockTFCF.HUMUS));
            case SANDY_CLAY_LOAM, SANDY_CLAY, CLAY_LOAM, SILTY_CLAY_LOAM, SILTY_CLAY, COARSE_SANDY_CLAY_LOAM, COARSE_SANDY_CLAY, COARSE_CLAY_LOAM,
                 COARSE_CLAY,
                 COARSE_SILTY_CLAY, COARSE_SILTY_CLAY_LOAM, COARSE_CLAY_HUMUS, SANDY_CLAY_LOAM_GRASS, SANDY_CLAY_LOAM_PODZOL, SANDY_CLAY_GRASS,
                 SANDY_CLAY_PODZOL, CLAY_LOAM_GRASS, CLAY_LOAM_PODZOL, CLAY_PODZOL, SILTY_CLAY_GRASS, SILTY_CLAY_PODZOL, SILTY_CLAY_LOAM_GRASS,
                 SILTY_CLAY_LOAM_PODZOL, DRY_SANDY_CLAY_LOAM_GRASS, DRY_SANDY_CLAY_GRASS, DRY_CLAY_LOAM_GRASS, DRY_CLAY_GRASS, DRY_SILTY_CLAY_GRASS,
                 DRY_SILTY_CLAY_LOAM_GRASS, CLAY_HUMUS, CLAY_HUMUS_GRASS, DRY_CLAY_HUMUS_GRASS, SPARSE_SANDY_CLAY_LOAM_GRASS, SPARSE_SANDY_CLAY_GRASS,
                 SPARSE_CLAY_LOAM_GRASS, SPARSE_SILTY_CLAY_GRASS, SPARSE_SILTY_CLAY_LOAM_GRASS, SPARSE_CLAY_HUMUS_GRASS, SPARSE_CLAY_GRASS ->
                    Items.CLAY_BALL;
            case EARTHENWARE_CLAY, SANDY_EARTHENWARE_CLAY_LOAM, COARSE_SANDY_EARTHENWARE_CLAY_LOAM, SANDY_EARTHENWARE_CLAY,
                 COARSE_SANDY_EARTHENWARE_CLAY,
                 EARTHENWARE_CLAY_LOAM, COARSE_EARTHENWARE_CLAY_LOAM, COARSE_EARTHENWARE_CLAY, SILTY_EARTHENWARE_CLAY, COARSE_SILTY_EARTHENWARE_CLAY,
                 SILTY_EARTHENWARE_CLAY_LOAM, COARSE_SILTY_EARTHENWARE_CLAY_LOAM, EARTHENWARE_CLAY_HUMUS, COARSE_EARTHENWARE_CLAY_HUMUS,
                 EARTHENWARE_CLAY_GRASS,
                 SANDY_EARTHENWARE_CLAY_LOAM_GRASS, SANDY_EARTHENWARE_CLAY_LOAM_PODZOL, SANDY_EARTHENWARE_CLAY_GRASS, SANDY_EARTHENWARE_CLAY_PODZOL,
                 EARTHENWARE_CLAY_LOAM_GRASS, EARTHENWARE_CLAY_LOAM_PODZOL, EARTHENWARE_CLAY_PODZOL, SILTY_EARTHENWARE_CLAY_GRASS,
                 SILTY_EARTHENWARE_CLAY_PODZOL, SILTY_EARTHENWARE_CLAY_LOAM_GRASS, SILTY_EARTHENWARE_CLAY_LOAM_PODZOL,
                 DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS,
                 DRY_SANDY_EARTHENWARE_CLAY_GRASS, DRY_EARTHENWARE_CLAY_LOAM_GRASS, DRY_EARTHENWARE_CLAY_GRASS, DRY_SILTY_EARTHENWARE_CLAY_GRASS,
                 DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS, EARTHENWARE_CLAY_HUMUS_GRASS, DRY_EARTHENWARE_CLAY_HUMUS_GRASS,
                 SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS, SPARSE_SANDY_EARTHENWARE_CLAY_GRASS, SPARSE_EARTHENWARE_CLAY_LOAM_GRASS,
                 SPARSE_EARTHENWARE_CLAY_GRASS, SPARSE_SILTY_EARTHENWARE_CLAY_GRASS, SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS,
                 SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS -> ItemsTFCF.EARTHENWARE_CLAY;
            case KAOLINITE_CLAY, SANDY_KAOLINITE_CLAY_LOAM, COARSE_SANDY_KAOLINITE_CLAY_LOAM, SANDY_KAOLINITE_CLAY, COARSE_SANDY_KAOLINITE_CLAY,
                 KAOLINITE_CLAY_LOAM, COARSE_KAOLINITE_CLAY_LOAM, COARSE_KAOLINITE_CLAY, SILTY_KAOLINITE_CLAY, COARSE_SILTY_KAOLINITE_CLAY,
                 SILTY_KAOLINITE_CLAY_LOAM, COARSE_SILTY_KAOLINITE_CLAY_LOAM, KAOLINITE_CLAY_HUMUS, COARSE_KAOLINITE_CLAY_HUMUS, KAOLINITE_CLAY_GRASS,
                 SANDY_KAOLINITE_CLAY_LOAM_GRASS, SANDY_KAOLINITE_CLAY_LOAM_PODZOL, SANDY_KAOLINITE_CLAY_GRASS, SANDY_KAOLINITE_CLAY_PODZOL,
                 KAOLINITE_CLAY_LOAM_GRASS, KAOLINITE_CLAY_LOAM_PODZOL, KAOLINITE_CLAY_PODZOL, SILTY_KAOLINITE_CLAY_GRASS,
                 SILTY_KAOLINITE_CLAY_PODZOL,
                 SILTY_KAOLINITE_CLAY_LOAM_GRASS, SILTY_KAOLINITE_CLAY_LOAM_PODZOL, DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS,
                 DRY_SANDY_KAOLINITE_CLAY_GRASS,
                 DRY_KAOLINITE_CLAY_LOAM_GRASS, DRY_KAOLINITE_CLAY_GRASS, DRY_SILTY_KAOLINITE_CLAY_GRASS, DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS,
                 KAOLINITE_CLAY_HUMUS_GRASS, DRY_KAOLINITE_CLAY_HUMUS_GRASS, SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS,
                 SPARSE_SANDY_KAOLINITE_CLAY_GRASS,
                 SPARSE_KAOLINITE_CLAY_LOAM_GRASS, SPARSE_KAOLINITE_CLAY_GRASS, SPARSE_SILTY_KAOLINITE_CLAY_GRASS,
                 SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS,
                 SPARSE_KAOLINITE_CLAY_HUMUS_GRASS -> ItemsTFCF.KAOLINITE_CLAY;
            case STONEWARE_CLAY, SANDY_STONEWARE_CLAY_LOAM, COARSE_SANDY_STONEWARE_CLAY_LOAM, SANDY_STONEWARE_CLAY, COARSE_SANDY_STONEWARE_CLAY,
                 STONEWARE_CLAY_LOAM, COARSE_STONEWARE_CLAY_LOAM, COARSE_STONEWARE_CLAY, SILTY_STONEWARE_CLAY, COARSE_SILTY_STONEWARE_CLAY,
                 SILTY_STONEWARE_CLAY_LOAM, COARSE_SILTY_STONEWARE_CLAY_LOAM, STONEWARE_CLAY_HUMUS, COARSE_STONEWARE_CLAY_HUMUS, STONEWARE_CLAY_GRASS,
                 SANDY_STONEWARE_CLAY_LOAM_GRASS, SANDY_STONEWARE_CLAY_LOAM_PODZOL, SANDY_STONEWARE_CLAY_GRASS, SANDY_STONEWARE_CLAY_PODZOL,
                 STONEWARE_CLAY_LOAM_GRASS, STONEWARE_CLAY_LOAM_PODZOL, STONEWARE_CLAY_PODZOL, SILTY_STONEWARE_CLAY_GRASS,
                 SILTY_STONEWARE_CLAY_PODZOL,
                 SILTY_STONEWARE_CLAY_LOAM_GRASS, SILTY_STONEWARE_CLAY_LOAM_PODZOL, DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS,
                 DRY_SANDY_STONEWARE_CLAY_GRASS,
                 DRY_STONEWARE_CLAY_LOAM_GRASS, DRY_STONEWARE_CLAY_GRASS, DRY_SILTY_STONEWARE_CLAY_GRASS, DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS,
                 STONEWARE_CLAY_HUMUS_GRASS, DRY_STONEWARE_CLAY_HUMUS_GRASS, SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS,
                 SPARSE_SANDY_STONEWARE_CLAY_GRASS,
                 SPARSE_STONEWARE_CLAY_LOAM_GRASS, SPARSE_STONEWARE_CLAY_GRASS, SPARSE_SILTY_STONEWARE_CLAY_GRASS,
                 SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS,
                 SPARSE_STONEWARE_CLAY_HUMUS_GRASS -> ItemsTFCF.STONEWARE_CLAY;
            case COARSE_DIRT -> Item.getItemFromBlock(get(rock, RockTFCF.COARSE_DIRT));
            case PODZOL, SPARSE_GRASS -> Item.getItemFromBlock(BlockRockVariant.get(((BlockRockVariantTFCF) state.getBlock()).rock, Rock.Type.DIRT));
            default -> super.getItemDropped(state, rand, fortune);
        };
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return rockTFCF.isGrass ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        switch (rockTFCF) {
            case SANDY_CLAY_LOAM:
            case SANDY_CLAY:
            case CLAY_LOAM:
            case SILTY_CLAY_LOAM:
            case SILTY_CLAY:
            case COARSE_SANDY_CLAY_LOAM:
            case COARSE_SANDY_CLAY:
            case COARSE_CLAY_LOAM:
            case COARSE_CLAY:
            case COARSE_SILTY_CLAY:
            case COARSE_SILTY_CLAY_LOAM:
            case SANDY_CLAY_LOAM_GRASS:
            case SANDY_CLAY_LOAM_PODZOL:
            case SANDY_CLAY_GRASS:
            case SANDY_CLAY_PODZOL:
            case CLAY_LOAM_GRASS:
            case CLAY_LOAM_PODZOL:
            case CLAY_PODZOL:
            case SILTY_CLAY_GRASS:
            case SILTY_CLAY_PODZOL:
            case SILTY_CLAY_LOAM_GRASS:
            case SILTY_CLAY_LOAM_PODZOL:
            case DRY_SANDY_CLAY_LOAM_GRASS:
            case DRY_SANDY_CLAY_GRASS:
            case DRY_CLAY_LOAM_GRASS:
            case DRY_CLAY_GRASS:
            case DRY_SILTY_CLAY_GRASS:
            case DRY_SILTY_CLAY_LOAM_GRASS:
            case CLAY_HUMUS:
            case COARSE_CLAY_HUMUS:
            case CLAY_HUMUS_GRASS:
            case DRY_CLAY_HUMUS_GRASS:
            case EARTHENWARE_CLAY:
            case SANDY_EARTHENWARE_CLAY_LOAM:
            case COARSE_SANDY_EARTHENWARE_CLAY_LOAM:
            case SANDY_EARTHENWARE_CLAY:
            case COARSE_SANDY_EARTHENWARE_CLAY:
            case EARTHENWARE_CLAY_LOAM:
            case COARSE_EARTHENWARE_CLAY_LOAM:
            case COARSE_EARTHENWARE_CLAY:
            case SILTY_EARTHENWARE_CLAY:
            case COARSE_SILTY_EARTHENWARE_CLAY:
            case SILTY_EARTHENWARE_CLAY_LOAM:
            case COARSE_SILTY_EARTHENWARE_CLAY_LOAM:
            case EARTHENWARE_CLAY_HUMUS:
            case COARSE_EARTHENWARE_CLAY_HUMUS:
            case EARTHENWARE_CLAY_GRASS:
            case SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SANDY_EARTHENWARE_CLAY_LOAM_PODZOL:
            case SANDY_EARTHENWARE_CLAY_GRASS:
            case SANDY_EARTHENWARE_CLAY_PODZOL:
            case EARTHENWARE_CLAY_LOAM_GRASS:
            case EARTHENWARE_CLAY_LOAM_PODZOL:
            case EARTHENWARE_CLAY_PODZOL:
            case SILTY_EARTHENWARE_CLAY_GRASS:
            case SILTY_EARTHENWARE_CLAY_PODZOL:
            case SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SILTY_EARTHENWARE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case DRY_SANDY_EARTHENWARE_CLAY_GRASS:
            case DRY_EARTHENWARE_CLAY_LOAM_GRASS:
            case DRY_EARTHENWARE_CLAY_GRASS:
            case DRY_SILTY_EARTHENWARE_CLAY_GRASS:
            case DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case EARTHENWARE_CLAY_HUMUS_GRASS:
            case DRY_EARTHENWARE_CLAY_HUMUS_GRASS:
            case KAOLINITE_CLAY:
            case SANDY_KAOLINITE_CLAY_LOAM:
            case COARSE_SANDY_KAOLINITE_CLAY_LOAM:
            case SANDY_KAOLINITE_CLAY:
            case COARSE_SANDY_KAOLINITE_CLAY:
            case KAOLINITE_CLAY_LOAM:
            case COARSE_KAOLINITE_CLAY_LOAM:
            case COARSE_KAOLINITE_CLAY:
            case SILTY_KAOLINITE_CLAY:
            case COARSE_SILTY_KAOLINITE_CLAY:
            case SILTY_KAOLINITE_CLAY_LOAM:
            case COARSE_SILTY_KAOLINITE_CLAY_LOAM:
            case KAOLINITE_CLAY_HUMUS:
            case COARSE_KAOLINITE_CLAY_HUMUS:
            case KAOLINITE_CLAY_GRASS:
            case SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case SANDY_KAOLINITE_CLAY_LOAM_PODZOL:
            case SANDY_KAOLINITE_CLAY_GRASS:
            case SANDY_KAOLINITE_CLAY_PODZOL:
            case KAOLINITE_CLAY_LOAM_GRASS:
            case KAOLINITE_CLAY_LOAM_PODZOL:
            case KAOLINITE_CLAY_PODZOL:
            case SILTY_KAOLINITE_CLAY_GRASS:
            case SILTY_KAOLINITE_CLAY_PODZOL:
            case SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case SILTY_KAOLINITE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case DRY_SANDY_KAOLINITE_CLAY_GRASS:
            case DRY_KAOLINITE_CLAY_LOAM_GRASS:
            case DRY_KAOLINITE_CLAY_GRASS:
            case DRY_SILTY_KAOLINITE_CLAY_GRASS:
            case DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case KAOLINITE_CLAY_HUMUS_GRASS:
            case DRY_KAOLINITE_CLAY_HUMUS_GRASS:
            case STONEWARE_CLAY:
            case SANDY_STONEWARE_CLAY_LOAM:
            case COARSE_SANDY_STONEWARE_CLAY_LOAM:
            case SANDY_STONEWARE_CLAY:
            case COARSE_SANDY_STONEWARE_CLAY:
            case STONEWARE_CLAY_LOAM:
            case COARSE_STONEWARE_CLAY_LOAM:
            case COARSE_STONEWARE_CLAY:
            case SILTY_STONEWARE_CLAY:
            case COARSE_SILTY_STONEWARE_CLAY:
            case SILTY_STONEWARE_CLAY_LOAM:
            case COARSE_SILTY_STONEWARE_CLAY_LOAM:
            case STONEWARE_CLAY_HUMUS:
            case COARSE_STONEWARE_CLAY_HUMUS:
            case STONEWARE_CLAY_GRASS:
            case SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case SANDY_STONEWARE_CLAY_LOAM_PODZOL:
            case SANDY_STONEWARE_CLAY_GRASS:
            case SANDY_STONEWARE_CLAY_PODZOL:
            case STONEWARE_CLAY_LOAM_GRASS:
            case STONEWARE_CLAY_LOAM_PODZOL:
            case STONEWARE_CLAY_PODZOL:
            case SILTY_STONEWARE_CLAY_GRASS:
            case SILTY_STONEWARE_CLAY_PODZOL:
            case SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case SILTY_STONEWARE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case DRY_SANDY_STONEWARE_CLAY_GRASS:
            case DRY_STONEWARE_CLAY_LOAM_GRASS:
            case DRY_STONEWARE_CLAY_GRASS:
            case DRY_SILTY_STONEWARE_CLAY_GRASS:
            case DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case STONEWARE_CLAY_HUMUS_GRASS:
            case DRY_STONEWARE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_CLAY_GRASS:
            case SPARSE_CLAY_LOAM_GRASS:
            case SPARSE_SILTY_CLAY_GRASS:
            case SPARSE_SILTY_CLAY_LOAM_GRASS:
            case SPARSE_CLAY_HUMUS_GRASS:
            case SPARSE_CLAY_GRASS:
            case SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_EARTHENWARE_CLAY_GRASS:
            case SPARSE_SILTY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_KAOLINITE_CLAY_GRASS:
            case SPARSE_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_KAOLINITE_CLAY_GRASS:
            case SPARSE_SILTY_KAOLINITE_CLAY_GRASS:
            case SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_KAOLINITE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_STONEWARE_CLAY_GRASS:
            case SPARSE_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_STONEWARE_CLAY_GRASS:
            case SPARSE_SILTY_STONEWARE_CLAY_GRASS:
            case SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_STONEWARE_CLAY_HUMUS_GRASS:
                return 2 + random.nextInt(2);
            case MOSSY_RAW:
                return 1 + random.nextInt(3);
            case MUD:
                return 1 + random.nextInt(3);
            case BOG_IRON:
            case BOG_IRON_GRASS:
            case DRY_BOG_IRON_GRASS:
            case SPARSE_BOG_IRON_GRASS:
            case BOG_IRON_PODZOL:
                return 1 + random.nextInt(1);
            default:
                return super.quantityDropped(state, fortune, random);
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        int beachDistance = 2;

        if (plantable instanceof BlockPlantTFC) {
            switch (((BlockPlantTFC) plantable).getPlantTypeTFC()) {
                case CLAY:
                    return
                            rockTFCF == RockTFCF.COARSE_DIRT ||
                                    rockTFCF == RockTFCF.MUD ||
                                    rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND ||
                                    rockTFCF == RockTFCF.SANDY_LOAM ||
                                    rockTFCF == RockTFCF.LOAM ||
                                    rockTFCF == RockTFCF.SILT_LOAM ||
                                    rockTFCF == RockTFCF.SILT ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_CLAY ||
                                    rockTFCF == RockTFCF.CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT ||
                                    rockTFCF == RockTFCF.PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.LOAM_GRASS ||
                                    rockTFCF == RockTFCF.LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_GRASS ||
                                    rockTFCF == RockTFCF.SILT_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                    rockTFCF == RockTFCF.HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS;
                case DESERT_CLAY:
                    return
                            rockTFCF == RockTFCF.MUD ||
                                    rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_CLAY ||
                                    rockTFCF == RockTFCF.CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_HUMUS;
                case DRY_CLAY:
                    return
                            rockTFCF == RockTFCF.COARSE_DIRT ||
                                    rockTFCF == RockTFCF.MUD ||
                                    rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND ||
                                    rockTFCF == RockTFCF.SANDY_LOAM ||
                                    rockTFCF == RockTFCF.LOAM ||
                                    rockTFCF == RockTFCF.SILT_LOAM ||
                                    rockTFCF == RockTFCF.SILT ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_CLAY ||
                                    rockTFCF == RockTFCF.CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.LOAM_GRASS ||
                                    rockTFCF == RockTFCF.LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_GRASS ||
                                    rockTFCF == RockTFCF.SILT_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                    rockTFCF == RockTFCF.HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS;
                case DRY:
                    return
                            rockTFCF == RockTFCF.COARSE_DIRT ||
                                    rockTFCF == RockTFCF.MUD ||
                                    rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND ||
                                    rockTFCF == RockTFCF.SANDY_LOAM ||
                                    rockTFCF == RockTFCF.LOAM ||
                                    rockTFCF == RockTFCF.SILT_LOAM ||
                                    rockTFCF == RockTFCF.SILT ||
                                    rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT ||
                                    rockTFCF == RockTFCF.COARSE_HUMUS ||
                                    rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                    rockTFCF == RockTFCF.HUMUS ||
                                    rockTFCF == RockTFCF.HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS;
                case FRESH_WATER:
                    return
                            rockTFCF == RockTFCF.COARSE_DIRT ||
                                    rockTFCF == RockTFCF.MUD ||
                                    rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND ||
                                    rockTFCF == RockTFCF.SANDY_LOAM ||
                                    rockTFCF == RockTFCF.LOAM ||
                                    rockTFCF == RockTFCF.SILT_LOAM ||
                                    rockTFCF == RockTFCF.SILT ||
                                    rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT ||
                                    rockTFCF == RockTFCF.COARSE_HUMUS ||
                                    rockTFCF == RockTFCF.PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.LOAM_GRASS ||
                                    rockTFCF == RockTFCF.LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_GRASS ||
                                    rockTFCF == RockTFCF.SILT_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                    rockTFCF == RockTFCF.HUMUS ||
                                    rockTFCF == RockTFCF.HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS;
                case SALT_WATER:
                    return
                            rockTFCF == RockTFCF.COARSE_DIRT ||
                                    rockTFCF == RockTFCF.MUD ||
                                    rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND ||
                                    rockTFCF == RockTFCF.SANDY_LOAM ||
                                    rockTFCF == RockTFCF.LOAM ||
                                    rockTFCF == RockTFCF.SILT_LOAM ||
                                    rockTFCF == RockTFCF.SILT ||
                                    rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT ||
                                    rockTFCF == RockTFCF.PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.LOAM_GRASS ||
                                    rockTFCF == RockTFCF.LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_GRASS ||
                                    rockTFCF == RockTFCF.SILT_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                    rockTFCF == RockTFCF.HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_HUMUS ||
                                    rockTFCF == RockTFCF.HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS;
                case FRESH_BEACH: {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++) {
                            if (BlocksTFC.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                                break;
                            }
                        }
                    }
                    return
                            (
                                    rockTFCF == RockTFCF.COARSE_DIRT ||
                                            rockTFCF == RockTFCF.MUD ||
                                            rockTFCF == RockTFCF.BOG_IRON ||
                                            rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                            rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                            rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                            rockTFCF == RockTFCF.LOAMY_SAND ||
                                            rockTFCF == RockTFCF.SANDY_LOAM ||
                                            rockTFCF == RockTFCF.LOAM ||
                                            rockTFCF == RockTFCF.SILT_LOAM ||
                                            rockTFCF == RockTFCF.SILT ||
                                            rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                            rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                            rockTFCF == RockTFCF.COARSE_LOAM ||
                                            rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                            rockTFCF == RockTFCF.COARSE_SILT ||
                                            rockTFCF == RockTFCF.PODZOL ||
                                            rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                            rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                            rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                            rockTFCF == RockTFCF.LOAM_GRASS ||
                                            rockTFCF == RockTFCF.LOAM_PODZOL ||
                                            rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                            rockTFCF == RockTFCF.SILT_GRASS ||
                                            rockTFCF == RockTFCF.SILT_PODZOL ||
                                            rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                            rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                            rockTFCF == RockTFCF.HUMUS ||
                                            rockTFCF == RockTFCF.COARSE_HUMUS ||
                                            rockTFCF == RockTFCF.HUMUS_GRASS ||
                                            rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS
                            ) && flag;
                }
                case SALT_BEACH: {
                    boolean flag = false;
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        for (int i = 1; i <= beachDistance; i++)
                            if (BlocksTFC.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
                                flag = true;
                            }
                    }
                    return
                            (
                                    rockTFCF == RockTFCF.COARSE_DIRT ||
                                            rockTFCF == RockTFCF.MUD ||
                                            rockTFCF == RockTFCF.BOG_IRON ||
                                            rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                            rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                            rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                            rockTFCF == RockTFCF.LOAMY_SAND ||
                                            rockTFCF == RockTFCF.SANDY_LOAM ||
                                            rockTFCF == RockTFCF.LOAM ||
                                            rockTFCF == RockTFCF.SILT_LOAM ||
                                            rockTFCF == RockTFCF.SILT ||
                                            rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                            rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                            rockTFCF == RockTFCF.COARSE_LOAM ||
                                            rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                            rockTFCF == RockTFCF.COARSE_SILT ||
                                            rockTFCF == RockTFCF.PODZOL ||
                                            rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                            rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                            rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                            rockTFCF == RockTFCF.LOAM_GRASS ||
                                            rockTFCF == RockTFCF.LOAM_PODZOL ||
                                            rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                            rockTFCF == RockTFCF.SILT_GRASS ||
                                            rockTFCF == RockTFCF.SILT_PODZOL ||
                                            rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                            rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                            rockTFCF == RockTFCF.HUMUS ||
                                            rockTFCF == RockTFCF.COARSE_HUMUS ||
                                            rockTFCF == RockTFCF.HUMUS_GRASS ||
                                            rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                            rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS
                            ) && flag;
                }
            }
        } else if (plantable instanceof BlockCropTFC) {
            IBlockState cropState = world.getBlockState(pos.up());
            if (cropState.getBlock() instanceof BlockCropTFC) {
                boolean isWild = cropState.getValue(WILD);
                if (isWild) {
                    if
                    (
                            rockTFCF == RockTFCF.BOG_IRON ||
                                    rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                    rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                    rockTFCF == RockTFCF.COARSE_DIRT ||
                                    rockTFCF == RockTFCF.LOAMY_SAND ||
                                    rockTFCF == RockTFCF.SANDY_LOAM ||
                                    rockTFCF == RockTFCF.LOAM ||
                                    rockTFCF == RockTFCF.SILT_LOAM ||
                                    rockTFCF == RockTFCF.SILT ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_CLAY ||
                                    rockTFCF == RockTFCF.CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILT ||
                                    rockTFCF == RockTFCF.COARSE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.PODZOL ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.LOAM_GRASS ||
                                    rockTFCF == RockTFCF.LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SILT_GRASS ||
                                    rockTFCF == RockTFCF.SILT_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                    rockTFCF == RockTFCF.HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_HUMUS ||
                                    rockTFCF == RockTFCF.HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_HUMUS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL ||
                                    rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                    rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS
                    ) {
                        return true;
                    }
                }
                return
                        rockTFCF == RockTFCF.LOAMY_SAND_FARMLAND ||
                                rockTFCF == RockTFCF.SANDY_LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.SILT_LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.SILT_FARMLAND ||
                                rockTFCF == RockTFCF.HUMUS_FARMLAND;
            }
        }

        switch (plantable.getPlantType(world, pos.offset(direction))) {
            case Plains:
                return
                        rockTFCF == RockTFCF.COARSE_DIRT ||
                                rockTFCF == RockTFCF.MUD ||
                                rockTFCF == RockTFCF.BOG_IRON ||
                                rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                rockTFCF == RockTFCF.LOAMY_SAND ||
                                rockTFCF == RockTFCF.SANDY_LOAM ||
                                rockTFCF == RockTFCF.LOAM ||
                                rockTFCF == RockTFCF.SILT_LOAM ||
                                rockTFCF == RockTFCF.SILT ||
                                rockTFCF == RockTFCF.SANDY_CLAY_LOAM ||
                                rockTFCF == RockTFCF.SANDY_CLAY ||
                                rockTFCF == RockTFCF.CLAY_LOAM ||
                                rockTFCF == RockTFCF.SILTY_CLAY_LOAM ||
                                rockTFCF == RockTFCF.SILTY_CLAY ||
                                rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SANDY_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SANDY_CLAY ||
                                rockTFCF == RockTFCF.COARSE_LOAM ||
                                rockTFCF == RockTFCF.COARSE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SILTY_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SILTY_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SILT ||
                                rockTFCF == RockTFCF.COARSE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.PODZOL ||
                                rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SANDY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SANDY_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SANDY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.LOAM_GRASS ||
                                rockTFCF == RockTFCF.LOAM_PODZOL ||
                                rockTFCF == RockTFCF.CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SILTY_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SILTY_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SILT_GRASS ||
                                rockTFCF == RockTFCF.SILT_PODZOL ||
                                rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SANDY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SANDY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                rockTFCF == RockTFCF.HUMUS ||
                                rockTFCF == RockTFCF.COARSE_HUMUS ||
                                rockTFCF == RockTFCF.HUMUS_GRASS ||
                                rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.CLAY_HUMUS ||
                                rockTFCF == RockTFCF.CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.DRY_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY ||
                                rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SANDY_EARTHENWARE_CLAY ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY ||
                                rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY ||
                                rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SILTY_EARTHENWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.COARSE_EARTHENWARE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_EARTHENWARE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SILTY_EARTHENWARE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SANDY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.EARTHENWARE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.DRY_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY ||
                                rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SANDY_KAOLINITE_CLAY ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY ||
                                rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY ||
                                rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SILTY_KAOLINITE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.COARSE_KAOLINITE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_KAOLINITE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SILTY_KAOLINITE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SANDY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.KAOLINITE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.DRY_KAOLINITE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY ||
                                rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SANDY_STONEWARE_CLAY ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY ||
                                rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY ||
                                rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY ||
                                rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.COARSE_SILTY_STONEWARE_CLAY_LOAM ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.COARSE_STONEWARE_CLAY_HUMUS ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SANDY_STONEWARE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_PODZOL ||
                                rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SILTY_STONEWARE_CLAY_LOAM_PODZOL ||
                                rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_SANDY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.STONEWARE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.DRY_STONEWARE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.LOAMY_SAND_FARMLAND ||
                                rockTFCF == RockTFCF.SANDY_LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.SILT_LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.SILT_FARMLAND ||
                                rockTFCF == RockTFCF.HUMUS_FARMLAND ||
                                rockTFCF == RockTFCF.SPARSE_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_KAOLINITE_CLAY_HUMUS_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SANDY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS ||
                                rockTFCF == RockTFCF.SPARSE_STONEWARE_CLAY_HUMUS_GRASS;
            case Crop:
                return
                        rockTFCF == RockTFCF.LOAMY_SAND_FARMLAND ||
                                rockTFCF == RockTFCF.SANDY_LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.SILT_LOAM_FARMLAND ||
                                rockTFCF == RockTFCF.SILT_FARMLAND ||
                                rockTFCF == RockTFCF.HUMUS_FARMLAND;
            case Cave:
                return true;
            case Water:
                return false;
            case Beach: {
                boolean flag = false;
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    for (int i = 1; i <= beachDistance; i++)
                        if (BlocksTFC.isWater(world.getBlockState(pos.offset(facing, i)))) {
                            flag = true;
                        }
                }
                return
                        (
                                rockTFCF == RockTFCF.COARSE_DIRT ||
                                        rockTFCF == RockTFCF.MUD ||
                                        rockTFCF == RockTFCF.BOG_IRON ||
                                        rockTFCF == RockTFCF.BOG_IRON_GRASS ||
                                        rockTFCF == RockTFCF.DRY_BOG_IRON_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_BOG_IRON_GRASS ||
                                        rockTFCF == RockTFCF.BOG_IRON_PODZOL ||
                                        rockTFCF == RockTFCF.LOAMY_SAND ||
                                        rockTFCF == RockTFCF.SANDY_LOAM ||
                                        rockTFCF == RockTFCF.LOAM ||
                                        rockTFCF == RockTFCF.SILT_LOAM ||
                                        rockTFCF == RockTFCF.SILT ||
                                        rockTFCF == RockTFCF.COARSE_LOAMY_SAND ||
                                        rockTFCF == RockTFCF.COARSE_SANDY_LOAM ||
                                        rockTFCF == RockTFCF.COARSE_LOAM ||
                                        rockTFCF == RockTFCF.COARSE_SILT_LOAM ||
                                        rockTFCF == RockTFCF.COARSE_SILT ||
                                        rockTFCF == RockTFCF.PODZOL ||
                                        rockTFCF == RockTFCF.LOAMY_SAND_GRASS ||
                                        rockTFCF == RockTFCF.LOAMY_SAND_PODZOL ||
                                        rockTFCF == RockTFCF.SANDY_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.SANDY_LOAM_PODZOL ||
                                        rockTFCF == RockTFCF.LOAM_GRASS ||
                                        rockTFCF == RockTFCF.LOAM_PODZOL ||
                                        rockTFCF == RockTFCF.SILT_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.SILT_LOAM_PODZOL ||
                                        rockTFCF == RockTFCF.SILT_GRASS ||
                                        rockTFCF == RockTFCF.SILT_PODZOL ||
                                        rockTFCF == RockTFCF.DRY_LOAMY_SAND_GRASS ||
                                        rockTFCF == RockTFCF.DRY_SANDY_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.DRY_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.DRY_SILT_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.DRY_SILT_GRASS ||
                                        rockTFCF == RockTFCF.HUMUS ||
                                        rockTFCF == RockTFCF.COARSE_HUMUS ||
                                        rockTFCF == RockTFCF.HUMUS_GRASS ||
                                        rockTFCF == RockTFCF.DRY_HUMUS_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_LOAMY_SAND_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_SANDY_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_SILT_LOAM_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_SILT_GRASS ||
                                        rockTFCF == RockTFCF.SPARSE_HUMUS_GRASS
                        ) && flag;
            }
            case Nether:
                return false;
        }

        return false;
    }

    public RockTFCF getType() {
        return rockTFCF;
    }

    public Rock getRock() {
        return rock;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL; // Store anywhere
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

    protected void onRockSlide(World world, BlockPos pos) {
        switch (rockTFCF) {
            case COARSE_DIRT:
            case MUD:
            case BOG_IRON:
            case BOG_IRON_GRASS:
            case DRY_BOG_IRON_GRASS:
            case SPARSE_BOG_IRON_GRASS:
            case BOG_IRON_PODZOL:
            case LOAMY_SAND:
            case COARSE_LOAMY_SAND:
            case SANDY_LOAM:
            case COARSE_SANDY_LOAM:
            case SANDY_CLAY_LOAM:
            case COARSE_SANDY_CLAY_LOAM:
            case SANDY_CLAY:
            case COARSE_SANDY_CLAY:
            case LOAM:
            case COARSE_LOAM:
            case CLAY_LOAM:
            case COARSE_CLAY_LOAM:
            case COARSE_CLAY:
            case SILTY_CLAY:
            case COARSE_SILTY_CLAY:
            case SILTY_CLAY_LOAM:
            case COARSE_SILTY_CLAY_LOAM:
            case SILT_LOAM:
            case COARSE_SILT_LOAM:
            case SILT:
            case COARSE_SILT:
            case COARSE_CLAY_HUMUS:
            case PODZOL:
            case LOAMY_SAND_GRASS:
            case LOAMY_SAND_PODZOL:
            case SANDY_LOAM_GRASS:
            case SANDY_LOAM_PODZOL:
            case SANDY_CLAY_LOAM_GRASS:
            case SANDY_CLAY_LOAM_PODZOL:
            case SANDY_CLAY_GRASS:
            case SANDY_CLAY_PODZOL:
            case LOAM_GRASS:
            case LOAM_PODZOL:
            case CLAY_LOAM_GRASS:
            case CLAY_LOAM_PODZOL:
            case CLAY_PODZOL:
            case SILTY_CLAY_GRASS:
            case SILTY_CLAY_PODZOL:
            case SILTY_CLAY_LOAM_GRASS:
            case SILTY_CLAY_LOAM_PODZOL:
            case SILT_LOAM_GRASS:
            case SILT_LOAM_PODZOL:
            case SILT_GRASS:
            case SILT_PODZOL:
            case DRY_LOAMY_SAND_GRASS:
            case DRY_SANDY_LOAM_GRASS:
            case DRY_SANDY_CLAY_LOAM_GRASS:
            case DRY_SANDY_CLAY_GRASS:
            case DRY_LOAM_GRASS:
            case DRY_CLAY_LOAM_GRASS:
            case DRY_CLAY_GRASS:
            case DRY_SILTY_CLAY_GRASS:
            case DRY_SILTY_CLAY_LOAM_GRASS:
            case DRY_SILT_LOAM_GRASS:
            case DRY_SILT_GRASS:
            case HUMUS:
            case COARSE_HUMUS:
            case COARSE_EARTHENWARE_CLAY_HUMUS:
            case COARSE_KAOLINITE_CLAY_HUMUS:
            case COARSE_STONEWARE_CLAY_HUMUS:
            case HUMUS_GRASS:
            case DRY_HUMUS_GRASS:
            case CLAY_HUMUS:
            case CLAY_HUMUS_GRASS:
            case DRY_CLAY_HUMUS_GRASS:
            case EARTHENWARE_CLAY:
            case SANDY_EARTHENWARE_CLAY_LOAM:
            case COARSE_SANDY_EARTHENWARE_CLAY_LOAM:
            case SANDY_EARTHENWARE_CLAY:
            case COARSE_SANDY_EARTHENWARE_CLAY:
            case EARTHENWARE_CLAY_LOAM:
            case COARSE_EARTHENWARE_CLAY_LOAM:
            case COARSE_EARTHENWARE_CLAY:
            case SILTY_EARTHENWARE_CLAY:
            case COARSE_SILTY_EARTHENWARE_CLAY:
            case SILTY_EARTHENWARE_CLAY_LOAM:
            case COARSE_SILTY_EARTHENWARE_CLAY_LOAM:
            case EARTHENWARE_CLAY_HUMUS:
            case EARTHENWARE_CLAY_GRASS:
            case SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SANDY_EARTHENWARE_CLAY_LOAM_PODZOL:
            case SANDY_EARTHENWARE_CLAY_GRASS:
            case SANDY_EARTHENWARE_CLAY_PODZOL:
            case EARTHENWARE_CLAY_LOAM_GRASS:
            case EARTHENWARE_CLAY_LOAM_PODZOL:
            case EARTHENWARE_CLAY_PODZOL:
            case SILTY_EARTHENWARE_CLAY_GRASS:
            case SILTY_EARTHENWARE_CLAY_PODZOL:
            case SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SILTY_EARTHENWARE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case DRY_SANDY_EARTHENWARE_CLAY_GRASS:
            case DRY_EARTHENWARE_CLAY_LOAM_GRASS:
            case DRY_EARTHENWARE_CLAY_GRASS:
            case DRY_SILTY_EARTHENWARE_CLAY_GRASS:
            case DRY_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case EARTHENWARE_CLAY_HUMUS_GRASS:
            case DRY_EARTHENWARE_CLAY_HUMUS_GRASS:
            case KAOLINITE_CLAY:
            case SANDY_KAOLINITE_CLAY_LOAM:
            case COARSE_SANDY_KAOLINITE_CLAY_LOAM:
            case SANDY_KAOLINITE_CLAY:
            case COARSE_SANDY_KAOLINITE_CLAY:
            case KAOLINITE_CLAY_LOAM:
            case COARSE_KAOLINITE_CLAY_LOAM:
            case COARSE_KAOLINITE_CLAY:
            case SILTY_KAOLINITE_CLAY:
            case COARSE_SILTY_KAOLINITE_CLAY:
            case SILTY_KAOLINITE_CLAY_LOAM:
            case COARSE_SILTY_KAOLINITE_CLAY_LOAM:
            case KAOLINITE_CLAY_HUMUS:
            case KAOLINITE_CLAY_GRASS:
            case SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case SANDY_KAOLINITE_CLAY_LOAM_PODZOL:
            case SANDY_KAOLINITE_CLAY_GRASS:
            case SANDY_KAOLINITE_CLAY_PODZOL:
            case KAOLINITE_CLAY_LOAM_GRASS:
            case KAOLINITE_CLAY_LOAM_PODZOL:
            case KAOLINITE_CLAY_PODZOL:
            case SILTY_KAOLINITE_CLAY_GRASS:
            case SILTY_KAOLINITE_CLAY_PODZOL:
            case SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case SILTY_KAOLINITE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case DRY_SANDY_KAOLINITE_CLAY_GRASS:
            case DRY_KAOLINITE_CLAY_LOAM_GRASS:
            case DRY_KAOLINITE_CLAY_GRASS:
            case DRY_SILTY_KAOLINITE_CLAY_GRASS:
            case DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case KAOLINITE_CLAY_HUMUS_GRASS:
            case DRY_KAOLINITE_CLAY_HUMUS_GRASS:
            case STONEWARE_CLAY:
            case SANDY_STONEWARE_CLAY_LOAM:
            case COARSE_SANDY_STONEWARE_CLAY_LOAM:
            case SANDY_STONEWARE_CLAY:
            case COARSE_SANDY_STONEWARE_CLAY:
            case STONEWARE_CLAY_LOAM:
            case COARSE_STONEWARE_CLAY_LOAM:
            case COARSE_STONEWARE_CLAY:
            case SILTY_STONEWARE_CLAY:
            case COARSE_SILTY_STONEWARE_CLAY:
            case SILTY_STONEWARE_CLAY_LOAM:
            case COARSE_SILTY_STONEWARE_CLAY_LOAM:
            case STONEWARE_CLAY_HUMUS:
            case STONEWARE_CLAY_GRASS:
            case SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case SANDY_STONEWARE_CLAY_LOAM_PODZOL:
            case SANDY_STONEWARE_CLAY_GRASS:
            case SANDY_STONEWARE_CLAY_PODZOL:
            case STONEWARE_CLAY_LOAM_GRASS:
            case STONEWARE_CLAY_LOAM_PODZOL:
            case STONEWARE_CLAY_PODZOL:
            case SILTY_STONEWARE_CLAY_GRASS:
            case SILTY_STONEWARE_CLAY_PODZOL:
            case SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case SILTY_STONEWARE_CLAY_LOAM_PODZOL:
            case DRY_SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case DRY_SANDY_STONEWARE_CLAY_GRASS:
            case DRY_STONEWARE_CLAY_LOAM_GRASS:
            case DRY_STONEWARE_CLAY_GRASS:
            case DRY_SILTY_STONEWARE_CLAY_GRASS:
            case DRY_SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case STONEWARE_CLAY_HUMUS_GRASS:
            case DRY_STONEWARE_CLAY_HUMUS_GRASS:
            case SPARSE_GRASS:
            case SPARSE_CLAY_GRASS:
            case SPARSE_LOAMY_SAND_GRASS:
            case SPARSE_SANDY_LOAM_GRASS:
            case SPARSE_SANDY_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_CLAY_GRASS:
            case SPARSE_LOAM_GRASS:
            case SPARSE_CLAY_LOAM_GRASS:
            case SPARSE_SILTY_CLAY_GRASS:
            case SPARSE_SILTY_CLAY_LOAM_GRASS:
            case SPARSE_SILT_LOAM_GRASS:
            case SPARSE_SILT_GRASS:
            case SPARSE_HUMUS_GRASS:
            case SPARSE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_EARTHENWARE_CLAY_GRASS:
            case SPARSE_SILTY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_SILTY_EARTHENWARE_CLAY_LOAM_GRASS:
            case SPARSE_EARTHENWARE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_KAOLINITE_CLAY_GRASS:
            case SPARSE_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_KAOLINITE_CLAY_GRASS:
            case SPARSE_SILTY_KAOLINITE_CLAY_GRASS:
            case SPARSE_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
            case SPARSE_KAOLINITE_CLAY_HUMUS_GRASS:
            case SPARSE_SANDY_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_SANDY_STONEWARE_CLAY_GRASS:
            case SPARSE_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_STONEWARE_CLAY_GRASS:
            case SPARSE_SILTY_STONEWARE_CLAY_GRASS:
            case SPARSE_SILTY_STONEWARE_CLAY_LOAM_GRASS:
            case SPARSE_STONEWARE_CLAY_HUMUS_GRASS:
            case LOAMY_SAND_FARMLAND:
            case SANDY_LOAM_FARMLAND:
            case LOAM_FARMLAND:
            case SILT_LOAM_FARMLAND:
            case SILT_FARMLAND:
            case HUMUS_FARMLAND:
                world.playSound(null, pos, TFCSounds.DIRT_SLIDE_SHORT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                break;
        }
    }
}
