package gay.lemmaeof.gateway.client.hud;

import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.api.TrionComponent;
import gay.lemmaeof.gateway.registry.GatewayComponents;
import gay.lemmaeof.gateway.registry.GatewayStatusEffects;
import gay.lemmaeof.impulse.api.ResourceBar;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public class TrionResourceBar implements ResourceBar {
	private static final Identifier ICON_TEX = new Identifier(Gateway.MODID, "textures/icons/trion.png");
	private static final int normalColor = 0x5FEC94;
	private static final int virtualColor = 0x5FD3EC;
	private static final int burstColor = 0xEC5F6B;
	private static final int unitsPerBar = 50; //TODO: support config for this? It's core to mod mechanics

	@Override
	public Identifier getIconId() {
		return ICON_TEX;
	}

	@Override
	public float getCurrentBarFill(ClientPlayerEntity player) {
		return GatewayComponents.TRION_COMPONENT.get(player).getTrion() / (float) unitsPerBar;
	}

	@Override
	public float getTopBarPercentage(ClientPlayerEntity player) {
		return (GatewayComponents.TRION_COMPONENT.get(player).getMaxTrion() % unitsPerBar) / (float) unitsPerBar;
	}

	@Override
	public int getTotalSegments(ClientPlayerEntity player) {
		return (int) Math.ceil(GatewayComponents.TRION_COMPONENT.get(player).getMaxTrion() / (float) unitsPerBar);
	}

	@Override
	public int getBarColor(ClientPlayerEntity player) {
		if (player.hasStatusEffect(GatewayStatusEffects.VIRTUAL_COMBAT)) return virtualColor;
		TrionComponent comp = GatewayComponents.TRION_COMPONENT.get(player);
		if (comp.isBurst()) return burstColor;
		return normalColor;
	}

	@Override
	public boolean isBarVisible(ClientPlayerEntity player) {
		TrionComponent comp = GatewayComponents.TRION_COMPONENT.get(player);
		return comp.getTrion() >= comp.getMaxTrion();
	}

	@Override
	public int getBarValue(ClientPlayerEntity player) {
		return GatewayComponents.TRION_COMPONENT.get(player).getTrion();
	}

	@Override
	public float getBarFadeoutTime(ClientPlayerEntity player) {
		return 1f;
	}

	@Override
	public BarStyle getBarStyle(ClientPlayerEntity player) {
		return BarStyle.SEGMENTED;
	}
}
