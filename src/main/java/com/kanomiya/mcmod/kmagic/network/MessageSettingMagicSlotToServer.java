package com.kanomiya.mcmod.kmagic.network;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author Kanomiya
 *
 */
public class MessageSettingMagicSlotToServer implements IMessage, IMessageHandler<MessageSettingMagicSlotToServer, IMessage> {
	public static final int TYPE_ITEM = 0;
	public static final int TYPE_TILE = 1;
	public static final int TYPE_NONE = 2;

	public int keySlotNum;
	public int type;
	public boolean state;

	public int itemSlotNum;

	public int tileX, tileY, tileZ;

	public MessageSettingMagicSlotToServer() {  }


	public MessageSettingMagicSlotToServer(int parKeySlotNum, boolean parState) {
		type = TYPE_NONE;
		keySlotNum = parKeySlotNum;
		state = parState;
	}

	public MessageSettingMagicSlotToServer(int parItemSlotNum, int parKeyId, boolean parState) {
		type = TYPE_ITEM;
		state = parState;
		keySlotNum = parKeyId;

		itemSlotNum = parItemSlotNum;
	}

	public MessageSettingMagicSlotToServer(int parTileX, int parTileY, int parTileZ, int parKeyId, boolean parState) {
		type = TYPE_TILE;
		state = parState;
		keySlotNum = parKeyId;

		tileX = parTileX;
		tileY = parTileY;
		tileZ = parTileZ;
	}



	@Override public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
		keySlotNum = buf.readInt();
		state = buf.readBoolean();

		if (type == TYPE_ITEM) {
			itemSlotNum = buf.readInt();
		} else if (type == TYPE_TILE) {
			tileX = buf.readInt();
			tileY = buf.readInt();
			tileZ = buf.readInt();
		}

	}

	@Override public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		buf.writeInt(keySlotNum);
		buf.writeBoolean(state);

		if (type == TYPE_ITEM) {
			buf.writeInt(itemSlotNum);
		} else if (type == TYPE_TILE) {
			buf.writeInt(tileX);
			buf.writeInt(tileY);
			buf.writeInt(tileZ);
		}

	}






	@Override public IMessage onMessage(MessageSettingMagicSlotToServer message, MessageContext ctx) {
		if (ctx.side.isServer()) {
			return onMessageServer(message, ctx);
		}

		return null;
	}

	public IMessage onMessageServer(MessageSettingMagicSlotToServer message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		if (player == null) return null;

		World worldObj = player.worldObj;
		MagicStatus status = null;
		ItemStack stack = null;



		if (message.type == TYPE_NONE) {
			status = KMagicAPI.getMagicStatus(player);

		} else if (message.type == TYPE_ITEM) {
			if (0 <= message.itemSlotNum && message.itemSlotNum < player.inventory.getSizeInventory()) {
				stack = player.inventory.getStackInSlot(message.itemSlotNum);
				if (stack != null) {
					status = KMagicAPI.getMagicStatus(stack);
				}

			}

		} else if (message.type == TYPE_TILE) {
			BlockPos pos = new BlockPos(message.tileX, message.tileY, message.tileZ);
			TileEntity tileEntity = worldObj.getTileEntity(pos);

			if (tileEntity != null) {
				status = KMagicAPI.getMagicStatus(tileEntity);
			}

		}

		if (status != null) {
			status.getAbilityHolder().setupAbilityInSlot(message.keySlotNum, message.state, worldObj);

			if (message.type == TYPE_ITEM) {
				KMagicAPI.setMagicStatus(stack, status);
			}

		}


		return null;
	}


}

// ByteBufUtils.writeUTF8String(buf, STRING);
