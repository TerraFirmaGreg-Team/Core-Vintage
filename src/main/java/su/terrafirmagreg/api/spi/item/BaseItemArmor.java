package su.terrafirmagreg.api.spi.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;


import org.jetbrains.annotations.Nullable;

public abstract class BaseItemArmor extends ItemArmor implements ISettingsItem {

    protected final Settings settings;

    // mark the armor material as nullable to take the null warning out of BaseItem
    @SuppressWarnings("ConstantConditions")
    public BaseItemArmor(@Nullable ArmorMaterial material, EntityEquipmentSlot slot, Settings settings) {
        super(material, -1, slot);

        this.settings = settings;

        setMaxStackSize(1);
        setCreativeTab(settings.tab);
        setTranslationKey(settings.translationKey);
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return settings.rarity;
    }
}
