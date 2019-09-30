package br.edu.ifpb.gugawag.so.sockets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parse {
    private String HOME;

    public Parse(String HOME) {
        this.HOME = HOME;
    }

    public String readdir (String dir) throws IOException {
        Path p = Paths.get(HOME + dir);
        return Files.list(p)
                .map(Path::getFileName)
                .map(Objects::toString)
                .collect(Collectors.joining(", "));
    }
    public String createFile (String name) throws IOException {
        Path p = Paths.get(HOME + "/" + name);
        Files.createFile(p);
        return "";
    }
    public String createDir (String name) throws IOException {
        Path p = Paths.get(HOME + "/" + name);
        Files.createDirectory(p);
        return "";
    }
    public String deleteFile (String name) throws IOException {
        Path p = Paths.get(HOME + "/" + name);
        if (!Files.isDirectory(p)) Files.deleteIfExists(p);
        return "";
    }
    public String deleteDir (String name) {
        try {
            Path p = Paths.get(HOME + "/" + name);
            if (Files.isDirectory(p)) Files.delete(p);
            return "";
        }catch (Exception e){
            return "diretório não está vazio";
        }
    }
    public String renameFile (String oldFile, String newFile) throws IOException {
        Path oldP = Paths.get(HOME + "/" + oldFile);
        Path newP = Paths.get(HOME + "/" + newFile);

        Files.move(oldP,newP);
        return "";
    }


}
