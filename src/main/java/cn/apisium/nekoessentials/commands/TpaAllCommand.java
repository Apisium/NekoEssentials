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
        if (!(sender instanceof final Player sp)) return false;
        final String str = "  §d玩家 §f" + sp.getName() + " §d希望你传送到他那里:";
        instance.getServer().getOnlinePlayers().forEach(it -> {
            if (it == sp) return;
            instance.requestTeleport(it, str, () -> instance.delayTeleport(it, sp.getLocation()));
        });
        sender.sendMessage("§a成功向玩家发送传送请求!");
        return true;
    }
}
