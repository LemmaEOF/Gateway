package gay.lemmaeof.gateway.item;

import dev.emi.trinkets.api.TrinketItem;
import gay.lemmaeof.gateway.api.CoilComponent;
import gay.lemmaeof.gateway.init.GatewayComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CoilItem extends TrinketItem {

	public CoilItem(Item.Settings settings) {
		super(settings);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		CoilComponent component = GatewayComponents.COIL.get(stack);
		if (Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("tooltip.gateway.params").formatted(Formatting.GRAY));
			tooltip.add(Text.translatable("tooltip.gateway.power", component.getPower()).formatted(Formatting.GRAY));
			tooltip.add(Text.translatable("tooltip.gateway.stability", component.getStability()).formatted(Formatting.GRAY));
			Text type = component.getType().getName();
			if (type != null) {
				tooltip.add(Text.translatable("tooltip.gateway.type", type).formatted(Formatting.GRAY));
			}
		} else {
			tooltip.add(Text.translatable("tooltip.gateway.more").formatted(Formatting.GRAY));
		}
	}
}
