/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.compat.top.interfaces;

/**
 * Does the direct "translation" from IWailaEntity to Hwyla
 */
public class HwylaEntityInterface
{
    protected final IWailaEntity internal;

    public HwylaEntityInterface(IWailaEntity internal)
    {
        this.internal = internal;
    }


    /*
    @Override
    public void register(IWailaRegistrar registrar)
    {
        // Register providers accordingly to each implementation
        for (Class<?> clazz : internal.getLookupClass())
        {
            registrar.registerBodyProvider(this, clazz);
            // Register to update NBT data on all entities.
            registrar.registerNBTProvider(this, clazz);
            if (internal.overrideTitle())
            {
                registrar.registerHeadProvider(this, clazz);
            }
        }
    }

    @Nonnull
    @Override
    public List<String> getWailaHead(Entity entity, List<String> currentTooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        currentTooltip.add(TextFormatting.WHITE.toString() + internal.getTitle(accessor.getEntity(), accessor.getNBTData()));
        return currentTooltip;
    }

    @Nonnull
    @Override
    public List<String> getWailaBody(Entity entity, List<String> currentTooltip, IWailaEntityAccessor accessor, IWailaConfigHandler config)
    {
        currentTooltip.addAll(internal.getTooltip(entity, accessor.getNBTData()));
        return currentTooltip;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world)
    {
        return ent.writeToNBT(tag);
    }*/
}
