package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("tpa")
public final class TpaCommand extends BasicCommand {
    public TpaCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player) || args.length != 1) return false;
        final Player sp = (Player) sender;
        final Player p = instance.getPlayer(sender, args[0]);
        if (p == null) return true;
        instance.requestTeleport(p, "  ��d��� ��f" + sp.getName() + " ��dϣ�����͵�������:",
                () -> instance.delayTeleport(sp, p.getLocation()));
        p.sendMessage("��a�ɹ�����ҷ��ʹ�������!");
        return true;
    }
}
