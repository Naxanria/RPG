package nl.naxanria.rpg.event;

import nl.naxanria.rpg.handler.PlayerHealthHandler;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.inventory.*;
import no.runsafe.framework.minecraft.event.inventory.RunsafeInventoryClickEvent;
import no.runsafe.framework.minecraft.event.inventory.RunsafeInventoryCloseEvent;
import no.runsafe.framework.minecraft.event.inventory.RunsafeInventoryMoveItemEvent;
import no.runsafe.framework.minecraft.event.inventory.RunsafeInventoryPickupItemEvent;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

import java.util.ArrayList;
import java.util.List;


public class InventoryChangeEvent implements IInventoryOpen, IInventoryClosed, IInventoryClick, IInventoryPickupItem, IInventoryMoveItem
{

	public InventoryChangeEvent(IOutput console, PlayerHealthHandler playerHealthHandler)
	{
		this.console = console;
		this.playerHealthHandler = playerHealthHandler;
	}

	@Override
	public void OnInventoryClickEvent(RunsafeInventoryClickEvent event)
	{
		console.fine(event.getEventName());
		console.fine(event.getWhoClicked().getName());
		console.fine(event.getInventory().getName());
	}

	@Override
	public void OnInventoryClosed(RunsafeInventoryCloseEvent event)
	{
		console.fine(event.getEventName());
		console.fine(event.getPlayer().getName());
		console.fine(event.getInventory().getName());
	}

	@Override
	public void OnInventoryMoveItemEvent(RunsafeInventoryMoveItemEvent event)
	{
		console.fine(event.getEventName());
		console.fine(playersToString(event.getInitiator().getViewers()));
	}

	@Override
	public boolean OnInventoryOpen(RunsafePlayer player, RunsafeInventory inventory)
	{

		console.fine(player.getName());
		console.fine(inventory.getName());

		return false;
	}

	@Override
	public void OnInventoryPickupItemEvent(RunsafeInventoryPickupItemEvent event)
	{
		console.fine(event.getEventName());
		console.fine(event.getInventory().getName());
	}

	public ArrayList<String> playersToNames(List<RunsafePlayer> players)
	{
		ArrayList<String> out = new ArrayList<String>();
		for (RunsafePlayer player : players)
			out.add(player.getName());
		return out;
	}

	public String playersToString(List<RunsafePlayer> players)
	{
		return Strings.join(playersToNames(players), " ,");
	}



	private final IOutput console;
	private final PlayerHealthHandler playerHealthHandler;

}
