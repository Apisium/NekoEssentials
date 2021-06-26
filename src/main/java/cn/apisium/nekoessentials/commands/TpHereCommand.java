package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("tphere")
public final class TpHereCommand extends BasicCommand {
    public TpHereCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof final Player sp) || args.length != 1) return false;
        final Player p = instance.getPlayer(sender, args[0]);
        if (p == null) return true;
        instance.requestTeleport(p, "  ��d��� ��f" + sp.getName() + " ��dϣ���㴫�͵�������:",
                () -> instance.delayTeleport(p, sp.getLocation()));
        sender.sendMessage("��a�ɹ�����ҷ��ʹ�������!");
        return true;
    }
}
