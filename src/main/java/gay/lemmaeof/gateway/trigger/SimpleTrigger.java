package gay.lemmaeof.gateway.trigger;

import gay.lemmaeof.gateway.api.Trigger;
import gay.lemmaeof.gateway.api.TriggerShifter;
import gay.lemmaeof.gateway.api.TrionComponent;
import net.minecraft.item.Item;

public class SimpleTrigger implements Trigger {
	private final Item item;

	public SimpleTrigger(Item item) {
		this.item = item;
	}

	@Override
	public void tick(TrionComponent component) {

	}

	@Override
	public TriggerShifter getItem() {
		return (TriggerShifter)item;
	}
}
