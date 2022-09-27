package gay.lemmaeof.gateway.api;

import org.jetbrains.annotations.Nullable;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

//TODO: other coil types? unenum it?
public enum CoilType {
	LEGAL(null, 60),
	ILLEGAL(Text.translatable("qi.coil.illegal").formatted(Formatting.RED), 20),
	KEY(Text.translatable("qi.coil.key").formatted(Formatting.AQUA), 240),
	MIRAI(Text.translatable("qi.coil.mirai").formatted(Formatting.GREEN), -1);

	@Nullable
	private final Text name;

	private final int decayRate;

	CoilType(@Nullable Text name, int decayRate) {
		this.name = name;
		this.decayRate = decayRate;
	}

	@Nullable
	public Text getName() {
		return name;
	}

	public int getDecayRate() {
		return decayRate;
	}
}
