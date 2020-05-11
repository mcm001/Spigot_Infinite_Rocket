package infiniterocket.infiniterocket;

import infiniterocket.infiniterocket.command.CommandSpawn;
import infiniterocket.infiniterocket.listener.OnClick;
import org.bukkit.plugin.java.JavaPlugin;

public final class InfiniteRocket extends JavaPlugin {
    private static InfiniteRocket instance;

    @Override
    public void onEnable() {
        this.instance = this;
        new CommandSpawn(this);
        new OnClick(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
