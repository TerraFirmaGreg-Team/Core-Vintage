package su.terrafirmagreg.api.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.Setter;
import net.darkhax.bookshelf.block.IColorfulBlock;
import net.darkhax.bookshelf.block.ITileEntityBlock;
import net.darkhax.bookshelf.item.IColorfulItem;
import net.darkhax.bookshelf.item.ICustomMesh;
import net.darkhax.bookshelf.item.ICustomModel;
import net.darkhax.bookshelf.lib.Constants;
import net.darkhax.bookshelf.lib.LootBuilder;
import net.darkhax.bookshelf.util.GameUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import su.terrafirmagreg.api.objects.tile.ITEBlock;
import su.terrafirmagreg.api.util.IHasModel;

import javax.annotation.Nonnull;
import java.util.List;

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

	/**
	 * A list of all the colored items registered here.
	 */
	private final List<Item> coloredItems = NonNullList.create();

	/**
	 * A list of all the colored blocks registered here.
	 */
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
	 * The instance of the owning mod.
	 */
	@Setter
	private Object modInstance;

	/**
	 * The auto registry for the helper.
	 */
	private AutoRegistry autoRegistry;

	/**
	 * Constructs a new RegistryHelper for the specified mod id. Multiple helpers can exist
	 * with the same id, but it's not recommended.
	 *
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
	public Registry(CreativeTabs tab) {
		this.tab = tab;
		this.modid = Loader.instance().activeModContainer().getModId();
		HELPERS.add(this);
	}



	/**
	 * Provides a list of all known registry helpers.
	 *
	 * @return A list of all known registry helpers.
	 */
	public static List<Registry> getAllHelpers() {

		return HELPERS;
	}

	/**
	 * Enables automatic registration for things like the event bus.
	 *
	 * @return The RegistryHelper, for convenience.
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
	 * Get's the owning mod instance. If none is set, Bookshelf will attempt to auto-get it
	 * using Forge's loader.
	 *
	 * @return The owning mod's instance.
	 */
	public Object getModInstance() {

		if (this.modInstance == null) {

			Constants.LOG.error("Registry helper for " + this.modid + " requires a mod instance be set. Attempting to get instance with mod ID. Please ask the mod author to set this themselves.");

			for (final ModContainer container : Loader.instance().getActiveModList()) {

				if (this.modid.equalsIgnoreCase(container.getModId())) {

					this.modInstance = container.getMod();
					break;
				}
			}
		}

		return this.modInstance;
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

	//region // ===== Block ==========================================================================================//

	/**
	 * Registers a block to the game. This will also set the unlocalized name, and creative tab
	 * if {@link #tab} has been set. The block will also be cached in {@link #blocks}.
	 *
	 * @param block The block to register.
	 * @param id    The id to register the block with.
	 * @return The block being registered.
	 */
	public Block registerBlock(@Nonnull Block block, @Nonnull String id) {

		return this.registerBlock(block, new ItemBlock(block), id);
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
	public Block registerBlock(@Nonnull Block block, @Nonnull ItemBlock itemBlock, @Nonnull String id) {

		block.setRegistryName(this.modid, id);
		block.setTranslationKey(this.modid + "." + id.toLowerCase().replace("_", "."));
		this.blocks.add(block);

		this.registerItem(itemBlock, id);

		if (this.tab != null) {
			block.setCreativeTab(this.tab);
		}

		if (block instanceof IColorfulBlock) {

			this.coloredBlocks.add(block);
		}

		if (block instanceof ITEBlock) {

			this.tileProviders.add((ITEBlock) block);
		}

		return block;
	}

	//endregion

	//region // ===== Item ===========================================================================================//

	/**
	 * Registers an item to the game. This will also set the unlocalized name, and creative tab
	 * if {@link #tab} has been set. The item will also be cached in {@link #items}.
	 *
	 * @param item The item to register.
	 * @param id   The id to register the item with.
	 * @return The item being registered.
	 */
	public Item registerItem(@Nonnull Item item, @Nonnull String id) {

		return this.registerItem(item, new ResourceLocation(this.modid, id));
	}

	public Item registerItem(@Nonnull Block block, @Nonnull String id) {

		return this.registerItem(new ItemBlock(block), id);
	}

	public Item registerItem(@Nonnull Item item, @Nonnull ResourceLocation id) {

		item.setRegistryName(id);
		item.setTranslationKey(id.getNamespace().replaceAll("_", ".") + "." + id.getPath().toLowerCase().replace("_", "."));
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

		return item;
	}

	//endregion

	//region // ===== Potions ========================================================================================//

	public void registerPotion(@Nonnull Potion potion, @Nonnull String id) {

		potion.setRegistryName(this.modid, id);
		potion.setPotionName(this.modid + "." + id.toLowerCase().replace("_", "."));
		potion.setPotionName(this.modid + ".effect." + id);
		this.potions.add(potion);
	}

	//endregion

	//region // ===== Potion Types ===================================================================================//

	public void registerPotionType(@Nonnull PotionType potionType, @Nonnull String id) {

		potionType.setRegistryName(this.modid, id);
		this.potionType.add(potionType);
	}

	//endregion

	//region // ===== Biome ==========================================================================================//

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

	//region // ===== Sound ==========================================================================================//

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

	//region // ===== Entity =========================================================================================//

	/**
	 * Registers any sort of entity. Will not have a spawn egg.
	 *
	 * @param entClass The entity class.
	 * @param id       The string id for the entity.
	 * @return The entity that was registered.
	 */
	public <T extends Entity> EntityEntryBuilder<T> registerEntity(Class<T> entClass, String id, int networkId) {

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
	public <T extends Entity> EntityEntryBuilder<T> registerMob(Class<T> entClass, String id, int networkId, int primary, int seconday) {

		final EntityEntryBuilder<T> builder = this.registerEntity(entClass, id, networkId);

		builder.tracker(64, 1, true);
		builder.egg(primary, seconday);
		return builder;
	}

	//endregion

	//region // ===== Recipe =========================================================================================//

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

	//region // ===== Enchantment ====================================================================================//

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

	/**
	 * Registers an inventory model for a block. The model name is equal to the registry name
	 * of the block. Only set for meta 0.
	 *
	 * @param block The block to register the model for.
	 */
	@SideOnly(Side.CLIENT)
	public void registerInventoryModel(@Nonnull Block block) {

		this.registerInventoryModel(Item.getItemFromBlock(block));
	}

	/**
	 * Registers an inventory model for a block with variants. The model name is equal to the
	 * registry name of the block, plus the variant string for the meta.
	 *
	 * @param block    The block to register models for.
	 * @param prefix   The prefix for the textures. Use empty string for none.
	 * @param variants An array of variant names in order of meta.
	 */
	@SideOnly(Side.CLIENT)
	public void registerInventoryModel(@Nonnull Block block, @Nonnull String prefix, @Nonnull String... variants) {

		for (int meta = 0; meta < variants.length; meta++) {
			this.registerInventoryModel(Item.getItemFromBlock(block), meta, block.getRegistryName().toString() + "_" + (prefix.isEmpty() ? prefix : prefix + "_") + variants[meta]);
		}
	}

	/**
	 * Registers an inventory model for a block.
	 *
	 * @param block     The block to register the model for.
	 * @param meta      The meta to register the model for.
	 * @param modelName The name of the model to register.
	 */
	@SideOnly(Side.CLIENT)
	public void registerInventoryModel(@Nonnull Block block, int meta, @Nonnull String modelName) {

		this.registerInventoryModel(Item.getItemFromBlock(block), meta, modelName);
	}

	/**
	 * Registers an inventory model for an item.The model name is equal to the registry name of
	 * the item. Only set for meta 0.
	 *
	 * @param item The item to register the model for.
	 */
	@SideOnly(Side.CLIENT)
	public void registerInventoryModel(@Nonnull Item item) {

		if (item instanceof IVariant variant) this.registerInventoryModel(item, variant.getPrefix(), variant.getVariant());
		else if (item instanceof ICustomModel) ((ICustomModel) item).registerMeshModels();
		else if (item instanceof IHasModel) ((IHasModel) item).onModelRegister();
		else this.registerInventoryModel(item, 0, item.getRegistryName().toString());
	}

	/**
	 * Registers an inventory model for an item with variants. The model name is equal to the
	 * registry name of the item, plus the variant string for the meta.
	 *
	 * @param item     The item to register models for.
	 * @param prefix   Adds a prefix to each of the model variants.
	 * @param variants An array of variant names in order of meta.
	 */
	@SideOnly(Side.CLIENT)
	public void registerInventoryModel(@Nonnull Item item, @Nonnull String prefix, @Nonnull String... variants) {

		for (int meta = 0; meta < variants.length; meta++) {
			this.registerInventoryModel(item, meta, item.getRegistryName().toString() + "_" + (prefix.isEmpty() ? prefix : prefix + "_") + variants[meta]);
		}
	}

	/**
	 * Registers an inventory model for an item.
	 *
	 * @param item      The item to register the model for.
	 * @param meta      The meta to register the model for.
	 * @param modelName The name of the model to register.
	 */
	@SideOnly(Side.CLIENT)
	public void registerInventoryModel(@Nonnull Item item, int meta, @Nonnull String modelName) {

		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modelName, "inventory"));
	}

	/**
	 * Gets a list of all items registered that have custom color handlers.
	 *
	 * @return A list of all colored items.
	 */
	@SideOnly(Side.CLIENT)
	public List<Item> getColoredItems() {

		return this.coloredItems;
	}

	/**
	 * Gets a list of all registered blocks that have custom color handlers.
	 *
	 * @return A list of all colored blocks.
	 */
	@SideOnly(Side.CLIENT)
	public List<Block> getColoredBlocks() {

		return this.coloredBlocks;
	}
}
