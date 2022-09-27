package gay.lemmaeof.gateway.trigger;

import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.api.TriggerItem;
import gay.lemmaeof.gateway.api.TrionComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class ChameleonTrigger implements Trigger {
	@Override
	public void tick(TrionComponent component) {
		PlayerEntity player = component.getPlayer();
		if (player.world.getTime() % 50 == 0
			&& player.getStackInHand(Hand.MAIN_HAND).isEmpty()
			&& player.getStackInHand(Hand.OFF_HAND).isEmpty()) {
			component.setTrion(component.getTrion() - 1, true);
		}
	}

	@Override
	public TriggerItem getItem() {
		return TriggerItem.NONE;
	}
}
