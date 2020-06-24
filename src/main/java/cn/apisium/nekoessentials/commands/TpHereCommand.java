package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Constants;
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
        if (!(sender instanceof Player) || args.length != 1) return false;
        final Player sp = (Player) sender;
        final Player p = instance.getPlayer(sender, args[0]);
        if (p == null) return true;
        instance.requestTeleport(p, "  ��d��� ��f" + sp.getName() + " ��dϣ���㴫�͵����Ǳ�:",
                () -> instance.delayTeleport(p, sp.getLocation()));
        p.sendMessage("��a�ɹ�����ҷ��ʹ�������!");
        return true;
    }
}