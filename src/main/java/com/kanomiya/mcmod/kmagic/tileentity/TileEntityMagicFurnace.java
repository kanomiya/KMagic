package com.kanomiya.mcmod.kmagic.tileentity;

import com.kanomiya.mcmod.kmagic.KMagicAPI;
import com.kanomiya.mcmod.kmagic.block.BlockMagicFurnace;
import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterial;
import com.kanomiya.mcmod.kmagic.magic.material.MagicMaterials;
import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Kanomiya
 *
 */
public class TileEntityMagicFurnace extends TileEntityMagicBase implements IUpdatePlayerListBox, ISidedInventory {

	// TileEntityFurnace

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 1};
	private static final int[] slotsSides = new int[] {1};
	private ItemStack[] furnaceItemStacks = new ItemStack[3];
	private int furnaceBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String furnaceCustomName;

	@Override public void initMagicStatus(MagicStatus status) {
		MagicMaterials materials = status.getMagicMaterials();

		materials.add(MagicMaterial.STONE, 8);

	}



	@Override public int getSizeInventory() { return furnaceItemStacks.length; }

	@Override public ItemStack getStackInSlot(int index) { return furnaceItemStacks[index]; }

	@Override public ItemStack decrStackSize(int index, int count) {
		if (furnaceItemStacks[index] == null) return null;

		ItemStack itemstack;

		if (furnaceItemStacks[index].stackSize <= count) {
			itemstack = furnaceItemStacks[index];
			furnaceItemStacks[index] = null;

		} else {
			itemstack = furnaceItemStacks[index].splitStack(count);
			if (furnaceItemStacks[index].stackSize == 0) furnaceItemStacks[index] = null;

		}

		return itemstack;
	}

	@Override public ItemStack getStackInSlotOnClosing(int index) {
		if (furnaceItemStacks[index] == null) return null;

		ItemStack itemstack = furnaceItemStacks[index];
		furnaceItemStacks[index] = null;
		return itemstack;
	}

	@Override public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, furnaceItemStacks[index]);
		furnaceItemStacks[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}

		if (index == 0 && ! flag) {
			totalCookTime = getTotalCookTime(stack);
			cookTime = 0;
			markDirty();
		}
	}

	@Override public String getName() { return hasCustomName() ? furnaceCustomName : "kmagic.container.magicFurnace"; }

	@Override public boolean hasCustomName() { return furnaceCustomName != null && furnaceCustomName.length() > 0; }

	public void setCustomInventoryName(String parCustomName) { furnaceCustomName = parCustomName; }

	@Override public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		furnaceItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < furnaceItemStacks.length) {
				furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		furnaceBurnTime = compound.getShort("BurnTime");
		cookTime = compound.getShort("CookTime");
		totalCookTime = compound.getShort("CookTimeTotal");
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);

		if (compound.hasKey("CustomName", 8)) {
			furnaceCustomName = compound.getString("CustomName");
		}

	}

	@Override public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setShort("BurnTime", (short)furnaceBurnTime);
		compound.setShort("CookTime", (short)cookTime);
		compound.setShort("CookTimeTotal", (short)totalCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < furnaceItemStacks.length; i++) {
			if (furnaceItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				furnaceItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (hasCustomName()) {
			compound.setString("CustomName", furnaceCustomName);
		}
	}

	@Override public int getInventoryStackLimit() { return 64; }

	public boolean isBurning() { return (furnaceBurnTime > 0); }

	@SideOnly(Side.CLIENT) public static boolean isBurning(IInventory inv) { return inv.getField(0) > 0; }


	private int count;

	@Override public void update() {
		// furnaceItemStacks  0: Source  1: Fuel  2: Result

		boolean flag = this.isBurning();
		boolean flag1 = false;


		if (this.isBurning())  furnaceBurnTime --;


		if (! worldObj.isRemote) {

			if (furnaceItemStacks[1] != null && furnaceItemStacks[1].stackSize == 1) {
				MagicStatus fuelStatus = KMagicAPI.getMagicStatus(furnaceItemStacks[1]);
				if (fuelStatus != null && MagicStatus.dealMp(fuelStatus, status, 1, false, false)) {
					KMagicAPI.setMagicStatus(furnaceItemStacks[1], fuelStatus);
					flag1 = true;
				}

			}


			if (! this.isBurning() && status.getMp() == 0 || furnaceItemStacks[0] == null) {
				if (! this.isBurning() && cookTime > 0 || count > 0) {
					cookTime = MathHelper.clamp_int(cookTime - 2, 0, totalCookTime);
					count = 0;
				}
			} else {
				if (! this.isBurning() && canSmelt()) {
					currentItemBurnTime = furnaceBurnTime = status.getMp();

					if (this.isBurning()) {
						flag1 = true;
					}
				}

				if (this.isBurning() && canSmelt()) {
					count ++;
					cookTime ++;

					if (count % 25 == 0) { // 200 / 25 == 8  smelt8回分
						MagicStatus.dealMp(status, null, 1, false, false);
						count = 0;
						flag1 = true;
					}

					if (cookTime == totalCookTime) {
						cookTime = 0;
						count = 0;
						totalCookTime = getTotalCookTime(furnaceItemStacks[0]);
						smeltItem();

						flag1 = true;
					}
				} else {
					count = 0;
					cookTime = 0;
				}
			}

			if (flag != this.isBurning()) {
				flag1 = true;
				// TODO: Clientで反映されない
				BlockMagicFurnace.setState(this.isBurning(), worldObj, pos);
			}
		}

		if (flag1) markDirty();
	}

	public int getTotalCookTime(ItemStack smeltStack) {
		return 200;
	}

	private boolean canSmelt() {
		if (furnaceItemStacks[0] == null) return false;

		ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks[0]);
		if (itemstack == null) return false;
		if (furnaceItemStacks[2] == null) return true;
		if (! furnaceItemStacks[2].isItemEqual(itemstack)) return false;
		int result = furnaceItemStacks[2].stackSize + itemstack.stackSize;
		return result <= getInventoryStackLimit() && result <= furnaceItemStacks[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
	}

	public void smeltItem() {
		if (canSmelt()) {
			ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(furnaceItemStacks[0]);

			if (furnaceItemStacks[2] == null)  furnaceItemStacks[2] = itemstack.copy();
			else if (furnaceItemStacks[2].getItem() == itemstack.getItem()) furnaceItemStacks[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items

			if (furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && furnaceItemStacks[0].getMetadata() == 1) {
				if (furnaceItemStacks[1] != null && furnaceItemStacks[1].getItem() == Items.bucket) furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
			}

			furnaceItemStacks[0].stackSize --;

			if (furnaceItemStacks[0].stackSize <= 0) furnaceItemStacks[0] = null;

		}
	}

	public static int getItemBurnTime(ItemStack fuelStack) {
		if (fuelStack == null) return 0;

		NBTTagCompound nbt = (KMagicAPI.hasMagicNBT(fuelStack)) ? KMagicAPI.getMagicNBT(fuelStack) : null;

		if (nbt != null) return KMagicAPI.getMp(nbt);

		return 0;
	}

	public static boolean isItemFuel(ItemStack fuelStack) {
		return (getItemBurnTime(fuelStack) > 0);
	}

	@Override public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override public void openInventory(EntityPlayer player) {}

	@Override public void closeInventory(EntityPlayer player) {}

	@Override public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 2 ? false : (index != 1 ? true : (furnaceItemStacks[1] == null && isItemFuel(stack)) || SlotFurnaceFuel.isBucket(stack));
	}

	@Override public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN && index == 1) {
			Item item = stack.getItem();

			if (! isItemFuel(stack)) return true;

			if (item != Items.water_bucket && item != Items.bucket) return false;

		}

		return true;
	}


	@SideOnly(Side.CLIENT) protected int mpCache, maxMpCache;

	@Override public int getField(int id) {
		switch (id) {
			case 0:
				return furnaceBurnTime;
			case 1:
				return currentItemBurnTime;
			case 2:
				return cookTime;
			case 3:
				return totalCookTime;
			case 4:
				return (worldObj.isRemote) ? mpCache : getMagicStatus().getMp();
			case 5:
				return (worldObj.isRemote) ? maxMpCache : getMagicStatus().getMaxMp();
			default:
				return 0;
		}
	}

	@Override public void setField(int id, int value) {
		switch (id) {
			case 0:
				furnaceBurnTime = value;
				break;
			case 1:
				currentItemBurnTime = value;
				break;
			case 2:
				cookTime = value;
				break;
			case 3:
				totalCookTime = value;
				break;
			case 4:
				if (worldObj.isRemote) mpCache = value;
				break;
			case 5:
				if (worldObj.isRemote) maxMpCache = value;
				break;
		}
	}

	@Override public int getFieldCount() { return 6; }

	@Override public void clear() {
		for (int i = 0; i < furnaceItemStacks.length; ++i) {
			furnaceItemStacks[i] = null;
		}
	}


	@Override public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
	}




}
