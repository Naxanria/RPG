package nl.naxanria.rpg.command.base;

import nl.naxanria.rpg.handler.DebugHandler;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandDebug extends PlayerCommand
{

	public CommandDebug(IConfiguration configuration, DebugHandler debugHandler)
	{
		super("rpgdebug", "turns debug on/off", "rpg.command.debug");
		this.configuration = configuration;
		this.debugHandler = debugHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		boolean debug = debugHandler.isInDebug(executor);
		debug = !debug;
		debugHandler.setDebug(executor, debug);
		return (debug) ? "&2Enabled debug" : "&cDisabled debug";
	}

	private final IConfiguration configuration;
	private final DebugHandler debugHandler;

}
