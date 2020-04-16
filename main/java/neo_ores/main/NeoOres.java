package neo_ores.main;

import neo_ores.blocks.ManaBlock;
import neo_ores.blocks.ManaFurnace;
import neo_ores.blocks.ManaWorkbench;
import neo_ores.blocks.UnditeBlock;
import neo_ores.creativetab.NeoOresTab;
import neo_ores.event.NeoPlayerEvent;
import neo_ores.gui.GuiHandler;
import neo_ores.items.ItemEffected;
import neo_ores.items.ItemEssence;
import neo_ores.items.ItemEssenceCoreAir;
import neo_ores.items.ItemEssenceCoreEarth;
import neo_ores.items.ItemEssenceCoreFire;
import neo_ores.items.ItemEssenceCoreWater;
import neo_ores.items.ItemManaBlock;
import neo_ores.items.ItemNeoArmor;
import neo_ores.items.ItemNeoAxe;
import neo_ores.items.ItemNeoHoe;
import neo_ores.items.ItemNeoPickaxe;
import neo_ores.items.ItemNeoSpade;
import neo_ores.items.ItemNeoSword;
import neo_ores.mana.TierCalc;
import neo_ores.tileentity.TileEntityManaFurnace;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions=Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class NeoOres 
{
	public static TierCalc tierCalc = new TierCalc();
	
	@Instance
	public static NeoOres instance;
	
	@Metadata(Reference.MOD_ID)
	private static ModMetadata meta;
	
	public static final int guiIDManaWorkbench = 0;
	public static final int guiIDManaFurnace = 1;
	
	public static final CreativeTabs neo_ores_tab = new NeoOresTab("neo_ores_tab");
	
	public static final Block mana_workbench = new ManaWorkbench()
			.setRegistryName(Reference.MOD_ID,"mana_workbench")
			.setUnlocalizedName("mana_workbench")
			.setCreativeTab(NeoOres.neo_ores_tab);	
	public static final Block mana_furnace = new ManaFurnace(false)
			.setRegistryName(Reference.MOD_ID,"mana_furnace")
			.setUnlocalizedName("mana_furnace")
			.setCreativeTab(NeoOres.neo_ores_tab);	
	public static final Block lit_mana_furnace = new ManaFurnace(true)
			.setRegistryName(Reference.MOD_ID,"lit_mana_furnace")
			.setUnlocalizedName("mana_furnace")
			.setCreativeTab(null);
	public static final Block mana_block = new ManaBlock()
			.setRegistryName(Reference.MOD_ID,"mana_block")
			.setUnlocalizedName("mana_block")
			.setCreativeTab(NeoOres.neo_ores_tab);
	public static final Block undite_block = new UnditeBlock()
			.setRegistryName(Reference.MOD_ID,"undite_block")
			.setUnlocalizedName("undite_block")
			.setCreativeTab(NeoOres.neo_ores_tab);
	
	public static final Item undite = new Item()
			.setRegistryName(Reference.MOD_ID, "undite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite");
	public static final Item gnomite_ingot = new Item()
			.setRegistryName(Reference.MOD_ID, "gnomite_ingot")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("gnomite_ingot");
	public static final Item salamite = new Item()
			.setRegistryName(Reference.MOD_ID, "salamite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("salamite");
	public static final Item sylphite = new Item()
			.setRegistryName(Reference.MOD_ID, "sylphite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("sylphite");
	public static final Item mana_ingot = new ItemEffected()
			.setRegistryName(Reference.MOD_ID, "mana_ingot")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("mana_ingot");
	public static final Item essence = new ItemEssence()
			.setRegistryName(Reference.MOD_ID, "essence")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("essence");
	public static final Item air_essence_core = new ItemEssenceCoreAir()
			.setRegistryName(Reference.MOD_ID, "air_essence_core")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("air_essence_core");
	public static final Item earth_essence_core = new ItemEssenceCoreEarth()
			.setRegistryName(Reference.MOD_ID, "earth_essence_core")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("earth_essence_core");
	public static final Item fire_essence_core = new ItemEssenceCoreFire()
			.setRegistryName(Reference.MOD_ID, "fire_essence_core")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("fire_essence_core");
	public static final Item water_essence_core = new ItemEssenceCoreWater()
			.setRegistryName(Reference.MOD_ID, "water_essence_core")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("water_essence_core");
	public static final Item sanitite = new Item()
			.setRegistryName(Reference.MOD_ID, "sanitite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("sanitite");
	public static final Item marlite_ingot = new Item()
			.setRegistryName(Reference.MOD_ID, "marlite_ingot")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("marlite_ingot");
	public static final Item aerite = new Item()
			.setRegistryName(Reference.MOD_ID, "aerite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("aerite");
	public static final Item drenite = new Item()
			.setRegistryName(Reference.MOD_ID, "drenite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("drenite");
	public static final Item guardite_ingot = new Item()
			.setRegistryName(Reference.MOD_ID, "guardite_ingot")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("guardite_ingot");
	public static final Item landite_ingot = new Item()
			.setRegistryName(Reference.MOD_ID, "landite_ingot")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("landite_ingot");
	public static final Item forcite = new Item()
			.setRegistryName(Reference.MOD_ID, "forcite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("forcite");
	public static final Item flamite = new Item()
			.setRegistryName(Reference.MOD_ID, "flamite")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("flamite");
	
	public static final ToolMaterial toolUndite = EnumHelper.addToolMaterial("tool_undite", 3, 2601, 14.0F, 4.1F, 10).setRepairItem(new ItemStack(NeoOres.undite));
	public static final ToolMaterial toolSalamite = EnumHelper.addToolMaterial("tool_salamite", 3, 1820, 9.8F, 12.0F, 7).setRepairItem(new ItemStack(NeoOres.salamite));
	public static final ToolMaterial toolSylphite = EnumHelper.addToolMaterial("tool_sylphite", 3, 1274, 6.9F, 8.4F, 20).setRepairItem(new ItemStack(NeoOres.sylphite));
	public static final ToolMaterial toolGnomite = EnumHelper.addToolMaterial("tool_gnomite", 3, 892, 20.0F, 5.9F, 14).setRepairItem(new ItemStack(NeoOres.gnomite_ingot));
	
	public static final ArmorMaterial armorUndite = EnumHelper.addArmorMaterial("armor_undite", "neo_ores:undite", 29, new int[] {5,8,10,6}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F).setRepairItem(new ItemStack(NeoOres.undite));
	public static final ArmorMaterial armorSalamite = EnumHelper.addArmorMaterial("armor_salamite", "neo_ores:salamite", 23, new int[] {3,7,9,4}, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.2F).setRepairItem(new ItemStack(NeoOres.salamite));
	public static final ArmorMaterial armorSylphite = EnumHelper.addArmorMaterial("armor_sylphite", "neo_ores:sylphite", 45, new int[] {2,6,7,3}, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.6F).setRepairItem(new ItemStack(NeoOres.sylphite));
	public static final ArmorMaterial armorGnomite = EnumHelper.addArmorMaterial("armor_gnomite", "neo_ores:gnomite", 36, new int[] {6,11,13,6}, 13, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F).setRepairItem(new ItemStack(NeoOres.gnomite_ingot));
	
	public static final Item undite_axe = new ItemNeoAxe(toolUndite)
	{
		public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
		{
			NeoOres.tierCalc.setWaterTier(itemStack, 1);
		}
	}
			.setRegistryName(Reference.MOD_ID, "undite_axe")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_axe");
	public static final Item undite_hoe = new ItemNeoHoe(toolUndite)
			.setRegistryName(Reference.MOD_ID, "undite_hoe")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_hoe");
	public static final Item undite_pickaxe = new ItemNeoPickaxe(toolUndite)
			.setRegistryName(Reference.MOD_ID, "undite_pickaxe")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_pickaxe");
	public static final Item undite_shovel = new ItemNeoSpade(toolUndite)
			.setRegistryName(Reference.MOD_ID, "undite_shovel")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_shovel");
	public static final Item undite_sword = new ItemNeoSword(toolUndite)
			.setRegistryName(Reference.MOD_ID, "undite_sword")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_sword");
	public static final Item salamite_axe = new ItemNeoAxe(toolSalamite)
			.setRegistryName(Reference.MOD_ID, "salamite_axe")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("salamite_axe");
	public static final Item salamite_hoe = new ItemNeoHoe(toolSalamite)
			.setRegistryName(Reference.MOD_ID, "salamite_hoe")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("salamite_hoe");
	public static final Item salamite_pickaxe = new ItemNeoPickaxe(toolSalamite)
			.setRegistryName(Reference.MOD_ID, "salamite_pickaxe")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("salamite_pickaxe");
	public static final Item salamite_shovel = new ItemNeoSpade(toolSalamite)
			.setRegistryName(Reference.MOD_ID, "salamite_shovel")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("salamite_shovel");
	public static final Item salamite_sword = new ItemNeoSword(toolSalamite)
			.setRegistryName(Reference.MOD_ID, "salamite_sword")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("salamite_sword");
	
	public static final Item undite_helmet = new ItemNeoArmor(NeoOres.armorUndite, 3, EntityEquipmentSlot.HEAD)
			.setRegistryName(Reference.MOD_ID, "undite_helmet")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_helmet");
	public static final Item undite_chestplate = new ItemNeoArmor(NeoOres.armorUndite, 3, EntityEquipmentSlot.CHEST)
			.setRegistryName(Reference.MOD_ID, "undite_chestplate")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_chestplate");
	public static final Item undite_leggings = new ItemNeoArmor(NeoOres.armorUndite, 3, EntityEquipmentSlot.LEGS)
			.setRegistryName(Reference.MOD_ID, "undite_leggings")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_leggings");
	public static final Item undite_boots = new ItemNeoArmor(NeoOres.armorUndite, 3, EntityEquipmentSlot.FEET)
			.setRegistryName(Reference.MOD_ID, "undite_boots")
			.setCreativeTab(NeoOres.neo_ores_tab)
			.setUnlocalizedName("undite_boots");
	
	@Mod.EventHandler
    public void construct(FMLConstructionEvent event) 
	{
        MinecraftForge.EVENT_BUS.register(this);
    }
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		NeoOresInfoCore.registerInfo(meta);
		MinecraftForge.EVENT_BUS.register(new NeoPlayerEvent());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
		IItemColor color = new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) 
			{
				if(stack.getItem() == NeoOres.air_essence_core)
				{
					int color_code = 0xFFFFFF;
					switch(stack.getMetadata()) 
					{
						case 0: color_code = 0xE0FFF8; break;
						case 1: color_code = 0xC0FFF2; break;
						case 2: color_code = 0xA0FFEC; break;
						case 3: color_code = 0x80FFE6; break;
						case 4: color_code = 0x60FFE0; break;
						case 5: color_code = 0x40FFDA; break;
						case 6: color_code = 0x20FFD4; break;
						case 7: color_code = 0x00FFCE; break;
					}
					return color_code;
				}
				else if(stack.getItem() == NeoOres.earth_essence_core)
				{
					int color_code = 0xFFFFFF;
					switch(stack.getMetadata()) 
					{
						case 0: color_code = 0xF6FFE0; break;
						case 1: color_code = 0xECFFC0; break;
						case 2: color_code = 0xE3FFA0; break;
						case 3: color_code = 0xDAFF80; break;
						case 4: color_code = 0xD1FF60; break;
						case 5: color_code = 0xC7FF40; break;
						case 6: color_code = 0xBEFF20; break;
						case 7: color_code = 0xB5FF00; break;
					}
					return color_code;
				}
				else if(stack.getItem() == NeoOres.fire_essence_core)
				{
					int color_code = 0xFFFFFF;
					switch(stack.getMetadata()) 
					{
						case 0: color_code = 0xFFE9E0; break;
						case 1: color_code = 0xFFD3C0; break;
						case 2: color_code = 0xFFBDA0; break;
						case 3: color_code = 0xFFA780; break;
						case 4: color_code = 0xFF9160; break;
						case 5: color_code = 0xFF7C40; break;
						case 6: color_code = 0xFF6220; break;
						case 7: color_code = 0xFF5200; break;
					}
					return color_code;
				}
				else if(stack.getItem() == NeoOres.water_essence_core)
				{
					int color_code = 0xFFFFFF;
					switch(stack.getMetadata()) 
					{
						case 0: color_code = 0xF0E0FF; break;
						case 1: color_code = 0xE1C0FF; break;
						case 2: color_code = 0xD2A0FF; break;
						case 3: color_code = 0xC380FF; break;
						case 4: color_code = 0xB460FF; break;
						case 5: color_code = 0xA540FF; break;
						case 6: color_code = 0x9620FF; break;
						case 7: color_code = 0x8700FF; break;
					}
					return color_code;
				}
				return 0xFFFFFF;
			}
		};
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, NeoOres.air_essence_core);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, NeoOres.earth_essence_core);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, NeoOres.fire_essence_core);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(color, NeoOres.water_essence_core);
		NetworkRegistry.INSTANCE.registerGuiHandler(NeoOres.instance, new GuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) 
	{
        event.getRegistry().register(NeoOres.undite_block);
        event.getRegistry().register(NeoOres.mana_block);
        event.getRegistry().register(NeoOres.mana_workbench);
		event.getRegistry().register(NeoOres.mana_furnace);
		event.getRegistry().register(NeoOres.lit_mana_furnace);
		
		GameRegistry.registerTileEntity(TileEntityManaFurnace.class, "mana_furnace");
    }
	
	@SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) 
	{		
		event.getRegistry().register(new ItemBlock(NeoOres.undite_block).setRegistryName(Reference.MOD_ID, "undite_block"));
		event.getRegistry().register(new ItemManaBlock(NeoOres.mana_block).setRegistryName(Reference.MOD_ID, "mana_block"));
		event.getRegistry().register(new ItemBlock(NeoOres.mana_workbench).setRegistryName(Reference.MOD_ID, "mana_workbench"));
		event.getRegistry().register(new ItemBlock(NeoOres.mana_furnace).setRegistryName(Reference.MOD_ID, "mana_furnace"));
		event.getRegistry().register(new ItemBlock(NeoOres.lit_mana_furnace).setRegistryName(Reference.MOD_ID, "lit_mana_furnace"));
		
        event.getRegistry().register(NeoOres.undite);
        event.getRegistry().register(NeoOres.gnomite_ingot);
        event.getRegistry().register(NeoOres.salamite);
        event.getRegistry().register(NeoOres.sylphite);
        event.getRegistry().register(NeoOres.mana_ingot);
        event.getRegistry().register(NeoOres.essence);
        event.getRegistry().register(NeoOres.air_essence_core);
        event.getRegistry().register(NeoOres.earth_essence_core);
        event.getRegistry().register(NeoOres.fire_essence_core);
        event.getRegistry().register(NeoOres.water_essence_core);
        event.getRegistry().register(NeoOres.sanitite);
        event.getRegistry().register(NeoOres.marlite_ingot);
        event.getRegistry().register(NeoOres.aerite);
        event.getRegistry().register(NeoOres.drenite);
        event.getRegistry().register(NeoOres.guardite_ingot);
        event.getRegistry().register(NeoOres.landite_ingot);
        event.getRegistry().register(NeoOres.flamite);
        event.getRegistry().register(NeoOres.forcite);
        
        event.getRegistry().register(NeoOres.undite_axe);
        event.getRegistry().register(NeoOres.undite_hoe);
        event.getRegistry().register(NeoOres.undite_pickaxe);
        event.getRegistry().register(NeoOres.undite_shovel);
        event.getRegistry().register(NeoOres.undite_sword);
        event.getRegistry().register(NeoOres.undite_helmet);
        event.getRegistry().register(NeoOres.undite_chestplate);
        event.getRegistry().register(NeoOres.undite_leggings);
        event.getRegistry().register(NeoOres.undite_boots);       
	}
	
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void registerModels(ModelRegistryEvent event) 
    {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.undite_block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_block"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.mana_block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "mana_block"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.mana_workbench), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "mana_workbench"), "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.mana_furnace), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "mana_furnace"), "facing=east"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.mana_furnace), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "mana_furnace"), "facing=west"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.mana_furnace), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "mana_furnace"), "facing=north"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.lit_mana_furnace), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "lit_mana_furnace"), "facing=east"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.lit_mana_furnace), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "lit_mana_furnace"), "facing=west"));	
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NeoOres.lit_mana_furnace), 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "lit_mana_furnace"), "facing=north"));	
    	
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.gnomite_ingot, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "gnomite_ingot"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.salamite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "salamite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.sylphite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "sylphite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.mana_ingot, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "mana_ingot"), "inventory"));
        for(int i = 0;i < 4;i++) ModelLoader.setCustomModelResourceLocation(NeoOres.essence, i, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "essence." + i), "inventory"));
        for(int i = 0;i < 8;i++) ModelLoader.setCustomModelResourceLocation(NeoOres.air_essence_core, i, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "essence_core"), "inventory"));
        for(int i = 0;i < 8;i++) ModelLoader.setCustomModelResourceLocation(NeoOres.earth_essence_core, i, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "essence_core"), "inventory"));
        for(int i = 0;i < 8;i++) ModelLoader.setCustomModelResourceLocation(NeoOres.fire_essence_core, i, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "essence_core"), "inventory"));
        for(int i = 0;i < 8;i++) ModelLoader.setCustomModelResourceLocation(NeoOres.water_essence_core, i, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "essence_core"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.sanitite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "sanitite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.marlite_ingot, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "marlite_ingot"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.aerite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "aerite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.drenite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "drenite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.guardite_ingot, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "guardite_ingot"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.landite_ingot, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "landite_ingot"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.flamite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "flamite"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.forcite, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "forcite"), "inventory"));
        
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_axe, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_axe"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_hoe, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_hoe"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_pickaxe, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_pickaxe"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_shovel, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_shovel"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_sword, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_sword"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_helmet, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_helmet"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_chestplate, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_chestplate"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_leggings, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_leggings"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(NeoOres.undite_boots, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "undite_boots"), "inventory"));
    }
}
