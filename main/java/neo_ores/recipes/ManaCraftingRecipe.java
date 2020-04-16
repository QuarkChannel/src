package neo_ores.recipes;

import java.util.ArrayList;
import java.util.List;

import neo_ores.main.NeoOres;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ManaCraftingRecipe 
{	
	List<Integer> mana = new ArrayList<Integer>();
	List<ItemStack> resultList = new ArrayList<ItemStack>();
	List<ItemStack> slot0 = new ArrayList<ItemStack>();
	List<ItemStack> slot1 = new ArrayList<ItemStack>();
	List<ItemStack> slot2 = new ArrayList<ItemStack>();
	List<ItemStack> slot3 = new ArrayList<ItemStack>();
	List<ItemStack> slot4 = new ArrayList<ItemStack>();
	List<ItemStack> slot5 = new ArrayList<ItemStack>();
	List<ItemStack> slot6 = new ArrayList<ItemStack>();
	List<ItemStack> slot7 = new ArrayList<ItemStack>();
	List<ItemStack> slot8 = new ArrayList<ItemStack>();
	
	public ManaCraftingRecipe() {}
	
	private void register()
	{
		this.addManaShapedRecipe(ItemStack.EMPTY, 0, -3,ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
		ItemStack undite_axe = new ItemStack(NeoOres.undite_axe); NBTTagCompound nbt_undite_axe = new NBTTagCompound(); nbt_undite_axe.setInteger("tier", 1); undite_axe.setTagCompound(nbt_undite_axe);
		this.addManaShapedRecipe(undite_axe, 100, 4,new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(Items.STICK), ItemStack.EMPTY,new ItemStack(Items.STICK));
		this.addManaShapedRecipe(new ItemStack(NeoOres.undite_block), 1, -3, new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite), new ItemStack(NeoOres.undite));
		this.addManaShapedRecipe(new ItemStack(NeoOres.undite,9), 1, 0, new ItemStack(NeoOres.undite_block));
		this.addManaShapedRecipe(new ItemStack(NeoOres.mana_workbench), 1, -1,new ItemStack(NeoOres.undite_block),new ItemStack(NeoOres.undite));
		this.addManaShapedRecipe(new ItemStack(NeoOres.undite_shovel), 1, 2, new ItemStack(NeoOres.undite),new ItemStack(Items.STICK),new ItemStack(Items.STICK));
		this.addManaShapedRecipe(new ItemStack(Blocks.PURPUR_SLAB,8), 1, 3, new ItemStack(Blocks.PURPUR_BLOCK),new ItemStack(Blocks.PURPUR_BLOCK),new ItemStack(Blocks.PURPUR_BLOCK),new ItemStack(Blocks.PURPUR_BLOCK));
	}
	
	public ItemStack getManaCraftingResult(ItemStack item0,ItemStack item1,ItemStack item2,ItemStack item3,ItemStack item4,ItemStack item5,ItemStack item6,ItemStack item7,ItemStack item8)
	{
		this.register();
		
		for(int i = 0;i < slot0.size();i++)
		{
			if(this.compareItemStacks(slot0.get(i), item0) 
					&& this.compareItemStacks(slot1.get(i), item1) 
					&& this.compareItemStacks(slot2.get(i), item2) 
					&& this.compareItemStacks(slot3.get(i), item3) 
					&& this.compareItemStacks(slot4.get(i), item4) 
					&& this.compareItemStacks(slot5.get(i), item5) 
					&& this.compareItemStacks(slot6.get(i), item6) 
					&& this.compareItemStacks(slot7.get(i), item7) 
					&& this.compareItemStacks(slot8.get(i), item8))
			{
				return resultList.get(i).copy();
			}
		}
		return ItemStack.EMPTY;
	}
	
	public int getManaCraftingValue(ItemStack item0,ItemStack item1,ItemStack item2,ItemStack item3,ItemStack item4,ItemStack item5,ItemStack item6,ItemStack item7,ItemStack item8)
	{
		this.register();
		
		for(int i = 0;i < slot0.size();i++)
		{
			if(this.compareItemStacks(slot0.get(i), item0) 
					&& this.compareItemStacks(slot1.get(i), item1) 
					&& this.compareItemStacks(slot2.get(i), item2) 
					&& this.compareItemStacks(slot3.get(i), item3) 
					&& this.compareItemStacks(slot4.get(i), item4) 
					&& this.compareItemStacks(slot5.get(i), item5) 
					&& this.compareItemStacks(slot6.get(i), item6) 
					&& this.compareItemStacks(slot7.get(i), item7) 
					&& this.compareItemStacks(slot8.get(i), item8))
			{
				return mana.get(i);
			}
		}
		return 0;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
	
	/**
	 * Type....
	 * 0.... "0"
	 * 1.... "0","1"
	 * -1... "01"
	 * 2.... "0","1","2"
	 * -2... "012"
	 * 3.... "01","23"
	 * 4.... "01","23","45"
	 * -4... "012","345"
	 * -3... "012","345","678"
	*/
	public void addManaShapedRecipe(ItemStack result,int value,int type,Object...objects)
	{
		for(int a = 0;a < objects.length;a++)
		{
			if(!(objects[a] instanceof ItemStack))
			{
				return;
			}
		}
		
		if(objects.length < 10)
		{
			if(type == 0 && objects.length == 1)
			{
				for(int i = 0;i < 9;i++) resultList.add(result);
				for(int i = 0;i < 9;i++) mana.add(value);
				for(int i = 0;i < 9;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 1) slot1.add((ItemStack)objects[0]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 2) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 3) slot3.add((ItemStack)objects[0]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 4) slot4.add((ItemStack)objects[0]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 5) slot5.add((ItemStack)objects[0]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 6) slot6.add((ItemStack)objects[0]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 7) slot7.add((ItemStack)objects[0]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 9;i++) if(i == 8) slot8.add((ItemStack)objects[0]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == 1 && objects.length == 2)
			{
				for(int i = 0;i < 6;i++) resultList.add(result);
				for(int i = 0;i < 6;i++) mana.add(value);
				for(int i = 0;i < 6;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 1) slot1.add((ItemStack)objects[0]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 2) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 3) slot3.add((ItemStack)objects[0]); else if(i == 0) slot3.add((ItemStack)objects[1]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 4) slot4.add((ItemStack)objects[0]); else if(i == 1) slot4.add((ItemStack)objects[1]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 5) slot5.add((ItemStack)objects[0]); else if(i == 2) slot5.add((ItemStack)objects[1]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 3) slot6.add((ItemStack)objects[1]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 4) slot7.add((ItemStack)objects[1]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 5) slot8.add((ItemStack)objects[1]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == -1 && objects.length == 2)
			{
				for(int i = 0;i < 12;i++) resultList.add(result);
				for(int i = 0;i < 12;i++) mana.add(value);
				for(int i = 0;i < 12;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else if(i == 6) slot0.add((ItemStack)objects[1]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 1) slot1.add((ItemStack)objects[0]); else if(i == 7) slot1.add((ItemStack)objects[1]); else if(i == 0) slot1.add((ItemStack)objects[1]); else if(i == 6) slot1.add((ItemStack)objects[0]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 1) slot2.add((ItemStack)objects[1]); else if(i == 7) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 2) slot3.add((ItemStack)objects[0]); else if(i == 8) slot3.add((ItemStack)objects[1]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 3) slot4.add((ItemStack)objects[0]); else if(i == 9) slot4.add((ItemStack)objects[1]); else if(i == 2) slot4.add((ItemStack)objects[1]); else if(i == 8) slot4.add((ItemStack)objects[0]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 3) slot5.add((ItemStack)objects[1]); else if(i == 9) slot5.add((ItemStack)objects[0]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 4) slot6.add((ItemStack)objects[0]); else if(i == 10) slot6.add((ItemStack)objects[1]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 5) slot7.add((ItemStack)objects[0]); else if(i == 11) slot7.add((ItemStack)objects[1]); else if(i == 4) slot7.add((ItemStack)objects[1]); else if(i == 10) slot7.add((ItemStack)objects[0]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 12;i++) if(i == 5) slot8.add((ItemStack)objects[1]); else if(i == 11) slot8.add((ItemStack)objects[0]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == 2 && objects.length == 3)
			{
				for(int i = 0;i < 3;i++) resultList.add(result);
				for(int i = 0;i < 3;i++) mana.add(value);
				for(int i = 0;i < 3;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 1) slot1.add((ItemStack)objects[0]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 2) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 0) slot3.add((ItemStack)objects[1]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 1) slot4.add((ItemStack)objects[1]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 2) slot5.add((ItemStack)objects[1]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 0) slot6.add((ItemStack)objects[2]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 1) slot7.add((ItemStack)objects[2]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 3;i++) if(i == 2) slot8.add((ItemStack)objects[2]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == -2 && objects.length == 3)
			{
				for(int i = 0;i < 6;i++) resultList.add(result);
				for(int i = 0;i < 6;i++) mana.add(value);
				for(int i = 0;i < 6;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else if(i == 3) slot0.add((ItemStack)objects[2]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 0 || i == 3) slot1.add((ItemStack)objects[1]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 0) slot2.add((ItemStack)objects[2]); else if(i == 3) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 1) slot3.add((ItemStack)objects[0]); else if(i == 4) slot3.add((ItemStack)objects[2]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 1 || i == 4) slot4.add((ItemStack)objects[1]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 1) slot5.add((ItemStack)objects[2]); else if(i == 4) slot5.add((ItemStack)objects[0]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 2) slot6.add((ItemStack)objects[0]); else if(i == 5) slot6.add((ItemStack)objects[2]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 2 || i == 5) slot7.add((ItemStack)objects[1]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 6;i++) if(i == 2) slot8.add((ItemStack)objects[2]); else if(i == 5) slot8.add((ItemStack)objects[0]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == 3 && objects.length == 4)
			{
				for(int i = 0;i < 8;i++) resultList.add(result);
				for(int i = 0;i < 8;i++) mana.add(value);
				for(int i = 0;i < 8;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else if(i == 4) slot0.add((ItemStack)objects[1]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 0) slot1.add((ItemStack)objects[1]); else if(i == 1) slot1.add((ItemStack)objects[0]); else if(i == 4) slot1.add((ItemStack)objects[0]); else if(i == 5) slot1.add((ItemStack)objects[1]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 1) slot2.add((ItemStack)objects[1]); else if(i == 5) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 0) slot3.add((ItemStack)objects[2]); else if(i == 2) slot3.add((ItemStack)objects[0]); else if(i == 4) slot3.add((ItemStack)objects[3]); else if(i == 6) slot3.add((ItemStack)objects[1]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 0) slot4.add((ItemStack)objects[3]); else if(i == 1) slot4.add((ItemStack)objects[2]); else if(i == 2) slot4.add((ItemStack)objects[1]); else if(i == 3) slot4.add((ItemStack)objects[0]); else if(i == 4) slot4.add((ItemStack)objects[2]); else if(i == 5) slot4.add((ItemStack)objects[3]); else if(i == 6) slot4.add((ItemStack)objects[0]); else if(i == 7) slot4.add((ItemStack)objects[1]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 1) slot5.add((ItemStack)objects[3]); else if(i == 3) slot5.add((ItemStack)objects[1]); else if(i == 5) slot5.add((ItemStack)objects[2]); else if(i == 7) slot5.add((ItemStack)objects[0]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 2) slot6.add((ItemStack)objects[2]); else if(i == 6) slot6.add((ItemStack)objects[3]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 2) slot7.add((ItemStack)objects[3]); else if(i == 3) slot7.add((ItemStack)objects[2]); else if(i == 6) slot7.add((ItemStack)objects[2]); else if(i == 7) slot7.add((ItemStack)objects[3]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 8;i++) if(i == 3) slot8.add((ItemStack)objects[3]); else if(i == 7) slot8.add((ItemStack)objects[2]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == 4 && objects.length == 6)
			{
				for(int i = 0;i < 4;i++) resultList.add(result);
				for(int i = 0;i < 4;i++) mana.add(value);
				for(int i = 0;i < 4;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else if(i == 2) slot0.add((ItemStack)objects[1]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot1.add((ItemStack)objects[0]); else if(i == 0) slot1.add((ItemStack)objects[1]); else if(i == 3) slot1.add((ItemStack)objects[1]); else if(i == 2) slot1.add((ItemStack)objects[0]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot2.add((ItemStack)objects[1]); else if(i == 3) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0) slot3.add((ItemStack)objects[2]); else if(i == 2) slot3.add((ItemStack)objects[3]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot4.add((ItemStack)objects[2]); else if(i == 0) slot4.add((ItemStack)objects[3]); else if(i == 3) slot4.add((ItemStack)objects[3]); else if(i == 2) slot4.add((ItemStack)objects[2]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot5.add((ItemStack)objects[3]); else if(i == 3) slot5.add((ItemStack)objects[2]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0) slot6.add((ItemStack)objects[4]); else if(i == 2) slot6.add((ItemStack)objects[5]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot7.add((ItemStack)objects[4]); else if(i == 0) slot7.add((ItemStack)objects[5]); else if(i == 3) slot7.add((ItemStack)objects[5]); else if(i == 2) slot7.add((ItemStack)objects[4]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot8.add((ItemStack)objects[5]); else if(i == 3) slot8.add((ItemStack)objects[4]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == -4 && objects.length == 6)
			{
				for(int i = 0;i < 4;i++) resultList.add(result);
				for(int i = 0;i < 4;i++) mana.add(value);
				for(int i = 0;i < 4;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else if(i == 2) slot0.add((ItemStack)objects[2]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0 || i == 2) slot1.add((ItemStack)objects[1]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0) slot2.add((ItemStack)objects[2]); else if(i == 2) slot0.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0) slot3.add((ItemStack)objects[3]); else if(i == 1) slot3.add((ItemStack)objects[0]); else if(i == 2) slot3.add((ItemStack)objects[5]); else if(i == 3) slot3.add((ItemStack)objects[2]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0 || i == 2) slot4.add((ItemStack)objects[4]); else if(i == 1 || i == 3) slot4.add((ItemStack)objects[1]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 0) slot5.add((ItemStack)objects[5]); else if(i == 1) slot5.add((ItemStack)objects[2]); else if(i == 2) slot5.add((ItemStack)objects[3]); else if(i == 3) slot5.add((ItemStack)objects[0]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot6.add((ItemStack)objects[3]); else if(i == 3) slot6.add((ItemStack)objects[5]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1 || i == 3) slot7.add((ItemStack)objects[4]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 4;i++) if(i == 1) slot8.add((ItemStack)objects[5]); else if(i == 3) slot8.add((ItemStack)objects[3]); else slot8.add(ItemStack.EMPTY);
			}
			else if(type == -3 && objects.length == 9)
			{
				for(int i = 0;i < 2;i++) resultList.add(result);
				for(int i = 0;i < 2;i++) mana.add(value);
				for(int i = 0;i < 2;i++) if(i == 0) slot0.add((ItemStack)objects[0]); else if(i == 1) slot0.add((ItemStack)objects[2]); else slot0.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0 || i == 1) slot1.add((ItemStack)objects[1]); else slot1.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0) slot2.add((ItemStack)objects[2]); else if(i == 1) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0) slot3.add((ItemStack)objects[3]); else if(i == 1) slot3.add((ItemStack)objects[5]); else slot3.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0 || i == 1) slot4.add((ItemStack)objects[4]); else slot4.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0) slot5.add((ItemStack)objects[5]); else if(i == 1) slot5.add((ItemStack)objects[3]); else slot5.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0) slot6.add((ItemStack)objects[6]); else if(i == 1) slot6.add((ItemStack)objects[8]); else slot6.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0 || i == 1) slot7.add((ItemStack)objects[7]); else slot7.add(ItemStack.EMPTY);
				for(int i = 0;i < 2;i++) if(i == 0) slot8.add((ItemStack)objects[8]); else if(i == 1) slot8.add((ItemStack)objects[6]); else slot8.add(ItemStack.EMPTY);
			}
			else
			{
				return;
			}
		}
	}
	
	public void addManaShapelessRecipe(ItemStack result,int value,Object...objects)
	{
		for(int a = 0;a < objects.length;a++)
		{
			if(!(objects[a] instanceof ItemStack))
			{
				return;
			}
		}
		
		if(objects.length == 1)
		{
			for(int a = 0;a < 9;a++) resultList.add(result);
			for(int a = 0;a < 9;a++) mana.add(value);
			for(int a = 0;a < 9;a++) if(a == 0) slot0.add((ItemStack)objects[0]); else slot0.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 1) slot1.add((ItemStack)objects[0]); else slot1.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 2) slot2.add((ItemStack)objects[0]); else slot2.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 3) slot3.add((ItemStack)objects[0]); else slot3.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 4) slot4.add((ItemStack)objects[0]); else slot4.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 5) slot5.add((ItemStack)objects[0]); else slot5.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 6) slot6.add((ItemStack)objects[0]); else slot6.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 7) slot7.add((ItemStack)objects[0]); else slot7.add(ItemStack.EMPTY);
			for(int a = 0;a < 9;a++) if(a == 8) slot8.add((ItemStack)objects[0]); else slot8.add(ItemStack.EMPTY);
		}
		else if(objects.length == 2)
		{
			for(int a = 0;a < 72;a++) resultList.add(result);
			for(int a = 0;a < 72;a++) mana.add(value);
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 0;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 1;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 2;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 3;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 4;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 5;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 6;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 7;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					int k = 8;
					if(a == k) this.getList(k).add((ItemStack)objects[0]); 
					else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
					else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
					else this.getList(k).add(ItemStack.EMPTY);
				}
			}
		}
		else if(objects.length == 3)
		{
			for(int a = 0;a < 504;a++) resultList.add(result);
			for(int a = 0;a < 504;a++) mana.add(value);
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 0;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 1;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 2;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 3;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 4;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 5;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 6;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}			
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 7;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
			for(int a = 0;a < 9;a++) 
			{
				for(int b = 0;b < 8;b++) 
				{
					for(int c = 0;c < 7;c++)
					{
						int k = 8;
						if(a == k) this.getList(k).add((ItemStack)objects[0]); 
						else if(b == k - 1 && a < k) this.getList(k).add((ItemStack)objects[1]);
						else if(b == k && a > k) this.getList(k).add((ItemStack)objects[1]);
						else if(c == k - 2 && a < k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k - 1 && a > k && b < k) this.getList(k).add((ItemStack)objects[2]);
						else if(c == k && a > k && b > k) this.getList(k).add((ItemStack)objects[2]);
						else this.getList(k).add(ItemStack.EMPTY);
					}
				}
			}
		}
		else
		{
			return;
		}
	}
	
	private List<ItemStack> getList(int k)
	{
		if(k == 0) return slot0;
		else if(k == 1) return slot1;
		else if(k == 2) return slot2;
		else if(k == 3) return slot3;
		else if(k == 4) return slot4;
		else if(k == 5) return slot5;
		else if(k == 6) return slot6;
		else if(k == 7) return slot7;
		else if(k == 8) return slot8;
		else return new ArrayList<ItemStack>();
	}
}
