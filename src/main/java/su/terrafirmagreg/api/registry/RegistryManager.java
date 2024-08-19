package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.network.NetworkEntityIdSupplier;
import su.terrafirmagreg.api.registry.spi.IBiomeRegistry;
import su.terrafirmagreg.api.registry.spi.IBlockRegistry;
import su.terrafirmagreg.api.registry.spi.ICommandRegistry;
import su.terrafirmagreg.api.registry.spi.IDataSerializerRegistry;
import su.terrafirmagreg.api.registry.spi.IEnchantmentRegistry;
import su.terrafirmagreg.api.registry.spi.IEntityRegistry;
import su.terrafirmagreg.api.registry.spi.IItemRegistry;
import su.terrafirmagreg.api.registry.spi.IKeyBindingRegistry;
import su.terrafirmagreg.api.registry.spi.ILootTableRegistry;
import su.terrafirmagreg.api.registry.spi.IPotionRegistry;
import su.terrafirmagreg.api.registry.spi.IPotionTypeRegistry;
import su.terrafirmagreg.api.registry.spi.ISoundRegistry;
import su.terrafirmagreg.api.registry.spi.IWorldGeneratorRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class RegistryManager
        implements IBlockRegistry, IBiomeRegistry, IKeyBindingRegistry, IDataSerializerRegistry, IEnchantmentRegistry, IEntityRegistry, IItemRegistry, ILootTableRegistry,
                   IPotionRegistry, IPotionTypeRegistry, ISoundRegistry, IWorldGeneratorRegistry, ICommandRegistry {

    /**
     * The id of the mod the registry helper instance belongs to.
     */
    private final String modID;

    /**
     * The creative tab used by the mod. This can be null.
     */
    private final CreativeTabs tab;

    /**
     * The auto registry for the helper.
     */
    private final Registry registry;

    private NetworkEntityIdSupplier networkEntityIdSupplier;

    /**
     * Constructs a new Registry. The modid for the helper is equal to that of the active mod container, and auto model registration is enabled.
     *
     * @param tab The tab for the registry helper.
     */
    public RegistryManager(@Nullable CreativeTabs tab, String modID) {

        this.modID = modID;
        this.tab = tab;

        this.registry = new Registry(this);
        MinecraftForge.EVENT_BUS.register(this.registry);
    }

    public void setNetworkEntityIdSupplier(NetworkEntityIdSupplier supplier) {

        if (this.networkEntityIdSupplier != null)
            throw new IllegalStateException("Network entity id supplier has already been set");

        this.networkEntityIdSupplier = supplier;
    }
}
