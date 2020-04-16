package neo_ores.inventory;

import neo_ores.main.NeoOres;
import neo_ores.mana.PlayerManaCalc;
import neo_ores.recipes.ManaCraftingRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerManaWorkbench extends Container
{
    private final IInventory outputSlot;
    private final IInventory inputSlots;
    private final World world;
    private final BlockPos selfPosition;
    public int cost;
    public PlayerManaCalc manaCalc = new PlayerManaCalc();
    public ManaCraftingRecipe recipe = new ManaCraftingRecipe();
    @SuppressWarnings("unused")
	private final EntityPlayer player;

    @SideOnly(Side.CLIENT)
    public ContainerManaWorkbench(InventoryPlayer playerInventory, World worldIn, EntityPlayer player)
    {
        this(playerInventory, worldIn, BlockPos.ORIGIN, player);
    }

    public ContainerManaWorkbench(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn, EntityPlayer player)
    {
        this.outputSlot = new InventoryCraftResult();
        this.inputSlots = new InventoryBasic("ManaWorkbench", true, 9)
        {
        	public int getInventoryStackLimit()
        	{
        		return 1;
        	}
            public void markDirty()
            {
                super.markDirty();
                ContainerManaWorkbench.this.onCraftMatrixChanged(this);
            }
        };
        this.selfPosition = blockPosIn;
        this.world = worldIn;
        this.player = player;
        this.addSlotToContainer(new Slot(this.inputSlots, 0, 40, 18));
        this.addSlotToContainer(new Slot(this.inputSlots, 1, 58, 18));
        this.addSlotToContainer(new Slot(this.inputSlots, 2, 76, 18));
        this.addSlotToContainer(new Slot(this.inputSlots, 3, 40, 36));
        this.addSlotToContainer(new Slot(this.inputSlots, 4, 58, 36));
        this.addSlotToContainer(new Slot(this.inputSlots, 5, 76, 36));
        this.addSlotToContainer(new Slot(this.inputSlots, 6, 40, 54));
        this.addSlotToContainer(new Slot(this.inputSlots, 7, 58, 54));
        this.addSlotToContainer(new Slot(this.inputSlots, 8, 76, 54));
        
        this.addSlotToContainer(new Slot(this.outputSlot, 9, 138, 43)
        {
            public boolean isItemValid(ItemStack stack)
            {
                return false;
            }

            public boolean canTakeStack(EntityPlayer playerIn)
            {
                return (playerIn.capabilities.isCreativeMode || manaCalc.getMP(playerIn) >= ContainerManaWorkbench.this.cost) && ContainerManaWorkbench.this.cost > 0 && this.getHasStack();
            }
            
            public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
            {
                if (!thePlayer.capabilities.isCreativeMode)
                {
                	manaCalc.addMP(thePlayer, -ContainerManaWorkbench.this.cost);
                }
                
                for(int i = 0;i < 9;i++)
                {
                	if(ContainerManaWorkbench.this.inputSlots.getStackInSlot(i).getCount() == 1)
                	{
                		ContainerManaWorkbench.this.inputSlots.setInventorySlotContents(i, ItemStack.EMPTY);
                	}
                	else if(!ContainerManaWorkbench.this.inputSlots.getStackInSlot(i).isEmpty())
                	{
                		ContainerManaWorkbench.this.inputSlots.getStackInSlot(i).shrink(1);
                	}
                }

                ContainerManaWorkbench.this.cost = 0;
                
                return stack;
            }
        });

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

	public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn == this.inputSlots)
        {
            this.updateManaCraftOutput();
        }
    }

    public void updateManaCraftOutput()
    {
        ItemStack item0 = this.inputSlots.getStackInSlot(0);
        ItemStack item1 = this.inputSlots.getStackInSlot(1);
        ItemStack item2 = this.inputSlots.getStackInSlot(2);
        ItemStack item3 = this.inputSlots.getStackInSlot(3);
        ItemStack item4 = this.inputSlots.getStackInSlot(4);
        ItemStack item5 = this.inputSlots.getStackInSlot(5);
        ItemStack item6 = this.inputSlots.getStackInSlot(6);
        ItemStack item7 = this.inputSlots.getStackInSlot(7);
        ItemStack item8 = this.inputSlots.getStackInSlot(8);
        
        this.cost = recipe.getManaCraftingValue(item0, item1, item2, item3, item4, item5, item6, item7, item8);;
        ItemStack outputItem = recipe.getManaCraftingResult(item0, item1, item2, item3, item4, item5, item6, item7, item8);
        
        if(outputItem.isEmpty())
        {
        	this.outputSlot.setInventorySlotContents(0, ItemStack.EMPTY);
        	this.cost = 0;
        }
        else
        {
        	this.outputSlot.setInventorySlotContents(0, outputItem);
            this.detectAndSendChanges();
        }
    }

    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendWindowProperty(this, 0, this.cost);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        if (id == 0)
        {
            this.cost = data;
        }
    }

    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.inputSlots);
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (this.world.getBlockState(this.selfPosition).getBlock() != NeoOres.mana_workbench)
        {
            return false;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.selfPosition.getX() + 0.5D, (double)this.selfPosition.getY() + 0.5D, (double)this.selfPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
    	
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 9)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 0 && index != 1 && index != 2 && index != 3 && index != 4 && index != 5 && index != 6 && index != 7 && index != 8)
            {
                if (index >= 10 && index < 46 && !this.mergeItemStack(itemstack1, 0, 8, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
