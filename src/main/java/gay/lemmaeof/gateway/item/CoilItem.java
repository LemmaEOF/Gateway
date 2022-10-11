package gay.lemmaeof.gateway.item;

import com.unascribed.lib39.sandman.api.TicksAlwaysItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import gay.lemmaeof.gateway.init.GatewayStatusEffects;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoilItem extends TrinketItem implements TicksAlwaysItem {

	public CoilItem(Item.Settings settings) {
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		CoilHolderComponent holder = GatewayComponents.COIL_HOLDER.get(stack);
		//coil item will always have a coil
		if (!holder.hasCoil()) throw new IllegalStateException("cardinal is broken");
		Coil coil = holder.getCoil();
		if (Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("tooltip.gateway.params").formatted(Formatting.GRAY));
			tooltip.add(Text.translatable("tooltip.gateway.power", coil.getPower()).formatted(Formatting.GRAY));
			tooltip.add(Text.translatable("tooltip.gateway.stability", coil.getStability()).formatted(Formatting.GRAY));
			Text type = coil.getType().getName();
			if (type != null) {
				tooltip.add(Text.translatable("tooltip.gateway.type", type).formatted(Formatting.GRAY));
			}
		} else {
			tooltip.add(Text.translatable("tooltip.gateway.more").formatted(Formatting.GRAY));
		}
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		super.tick(stack, slot, entity);
		GatewayComponents.COIL_HOLDER.get(stack).getCoil().tick(entity.world, entity);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		GatewayComponents.COIL_HOLDER.get(stack).getCoil().tick(entity.world, entity);
	}

	@Override
	public void blockInventoryTick(ItemStack stack, World world, BlockPos pos, int slot) {
		GatewayComponents.COIL_HOLDER.get(stack).getCoil().tick(world, null);
	}
}
