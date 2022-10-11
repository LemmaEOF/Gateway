package gay.lemmaeof.gateway.hooks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

import javax.annotation.Nullable;

public class CustomDamageSource extends EntityDamageSource {
	private boolean isTrion;

	//TODO: more info?
	public CustomDamageSource(String name, @Nullable Entity entity) {
		super("gateway." + name, entity);
	}

	public CustomDamageSource setBypassesArmor() {
		super.setBypassesArmor();
		return this;
	}

	public boolean isTrion() {
		return isTrion;
	}

	public CustomDamageSource setTrion() {
		this.isTrion = true;
		return this;
	}
}
