package nl.naxanria.rpg.party.handler;

import nl.naxanria.rpg.party.Party;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class PartyHandler
{

	public PartyHandler()
	{
		parties = new HashMap<Integer, Party>();
		partyLink = new HashMap<String, Integer>();
		invites = new HashMap<String, ArrayList<Integer>>();
		invitees = new HashMap<String, ArrayList<String>>();
	}

	public void join(RunsafePlayer toJoin, RunsafePlayer joining)
	{

		Party party = getParty(toJoin);
		if (party == null )
			newParty(toJoin, joining);
		else
			addToParty(toJoin, joining);
	}

	public void removeInvites(RunsafePlayer player)
	{
		invites.remove(player.getName());
		invitees.remove(player.getName());
	}

	public boolean isInvited(RunsafePlayer player, RunsafePlayer invitee)
	{
		int searchId = getPartyId(invitee);
		if (searchId == -1)
			return invitees.get(player.getName()).contains(invitee.getName());

		for (int id : invites.get(player.getName()))
			if (id == searchId) return true;
		return false;
	}

	public void newParty(RunsafePlayer leader, RunsafePlayer member)
	{
		Party party = new Party(leader, member);
		parties.put(lastPartyId + 1, party);
		partyLink.put(leader.getName(), lastPartyId + 1);
		partyLink.put(member.getName(), lastPartyId + 1);

		lastPartyId++;
	}

	public void addToParty(RunsafePlayer leader, RunsafePlayer member)
	{
		Party party = getParty(leader);
		int id = getPartyId(leader);

		party.addMember(member);
		partyLink.put(member.getName(), id);
	}

	public void invite(RunsafePlayer invitee, RunsafePlayer invited)
	{
		if (!invites.containsKey(invited.getName()))
			invites.put(invited.getName(), new ArrayList<Integer>());
		invites.get(invited.getName()).add(getPartyId(invitee));
		if (!invitees.containsKey(invited.getName()))
			invitees.put(invited.getName(), new ArrayList<String>());
		invitees.get(invited.getName()).add(invitee.getName());
	}


	public Party getParty(RunsafePlayer player)
	{
		if (!partyLink.containsKey(player.getName()))
			return null;
		return parties.get(partyLink.get(player.getName()));
	}

	public int getPartyId(RunsafePlayer player)
	{

		if(!partyLink.containsKey(player.getName()))
			return -1;

		return partyLink.get(player.getName());
	}

	public boolean isInParty(RunsafePlayer player)
	{
		return getParty(player) != null;
	}

	public boolean isInSameParty(RunsafePlayer search, RunsafePlayer partyPlayer)
	{
		Party party = getParty(partyPlayer);
		if (party == null)
			return false;
		return party.isMember(search);
	}

	public void leaveParty(RunsafePlayer player)
	{
		getParty(player).removeMember(player);
		partyLink.remove(player.getName());
	}


	public void disband(RunsafePlayer player)
	{
		Party party = getParty(player);
		party.messageAll("&cThe group has been disbanded");
		int partyid = getPartyId(player);
		for(RunsafePlayer member : party.getMembers())
			leaveParty(member);
		parties.remove(partyid);
	}

	private HashMap<Integer, Party> parties;
	private HashMap<String, Integer> partyLink;
	private int lastPartyId = 0;
	private HashMap<String, ArrayList<Integer>> invites;
	private HashMap<String, ArrayList<String>> invitees;

}
