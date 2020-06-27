package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.*;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("home")
public final class HomeCommand extends TeleportCommand {
    public HomeCommand(Main main) {
        super(main);
    }

    @Override
    public void doTeleport(CommandSender sender, Player p, boolean now) {
        final byte[] bytes = DatabaseSingleton.INSTANCE.getPlayerData(p, "home");
        if (bytes == null) sender.sendMessage("��c�㻹û�����ü�!");
        else instance.delayTeleport(p, Serializer.deserializeLocation(bytes), now);
    }
}
