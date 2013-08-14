package nl.naxanria.rpg.command.base;

import nl.naxanria.rpg.database.PlayerLocationRepository;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandRpg extends PlayerCommand
{


	public CommandRpg(PlayerLocationRepository playerLocationRepository)
	{
		super("rpg", "Teleports you to the rpg world", "rpg.command.rpg");

		this.playerLocationRepository = playerLocationRepository;
	}


	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		if(executor.getWorld().getName().equalsIgnoreCase(playerLocationRepository.getRpgWorld()))
			return "&eYou are already enjoying your adventure!";

		executor.teleport(playerLocationRepository.getPlayerLocation(executor));

		return "&2Enjoy your adventures!";
	}

	private final PlayerLocationRepository playerLocationRepository;

}
