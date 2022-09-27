package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.hooks.CustomStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayStatusEffects {
	public static final StatusEffect VIRTUAL_COMBAT = register(new CustomStatusEffect(StatusEffectType.NEUTRAL, 0x5FD3EC), "virtual_combat");
	public static final StatusEffect CHARGED = register(new CustomStatusEffect(StatusEffectType.BENEFICIAL, 0x00FFFF), "charged");

	public static void init() { }

	private static StatusEffect register(StatusEffect effect, String name) {
		return Registry.register(Registry.STATUS_EFFECT, new Identifier(Gateway.MODID, name), effect);
	}
}
