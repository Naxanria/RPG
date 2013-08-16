package nl.naxanria.rpg.event;

import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.entity.IEntityDamageByEntityEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageByEntityEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class EntityDamageByEntityEvent implements IEntityDamageByEntityEvent, IConfigurationChanged
{

	public EntityDamageByEntityEvent(PartyHandler partyHandler, IOutput console)
	{
		this.partyHandler = partyHandler;
		this.console = console;
	}

	@Override
	public void OnEntityDamageByEntity(RunsafeEntityDamageByEntityEvent event)
	{
		if(!event.getEntity().getWorld().getName().equalsIgnoreCase(world))
			return;

		RunsafeEntity damageActor = event.getDamageActor();
		RunsafeEntity damaged = event.getEntity();

		if(damageActor instanceof RunsafePlayer  && damaged instanceof RunsafePlayer)
		{

			if (partyHandler.isInSameParty((RunsafePlayer) damageActor, (RunsafePlayer) damaged))
			{
				try {
					event.cancel();
				}catch (Exception e){
					return;
				}
			}

		}


	}


	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		world = configuration.getConfigValueAsString("world");
	}

	private String world;
	private final PartyHandler partyHandler;
	private final IOutput console;



}
