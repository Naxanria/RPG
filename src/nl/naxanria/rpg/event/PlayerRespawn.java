package nl.naxanria.rpg.event;

import nl.naxanria.rpg.handler.PlayerHealthHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerRespawn;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class PlayerRespawn implements IPlayerRespawn, IConfigurationChanged
{

	public PlayerRespawn(PlayerHealthHandler playerHealthHandler)
	{
		this.playerHealthHandler = playerHealthHandler;
	}

	@Override
	public RunsafeLocation OnPlayerRespawn(RunsafePlayer player, RunsafeLocation location, boolean b)
	{
		if(player.getWorld().getName().equalsIgnoreCase(world))
			playerHealthHandler.updateHealth(player, playerHealthHandler.getMaxHealth(player));
		return null;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		world = configuration.getConfigValueAsString("world");
	}

	private final PlayerHealthHandler playerHealthHandler;
	private String world;
}
