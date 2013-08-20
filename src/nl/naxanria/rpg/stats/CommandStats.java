package nl.naxanria.rpg.stats;

import no.runsafe.framework.api.command.argument.IArgument;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandStats extends PlayerCommand
{

	public CommandStats(StatsHandler statsHandler)
	{
		super("stats", "Change someones stats", "rpg.stats", new RequiredArgument("stat"), new RequiredArgument("value"), new PlayerArgument(false));
		this.statsHandler = statsHandler;

		stats = new ArrayList<String>();
		for(Stat stat : Stat.values())
			stats.add(stat.name());
	}

	@Nullable
	@Override
	public List<String> getParameterOptions(@Nonnull String parameter) {

		if (parameter.equalsIgnoreCase("stat"))
			return stats;

		return super.getParameterOptions(parameter);
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		double value = Double.parseDouble(parameters.get("value"));

		if (!stats.contains(parameters.get("stat")))
			return "&cIncorrect stat!";
		Stat stat = Stat.valueOf(parameters.get("stat"));


		RunsafePlayer player = executor;
		if(parameters.containsKey("player"))
		{
			player = RunsafeServer.Instance.getPlayer(parameters.get("player"));
			if (player == null)
				return "&cCould not find that player";
		}


		statsHandler.setStat(player, stat, value);
		return String.format("&2Updated the &f%s &2of &f%s &2to &f%.2f", stat.getName(), player.getPrettyName(), value);
	}

	private ArrayList<String> stats;
	private final StatsHandler statsHandler;

}
