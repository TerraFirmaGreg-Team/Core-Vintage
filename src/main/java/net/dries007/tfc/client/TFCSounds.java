package net.dries007.tfc.client;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.data.Constants.MODID_TFC;

@Mod.EventBusSubscriber(modid = MODID_TFC)
public class TFCSounds {

    @GameRegistry.ObjectHolder(MODID_TFC + ":rock.slide.long")
    public static final SoundEvent ROCK_SLIDE_LONG = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":rock.slide.short")
    public static final SoundEvent ROCK_SLIDE_SHORT = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":dirt.slide.short")
    public static final SoundEvent DIRT_SLIDE_SHORT = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":quern.stonedrag")
    public static final SoundEvent QUERN_USE = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":item.ceramicbreak")
    public static final SoundEvent CERAMIC_BREAK = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":anvil.metalimpact")
    public static final SoundEvent ANVIL_IMPACT = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":item.throw")
    public static final SoundEvent ITEM_THROW = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":item.jug.blow")
    public static final SoundEvent JUG_BLOW = getNull();
    @GameRegistry.ObjectHolder(MODID_TFC + ":item.jug.fill")
    public static final SoundEvent JUG_FILL = getNull();

    // These are static initialized because we need a custom sound type which uses the sounds before initialization
    private static final SoundEvent CHARCOAL_PILE_BREAK = createSoundEvent("block.charcoal.break");
    private static final SoundEvent CHARCOAL_PILE_FALL = createSoundEvent("block.charcoal.fall");
    private static final SoundEvent CHARCOAL_PILE_HIT = createSoundEvent("block.charcoal.hit");
    private static final SoundEvent CHARCOAL_PILE_PLACE = createSoundEvent("block.charcoal.place");
    private static final SoundEvent CHARCOAL_PILE_STEP = createSoundEvent("block.charcoal.step");

    public static final SoundType CHARCOAL_PILE = new SoundType(1.0F, 1.0F, CHARCOAL_PILE_BREAK, CHARCOAL_PILE_STEP, CHARCOAL_PILE_PLACE, CHARCOAL_PILE_HIT, CHARCOAL_PILE_FALL);

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                // Custom block sounds
                CHARCOAL_PILE_BREAK,
                CHARCOAL_PILE_FALL,
                CHARCOAL_PILE_HIT,
                CHARCOAL_PILE_PLACE,
                CHARCOAL_PILE_STEP,
                // Misc
                createSoundEvent("rock.slide.long"),
                createSoundEvent("rock.slide.short"),
                createSoundEvent("dirt.slide.short"),
                createSoundEvent("quern.stonedrag"),
                createSoundEvent("item.ceramicbreak"),
                createSoundEvent("anvil.metalimpact"),
                createSoundEvent("item.throw"),
                createSoundEvent("item.jug.blow"),
                createSoundEvent("item.jug.fill")
        );
    }

    private static SoundEvent createSoundEvent(String name) {
        final ResourceLocation soundID = new ResourceLocation(MODID_TFC, name);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }
}
