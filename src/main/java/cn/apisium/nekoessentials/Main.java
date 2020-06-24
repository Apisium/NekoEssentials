package cn.apisium.nekoessentials;

import cn.apisium.nekoessentials.commands.*;
import cn.apisium.nekoessentials.utils.Pair;
import cn.apisium.nekoessentials.utils.Serializer;
import cn.apisium.nekoessentials.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.*;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.scheduler.BukkitTask;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

@Plugin(name = "NekoEssentials", version = "1.0")
@Description("An essential plugin used in NekoCraft.")
@Author("Shirasawa")
@Website("https://apisium.cn")
@Command(name = "back", permission = "nekoess.back")
@Command(name = "tpcancel")
@Command(name = "home", permission = "nekoess.home")
@Command(name = "mute", permission = "nekoess.mute")
@Command(name = "othershome", permission = "nekoess.others")
@Command(name = "sethome", permission = "nekoess.home")
@Command(name = "spawn", permission = "nekoess.spawn")
@Command(name = "sudo", permission = "nekoess.sudo")
@Command(name = "toggle", permission = "nekoess.toggle")
@Command(name = "tpaall", permission = "nekoess.tpaall")
@Command(name = "tpaccept")
@Command(name = "tpa", permission = "nekoess.tpa")
@Command(name = "tpdeny")
@Command(name = "tphere", permission = "nekoess.tphere")
@Permission(name = "nekoess.spawn", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.home", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.tpa", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.tphere", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.back", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.toggle", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.tpaall", defaultValue = PermissionDefault.TRUE)
@Permission(name = "nekoess.others")
@Permission(name = "nekoess.immediate")
@Permission(name = "nekoess.sudo")
@Permission(name = "nekoess.mute")
@ApiVersion(ApiVersion.Target.v1_13)
public final class Main extends JavaPlugin{
    public final WeakHashMap<Player, Pair<Integer, Location>> countdowns = new WeakHashMap<>();
    public final WeakHashMap<Player, Pair<Long, Runnable>> playerTasks = new WeakHashMap<>();
    private final WeakHashMap<Player, Long> delays = new WeakHashMap<>();
    private BukkitTask countdownTask;
    public DB db;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) getDataFolder().mkdir();
            db = Iq80DBFactory.factory.open(new File(getDataFolder(), "database"),
                    new Options().createIfMissing(true));
        } catch (IOException e) {
            e.printStackTrace();
            setEnabled(false);
            return;
        }
        getServer().getPluginManager().registerEvents(new Events(this), this);
        try {
            Utils.loadCommands(
                    this,
                    BackCommand.class,
                    CancelCommand.class,
                    HomeCommand.class,
                    MuteCommand.class,
                    OthersHomeCommand.class,
                    SetHomeCommand.class,
                    SpawnCommand.class,
                    SudoCommand.class,
                    ToggleCommand.class,
                    TpaAllCommand.class,
                    TpAcceptCommand.class,
                    TpaCommand.class,
                    TpDenyCommand.class,
                    TpHereCommand.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            setEnabled(false);
            return;
        }
        countdownTask = getServer().getScheduler().runTaskTimer(this, () -> {
            final Iterator<Map.Entry<Player, Pair<Integer, Location>>> itor = countdowns.entrySet().iterator();
            while (itor.hasNext()) {
                final Map.Entry<Player, Pair<Integer, Location>> it = itor.next();
                final Player p = it.getKey();
                final Pair<Integer, Location> pair = it.getValue();
                if (--pair.left < 1) {
                    itor.remove();
                    final Location dest = pair.right;
                    if (dest == null) continue;
                    recordPlayerLocation(p);
                    p.teleport(dest);
                    p.sendActionBar("��a���ͳɹ�!");
                } else p.sendActionBar("��e���� ��b" + pair.left + "�� ��e����д���!");
            }
        }, 20, 20);
    }

    @Override
    public void onDisable() {
        if (countdownTask != null) countdownTask.cancel();
        countdowns.clear();
        playerTasks.clear();
        try {
            if (db != null) db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db = null;
        countdownTask = null;
    }

    public void delayTeleport(final Player player, final Location loc) { delayTeleport(player, loc, false); }
    public void delayTeleport(final Player player, Location loc, final boolean now) {
        if (now || !shouldPlayerBeDelayed(player, loc)) {
            countdowns.put(player, new Pair<>(1, loc));
        } else {
            player.sendMessage(Constants.MESSAGE_HEADER);
            if (Utils.isSafeLocation(loc)) {
                player.sendMessage("  ��c��⵽Ŀ��λ�ÿ��ܲ���ȫ!");
                loc.setY(loc.getWorld().getHighestBlockYAt(loc));
            }
            player.sendMessage(Constants.CANCEL_HUB);
            player.sendMessage(Constants.MESSAGE_FOOTER);
            delays.put(player, System.currentTimeMillis() + 2 * 1000 * 60);
            countdowns.put(player, new Pair<>(15, loc));
        }
    }

    public void requestTeleport(Player player, String message, Runnable fn) {
        playerTasks.put(player, new Pair<>(System.currentTimeMillis() + 2 * 1000 * 60, fn));
        player.sendMessage(Constants.MESSAGE_HEADER);
        player.sendMessage(message);
        player.sendMessage(Constants.REQUEST_HUB);
        player.sendMessage(Constants.MESSAGE_FOOTER);
    }

    public boolean cancelTeleport(final Player player) {
        return countdowns.remove(player) != null;
    }

    public boolean shouldPlayerBeDelayed(Player player, Location loc) {
        if (player.hasPermission("nekoess.immediate")) return false;
        if (Utils.isSafeLocation(loc)) return true;
        Long time = delays.get(player);
        return time != null && time > System.currentTimeMillis();
    }

    public boolean canTeleportOthers(CommandSender who) {
        return who.hasPermission("nekoess.others");
    }

    public void recordPlayerLocation(Player player) { recordPlayerLocation(player, player.getLocation()); }
    public void recordPlayerLocation(Player player, Location loc) {
        db.put((player.getUniqueId().toString() + ".lastLocation").getBytes(), Serializer.serializeLocation(loc));
    }

    public Player getPlayer(CommandSender sender, String name) {
        final Player p = getServer().getPlayerExact(name);
        if (p == null) {
            sender.sendMessage(Constants.NO_SUCH_PLAYER);
            return null;
        }
        if (p == sender) {
            sender.sendMessage("��c�㲻�ܴ������Լ�!");
            return null;
        }
        return p;
    }
}