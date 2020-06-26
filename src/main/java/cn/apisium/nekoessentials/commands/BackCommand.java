package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.*;
import cn.apisium.nekoessentials.utils.Serializer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("back")
public final class BackCommand extends TeleportCommand {
    public BackCommand(Main main) {
        super(main);
    }

    @Override
    public void doTeleport(CommandSender sender, Player p, boolean now) {
        final byte[] bytes = instance.db.getPlayerData(p,"lastLocation");
        if (bytes == null) sender.sendMessage("��c�Ҳ�����һ��λ��!");
        else {
            final Location location = Serializer.deserializeLocation(bytes);
            instance.delayTeleport(p, location, now);
        }
    }
}
