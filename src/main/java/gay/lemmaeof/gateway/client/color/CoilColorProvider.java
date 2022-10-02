package gay.lemmaeof.gateway.client.color;

import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class CoilColorProvider implements ItemColorProvider {
	public static final CoilColorProvider INSTANCE = new CoilColorProvider();

	@Override
	public int getColor(ItemStack stack, int index) {
		if (index == 0) return 0xFFFFFF;
		PlayerEntity player = MinecraftClient.getInstance().player;
		CoilHolderComponent comp = GatewayComponents.COIL_HOLDER.get(stack);
		if (comp.getCoil().isEmpty()) return 0xFFFFFF;
		Coil coil = comp.getCoil().get();
		if (player != null && !stack.getOrCreateNbt().getBoolean("AlwaysGlow") && !coil.getType().isAlwaysCharged()) {
			//TODO: more efficient way to do this? lib39-sandman?
			if (player.hasStatusEffect(GatewayStatusEffects.CHARGED)) {
				for (int i = 0; i < player.getInventory().size(); i++) {
					if (player.getInventory().getStack(i) == stack) {
						return getCoilColor(coil);
					}
				}
			}
		} else {
			return getCoilColor(coil);
		}
		return 0;
	}

	public static int getCoilColor(Coil coil) {
		float hue = coil.getStability() / 200f;
		float powerMul = coil.getPower() / 100f;
		float decis = System.nanoTime() / 100_000_000f;
		float value = (float) (Math.sin(decis * powerMul) + 2) / 3;
		return MathHelper.hsvToRgb(hue, 1f, value);
	}
}
