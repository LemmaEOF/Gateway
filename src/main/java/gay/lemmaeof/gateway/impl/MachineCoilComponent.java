package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.api.CoilType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class MachineCoilComponent extends ItemComponent implements CoilComponent, AutoSyncedComponent {
	private int power = 75;
	private int stability = 100;
	private CoilType type = CoilType.LEGAL;
	private int stabilityTicks = 0;

	public MachineCoilComponent(ItemStack stack) {
		super(stack);
	}

	public void initialize(CoilComponent coil) {
		this.power = coil.getPower();
		this.stability = coil.getStability();
		this.type = coil.getType();
	}

	public boolean use() {
		if (type == CoilType.MIRAI) return false; //無限のエネルギ
		stabilityTicks++;
		if (stabilityTicks >= type.getDecayRate()) {
			stability--;
			stabilityTicks = 0;
		}
		return stability <= 0;
	}

	@Override
	public boolean isMachine() {
		return true;
	}

	@Override
	public int getPower() {
		return power;
	}

	@Override
	public int getStability() {
		return stability;
	}

	@Override
	public CoilType getType() {
		return type;
	}

	public void readStackNbt(NbtCompound tag) {
		this.power = tag.getInt("Power");
		this.stability = tag.getInt("Stability");
		this.type = CoilType.values()[tag.getInt("Type")];
		this.stabilityTicks = tag.getInt("StabilityTicks");
	}

	public void writeStackNbt(NbtCompound tag) {
		tag.putInt("Power", power);
		tag.putInt("Stability", stability);
		tag.putInt("Type", type.ordinal());
		tag.putInt("StabilityTicks", stabilityTicks);
	}
}

