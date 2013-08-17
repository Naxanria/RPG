package nl.naxanria.rpg.handler;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

import java.util.concurrent.ConcurrentHashMap;

public class DebugHandler
{

	public DebugHandler()
	{
		debug = new ConcurrentHashMap<String, Boolean>();
	}

	public void setDebug(RunsafePlayer player, boolean debug_)
	{
		debug.put(player.getName(), debug_);
	}

	public boolean isInDebug(RunsafePlayer player)
	{
		return debug.containsKey(player.getName()) && debug.get(player.getName());
	}

	public void debugMsg(RunsafePlayer player, String msg)
	{
		if(isInDebug(player))
			player.sendColouredMessage("&3[Debug] &7" + msg);
	}


	private ConcurrentHashMap<String, Boolean> debug;

}
