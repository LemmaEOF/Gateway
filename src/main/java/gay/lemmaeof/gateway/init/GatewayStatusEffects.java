package gay.lemmaeof.gateway.init;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.hooks.CustomStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GatewayStatusEffects {
	public static final CustomStatusEffect VIRTUAL_COMBAT = new CustomStatusEffect(StatusEffectType.NEUTRAL, 0x5FD3EC);
	public static final CustomStatusEffect CHARGED = new CustomStatusEffect(StatusEffectType.BENEFICIAL, 0x00FFFF);

	public static void init() {
		Gateway.AUTOREG.autoRegister(Registry.STATUS_EFFECT, GatewayStatusEffects.class, StatusEffect.class);
	}
}
