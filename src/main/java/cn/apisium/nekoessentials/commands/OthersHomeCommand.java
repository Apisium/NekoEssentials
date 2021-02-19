package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import cn.apisium.nekoessentials.utils.Utils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("othershome")
public final class OthersHomeCommand extends BasicCommand {
    public OthersHomeCommand(Main main) {
        super(main);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) || args.length != 1) return false;
        final OfflinePlayer o = instance.getServer().getOfflinePlayer(args[0]);
        if (!o.hasPlayedBefore()) {
            sender.sendMessage("§c该玩家还从未在服务器游玩过!");
            return true;
        }
        final byte[] bytes = DatabaseSingleton.INSTANCE.getPlayerData(o, "home");
        if (bytes == null) sender.sendMessage("§c该玩家还没有设置家!");
        else {
            Utils.teleportPlayer((Player) sender, Serializer.deserializeLocation(bytes));
            sender.sendMessage("§a传送成功!");
        }
        return true;
    }
}
