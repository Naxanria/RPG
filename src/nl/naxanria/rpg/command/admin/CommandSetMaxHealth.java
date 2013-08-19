package nl.naxanria.rpg.command.admin;

import nl.naxanria.rpg.base.BaseHealth;
import nl.naxanria.rpg.handler.DebugHandler;
import nl.naxanria.rpg.handler.PlayerHealthHandler;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandSetMaxHealth extends PlayerCommand
{

	public CommandSetMaxHealth(PlayerHealthHandler playerHealthHandler, DebugHandler debugHandler)
	{
		super("setmaxhealth", "set the max health of a player", "rpg.command.admin", new RequiredArgument("health"), new PlayerArgument(false));
		this.healthHandler = playerHealthHandler;
		this.debugHandler = debugHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		RunsafePlayer player;
		if (!parameters.containsKey("player"))
			player = executor;
		else
		{
			player = RunsafeServer.Instance.getPlayer(parameters.get("player"));

			if (player == null)
				return "&cCould not find the player";
		}

		double newHealth;
		if (parameters.get("health").equalsIgnoreCase("base"))
			newHealth = BaseHealth.getBaseHealth(player);
		else
			newHealth = Double.valueOf(parameters.get("health"));
		if (newHealth <= 0)
			return "&cAtleast a value of 1 or higher is expected!";

		if (!healthHandler.keepsTrackOf(player))
			healthHandler.addPlayer(player, newHealth, newHealth);
		else
			healthHandler.setMaxHealth(player, newHealth);


		return "&2Updated the maximum health of " + player.getPrettyName() + "&2 to &f" + newHealth;

	}

	private final PlayerHealthHandler healthHandler;
	private final DebugHandler debugHandler;

}
