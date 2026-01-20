package dk.mosberg.tab.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class TextureWatcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path itemDir = Paths.get("src/main/resources/assets/tab/textures/item");
        Path blockDir = Paths.get("src/main/resources/assets/tab/textures/block");

        WatchService watchService = FileSystems.getDefault().newWatchService();

        itemDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        blockDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        System.out.println("Watching texture dirs. Ctrl+C to exit.");

        //
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Change detected: " + event.context());
                // You can shell out to Gradle here:
                // new ProcessBuilder("./gradlew", "generateMaterialsJson",
                // "runDatagen").inheritIO().start();
            }
            key.reset();
        }
    }
}
