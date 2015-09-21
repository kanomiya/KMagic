package com.kanomiya.mcmod.kmagic.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;

/**
 * @author Kanomiya
 *
 */
public class MessageMagicStatusToClient implements IMessage, IMessageHandler<MessageMagicStatusToClient, IMessage> {
	public int entityId = -1;
	public NBTTagCompound nbt;

	public MessageMagicStatusToClient() {  }

	public MessageMagicStatusToClient(MagicStatusEntity magicStatusEntity) {
		entityId = magicStatusEntity.getEntity().getEntityId();

		nbt = new NBTTagCompound();
		magicStatusEntity.saveNBTData(nbt);
	}

	@Override public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
		ByteBufUtils.writeTag(buf, nbt);
	}






	@Override public IMessage onMessage(MessageMagicStatusToClient message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			return onMessageClient(message, ctx);
		}

		return null;
	}

	public IMessage onMessageClient(MessageMagicStatusToClient message, MessageContext ctx) {
		Entity parent = Minecraft.getMinecraft().theWorld.getEntityByID(message.entityId);

		if (KMagicAPI.isMagicObject(parent)) {
			MagicStatusEntity status = KMagicAPI.getMagicStatus(parent);

			if (status != null) {
				status.loadNBTData(message.nbt);
			}
		}

		return null;
	}


}

// ByteBufUtils.writeUTF8String(buf, STRING);
