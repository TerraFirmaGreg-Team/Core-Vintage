package net.dries007.tfc.api.capability.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;


import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.network.PacketPlayerDataUpdate;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface for the capability attached to a player Holds an internal list of skill implementations
 *
 * @see SkillType
 */
public interface IPlayerData extends INBTSerializable<NBTTagCompound> {

    @Nullable
    <S extends Skill> S getSkill(SkillType<S> skillType);

    @NotNull
    EntityPlayer getPlayer();

    /*
     * Gets the tool that was used in the last {@link net.minecraftforge.event.world.BlockEvent.BreakEvent} event
     */
    @NotNull
    ItemStack getHarvestingTool();

    void setHarvestingTool(@NotNull ItemStack stack);

    /**
     * Gets the current chiseling mode.
     *
     * @return enum value of the chiseling mode
     */
    @NotNull
    ChiselRecipe.Mode getChiselMode();

    /**
     * Sets the current chiseling mode.
     *
     * @param chiselMode enum value for the new chiseling mode
     */
    void setChiselMode(@NotNull ChiselRecipe.Mode chiselMode);

    /**
     * Makes the player intoxicated
     *
     * @param ticks Ticks for the player to be intoxicated
     */
    void addIntoxicatedTime(long ticks);

    /**
     * Gets the number of ticks the player is intoxicated for
     */
    long getIntoxicatedTime();

    /**
     * If the player has been given the guide book
     */
    boolean hasBook();

    /**
     * Sets if the player has been given the guide book
     */
    void setHasBook(boolean value);

    default void updateAndSync() {
        EntityPlayer player = getPlayer();
        if (player instanceof EntityPlayerMP) {
            TerraFirmaCraft.getNetwork().sendTo(new PacketPlayerDataUpdate(serializeNBT()), (EntityPlayerMP) player);
        }
    }
}
