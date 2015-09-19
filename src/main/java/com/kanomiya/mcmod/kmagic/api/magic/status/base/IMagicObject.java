package com.kanomiya.mcmod.kmagic.api.magic.status.base;

import com.kanomiya.mcmod.kmagic.api.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public interface IMagicObject {

	public MagicStatus getMagicStatus();

	public void initMagicStatus(MagicStatus status);


}
