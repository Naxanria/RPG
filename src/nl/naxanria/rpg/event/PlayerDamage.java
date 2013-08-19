package nl.naxanria.rpg.event;

import nl.naxanria.rpg.handler.DebugHandler;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.player.IPlayerDamageEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDamageEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class PlayerDamage implements IPlayerDamageEvent, IConfigurationChanged
{

	public PlayerDamage(IOutput console, PlayerHealthHandler playerHealthHandler, DebugHandler debugHandler)
	{
		this.console = console;
		this.playerHealthHandler = playerHealthHandler;
		this.debugHandler = debugHandler;
	}

	@Override
	public void OnPlayerDamage(RunsafePlayer player, RunsafeEntityDamageEvent event)
	{

		if (player.getWorld().getName().equalsIgnoreCase(world))
		{

			console.fine("damage in the world...");
			console.fine(event.getEventName());
			console.fine(event.getCause().name());
			double percent = (event.getDamage() != 0) ? event.getDamage() / 20 : 0;

			debugHandler.debugMsg(player, String.format("Event: %s - Cause: %s - Damage: %.2f - Percent: %.2f%%",
					event.getEventName(), event.getCause().name(), event.getDamage(), percent
			));



			if (event.getEventName().equalsIgnoreCase("EntityDamageEvent"))
			{
				if (event.getCause().name().equalsIgnoreCase("FALL"))
				{
					playerHealthHandler.updateHealth(player,
							-playerHealthHandler.getMaxHealth(player) * percent);
					event.setDamage(0);
				}
				else if (event.getCause().name().equalsIgnoreCase("FIRE"))
				{
					playerHealthHandler.updateHealth(player,
							-playerHealthHandler.getMaxHealth(player) * percent);
					event.setDamage(0);
				}
				else if (event.getCause().name().equalsIgnoreCase("FIRE_TICK"))
				{
					playerHealthHandler.updateHealth(player,
							-playerHealthHandler.getMaxHealth(player) * percent);
					event.setDamage(0);
				}
				else if (event.getCause().name().equalsIgnoreCase("POISON"))
				{
					playerHealthHandler.updateHealth(player,
							-playerHealthHandler.getMaxHealth(player) * percent);
					event.setDamage(0);
				}
				else if (event.getCause().name().equalsIgnoreCase("MAGIC"))
				{
					playerHealthHandler.updateHealth(player,
							-playerHealthHandler.getMaxHealth(player) * percent);
					event.setDamage(0);
				}
				else if (event.getCause().name().equalsIgnoreCase("STARVATION"))
				{
					playerHealthHandler.updateHealth(player,
							-playerHealthHandler.getMaxHealth(player) * percent);
					event.setDamage(0);
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

	private final IOutput console;
	private final PlayerHealthHandler playerHealthHandler;
	private final DebugHandler debugHandler;
}
