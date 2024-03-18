package de.mennomax.astikorcarts.proxy;

import de.mennomax.astikorcarts.init.ModKeybindings;

public class ClientProxy implements IProxy {
	public void preInit() {
	}

	public void init() {
		ModKeybindings.registerKeyBindings();
	}

	public void postInit() {

	}
}
