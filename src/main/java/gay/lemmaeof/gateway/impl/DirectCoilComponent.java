package gay.lemmaeof.gateway.impl;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import gay.lemmaeof.gateway.api.Coil;
import gay.lemmaeof.gateway.api.CoilHolderComponent;
import gay.lemmaeof.gateway.api.CoilType;
import gay.lemmaeof.gateway.init.GatewayComponents;
import gay.lemmaeof.gateway.init.GatewayStatusEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Optional;

import javax.annotation.Nullable;

public class DirectCoilComponent extends ItemComponent implements Coil, CoilHolderComponent {

	public DirectCoilComponent(ItemStack stack) {
		super(stack, GatewayComponents.COIL_HOLDER);
	}

	@Override
	public Coil getCoil() {
		return this;
	}

	@Override
	public int getPower() {
		return getOrCreateRootTag().getInt("Power");
	}

	public void setPower(int power) {
		getOrCreateRootTag().putInt("Power", power);
	}

	@Override
	public int getStability() {
		return getOrCreateRootTag().getInt("Stability");
	}

	public void setStability(int stability) {
		getOrCreateRootTag().putInt("Stability", stability);
	}

	@Override
	public int getHeat() {
		return getOrCreateRootTag().getInt("Heat");
	}

	public void setHeat(int heat) {
		getOrCreateRootTag().putInt("Heat", Math.min(heat, getType().getMaxHeat()));
	}

	@Override
	public boolean isCharged() {
		return getType().isAlwaysCharged() || getOrCreateRootTag().getBoolean("Charged");
	}

	private void setCharged(boolean charged) {
		getOrCreateRootTag().putBoolean("Charged", charged);
	}

	@Override
	public CoilType getType() {
		return CoilType.values()[getOrCreateRootTag().getInt("Type")];
	}

	public void setType(CoilType type) {
		getOrCreateRootTag().putInt("Type", type.ordinal());
	}

	@Override
	public void tick(World world, @Nullable Entity user) {
		int heat = getHeat();
		if (heat > 0) setHeat(heat - 1);
		if (!getType().isAlwaysCharged()) {
			if (user instanceof LivingEntity living) {
				boolean charged = isCharged();
				if (charged && !living.hasStatusEffect(GatewayStatusEffects.CHARGED)) {
					setCharged(false);
				} else if (!charged && living.hasStatusEffect(GatewayStatusEffects.CHARGED)) {
					setCharged(true);
				}
			} else {
				setCharged(false);
			}
		}
	}

	@Override
	public void use(World world, @Nullable Entity user) {
		int heatToGen = Math.max(2, getPower() / 20);
		setHeat(getHeat() + heatToGen);
		if (getHeat() == getType().getMaxHeat()) {
			if (world.getRandom().nextInt(70) + 31 < getPower()) {
				//TODO: cooldown from a stability loss?
				setStability(getStability() - 1);
				if (getStability() <= 0) {
					//do something really really bad
				}
			}
		}
	}

	@Override
	public boolean tryUse(World world, @Nullable Entity user) {
		if (isCharged()) {
			use(world, user);
			return true;
		} else {
			return false;
		}
	}
}

