package nl.naxanria.rpg.party.command;

import nl.naxanria.rpg.party.Party;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandPartyKick extends PlayerCommand
{

	public CommandPartyKick(PartyHandler partyHandler)
	{
		super("kick", "Removes a player from your party", "rpg.party", new PlayerArgument());
		this.partyHandler = partyHandler;
	}


	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		RunsafePlayer toKick = RunsafeServer.Instance.getPlayer(parameters.get("player"));
		if (toKick == null)
			return "&cNo such player";

		Party party = partyHandler.getParty(executor);
		if (party == null)
			return "&cYou are not in a party!";

		if (!party.isLeader(executor))
			return "&cYou are not the party leader!";

		if (!party.isMember(toKick))
			return "&cPlayer is not in your party!";

		partyHandler.leaveParty(toKick);
		toKick.sendColouredMessage("&eYou have been removed from the group");

		party.messageAll(String.format("%s&e has been removed from your party", toKick.getPrettyName()));

		return null;
	}

	private final PartyHandler partyHandler;

}
