package su.terrafirmagreg.api.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.objects.block.IColorfulBlock;
import su.terrafirmagreg.api.objects.item.IColorfulItem;
import su.terrafirmagreg.api.objects.item.ICustomMesh;
import su.terrafirmagreg.api.objects.tile.ITEBlock;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.IHasModel;
import su.terrafirmagreg.api.util.LootBuilder;

import java.util.List;
import javax.annotation.Nonnull;

public class Registry {

    /**
     * A list of all known helpers.
     */
    private static final List<Registry> HELPERS = NonNullList.create();

    /**
     * The id of the mod the registry helper instance belongs to.
     */
    @Getter
    private final String modid;

    /**
     * A list of all items registered by the helper.
     */
    @Getter
    private final NonNullList<Item> items = NonNullList.create();

    /**
     * A list of all blocks registered by the helper.
     */
    @Getter
    private final NonNullList<Block> blocks = NonNullList.create();

    /**
     * A list of all potions registered by the helper.
     */
    @Getter
    private final NonNullList<Potion> potions = NonNullList.create();

    /**
     * A list of all potion type registered by the helper.
     */
    @Getter
    private final NonNullList<PotionType> potionType = NonNullList.create();

    /**
     * A list of all biomes registered by the helper.
     */
    @Getter
    private final NonNullList<Biome> biomes = NonNullList.create();

    /**
     * A list of all the sounds registered by the helper.
     */
    @Getter
    private final NonNullList<SoundEvent> sounds = NonNullList.create();

    /**
     * A list of all entities registered by the helper.
     */
    @Getter
    private final NonNullList<EntityEntryBuilder<? extends Entity>> entities = NonNullList.create();

    /**
     * A list of all entities registered by the helper.
     */
    @Getter
    private final NonNullList<ResourceLocation> entityIds = NonNullList.create();

    /**
     * A local map of all the entries that have been added. This is on a per instance basis,
     * used to get mod-specific entries.
     */
    @Getter
    private final Multimap<ResourceLocation, LootBuilder> lootTableEntries = HashMultimap.create();

    /**
     * A list of all the custom mesh definitions.
     */
    @Getter
    private final List<ICustomMesh> customMeshes = NonNullList.create();

    @Getter
    private final List<IHasModel> models = NonNullList.create();

    /**
     * A list of all the colored items registered here.
     */
    @Getter
    private final List<Item> coloredItems = NonNullList.create();

    /**
     * A list of all the colored blocks registered here.
     */
    @Getter
    private final List<Block> coloredBlocks = NonNullList.create();

    /**
     * A list of all the tile providers registered here.
     */
    @Getter
    private final List<ITEBlock> tileProviders = NonNullList.create();

    /**
     * A list of all recipes registered.
     */
    @Getter
    private final List<IRecipe> recipes = NonNullList.create();

    /**
     * A list of all enchantments registered.
     */
    @Getter
    private final List<Enchantment> enchantments = NonNullList.create();

    /**
     * The creative tab used by the mod. This can be null.
     */
    @Getter
    private CreativeTabs tab;

    /**
     * The auto registry for the helper.
     */
    @Getter
    private AutoRegistry autoRegistry;

    /**
     * Constructs a new RegistryHelper for the specified mod id. Multiple helpers can exist
     * with the same id, but it's not recommended.
     */
    public Registry() {
        this(null);
    }

    /**
     * Constructs a new RegistryHelper. The modid for the helper is equal to that of the active
     * mod container, and auto model registration is enabled.
     *
     * @param tab The tab for the registry helper.
     */
    public Registry(@Nullable CreativeTabs tab) {
        this.tab = tab;
        this.modid = Loader.instance().activeModContainer().getModId();
        HELPERS.add(this);
    }


    /**
     * Enables automatic registration for things like the event bus.
     *
     * @return The Registry, for convenience.
     */
    public Registry enableAutoRegistration() {

        this.autoRegistry = this.getNewAutoRegistry();
        MinecraftForge.EVENT_BUS.register(this.autoRegistry);
        return this;
    }

    /**
     * Checks if the registry has automatic registration.
     *
     * @return Whether or not the helper has automatic registration.
     */
    public boolean hasAutoRegistry() {
        return this.autoRegistry != null;
    }


    /**
     * Used to get a new auto registry instance. Only used if {@link #enableAutoRegistration()}
     * is used.
     *
     * @return The new auto registry instance.
     */
    public AutoRegistry getNewAutoRegistry() {
        return new AutoRegistry(this);
    }

    //region // ===== Block ========================================================================================================================//

    public Block[] registerBlocks(Block... blocks) {
        for (Block block : blocks) {
            registerBlock(block);
        }
        return blocks;
    }

