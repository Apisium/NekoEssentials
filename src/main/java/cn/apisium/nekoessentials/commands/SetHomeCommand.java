package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("sethome")
public final class SetHomeCommand extends BasicCommand {
    public SetHomeCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof final Player p)) return false;
        if (p.getGameMode() == GameMode.SPECTATOR) {
            p.sendMessage("��c�㲻�����Թ�ģʽ���ü�!");
        } else {
            DatabaseSingleton.INSTANCE.setPlayerData(p, "home", Serializer.serializeLocation(p.getLocation()));
            p.sendMessage("��a�ɹ����ü�!");
        }
        return true;
    }
}
