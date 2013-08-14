package nl.naxanria.rpg.party.command;

import nl.naxanria.rpg.party.Party;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandPartyDisband extends PlayerCommand
{

	public CommandPartyDisband(PartyHandler partyHandler)
	{
		super("disband", "disband the party", "rpg.party");
		this.partyHandler = partyHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		Party party = partyHandler.getParty(executor);
		if (party == null)
			return "&cYou are not in a party!";

		if (!party.isLeader(executor))
			return "&cOnly party leaders can disband the party";

		partyHandler.disband(executor);

		return null;
	}

	private final PartyHandler partyHandler;

}
