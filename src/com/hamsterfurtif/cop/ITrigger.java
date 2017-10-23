package com.hamsterfurtif.cop;

public interface ITrigger {
	
	public static enum TriggerType{
		AIRBORNE,
		EXPLOSION,
		GROUNDED;
	}

	public TriggerType getSource();
}
