package gay.lemmaeof.gateway.api;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.entity.player.PlayerEntity;

//TODO: documentation
public interface TrionComponent extends PlayerComponent<TrionComponent> {
	PlayerEntity getPlayer();
	boolean isTriggerActive();
	void tick();
	void activateTrigger(TriggerConfig config);
	void deactivateTrigger();
	TriggerConfig getConfig();
	boolean isBurst(); //TODO: better name?
	int getTrion();
	default void setTrion(int trion) {
		setTrion(trion, false);
	}
	void setTrion(int trion, boolean realOnly);
	int getMaxTrion();
	void setMaxTrion(int maxTrion);
}
