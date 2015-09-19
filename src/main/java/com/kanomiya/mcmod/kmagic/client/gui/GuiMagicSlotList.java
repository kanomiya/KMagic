/**
 *
 */
package com.kanomiya.mcmod.kmagic.client.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.kanomiya.mcmod.kmagic.api.magic.ability.MagicAbility;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicAbilityHolder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Kanomiya
 *
 */
@SideOnly(Side.CLIENT)
public class GuiMagicSlotList extends GuiListExtended {
	public final GuiScreen gui;

	public final MagicAbilityHolder holder;
	public List<GuiMagicSlotEntry> entries;



	/**
	 * @param list
	 * @param parGui
	 * @param mcIn
	 * @param width
	 * @param height
	 * @param topIn
	 * @param bottomIn
	 * @param slotHeightIn
	 */
	public GuiMagicSlotList(MagicAbilityHolder parHolder, GuiScreen parGui, Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
		super(mcIn, width, height, topIn, bottomIn, slotHeightIn);

		gui = parGui;
		holder = parHolder;
		entries = Lists.newLinkedList();

		loadAbilities();
	}

	protected void loadAbilities() {
		for (MagicAbility ability: holder.abilitySet) {
			entries.add(new GuiMagicSlotEntry(ability)); // TODO:
		}
	}

	/**
	* @inheritDoc
	*/
	@Override public GuiMagicSlotEntry getListEntry(int slotIndex) {
		return entries.get(slotIndex);
	}

	public GuiMagicSlotEntry setListEntry(int slotIndex, GuiMagicSlotEntry entry) {
		return entries.set(slotIndex, entry);
	}

	/**
	* @inheritDoc
	*/
	@Override protected int getSize() {
		return entries.size();
	}

	/**
	* @inheritDoc
	*/
	@Override protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {  }

	/**
	* @inheritDoc
	*/
	@Override protected boolean isSelected(int slotIndex)
	{
		return (selectedElement == slotIndex);
	}


	public boolean isValid(int index) {
		return (0 <= index && index < getSize());
	}

	public void exchangeSelected(int toIndex) {
		if (isValid(selectedElement) && isValid(toIndex)) {
			setListEntry(selectedElement, setListEntry(toIndex, getListEntry(selectedElement)));
		}

	}

	public void moveUpSelected() {
		exchangeSelected(selectedElement -1);
	}

	public void moveDownSelected() {
		exchangeSelected(selectedElement +1);
	}




	public class GuiMagicSlotEntry implements GuiListExtended.IGuiListEntry {
		public final MagicAbility ability;

		public GuiMagicSlotEntry(MagicAbility parAbility) {
			ability = parAbility;
		}

		/**
		* @inheritDoc
		*/
		@Override public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {

		}

		/**
		* @inheritDoc
		*/
		@Override public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
			if (ability != null && gui != null) {
				gui.mc.fontRendererObj.drawString("" + ability.toString(), x, y, 0xffffff);
			}

		}

		/**
		* @inheritDoc
		*/
		@Override public boolean mousePressed(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {

			return false;
		}

		/**
		* @inheritDoc
		*/
		@Override public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {
			if (0 <= slotIndex && slotIndex < getSize() && slotIndex < holder.getAbilitySlotSize()) {
				holder.setAbilityInSlot(slotIndex, getListEntry(slotIndex).ability);
			}

		}

	}

}
