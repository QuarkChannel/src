package neo_ores.items;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemNeoArmor extends ItemArmor
{

	public ItemNeoArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
	}
	public void addInformation(ItemStack itemStack, World world, List<String> list, ITooltipFlag flag)
	{
		if(itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("tiers", 9))
		{
			NBTTagList NBTList = itemStack.getTagCompound().getTagList("tiers", 10);
			if(NBTList != null && NBTList.getCompoundTagAt(0) != null)
			{
				NBTTagCompound nbt = NBTList.getCompoundTagAt(0);
				if(nbt.hasKey("air") && nbt.hasKey("earth") && nbt.hasKey("fire") && nbt.hasKey("water"))
				{
					boolean tipflag = false;
					
					if(nbt.getInteger("water") > 0)
					{
						for(int i = 0;i < list.size();i++)
						{
							List<String> spList = Arrays.asList(list.get(i).split(" "));
							for(int j = 0;j < spList.size();j++)
							{
								if(spList.get(j).equals(TextFormatting.DARK_PURPLE + I18n.translateToLocal("armortip.water").trim()))
								{
									list.remove(i);
									list.add(i, TextFormatting.DARK_PURPLE + I18n.translateToLocal("armortip.water").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("water")).trim());
									tipflag = true;
									break;
								}
								if(tipflag) break;
							}
						}			
						if(!tipflag) list.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("armortip.water").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("water")).trim());
						else tipflag = false;
					}
					
					if(nbt.getInteger("fire") > 0)
					{
						for(int i = 0;i < list.size();i++)
						{
							List<String> spList = Arrays.asList(list.get(i).split(" "));
							for(int j = 0;j < spList.size();j++)
							{
								if(spList.get(j).equals(TextFormatting.GOLD + I18n.translateToLocal("armortip.fire").trim()))
								{
									list.remove(i);
									list.add(i, TextFormatting.GOLD + I18n.translateToLocal("armortip.fire").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("fire")).trim());
									tipflag = true;
									break;
								}
								if(tipflag) break;
							}
						}			
						if(!tipflag) list.add(TextFormatting.GOLD + I18n.translateToLocal("armortip.fire").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("fire")).trim());
						else tipflag = false;
					}
					
					if(nbt.getInteger("earth") > 0)
					{
						for(int i = 0;i < list.size();i++)
						{
							List<String> spList = Arrays.asList(list.get(i).split(" "));
							for(int j = 0;j < spList.size();j++)
							{
								if(spList.get(j).equals(TextFormatting.GREEN + I18n.translateToLocal("armortip.earth").trim()))
								{
									list.remove(i);
									list.add(i, TextFormatting.GREEN + I18n.translateToLocal("armortip.earth").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("earth")).trim());
									tipflag = true;
									break;
								}
								if(tipflag) break;
							}
						}			
						if(!tipflag) list.add(TextFormatting.GREEN + I18n.translateToLocal("armortip.earth").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("earth")).trim());
						else tipflag = false;
					}
					
					if(nbt.getInteger("air") > 0)
					{
						for(int i = 0;i < list.size();i++)
						{
							List<String> spList = Arrays.asList(list.get(i).split(" "));
							for(int j = 0;j < spList.size();j++)
							{
								if(spList.get(j).equals(TextFormatting.AQUA + I18n.translateToLocal("armortip.air").trim()))
								{
									list.remove(i);
									list.add(i, TextFormatting.AQUA + I18n.translateToLocal("armortip.air").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("air")).trim());
									tipflag = true;
									break;
								}
								if(tipflag) break;
							}
						}			
						if(!tipflag) list.add(TextFormatting.AQUA + I18n.translateToLocal("armortip.air").trim() + " " + I18n.translateToLocal("tier." + nbt.getInteger("air")).trim());
					}
				}
			}	
		}
	}
}
