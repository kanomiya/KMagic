package com.kanomiya.mcmod.kmagic.magic.ability;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import com.kanomiya.mcmod.kmagic.api.magic.ability.MagicAbility;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusPlayer;
import com.kanomiya.mcmod.kmagic.api.magic.status.wrapper.PlayerWrapper;

/**
 * @author Kanomiya
 *
 */
public class MAFlight extends MagicAbility {
	Minecraft mc;

	EntityPlayer player;
	boolean shouldRemove;

	boolean isFlying;


	public MAFlight(MagicStatus parStatus) {
		super(parStatus);

		if (status instanceof MagicStatusPlayer) {
			player = ((PlayerWrapper) status.getMagicObject()).getPlayer();
		}

	}

	@Override public boolean shouldRemove(World worldIn) { return shouldRemove; }

	@Override public void update(World worldIn) {
		if (player == null) return;

		boolean allowFlying = ! (player.capabilities.isCreativeMode || player.capabilities.allowFlying);

		if(! allowFlying) {
			isFlying = false;

		} else {
			isFlying = true;

			if (player instanceof EntityPlayerSP) flight((EntityPlayerSP) player);
		}

		if (player.onGround) isFlying = false;

		if (! MagicStatus.dealMp(status, null, 1, false, false)) isFlying = false;

		if (! isFlying) shouldRemove = true;

		player.fallDistance = 0f;
	}

	@Override public void onFall(World worldIn, LivingFallEvent event) {
		event.setCanceled(true);
	}


	private void flight(EntityPlayerSP playerSp) {
		if (mc == null) mc = Minecraft.getMinecraft();

		double flySpeed = 0.15d *1.5d;

		if (isFlying) {
			playerSp.motionY = 0d;
			playerSp.jumpMovementFactor = 0.02f *2.2f;

			boolean keySneak = playerSp.movementInput.sneak;
			boolean keyJump = playerSp.movementInput.jump;

			if (keySneak) {
				if (! keyJump && playerSp.getHeldItem() == null && mc.gameSettings.keyBindUseItem.isKeyDown()) {
					playerSp.motionY -= flySpeed;
				}

				playerSp.motionY -= flySpeed;
			}

			if (keyJump) {
				playerSp.motionY += flySpeed;
			}

			if (! keySneak && ! keyJump) {
				playerSp.motionY -= 0.03f;
			}

		} else {
			// 滞空時 速度リセット
			playerSp.jumpMovementFactor = 0.02f;
		}

	}

	@Override public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("isFlying", isFlying);

	}

	@Override public void readFromNBT(NBTTagCompound nbt) {
		isFlying = nbt.getBoolean("isFlying");

	}



}
