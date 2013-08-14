package nl.naxanria.rpg.party.command;

import nl.naxanria.rpg.party.Party;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandPartyChat extends PlayerCommand
{

	public CommandPartyChat(PartyHandler partyHandler)
	{
		super("partychat", "chat in a party", "rpg.party.command.chat", new TrailingArgument("msg"));
		this.partyHandler = partyHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{
		Party party = partyHandler.getParty(executor);
		if (party == null)
			return "&cYou are not in a party!";

		String prefix = "&f[&5PC&f]%s";
		if (party.isLeader(executor))
			prefix = "&f[&2PC&f]%s";

		party.messageAll(String.format(prefix + "&f: %s", executor.getPrettyName(), parameters.get("msg")));

		return null;
	}

	private final PartyHandler partyHandler;
}
