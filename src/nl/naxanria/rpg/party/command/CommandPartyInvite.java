package nl.naxanria.rpg.party.command;

import nl.naxanria.rpg.party.Party;
import nl.naxanria.rpg.party.handler.PartyHandler;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.Map;

public class CommandPartyInvite extends PlayerCommand
{

	public  CommandPartyInvite(PartyHandler partyHandler)
	{
		super("invite", "Invite a player to your party", "rpg.party.invite", new PlayerArgument(true));
		this.partyHandler = partyHandler;
	}


	@Override
	public String OnExecute(RunsafePlayer executor, Map<String, String> parameters) {

		Party party = partyHandler.getParty(executor);
		if (party != null && !party.isLeader(executor))
			return "&cYou are not the party leader!";

		if (parameters.get("player").equalsIgnoreCase(executor.getName()))
			return "&cYou can not invite yourself";

		RunsafePlayer invited = RunsafeServer.Instance.getPlayer(parameters.get("player"));
		if (invited == null)
			return "&cNo such player: " + parameters.get("player");

		if (!invited.isOnline())
			return invited.getPrettyName() + " &c is not online!";



		if (partyHandler.isInParty(invited))
			return invited.getPrettyName() + " &c is already in a party!";

		partyHandler.invite(executor, invited);
		invited.sendColouredMessage(String.format("%s &2has invited you join their party.", executor.getPrettyName()));

		return "&2Invited &f" + parameters.get("player");
	}

	private final PartyHandler partyHandler;

}
