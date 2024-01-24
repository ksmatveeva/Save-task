import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress game1 = new GameProgress(100, 3, 5, 150.75);
        GameProgress game2 = new GameProgress(90, 2, 3, 75.25);
        GameProgress game3 = new GameProgress(80, 1, 1, 10.5);


        saveGame("/Users/admin/Games/GunRunner/savegames/save1.dat", game1);
        saveGame("/Users/admin/Games/GunRunner/savegames/save2.dat", game2);
        saveGame("/Users/admin/Games/GunRunner/savegames/save3.dat", game3);


        zipFiles("/Users/admin/Games/GunRunner/savegames/zip.zip", List.of(
                "/Users/admin/Games/GunRunner/savegames/save1.dat",
                "/Users/admin/Games/GunRunner/savegames/save2.dat",
                "/Users/admin/Games/GunRunner/savegames/save3.dat"
        ));

        deleteNonZippedFiles(List.of(
                "/Users/admin/Games/GunRunner/savegames/save1.dat",
                "/Users/admin/Games/GunRunner/savegames/save2.dat",
                "/Users/admin/Games/GunRunner/savegames/save3.dat"
        ));
    }


    private static void saveGame(String filePath, GameProgress game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(game);
            System.out.println("Игровой прогресс сохранен в файл: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void zipFiles(String zipFilePath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(new File(file).getName());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Файлы успешно запакованы в архив: " + zipFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void deleteNonZippedFiles(List<String> files) {
        for (String file : files) {
            File currentFile = new File(file);
            if (currentFile.exists()) {
                boolean deleted = currentFile.delete();
                System.out.println("Файл удален: " + file + " - " + (deleted ? "Успешно" : "Неудача"));
            }
        }
    }
}