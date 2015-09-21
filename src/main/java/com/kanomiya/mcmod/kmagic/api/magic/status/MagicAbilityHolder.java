package com.kanomiya.mcmod.kmagic.api.magic.status;

import java.util.Map;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kanomiya.mcmod.kmagic.KMagic;
import com.kanomiya.mcmod.kmagic.api.magic.ability.MagicAbility;
import com.kanomiya.mcmod.kmagic.api.magic.ability.RegistryMagicAbility;
import com.kanomiya.mcmod.kmagic.event.MagicStatusEventHandler;

/**
 * @author Kanomiya
 *
 */
public class MagicAbilityHolder {
	public final Set<MagicAbility> abilitySet;

	protected final MagicAbility[] slotToAbility;

	protected MagicStatus status;
	public boolean isUpdated;

	public MagicAbilityHolder(MagicStatus parStatus) {
		status = parStatus;

		abilitySet = Sets.newConcurrentHashSet();

		slotToAbility = new MagicAbility[10];
	}

	public MagicAbilityHolder(MagicStatus parStatus, NBTTagCompound nbt) {
		this(parStatus);

		readFromNBT(nbt);
	}








	public void addAbility(MagicAbility parAbility) {
		abilitySet.add(parAbility);

		isUpdated = true;
	}

	public void removeAbility(MagicAbility parAbility) {
		abilitySet.remove(parAbility);

		for (int i=0; i<slotToAbility.length; i++) {
			if (slotToAbility[i] == parAbility) slotToAbility[i] = null;
		}

		isUpdated = true;
	}

	public boolean hasMagicAbility(Class<? extends MagicAbility> atClass) {
		for (MagicAbility ability: abilitySet) {
			if (atClass.isAssignableFrom(ability.getClass())) return true;
		}

		return false;
	}


	public MagicAbility getAbilityInSlot(int slotNum) {
		if (0 <= slotNum && slotNum < slotToAbility.length) return slotToAbility[slotNum];
		return null;
	}

	public void setAbilityInSlot(int slotNum, MagicAbility ability) {
		slotToAbility[slotNum] = ability;

		isUpdated = true;
	}

	public int getAbilitySlotSize() { return slotToAbility.length; }





	public synchronized void setupAbilityInSlot(int slotNum, boolean state, World worldIn) {
		if (0 <= slotNum && slotNum < slotToAbility.length) {
			// slotToAbility[slotNum] = ability;

			isUpdated = true;

		}

	}



	public synchronized void update(World worldIn) {
		if (worldIn == null) return ;

		for (MagicAbility ability: abilitySet) ability.update(worldIn);
	}

	// TODO: onSpawn
	public synchronized void onSpawn(World worldIn) {
		for (MagicAbility ability: abilitySet) ability.onSpawn(worldIn);
	}

	/**
	 *
	 * @see MagicStatusEventHandler#onPlayerInteractEvent(PlayerInteractEvent)
	 * @see MagicStatusEventHandler#onEntityInteractEvent(PlayerInteractEvent)
	 * @param worldIn
	 * @param event
	 */
	public synchronized void onInteractedWith(World worldIn, Entity interactor) {
		for (MagicAbility ability: abilitySet) ability.onInteractedWith(worldIn, interactor);
	}

	public synchronized void onFall(World worldIn, LivingFallEvent event) {
		for (MagicAbility ability: abilitySet) ability.onFall(worldIn, event);
	}

	// TODO: onDeath
	public synchronized void onDeath(World worldIn) {
		for (MagicAbility ability: abilitySet) ability.onDeath(worldIn);
	}




	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtBase = new NBTTagCompound();

		NBTTagList listAbility = new NBTTagList();

		for (MagicAbility ability: abilitySet) {
			NBTTagCompound nbtAbility = new NBTTagCompound();

			nbtAbility.setString("id", RegistryMagicAbility.getAbilityId(ability));

			NBTTagCompound nbtAbilityData = new NBTTagCompound();
			ability.writeToNBT(nbtAbilityData);;

			nbtAbility.setTag("data", nbtAbilityData);
			listAbility.appendTag(nbtAbility);
		}


		NBTTagList listSlot = new NBTTagList();

		for (int i=0; i<slotToAbility.length; i++) {
			if (slotToAbility[i] != null) listSlot.appendTag(new NBTTagString(RegistryMagicAbility.getAbilityId(slotToAbility[i])));
			else listSlot.appendTag(new NBTTagString());
		}


		nbtBase.setTag("abilityData", listAbility);
		nbtBase.setTag("slotData", listSlot);

		nbt.setTag("abilities", nbtBase);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		clear();

		NBTTagCompound nbtBase = nbt.getCompoundTag("abilities");

		NBTTagList listAbility = nbtBase.getTagList("abilityData", NBT.TAG_COMPOUND);

		Map<String, MagicAbility> idToAbility = Maps.newConcurrentMap();

		for (int i=0; i<listAbility.tagCount(); i++) {
			NBTTagCompound entryNbt = listAbility.getCompoundTagAt(i);

			String id = entryNbt.getString("id");

			MagicAbility ability = RegistryMagicAbility.getAbilityInstance(id, status);

			if (ability != null) {
				ability.readFromNBT(entryNbt.getCompoundTag("data"));

				abilitySet.add(ability);
				idToAbility.put(id, ability);
			} else {
				KMagic.logger.error("Unknown MagicAbility (id: " + id + ")");
			}
		}




		NBTTagList listSlot = nbtBase.getTagList("slotData", NBT.TAG_STRING);

		for (int i=0; i<listSlot.tagCount(); i++) {
			String id = listSlot.getStringTagAt(i);

			if (! id.equals("")) {
				MagicAbility ability = idToAbility.get(id);
				if (ability != null) {
					slotToAbility[i] = ability;
				}
			}
		}


		isUpdated = false;

	}

	public void clear() {
		abilitySet.clear();

	}








}
