package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.api.CoilType;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class DirectCoilComponent extends ItemComponent implements Coil, CoilHolderComponent {

	public DirectCoilComponent(ItemStack stack) {
		super(stack, GatewayComponents.COIL_HOLDER);
	}

	@Override
	public Optional<Coil> getCoil() {
		return Optional.of(this);
	}

	@Override
	public int getPower() {
		return getOrCreateRootTag().getInt("Power");
	}

	public void setPower(int power) {
		getOrCreateRootTag().putInt("Power", power);
	}

	@Override
	public int getStability() {
		return getOrCreateRootTag().getInt("Stability");
	}

	public void setStability(int stability) {
		getOrCreateRootTag().putInt("Stability", stability);
	}

	@Override
	public int getHeat() {
		return getOrCreateRootTag().getInt("Heat");
	}

	public void setHeat(int heat) {
		getOrCreateRootTag().putInt("Heat", heat);
	}

	@Override
	public CoilType getType() {
		return CoilType.values()[getOrCreateRootTag().getInt("Type")];
	}

	public void setType(CoilType type) {
		getOrCreateRootTag().putInt("Type", type.ordinal());
	}

	public void tick() {
		int heat = getHeat();
		if (heat > 0) setHeat(heat - 1);
	}
}

