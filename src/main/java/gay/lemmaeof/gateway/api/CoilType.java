package gay.lemmaeof.gateway.api;

import org.jetbrains.annotations.Nullable;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

//TODO: other coil types? unenum it?
public enum CoilType {
	//TODO: rework with heat system
	LEGAL(null, 60, false),
	ILLEGAL(Text.translatable("gateway.coil.illegal").formatted(Formatting.RED), 20, false),
	KEY(Text.translatable("gateway.coil.key").formatted(Formatting.AQUA), 240, true),
	MIRAI(Text.translatable("gateway.coil.mirai").formatted(Formatting.GREEN), -1, true);

	@Nullable
	private final Text name;
	private final int decayRate;
	private final boolean alwaysCharged;


	CoilType(@Nullable Text name, int decayRate, boolean alwaysCharged) {
		this.name = name;
		this.decayRate = decayRate;
		this.alwaysCharged = alwaysCharged;
	}

	@Nullable
	public Text getName() {
		return name;
	}

	public int getDecayRate() {
		return decayRate;
	}

	public boolean isAlwaysCharged() {
		return alwaysCharged;
	}
}
