package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("tpaall")
public final class TpaAllCommand extends BasicCommand {
    public TpaAllCommand(Main main) {
        super(main);
    }

    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return false;
        final Player sp = (Player) sender;
        final String str = "  ��d��� ��f" + sp.getName() + " ��dϣ�����͵�������:";
        instance.getServer().getOnlinePlayers().forEach(it -> instance.requestTeleport(it, str,
                () -> instance.delayTeleport(sp, sp.getLocation())));
        sender.sendMessage("��a�ɹ�����ҷ��ʹ�������!");
        return true;
    }
}
