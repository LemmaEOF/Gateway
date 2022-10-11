package gay.lemmaeof.gateway.api;

import com.unascribed.lib39.sandman.api.TicksAlwaysItem;
import gay.lemmaeof.gateway.Gateway;
import gay.lemmaeof.gateway.init.GatewayComponents;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface TriggerShifter extends ItemConvertible, TicksAlwaysItem {
	TriggerShifter NONE = new TriggerShifter() {
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

	default void blockInventoryTick(ItemStack stack, World world, BlockPos pos, int slot) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be instanceof Inventory inv) {
			inv.setStack(slot, unequip(stack));
		}
	}

	default void entityInventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (GatewayComponents.TRION.isProvidedBy(entity) && entity instanceof PlayerEntity player) {
			TrionComponent comp = GatewayComponents.TRION.get(entity);
			if (!comp.isTriggerActive()) {
				player.getInventory().setStack(slot, unequip(stack));
			}
		} else {
			if (entity instanceof ItemEntity ie) {
				ie.setStack(unequip(stack));
			}
		}
	}
}
