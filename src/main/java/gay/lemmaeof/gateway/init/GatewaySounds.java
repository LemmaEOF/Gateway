package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewaySounds {

	public static final SoundEvent TRANSFORMATION_ON = create("transformation.on");
	public static final SoundEvent TRANSFORMATION_OFF = create("transformation.off");
	public static final SoundEvent TRANSFORMATION_CHARGE = create("transformation.charge");

	public static void init() {
		Gateway.AUTOREG.autoRegister(Registry.SOUND_EVENT, GatewaySounds.class, SoundEvent.class);
	}

	private static SoundEvent create(String name) {
		return new SoundEvent(new Identifier(Gateway.MODID, name));
	}
}
