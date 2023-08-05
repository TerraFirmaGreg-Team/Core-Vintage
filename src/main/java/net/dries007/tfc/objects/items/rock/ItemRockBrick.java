package net.dries007.tfc.objects.items.rock;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.util.IRockItem;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class ItemRockBrick extends ItemTFC implements IRockItem {
    private final Rock rock;

    public ItemRockBrick(Rock rock) {
        this.rock = rock;

        var blockRegistryName = String.format("brick/%s", rock);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.ROCK);

        OreDictionaryHelper.register(this, "brick");
        OreDictionaryHelper.register(this, "brick", rock.getRockCategory());
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

    @Nonnull
    @Override
    public Rock getRock() {
        return rock;
    }
}
