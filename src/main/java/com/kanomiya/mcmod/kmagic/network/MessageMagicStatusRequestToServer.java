package com.kanomiya.mcmod.kmagic.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.kanomiya.mcmod.kmagic.api.KMagicAPI;
import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatusEntity;
import com.kanomiya.mcmod.kmagic.event.MagicStatusEventHandler;

/**
 * @author Kanomiya
 *
 */
public class MessageMagicStatusRequestToServer implements IMessage, IMessageHandler<MessageMagicStatusRequestToServer, IMessage> {
	public int entityId = -1;

	public MessageMagicStatusRequestToServer() {  }

	public MessageMagicStatusRequestToServer(Entity entity) {
		entityId = entity.getEntityId();
	}

	@Override public void fromBytes(ByteBuf buf) {
		entityId = buf.readInt();
	}

	@Override public void toBytes(ByteBuf buf) {
		buf.writeInt(entityId);
	}






	@Override public IMessage onMessage(MessageMagicStatusRequestToServer message, MessageContext ctx) {
		if (ctx.side.isServer()) {
			return onMessageServer(message, ctx);
		}

		return null;
	}

	/** @see MagicStatusEventHandler#request(Entity) */
	public IMessage onMessageServer(MessageMagicStatusRequestToServer message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.getEntityWorld();
		if (world != null) {
			Entity entity = world.getEntityByID(message.entityId); // TODO: null? タイミングだろうか

			if (entity != null) {
				MagicStatusEntity status = KMagicAPI.getMagicStatus(entity);

				if (status != null) return new MessageMagicStatusToClient(status);
			}

		}

		return null;
	}


}

// ByteBufUtils.writeUTF8String(buf, STRING);
