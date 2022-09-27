package gay.lemmaeof.gateway.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

public interface TriggerItem extends ItemConvertible {
	TriggerItem NONE = new TriggerItem() {
		@Override
		public Item asItem() {
			return Items.AIR;
		}

		@Override
		public ItemStack equip(ItemStack previous, TriggerConfig config) {
			return previous;
		}

		@Override
		public ItemStack unequip(ItemStack equipped) {
			return equipped;
		}
	};

	default ItemStack equip(ItemStack previous, TriggerConfig config) {
		ItemStack newStack = new ItemStack(asItem(), 1);
		NbtCompound tag = newStack.getOrCreateNbt();
		tag.put("Previous", previous.writeNbt(new NbtCompound()));
		return newStack;
	}

	default ItemStack unequip(ItemStack equipped) {
		NbtCompound tag = equipped.getOrCreateNbt();
		if (tag.contains("Previous")) {
			return ItemStack.fromNbt(tag.getCompound("Previous"));
		}
		return equipped;
	}
}