    public Block registerBlock(Block block) {
        if (this.tab != null) {
            block.setCreativeTab(this.tab);
        }
        this.blocks.add(block);

        return block;
    }

    public Block registerBlockWithItem(@Nonnull Block block, @Nonnull String id) {

        ItemBlock itemBlock;

        if (block instanceof IAutoRegistry provider) itemBlock = provider.getItemBlock();
        else itemBlock = new ItemBlock(block);

        return this.registerBlock(block, itemBlock, id);
    }

    public void registerAuto(Block block) {
        if (block instanceof IAutoRegistry provider) {
            ItemBlock itemBlock = provider.getItemBlock();
            String id = provider.getName();

            this.registerBlock(block, itemBlock, id);
        }
    }

    /**
     * Registers a block to the game. This will also set the unlocalized name, and creative tab
     * if {@link #tab} has been set. The block will also be cached in {@link #blocks}.
     *
     * @param block     The block to register.
     * @param itemBlock The ItemBlock for the block.
     * @param id        The id to register the block with.
     * @return The block being registered.
     */
    public <B extends Block, I extends ItemBlock> B registerBlock(@Nonnull B block, @Nullable I itemBlock, @Nonnull String id) {

        block.setRegistryName(this.modid, id);
        block.setTranslationKey(this.modid + "." + id.toLowerCase().replace("_", ".").replaceAll("/", "."));
        this.blocks.add(block);

        if (itemBlock != null) this.registerItem(itemBlock, id);


        if (this.tab != null) block.setCreativeTab(this.tab);

        if (block instanceof IColorfulBlock) this.coloredBlocks.add(block);

        if (block instanceof ITEBlock te) this.tileProviders.add(te);

        return block;
    }

    //endregion

    //region // ===== Item =========================================================================================================================//


    public void registerAuto(Item item) {
        if (item instanceof IAutoRegistry provider) {
            String id = provider.getName();

            this.registerItem(item, id);
        }
    }

    /**
     * Registers an item to the game. This will also set the unlocalized name, and creative tab
     * if {@link #tab} has been set. The item will also be cached in {@link #items}.
     *
     * @param item The item to register.
     * @param id   The id to register the item with.
     */
    public void registerItem(@Nonnull Item item, @Nonnull String id) {

        item.setRegistryName(this.modid, id);
        item.setTranslationKey(this.modid + "." + id.toLowerCase().replace("_", ".").replaceAll("/", "."));
        this.items.add(item);

        if (this.tab != null) {
            item.setCreativeTab(this.tab);
        }

        if (GameUtils.isClient()) {

            if (item instanceof ICustomMesh mesh) {

                this.customMeshes.add(mesh);
                ModelLoader.setCustomMeshDefinition(item, mesh.getCustomMesh());
            }

            if (item instanceof IColorfulItem) {

                this.coloredItems.add(item);
            }
        }
    }

    //endregion

    //region // ===== Potions ======================================================================================================================//

    public void registerPotion(@Nonnull Potion potion, @Nonnull String id) {

        potion.setRegistryName(this.modid, id);
        potion.setPotionName(this.modid + "." + id.toLowerCase().replace("_", "."));
        potion.setPotionName(this.modid + ".effect." + id);
        this.potions.add(potion);
    }

    //endregion

    //region // ===== Potion Types =================================================================================================================//

    public void registerPotionType(@Nonnull PotionType potionType, @Nonnull String id) {

        potionType.setRegistryName(this.modid, id);
        this.potionType.add(potionType);
    }

    //endregion

    //region // ===== Biome ========================================================================================================================//

    public void registerBiome(Biome biome, String id) {
        this.registerBiome(biome, id, new BiomeDictionary.Type[0]);
    }

    public void registerBiome(Biome biome, String id, BiomeDictionary.Type[] types) {

        biome.setRegistryName(this.modid, id);
        this.biomes.add(biome);

        if (types.length > 0) {
            BiomeDictionary.addTypes(biome, types);
        }


    }

    //endregion

    //region // ===== Sound ========================================================================================================================//

    /**
     * Registers a new sound with the game. The sound must also exist in the sounds.json file.
     *
     * @param name The name of the sound file. No upper case chars!
     * @return The sound event that was registered.
     */
    public SoundEvent registerSound(String name) {

        final ResourceLocation id = new ResourceLocation(this.modid, name);
        final SoundEvent sound = new SoundEvent(id).setRegistryName(id);
        this.sounds.add(sound);
        return sound;
    }

    //endregion

    //region // ===== Entity =======================================================================================================================//

