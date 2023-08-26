package net.dries007.tfc.common.objects.items.rock;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.rock.IRockItem;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.item.RockItemVariant;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemRock extends ItemTFC implements IRockItem {

    private final RockItemVariant variant;
    private final RockType type;

    public ItemRock(RockItemVariant variant, RockType type) {

        this.variant = variant;
        this.type = type;

        setCreativeTab(CreativeTabsTFC.ROCK);
        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());

        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.getCategory().toString());

        if (type.isFlux())
            OreDictionaryHelper.register(this, variant.toString(), "flux");
    }

    @Nonnull
    @Override
    public RockItemVariant getItemVariant() {
        return variant;
    }

    @Override
    @Nonnull
    public RockType getType() {
        return type;
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1) {
            TFCGuiHandler.openGui(world, player.getPosition(), player, TFCGuiHandler.Type.KNAPPING_STONE);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + getType().getCategory().getLocalizedName());

        if (type.isFlux())
            tooltip.add(TextFormatting.GREEN + new TextComponentTranslation("is_flux_rock.name").getFormattedText());
    }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getResourceLocation(), "rocktype=" + getType()));
    }
}
