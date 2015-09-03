package com.kanomiya.mcmod.kmagic.magic.status.base;

import com.kanomiya.mcmod.kmagic.magic.status.MagicStatus;

/**
 * @author Kanomiya
 *
 */
public interface IMagicObject {

	public MagicStatus getMagicStatus();

	public void initMagicStatus(MagicStatus status);


}
