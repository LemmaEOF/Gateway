package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.api.CoilType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class PlainCoilComponent extends ItemComponent implements CoilComponent, AutoSyncedComponent {
	private int power = 75;
	private int stability = 100;
	private CoilType type = CoilType.LEGAL;

	public PlainCoilComponent(ItemStack stack) {
		super(stack);
	}

	@Override
	public boolean isMachine() {
		return false;
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

	public void setPower(int power) {
		this.power = power;
	}

	public void setStability(int stability) {
		this.stability = stability;
	}

	public void setType(CoilType type) {
		this.type = type;
	}

	public void readStackNbt(NbtCompound tag) {
		this.power = tag.getInt("Power");
		this.stability = tag.getInt("Stability");
		this.type = CoilType.values()[tag.getInt("Type")];
	}

	public void writeStackNbt(NbtCompound tag) {
		tag.putInt("Power", power);
		tag.putInt("Stability", stability);
		tag.putInt("Type", type.ordinal());
	}
}

