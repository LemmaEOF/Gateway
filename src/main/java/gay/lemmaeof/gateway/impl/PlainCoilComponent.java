package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.api.CoilType;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class PlainCoilComponent extends ItemComponent implements CoilComponent, AutoSyncedComponent {

	public PlainCoilComponent(ItemStack stack) {
		super(stack, GatewayComponents.COIL);
	}

	@Override
	public boolean isMachine() {
		return false;
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

	public void setPower(int power) {
		getOrCreateRootTag().putInt("Power", power);
	}

	public void setStability(int stability) {
		getOrCreateRootTag().putInt("Stability", stability);
	}

	public void setType(CoilType type) {
		getOrCreateRootTag().putInt("Type", type.ordinal());
	}
}

