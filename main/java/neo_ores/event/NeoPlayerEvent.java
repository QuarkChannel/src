package neo_ores.event;

import java.util.ListIterator;

import neo_ores.gui.GuiNeoGameOverlay;
import neo_ores.main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class NeoPlayerEvent 
{	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onRightClickItem(PlayerInteractEvent.RightClickItem event)
	{
		if(!event.getEntityPlayer().world.isRemote && event.getEntityPlayer() != null && event.getItemStack().getItem() instanceof ItemArmor && event.getItemStack().equals(event.getEntityPlayer().getHeldItemMainhand()))
		{
			ItemArmor armor = (ItemArmor)event.getItemStack().getItem();
			ItemStack headItem = event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			if(headItem != ItemStack.EMPTY && armor.getEquipmentSlot() == EntityEquipmentSlot.HEAD && headItem.getTagCompound() != null)
			{
				NBTTagList headNBTList = headItem.getTagCompound().getTagList("neo_ores", 10);
				if(headNBTList != null && headNBTList.getCompoundTagAt(0) != null)
				{
					ItemStack item = event.getItemStack().copy();
					NBTTagCompound headNBT = headNBTList.getCompoundTagAt(0);
					if(headNBT.hasKey("ML") && headNBT.hasKey("maxMP") && headNBT.hasKey("MP") && headNBT.hasKey("MXP") && headNBT.hasKey("isSoulBound"))
					{
						//set tag of held item from head item and put held item on head 
						int ML = headNBT.getInteger("ML");
						int maxMP = headNBT.getInteger("maxMP");
						int MP = headNBT.getInteger("MP");
						int MXP = headNBT.getInteger("MXP");
						int isSoulBound = headNBT.getInteger("isSoulBound");
						event.getEntityPlayer().inventory.setInventorySlotContents(39,item);
						ItemStack headItemFromHand = event.getEntityPlayer().getItemStackFromSlot(EntityEquipmentSlot.HEAD);
						if(headItemFromHand.getTagCompound() == null)
						{
							headItemFromHand.setTagCompound(new NBTTagCompound());
						}

						if(!headItemFromHand.getTagCompound().hasKey("neo_ores", 9))
						{
							headItemFromHand.getTagCompound().setTag("neo_ores", new NBTTagList());
						}
						NBTTagList tag = headItemFromHand.getTagCompound().getTagList("neo_ores", 10);
						NBTTagCompound itemNBT = new NBTTagCompound();
						itemNBT.setInteger("ML", ML);
						itemNBT.setInteger("maxMP", maxMP);
						itemNBT.setInteger("MP", MP);
						itemNBT.setInteger("MXP", MXP);
						itemNBT.setInteger("isSoulBound", isSoulBound);
						if(tag.tagCount() > 0)
						{
							tag.set(0, itemNBT);
						}
						else
						{
							tag.appendTag(itemNBT);
						}
						headItemFromHand.getTagCompound().setBoolean("Unbreakable", true);
						headItemFromHand.addEnchantment(Enchantment.getEnchantmentByID(10), 1);
						//clear held item
						NBTTagCompound nbtClear = new NBTTagCompound();
						nbtClear.setBoolean("clear", true);
						event.getEntityPlayer().getHeldItemMainhand().setTagCompound(nbtClear);
						event.getEntityPlayer().inventory.clearMatchingItems(event.getEntityPlayer().getHeldItemMainhand().getItem(), event.getEntityPlayer().getHeldItemMainhand().getMetadata(), event.getEntityPlayer().getHeldItemMainhand().getCount(), event.getEntityPlayer().getHeldItemMainhand().getTagCompound());
						//give old head item and clear tag
						NBTTagCompound headNBTOld = headItem.getTagCompound();
						headNBTOld.removeTag("ench");
						headNBTOld.removeTag("neo_ores");
						if(isSoulBound == 1)
						{
							headNBTOld.removeTag("Unbreakable");
						}
						event.getEntityPlayer().addItemStackToInventory(headItem.copy());
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingEvent(LivingEvent event)
	{
		if(event.getEntity() != null && !event.getEntity().getEntityWorld().isRemote && event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			if(!player.getEntityWorld().isRemote && player.hasItemInSlot(EntityEquipmentSlot.HEAD));
			{
				ItemStack headItem = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				if(headItem.getTagCompound() == null)
				{
					return;
				}

				if(!headItem.getTagCompound().hasKey("neo_ores", 9))
				{
					return;
				}
				
				NBTTagList headNBTList = headItem.getTagCompound().getTagList("neo_ores", 10);
				if(headNBTList != null && headNBTList.getCompoundTagAt(0) != null)
				{
					NBTTagCompound nbt = headNBTList.getCompoundTagAt(0);
					if(nbt.hasKey("ML") && nbt.hasKey("maxMP") && nbt.hasKey("MP") && nbt.hasKey("MXP") && nbt.hasKey("isSoulBound"))
					{
						float ML = (float)nbt.getInteger("ML");
						int maxMP = nbt.getInteger("maxMP");
						int MP = nbt.getInteger("MP");
						int MXP = nbt.getInteger("MXP");
						float MXPBar = 1.003F;
						for(int i = 0;i < ML;i++)
						{
							MXPBar *= 1.003F;
						}
						
						if(16 * MXPBar < MXP)
						{
							ML++;
							maxMP += (int)(ML * ML * ML * 0.0000005F + 1.0F);
							MXP -= (16 * MXPBar);
						}
						
						if(player.ticksExisted % 40 == 0)
						{
							MP += (int)(ML * ML * 0.0007F + 1.0F);
						}
						
						if(MP > maxMP)
						{
							MP = maxMP;
						}
						
						//setNBT
						NBTTagCompound itemNBT = new NBTTagCompound();
						itemNBT.setInteger("ML", (int)ML);
						itemNBT.setInteger("maxMP", maxMP);
						itemNBT.setInteger("MP", MP);
						itemNBT.setInteger("MXP", MXP);
						itemNBT.setInteger("isSoulBound", nbt.getInteger("isSoulBound"));
						headNBTList.set(0, itemNBT);
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(event.getEntityLiving() != null && event.getSource().getImmediateSource() != null && event.getSource().getImmediateSource() instanceof EntityPlayer)
		{
			EntityPlayer player = ((EntityPlayer)event.getSource().getImmediateSource());
			if(!player.world.isRemote && player.hasItemInSlot(EntityEquipmentSlot.HEAD))
			{
				ItemStack headItem = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				if(headItem.getTagCompound() == null)
				{
					return;
				}

				if(!headItem.getTagCompound().hasKey("neo_ores", 9))
				{
					return;
				}
				
				NBTTagList headNBTList = headItem.getTagCompound().getTagList("neo_ores", 10);
				if(headNBTList != null && headNBTList.getCompoundTagAt(0) != null)
				{
					NBTTagCompound nbt = headNBTList.getCompoundTagAt(0);
					if(nbt.hasKey("ML") && nbt.hasKey("maxMP") && nbt.hasKey("MP") && nbt.hasKey("MXP") && nbt.hasKey("isSoulBound"))
					{
						int ML = nbt.getInteger("ML");
						int maxMP = nbt.getInteger("maxMP");
						int MP = nbt.getInteger("MP");
						int MXP = nbt.getInteger("MXP");
						System.out.print("+XP!(Now ML:" + ML + ",maxMP:" + maxMP + ",MP:" + MP + ",XP:" + MXP + ")\n");
						//setNBT
						NBTTagCompound itemNBT = new NBTTagCompound();
						itemNBT.setInteger("ML", ML);
						itemNBT.setInteger("maxMP", maxMP);
						itemNBT.setInteger("MP", MP);
						itemNBT.setInteger("MXP", MXP + 10);
						itemNBT.setInteger("isSoulBound", nbt.getInteger("isSoulBound"));
						headNBTList.set(0, itemNBT);
					}
				}
			}
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onPlayerDeath(PlayerDropsEvent event)
	{
	    if ((event.getEntityPlayer() == null) || ((event.getEntityPlayer() instanceof FakePlayer)) || (event.isCanceled())) 
	    {
	    	return;
	    }
	    if (event.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) 
	    {
	    	return;
	    }
	    ListIterator<EntityItem> iter = event.getDrops().listIterator();
	    while (iter.hasNext())
	    {
	    	EntityItem ei = (EntityItem)iter.next();
	    	ItemStack stack = ei.getItem();
	    	if (stack.getTagCompound() != null)
	    	{
	    		if(stack.getTagCompound().hasKey("neo_ores", 9))
	    		{
	    			NBTTagList headNBTList = stack.getTagCompound().getTagList("neo_ores", 10);
	    			if(headNBTList != null && headNBTList.getCompoundTagAt(0) != null)
	    			{
	    				NBTTagCompound nbt = headNBTList.getCompoundTagAt(0);
						if(nbt.hasKey("ML") && nbt.hasKey("maxMP") && nbt.hasKey("MP") && nbt.hasKey("MXP") && nbt.hasKey("isSoulBound"))
						{
							event.getEntityPlayer().inventory.add(39, stack);
				    		iter.remove();
						}
	    			}
	    		}
	    	}
	    }
	}
	  
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onPlayerClone(PlayerEvent.Clone evt)
	{
	    if ((!evt.isWasDeath()) || (evt.isCanceled())) 
	    {
	    	return;
	    }
	    if ((evt.getOriginal() == null) || (evt.getEntityPlayer() == null) || ((evt.getEntityPlayer() instanceof FakePlayer))) 
	    {
	    	return;
	    }
	    if (evt.getEntityPlayer().getEntityWorld().getGameRules().getBoolean("keepInventory")) 
	    {
	    	return;
	    }
	    for (int i = 0; i < evt.getOriginal().inventory.armorInventory.size(); i++)
	    {
	    	ItemStack stack = (ItemStack)evt.getOriginal().inventory.armorInventory.get(i);
	    	if (stack.getTagCompound() != null)
	    	{
	    		if(stack.getTagCompound().hasKey("neo_ores", 9))
	    		{
	    			NBTTagList headNBTList = stack.getTagCompound().getTagList("neo_ores", 10);
	    			if(headNBTList != null && headNBTList.getCompoundTagAt(0) != null)
	    			{
	    				NBTTagCompound nbt = headNBTList.getCompoundTagAt(0);
						if(nbt.hasKey("ML") && nbt.hasKey("maxMP") && nbt.hasKey("MP") && nbt.hasKey("MXP") && nbt.hasKey("isSoulBound"))
						{
							evt.getEntityPlayer().inventory.add(39, stack);
				    		evt.getOriginal().inventory.armorInventory.set(i, ItemStack.EMPTY);
						}
	    			}
	    		}
	    	}
	    }
	}
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		if (event.getType() == ElementType.ALL) 
		{
			new GuiNeoGameOverlay(Minecraft.getMinecraft());
		}
	}
}