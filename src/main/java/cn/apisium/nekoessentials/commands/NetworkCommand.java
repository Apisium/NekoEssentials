package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

@CommandName("network")
public final class NetworkCommand extends BasicCommand implements Listener {
    private final HashMap<String, Integer> map = new HashMap<>();
    private final HashMap<UUID, String> playerToAddress = new HashMap<>();
    public NetworkCommand(Main main) {
        super(main);
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        sender.sendMessage("§b连接信息:");
        map.forEach((k, v) -> sender.sendMessage(k + "§7: " + v));
        if (sender instanceof Player) sender.sendMessage("Ping: §7" + ((Player) sender).getPing() + "ms");
        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLogin(final PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
        playerToAddress.put(e.getPlayer().getUniqueId(), e.getHostname().replace("\00FML2\00", "")
                .replace(".:", ":").replace(":25565", ""));
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        String ip = playerToAddress.get(e.getPlayer().getUniqueId());
        if (ip == null) return;
        map.put(ip, map.getOrDefault(ip, 0) + 1);
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        String ip = playerToAddress.get(e.getPlayer().getUniqueId());
        if (ip == null) return;
        Integer it = map.get(ip);
        if (it == null) return;
        if (--it == 0) map.remove(ip);
        else map.put(ip, it);
    }
}
