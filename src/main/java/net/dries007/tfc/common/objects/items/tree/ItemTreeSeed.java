package net.dries007.tfc.common.objects.items.tree;

import com.ferreusveritas.dynamictrees.items.Seed;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.tree.ITreeItem;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.types.tree.variant.item.TreeItemVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemTreeSeed extends Seed implements ITreeItem {

    private final TreeType type;
    private final TreeItemVariant variant;

    public ItemTreeSeed(TreeItemVariant variant, TreeType type) {
        super(String.format("tree/%s/%s", variant, type));
        this.type = type;
        this.variant = variant;

        setTranslationKey(getTranslationName());

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }


    @Nonnull
    @Override
    public TreeItemVariant getItemVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public TreeType getType() {
        return type;
    }

    @Nonnull
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) type.getMinRain(), (int) type.getMaxRain()));
            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", type.getMinRain()), String.format("%.1f", type.getMaxRain())));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
        }
    }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getRegistryLocation(), "normal"));
    }
}
