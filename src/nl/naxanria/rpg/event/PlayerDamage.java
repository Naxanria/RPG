package nl.naxanria.rpg.event;

import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.player.IPlayerDamageEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class PlayerDamage implements IPlayerDamageEvent, IConfigurationChanged
{


	public PlayerDamage(PartyHandler partyHandler, IOutput console)
	{
		this.partyHandler = partyHandler;
		this.console = console;
	}

	@Override
	public void OnPlayerDamage(RunsafePlayer player, RunsafeEntityDamageEvent event) {

		if (player.getWorld().getName().equalsIgnoreCase(world))
		{

			console.fine("damage in the world...");
			console.fine(event.getEventName());
			console.fine(event.getCause().name());
			console.fine(event.getEntity().toString());

			if(event.getEntity() instanceof RunsafePlayer)
			{
				RunsafePlayer damager = (RunsafePlayer) event.getEntity();

				console.fine("It is a player!");
				boolean sameParty = partyHandler.isInSameParty(damager, player);
				console.fine("Same party: " + sameParty);
				console.fine("%s hit %s", damager.getName(), player.getName());
				if (sameParty)
				{
					event.cancel();
				}


			}
		}

	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.world = configuration.getConfigValueAsString("world");
	}


	private String world;

	private final PartyHandler partyHandler;
	IOutput console;
}
