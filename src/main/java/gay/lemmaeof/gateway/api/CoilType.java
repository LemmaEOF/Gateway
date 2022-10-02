package gay.lemmaeof.gateway.api;

import org.jetbrains.annotations.Nullable;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

//TODO: other coil types? unenum it?
public enum CoilType {
	LEGAL(null, 20, false),
	ILLEGAL(Text.translatable("gateway.coil.illegal").formatted(Formatting.RED), 50, false),
	KEY(Text.translatable("gateway.coil.key").formatted(Formatting.AQUA), 100, true),
	MIRAI(Text.translatable("gateway.coil.mirai").formatted(Formatting.GREEN), 0, true);

	@Nullable
	private final Text name;
	private final int maxHeat;
	private final boolean alwaysCharged;

	CoilType(@Nullable Text name, int maxHeat, boolean alwaysCharged) {
		this.name = name;
		this.maxHeat = maxHeat;
		this.alwaysCharged = alwaysCharged;
	}

	@Nullable
	public Text getName() {
		return name;
	}

	public int getMaxHeat() {
		return maxHeat;
	}

	public boolean isAlwaysCharged() {
		return alwaysCharged;
	}
}
