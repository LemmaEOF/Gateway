package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.api.CoilType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class MachineCoilComponent extends ItemComponent implements CoilComponent, AutoSyncedComponent {

	public MachineCoilComponent(ItemStack stack, ComponentKey<CoilComponent> key) {
		super(stack, key);
		setPower(75);
		setStability(100);
		setType(CoilType.LEGAL);
		setStabilityTicks(0);
	}

	public void initialize(CoilComponent coil) {
		setPower(coil.getPower());
		setStability(coil.getStability());
		setType(coil.getType());
	}

	public boolean use() {
		//TODO: rework for legal coils
		if (getType() == CoilType.MIRAI) return false; //無限のエネルギ
		setStabilityTicks(getStabilityTicks() + 1);
		if (getStabilityTicks() >= getType().getDecayRate()) {
			setStability(getStability() - 1);
			setStabilityTicks(0);
		}
		return getStability() <= 0;
	}

	@Override
	public boolean isMachine() {
		return true;
	}

	@Override
	public int getPower() {
		return getOrCreateRootTag().getInt("Power");
	}

	@Override
	public int getStability() {
		return getOrCreateRootTag().getInt("Stability");
	}

	@Override
	public CoilType getType() {
		return CoilType.values()[getOrCreateRootTag().getInt("Type")];
	}

	private int getStabilityTicks() {
		return getOrCreateRootTag().getInt("StabilityTicks");
	}

	private void setPower(int power) {
		getOrCreateRootTag().putInt("Power", power);
	}

	private void setStability(int stability) {
		getOrCreateRootTag().putInt("Stability", stability);
	}

	private void setType(CoilType type) {
		getOrCreateRootTag().putInt("Type", type.ordinal());
	}

	private void setStabilityTicks(int stabilityTicks) {
		getOrCreateRootTag().putInt("StabilityTicks", stabilityTicks);
	}
}

