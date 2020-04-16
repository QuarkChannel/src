package neo_ores.items;

import neo_ores.main.NeoOres;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemEssence extends Item
{
	public ItemEssence()
    {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(NeoOres.neo_ores_tab);
    }
	
	public String getUnlocalizedName(ItemStack stack)
    {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + "." + i;
    }
	
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 4; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if(!player.hasItemInSlot(EntityEquipmentSlot.HEAD))
		{
			player.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
		}
		
		ItemStack headItem = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		if(headItem.getTagCompound() == null)
		{
			headItem.setTagCompound(new NBTTagCompound());
		}

		if(!headItem.getTagCompound().hasKey("neo_ores", 9))
		{
			headItem.getTagCompound().setTag("neo_ores", new NBTTagList());
		}
		NBTTagList headNBTList = headItem.getTagCompound().getTagList("neo_ores", 10);
		if(headNBTList != null && headNBTList.getCompoundTagAt(0) != null)
		{
			NBTTagCompound nbt = headNBTList.getCompoundTagAt(0);
			if(nbt.hasKey("ML") && nbt.hasKey("maxMP") && nbt.hasKey("MP") && nbt.hasKey("MXP") && nbt.hasKey("isSoulBound"))
			{
				return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
			}
			else
			{				
				NBTTagCompound newTag = new NBTTagCompound();
				newTag.setInteger("ML", 1);
				newTag.setInteger("maxMP", 101);
				newTag.setInteger("MP", 50);
				newTag.setInteger("MXP", 0);
				if(headItem.getTagCompound().hasKey("Unbreakable") && headItem.getTagCompound().getBoolean("Unbreakable"))
				{
					newTag.setInteger("isSoulBound", 0);
				}
				else
				{
					newTag.setInteger("isSoulBound", 1);
				}
				headNBTList.appendTag(newTag);
				headItem.getTagCompound().setBoolean("Unbreakable", true);
				headItem.addEnchantment(Enchantment.getEnchantmentByID(10), 1);
				
				player.playSound(SoundEvents.BLOCK_ANVIL_USE, 1.0F, 1.0F);
			}
		}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}