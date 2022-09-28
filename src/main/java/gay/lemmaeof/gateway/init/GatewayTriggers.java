package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.trigger.BailOutTrigger;
import gay.lemmaeof.gateway.trigger.ChameleonTrigger;
import gay.lemmaeof.gateway.trigger.SimpleTrigger;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayTriggers {
	public static final BailOutTrigger BAIL_OUT = new BailOutTrigger();
	public static final ChameleonTrigger CHAMELEON = new ChameleonTrigger();
	public static final SimpleTrigger RAYGUST = new SimpleTrigger(GatewayItems.RAYGUST);
	public static final SimpleTrigger KOGETSU = new SimpleTrigger(GatewayItems.KOGETSU);
	public static final SimpleTrigger SCORPION = new SimpleTrigger(GatewayItems.SCORPION);
	public static final SimpleTrigger SHIELD = new SimpleTrigger(GatewayItems.TRION_SHIELD);

	public static void init() {
		Gateway.AUTOREG.autoRegister(Gateway.TRIGGERS, GatewayTriggers.class, Trigger.class);
	}

}
