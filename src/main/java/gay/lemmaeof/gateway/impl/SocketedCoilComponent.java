package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;

import java.util.Optional;

import javax.annotation.Nullable;

public class SocketedCoilComponent extends ItemComponent implements CoilHolderComponent {
	private ItemStack coil = ItemStack.EMPTY;

	public SocketedCoilComponent(ItemStack stack) {
		super(stack, GatewayComponents.COIL_HOLDER);
		if (getOrCreateRootTag().contains("Coil", NbtElement.COMPOUND_TYPE)) {
			coil = ItemStack.fromNbt(getOrCreateRootTag().getCompound("Coil"));
		}
	}

	@Override
	public Coil getCoil() {
		updateComponent();
		if (GatewayComponents.COIL_HOLDER.isProvidedBy(coil)) {
			return GatewayComponents.COIL_HOLDER.get(coil).getCoil();
		}
		return null;
	}

	@Override
	public boolean tryUse(World world, @Nullable Entity user) {
		Coil currentSocket = getCoil();
		if (currentSocket != null && currentSocket.isCharged()) {
			currentSocket.use(world, user);
			return true;
		}
		return false;
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
}
