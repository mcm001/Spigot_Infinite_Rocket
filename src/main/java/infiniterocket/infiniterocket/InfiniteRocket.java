package infiniterocket.infiniterocket;

import infiniterocket.infiniterocket.command.RocketCommands;
import infiniterocket.infiniterocket.listener.RocketClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class InfiniteRocket extends JavaPlugin {

    @Override
    public void onEnable() {
        new RocketCommands(this);
        new RocketClickListener(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
