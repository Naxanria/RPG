package nl.naxanria.rpg.event;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.entity.IEntityDeathEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDeathEvent;

public class EntityDeathEvent implements IEntityDeathEvent, IConfigurationChanged
{



	@Override
	public void OnEntityDeath(RunsafeEntityDeathEvent event)
	{
		event.getEntity();

	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{

	}
}
