package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.Optional;

public class SocketedCoilComponent extends ItemComponent implements CoilHolderComponent {
	private ItemStack coil = ItemStack.EMPTY;

	public SocketedCoilComponent(ItemStack stack) {
		super(stack, GatewayComponents.COIL_HOLDER);
		if (getOrCreateRootTag().contains("Coil", NbtElement.COMPOUND_TYPE)) {
			coil = ItemStack.fromNbt(getOrCreateRootTag().getCompound("Coil"));
		}
	}

	@Override
	public Optional<Coil> getCoil() {
		updateComponent();
		if (GatewayComponents.COIL_HOLDER.isProvidedBy(coil)) {
			return GatewayComponents.COIL_HOLDER.get(coil).getCoil();
		}
		return Optional.empty();
	}

	public void socketCoil(ItemStack coil) {
		this.coil = coil;
		updateStack();
	}

	public ItemStack unsocketCoil() {
		updateComponent();
		ItemStack ret = coil;
		this.coil = ItemStack.EMPTY;
		updateStack();
		return ret;
	}

	private void updateStack() {
		if (!coil.isEmpty()) {
			getOrCreateRootTag().put("Coil", coil.writeNbt(new NbtCompound()));
		} else {
			getOrCreateRootTag().remove("Coil");
		}
	}

	private void updateComponent() {
		if (getOrCreateRootTag().contains("Coil", NbtElement.COMPOUND_TYPE)) {
			coil = ItemStack.fromNbt(getOrCreateRootTag().getCompound("Coil"));
		}
	}

	@Override
	public void tick() {
		//todo impl
	}
}
