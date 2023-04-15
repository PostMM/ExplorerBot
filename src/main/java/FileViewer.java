import java.io.File;
import java.util.Objects;
import java.util.Set;
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
        this.path = path;
        resetDir();
    }

    public void gotoDir(String path) {
        this.path = this.path + "/" + path;
        resetDir();
    }

    public void parentDir() {
        if (dir.getParent() != null) {
            path = dir.getParent();
        }
        resetDir();
    }

    private void resetDir() {
        dir = new File(this.path);
    }

}
