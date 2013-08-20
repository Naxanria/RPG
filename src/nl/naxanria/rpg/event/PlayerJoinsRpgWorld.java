package nl.naxanria.rpg.event;

import nl.naxanria.rpg.base.BaseHealth;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import nl.naxanria.rpg.database.PlayerLocationRepository;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerJoinEvent;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerJoinEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class PlayerJoinsRpgWorld implements IPlayerJoinEvent, IPlayerTeleportEvent, IConfigurationChanged
{

	public PlayerJoinsRpgWorld(PlayerLocationRepository playerLocationRepository, PlayerHealthHandler playerHealthHandler)
	{
		this.playerLocationRepository = playerLocationRepository;
		this.playerHealthHandler = playerHealthHandler;
	}

	@Override
	public void OnPlayerJoinEvent(RunsafePlayerJoinEvent event)
	{
		RunsafePlayer player = event.getPlayer();

		if(player.getWorld().getName().equalsIgnoreCase(world))
		{
			if (!playerHealthHandler.keepsTrackOf(player))
			{
				double playerHealth = BaseHealth.getBaseHealth(player);
				playerHealthHandler.addPlayer(player, playerHealth, playerHealth);
			}
		}
	}

	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		RunsafePlayer player = event.getPlayer();
		if (event.getFrom().getWorld().getName().equalsIgnoreCase(world)
				&& !event.getTo().getWorld().getName().equalsIgnoreCase(world))
			playerLocationRepository.safePlayerLocation(player, event.getFrom());
	}


	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		world = configuration.getConfigValueAsString("world");
	}

	private final PlayerLocationRepository playerLocationRepository;
	private final PlayerHealthHandler playerHealthHandler;

	private String world;

}
