package gay.lemmaeof.gateway.registry;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.trigger.BailOutTrigger;
import gay.lemmaeof.gateway.trigger.ChameleonTrigger;
import gay.lemmaeof.gateway.trigger.SimpleTrigger;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayTriggers {
	public static final Trigger BAIL_OUT = register(new BailOutTrigger(), "bail_out");
	public static final Trigger CHAMELEON = register(new ChameleonTrigger(), "chameleon");
	public static final Trigger RAYGUST = register(new SimpleTrigger(GatewayItems.RAYGUST), "raygust");
	public static final Trigger KOGETSU = register(new SimpleTrigger(GatewayItems.KOGETSU), "kogetsu");
	public static final Trigger SCORPION = register(new SimpleTrigger(GatewayItems.SCORPION), "scorpion");
	public static final Trigger SHIELD = register(new SimpleTrigger(GatewayItems.TRION_SHIELD), "shield");

	public static void init() { }

	private static Trigger register(Trigger trigger, String name) {
		return Registry.register(Gateway.TRIGGERS, new Identifier(Gateway.MODID, name), trigger);
	}
}
