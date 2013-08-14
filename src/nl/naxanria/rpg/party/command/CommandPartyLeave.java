package nl.naxanria.rpg.party.command;

import nl.naxanria.rpg.party.Party;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandPartyLeave extends PlayerCommand
{

	public CommandPartyLeave(PartyHandler partyHandler)
	{
		super("leave", "leave your party", "rpg.party");
		this.partyHandler = partyHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters) {


		Party party = partyHandler.getParty(executor);
		if (party == null)
			return "&cYou are not in a party!";
		if (party.isLeader(executor))
			party.setNextLeader();
		if (party.size() == 2) //disband
			partyHandler.disband(executor);
		else partyHandler.leaveParty(executor);
		party.messageAll(String.format("%s &ehas left the party", executor.getPrettyName()));
		return "&eYou have left the group";
	}

	private final PartyHandler partyHandler;

}
