package nl.naxanria.rpg.party;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.ArrayList;

public class Party
{

	public Party(RunsafePlayer leader, RunsafePlayer member)
	{
		this.members = new ArrayList<RunsafePlayer>();
		this.names = new ArrayList<String>();

		members.add(leader);
		members.add(member);

		names.add(leader.getName());
		names.add(member.getName());

		this.leader = leader;

	}

	public Party(RunsafePlayer leader, ArrayList<RunsafePlayer> members)
	{
		this.leader = leader;
		this.members = members;

		names = new ArrayList<String>();
		for (RunsafePlayer player: members)
			names.add(player.getName());
	}

	public boolean isMember(RunsafePlayer player)
	{
		return  names.contains(player.getName());
	}

	public boolean isLeader(RunsafePlayer player)
	{
		return leader != null && player.getName().equalsIgnoreCase(leader.getName());
	}

	public boolean addMember(RunsafePlayer player)
	{
		if(!isMember(player))
		{
			members.add(player);
			names.add(player.getName());
			return true;
		}
		return false;
	}

	public boolean setLeader(RunsafePlayer player)
	{
		if(!isMember(player) || isLeader(player))
			return false;

		leader = player;

		return true;
	}

	public boolean removeMember(RunsafePlayer player)
	{
		if (!isMember(player))
			return false;

		members.remove(player);
		names.remove(player.getName());

		return true;
	}

	public ArrayList<RunsafePlayer> getMembers()
	{
		return members;
	}

	public int size()
	{
		return members.size();
	}

	public void messageAll(String msg)
	{
		for (RunsafePlayer player : members)
			player.sendColouredMessage(msg);
	}

	public void setNextLeader()
	{
		for (RunsafePlayer player : members)
			if (!player.getName().equalsIgnoreCase(leader.getName()))
			{
				leader = player;
				return;
			}
	}

	private RunsafePlayer leader;
	private ArrayList<RunsafePlayer> members;
	private ArrayList<String> names;


}
