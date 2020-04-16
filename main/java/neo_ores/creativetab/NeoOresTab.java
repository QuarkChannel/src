package neo_ores.creativetab;

import neo_ores.main.NeoOres;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class NeoOresTab extends CreativeTabs 
{
	public NeoOresTab(String label) 
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(NeoOres.undite);
	}
}
