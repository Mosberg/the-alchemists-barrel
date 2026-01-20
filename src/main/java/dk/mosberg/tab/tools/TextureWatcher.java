package dk.mosberg.tab.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public final class TextureWatcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path itemDir = Paths.get("src/main/resources/assets/tab/textures/item");
        Path blockDir = Paths.get("src/main/resources/assets/tab/textures/block");

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            if (Files.isDirectory(itemDir)) {
                itemDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            }

            if (Files.isDirectory(blockDir)) {
                blockDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            }

            System.out.println("Watching texture dirs. Ctrl+C to exit.");

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out
                            .println("Change detected: " + event.kind() + " -> " + event.context());
                    // Example hook:
                    // new ProcessBuilder("./gradlew", "runDatagen").inheritIO().start();
                }
                if (!key.reset())
                    break;
            }
        }
    }
}