    /**
     * Registers any sort of entity. Will not have a spawn egg.
     *
     * @param entClass The entity class.
     * @param id       The string id for the entity.
     * @return The entity that was registered.
     */
    public <T extends Entity> EntityEntryBuilder<T> registerEntity(String id, Class<T> entClass, int networkId) {

        final ResourceLocation entId = new ResourceLocation(this.modid, id);
        final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
        builder.id(entId, networkId);
        builder.name(this.modid + "." + id);
        builder.entity(entClass);

        this.entities.add(builder);
        this.entityIds.add(entId);
        return builder;
    }

    /**
     * Registers any sort of entity. Will not have a spawn egg.
     *
     * @param entClass The entity class.
     * @param id       The string id for the entity.
     * @return The entity that was registered.
     */
    public <T extends Entity> EntityEntryBuilder<T> registerMob(String id, Class<T> entClass,  int networkId, int primary, int seconday) {

        final EntityEntryBuilder<T> builder = this.registerEntity(id, entClass, networkId);

        builder.tracker(64, 1, true);
        builder.egg(primary, seconday);
        return builder;
    }

    //endregion

    //region // ===== Enchantment ==================================================================================================================//

    /**
     * Registers an enchantment.
     *
     * @param enchant The enchantment to register.
     * @param id      The ID of the enchantment.
     * @return The enchantment that was registered.
     */
    public Enchantment registerEnchantment(Enchantment enchant, String id) {

        enchant.setRegistryName(new ResourceLocation(this.modid, id));
        this.enchantments.add(enchant);
        return enchant;
    }

    //endregion

    //region // ===== Villager Profession ==========================================================================================================//


    //endregion

    //region // ===== Recipe =======================================================================================================================//

    /**
     * Adds a shaped recipe to the game.
     *
     * @param id     The id of the recipe.
     * @param output The output for the recipe.
     * @param inputs The inputs. Pattern, then char followed by what it represents.
     * @return The recipe registered.
     */
    public IRecipe addShapedRecipe(String id, ItemStack output, Object... inputs) {

        return this.registerRecipe(id, new ShapedOreRecipe(null, output, inputs));
    }

    /**
     * Adds a shapeless recipe to the game.
     *
     * @param id     The id of the recipe.
     * @param output The output of the recipe.
     * @param inputs The inputs for the recipe.
     * @return The recipe registered.
     */
    public IRecipe addShapelessRecipe(String id, ItemStack output, Object... inputs) {

        return this.registerRecipe(id, new ShapelessOreRecipe(null, output, inputs));
    }

    /**
     * Adds a recipe to the game.
     *
     * @param id     The id of the recipe.
     * @param recipe The recipe object.
     * @return The registered registry object.
     */
    public IRecipe registerRecipe(String id, IRecipe recipe) {

        recipe.setRegistryName(new ResourceLocation(this.modid, id));
        this.recipes.add(recipe);
        return recipe;
    }

    //endregion


    //region // ===== Loot Table ===================================================================================================================//


    /**
     * Registers a loot table with the loot table list. This needs to be called before a loot
     * table can be used.
     *
     * @param name The name of the loot table to use.
     * @return A ResourceLocation pointing to the table.
     */
    public ResourceLocation registerLootTable(String name) {

        return LootTableList.register(new ResourceLocation(this.getModid(), name));
    }

    //endregion


    //region // ===== Models =======================================================================================================================//

    /**
     * Registers an inventory model for a block. The model name is equal to the registry name
     * of the block. Only set for meta 0.
     *
     * @param block The block to register the model for.
     */
    @SideOnly(Side.CLIENT)
    public void registerInventoryModel(@Nonnull Block block) {
        if (block instanceof IHasModel model) model.onModelRegister();
        else this.registerBlockModel(block, new StateMap.Builder().build());
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockModel(@Nonnull Block block, IStateMapper stateMap) {
        ModelLoader.setCustomStateMapper(block, stateMap);
    }

    /**
     * Registers an inventory model for an item.The model name is equal to the registry name of
     * the item. Only set for meta 0.
     *
     * @param item The item to register the model for.
     */
    @SideOnly(Side.CLIENT)
    public void registerInventoryModel(@Nonnull Item item) {
        if (item instanceof IHasModel model) model.onModelRegister();
        else this.registerItemModel(item, 0, item.getRegistryName().toString());
    }

    /**
     * Registers an inventory model for an item.
     *
     * @param item The item to register the model for.
     * @param meta The meta to register the model for.
     * @param modelName The name of the model to register.
     */
    @SideOnly(Side.CLIENT)
    public void registerItemModel(@Nonnull Item item, int meta, @Nonnull String modelName) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelName, "normal"));
    }


    @SideOnly(Side.CLIENT)
    public void registerClientModelRegistration(IHasModel model) {
        this.models.add(model);
    }

    //endregion
}
