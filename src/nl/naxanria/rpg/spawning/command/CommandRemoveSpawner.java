package nl.naxanria.rpg.spawning.command;

import nl.naxanria.rpg.spawning.handler.SpawnHandler;
import no.runsafe.framework.api.command.argument.IArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandRemoveSpawner extends PlayerCommand
{

	public CommandRemoveSpawner(SpawnHandler spawnHandler) {
		super("remove", "remove a spawner", "rpg.spawner.remove", new RequiredArgument("id"));
		this.spawnHandler = spawnHandler;
	}

	@Nullable
	@Override
	public List<String> getParameterOptions(@Nonnull String parameter) {

		ArrayList<String> out = new ArrayList<String>();
		out.add("ALL");
		for (int id : spawnHandler.getIds())
			out.add(String.valueOf(id));
		return out;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters) {


		if (parameters.get("id").equalsIgnoreCase("ALL"))
		{
			spawnHandler.clear();
			return "&eRemoved all spawn points!";
		}

		int id = Integer.parseInt(parameters.get("id"));

		boolean success = spawnHandler.remove(id);

		if (success)
			return "&3Successfully removed spawner with id: &f" + id;
		else
		  return "&cId &f" + id + " not found!";

	}

	private final SpawnHandler spawnHandler;

}
