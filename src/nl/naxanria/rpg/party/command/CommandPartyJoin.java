package nl.naxanria.rpg.party.command;

import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandPartyJoin extends PlayerCommand
{

	public CommandPartyJoin(PartyHandler partyHandler)
	{
		super("join", "join a party", "rpg.party.join", new PlayerArgument(false));
		this.partyHandler = partyHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters)
	{

		if (partyHandler.isInParty(executor))
			return "&cYou are already in a party!";

		RunsafePlayer invitee = RunsafeServer.Instance.getPlayer(parameters.get("player"));
		if (invitee == null)
			return "&cCould not find &f" + parameters.get("player");

		if (!partyHandler.isInvited(executor, invitee))
			return "&cYou do not seem not been invites by that player";

		partyHandler.join(invitee, executor);
		invitee.sendColouredMessage(String.format("%s &2has joined your party!", executor.getPrettyName()));
		partyHandler.removeInvites(executor);

		return "&2You have joined the party!";
	}

	private final PartyHandler partyHandler;

}
