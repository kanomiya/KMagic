package com.kanomiya.mcmod.kmagic.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public class CommandKMagic extends CommandBase {

	@Override public String getName() {
		return "kmagic";
	}

	@Override public String getCommandUsage(ICommandSender sender) {
		return "kmagic.command.kmagic.usage";
	}

	@Override public List addTabCompletionOptions(ICommandSender sender, String[] option, BlockPos pos) {
		List<String> list = new ArrayList<String>();

		if (option.length == 1) {
			list.add("mp");
			list.add("charge");
			// list.add("maxmp");
		}


		if (option.length == 2) {
			if (option[0].equals("charge")) {
				list.add("item");
			}
		}

		return list;
	}

	@Override public void execute(ICommandSender sender, String[] args) {
		int argLength = args.length;
		if (argLength == 0) { missCommand(sender); return; }

		boolean success = false;

		Entity entity = sender.getCommandSenderEntity();
		MagicStatus status = KMagicAPI.getMagicStatus(entity);

		if (status != null) {

			if (argLength == 2 && args[0].equals("mp") && args[1].matches("-*[0-9]+")) {
				int amount = Integer.valueOf(args[1]);

				MagicStatus.dealMp(null, status, amount, true, false);
				sender.addChatMessage(new ChatComponentTranslation("kmagic.command.kmagic.mp", sender.getDisplayName(), new ChatComponentText("" +amount)));

				success = true;

			} else if (argLength == 3 && args[0].equals("charge") && args[2].matches("-*[0-9]+") && entity != null && entity instanceof EntityLivingBase) {
				int amount = Integer.valueOf(args[2]);

				if (args[1].equals("item")) {
					ItemStack heldStack = ((EntityLivingBase) entity).getHeldItem();

					if (heldStack != null) {
						MagicStatus stackStatus = KMagicAPI.getMagicStatus(heldStack);

						if (stackStatus != null) {
							MagicStatus.dealMp(null, stackStatus, amount, true, false);
							sender.addChatMessage(new ChatComponentTranslation("kmagic.command.kmagic.mp", heldStack.getDisplayName(), new ChatComponentText("" +amount)));
							KMagicAPI.setMagicStatus(heldStack, stackStatus);

							success = true;

						}
					}

				}


			}

		}

		if (! success) {
			missCommand(sender);
		}

	}

	public void missCommand(ICommandSender sender) {
		sender.addChatMessage(new ChatComponentTranslation("kmagic.command.miss"));
	}

}
