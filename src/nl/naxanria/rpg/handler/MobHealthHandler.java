package nl.naxanria.rpg.handler;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;

import java.util.concurrent.ConcurrentHashMap;

public class MobHealthHandler implements IConfigurationChanged
{

	public MobHealthHandler(IOutput console)
	{
		this.console = console;

		entityHealth = new ConcurrentHashMap<Integer, ConcurrentHashMap<String, Double>>();
	}

	public void addEntity(RunsafeLivingEntity entity)
	{
		addEntity(entity, baseHealth);
	}

	public void addEntity(RunsafeLivingEntity entity, double maxHealth)
	{
		ConcurrentHashMap<String, Double> entityMap = new ConcurrentHashMap<String, Double>();

		entityMap.put("maxHealth", maxHealth);
		entityMap.put("health", maxHealth);

		entityHealth.put(entity.getEntityId(), entityMap);
	}

	public void removeEntity(RunsafeLivingEntity entity)
	{
		entityHealth.remove(entity.getEntityId());
	}

	public boolean keepsTrack(RunsafeLivingEntity entity)
	{
		return entityHealth.containsKey(entity.getEntityId());
	}

	public double getMaxHealth(RunsafeLivingEntity entity)
	{
		return entityHealth.get(entity.getEntityId()).get("maxHealth");
	}

	public double getHealth(RunsafeLivingEntity entity)
	{
		return entityHealth.get(entity.getEntityId()).get("health");
	}

	public void setHealth(RunsafeLivingEntity entity, double health)
	{
		entityHealth.get(entity.getEntityId()).put("health", health);
	}

	public void updateHealth(RunsafeLivingEntity entity, double change)
	{
		double health = getHealth(entity);
		health += change;
		double maxHealth = getMaxHealth(entity);
		if (health > maxHealth)
			health = maxHealth;
		else
			if (health <= 0)
				entity.setHealth(0d);
		setHealth(entity, health);
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		baseHealth = configuration.getConfigValueAsDouble("mobs.base-health");
	}

	private ConcurrentHashMap<Integer, ConcurrentHashMap<String, Double>> entityHealth;

	private double baseHealth = 30;

	private final IOutput console;

}
