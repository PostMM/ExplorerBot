import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileViewer {

    private File dir;
    private String path;
    public FileViewer(String path) {

        this.path = path;
        dir = new File(path);
    }

    public Set<String> getFiles() {
        return Stream.of(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

    }

    public Set<String> getDirs() {
        return Stream.of(Objects.requireNonNull(dir.listFiles()))
                .filter(File::isDirectory)
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public void setDir(String path) {
        if (Files.isDirectory(Path.of(path))) {
            this.path = path;
        }
        resetDir();
    }

    public void gotoDir(String path) {

        String newPath = this.path + "\\" + path;
        if (Files.isDirectory(Path.of(newPath))) {
            this.path = newPath;
        }
        resetDir();
    }

    public void parentDir() {
        if (dir.getParent() != null) {
            path = dir.getParent();
        }
        resetDir();
    }

    public void clearMessages(PrivateChannel channel) {
        try {
            getSelfMessages(channel).get().forEach(m -> m.delete().queue());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    public File get(String filePath) {
        String newPath = this.path + "\\" + filePath;
        if (Files.isRegularFile(Path.of(newPath))) {
            return new File(newPath);
        }
        return null;
    }

    public String getPath() {
        return path;
    }
    private void resetDir() {
        dir = new File(this.path);
    }
    private static CompletableFuture<List<Message>> getSelfMessages(PrivateChannel channel) {
        return channel.getIterableHistory()
                .takeAsync(1000)
                .thenApply(list ->
                        list.stream()
                                .filter(m -> (m.getAuthor().isBot()))
                                .collect(Collectors.toList())
                );
    }

}
