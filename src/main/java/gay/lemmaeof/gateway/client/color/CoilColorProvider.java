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
		CoilHolderComponent comp = GatewayComponents.COIL_HOLDER.get(stack);
		if (!comp.hasCoil()) return 0xFFFFFF;
		Coil coil = comp.getCoil();
		if (stack.getOrCreateNbt().getBoolean("AlwaysGlow") || coil.isCharged()) {
				return getCoilColor(coil);
		} else {
			return 0;
		}
	}

	public static int getCoilColor(Coil coil) {
		float hue = coil.getStability() / 200f;
		float powerMul = coil.getPower() / 100f;
		float decis = System.nanoTime() / 100_000_000f;
		float value = (float) (Math.sin(decis * powerMul) + 2) / 3;
		return MathHelper.hsvToRgb(hue, 1f, value);
	}
}
