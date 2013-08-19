package nl.naxanria.rpg.event;

import nl.naxanria.rpg.handler.MobHealthHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.entity.IEntityDeathEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.entity.RunsafeLivingEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDeathEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class EntityDeathEvent implements IEntityDeathEvent, IConfigurationChanged
{


	public EntityDeathEvent(MobHealthHandler mobHealthHandler)
	{
		this.mobHealthHandler = mobHealthHandler;
	}

	@Override
	public void OnEntityDeath(RunsafeEntityDeathEvent event)
	{
		RunsafeEntity entity = event.getEntity();
		if (!entity.getWorld().getName().equalsIgnoreCase(world.getName()))
			return;


		if (entity instanceof RunsafeLivingEntity)
		{
			if (entity instanceof RunsafePlayer)
				return;

			if (mobHealthHandler.keepsTrack((RunsafeLivingEntity) entity))
				mobHealthHandler.removeEntity((RunsafeLivingEntity) entity);
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		world = configuration.getConfigValueAsWorld("world");
	}

	private RunsafeWorld world;
	private final MobHealthHandler mobHealthHandler;

}
