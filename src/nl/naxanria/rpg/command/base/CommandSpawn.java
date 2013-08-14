package nl.naxanria.rpg.command.base;

import nl.naxanria.rpg.database.PlayerLocationRepository;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandSpawn extends PlayerCommand implements IConfigurationChanged
{

	public CommandSpawn(PlayerLocationRepository playerLocationRepository)
	{
		super("spawn", "teleports the user to spawn", "rpg.command.spawn");
		this.playerLocationRepository = playerLocationRepository;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		spawn = new RunsafeLocation(
				RunsafeServer.Instance.getWorld(configuration.getConfigValueAsString("spawn.world")),
				configuration.getConfigValueAsDouble("spawn.x"),
				configuration.getConfigValueAsDouble("spawn.y"),
				configuration.getConfigValueAsDouble("spawn.z"),
				configuration.getConfigValueAsFloat("spawn.yaw"),
				configuration.getConfigValueAsFloat("spawn.pitch")
				);
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		if(executor.getWorld().getName().equalsIgnoreCase(playerLocationRepository.getRpgWorld()))
			playerLocationRepository.safePlayerLocation(executor);

		executor.teleport(spawn);

		return null;
	}

	private RunsafeLocation spawn;
	private final PlayerLocationRepository playerLocationRepository;
}
