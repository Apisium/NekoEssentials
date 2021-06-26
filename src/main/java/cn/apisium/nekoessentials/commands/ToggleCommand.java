package cn.apisium.nekoessentials.commands;

import cn.apisium.nekoessentials.Main;
import cn.apisium.nekoessentials.utils.DatabaseSingleton;
import cn.apisium.nekoessentials.utils.Serializer;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("toggle")
public final class ToggleCommand extends BasicCommand {
    public ToggleCommand(Main main) {
        super(main);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean callback(CommandSender sender, String[] args) {
        if (!(sender instanceof final Player p)) return false;
        switch (p.getGameMode()) {
            case SURVIVAL -> {
                DatabaseSingleton.INSTANCE.setPlayerData(p, "toggleLocation", Serializer.serializeLocation(p.getLocation()));
                p.setGameMode(GameMode.SPECTATOR);
            }
            case SPECTATOR -> {
                final byte[] bytes = DatabaseSingleton.INSTANCE.getPlayerData(p, "toggleLocation");
                if (bytes != null) {
                    DatabaseSingleton.INSTANCE.deletePlayerData(p, "toggleLocation");
                    p.teleport(Serializer.deserializeLocation(bytes));
                }
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
        p.sendActionBar("��aģʽ�л��ɹ�!");
        return true;
    }
}
