import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameSaver {

    public static void main(String[] args) {
        // Создаем три экземпляра класса GameProgress
        GameProgress progress1 = new GameProgress(100, 3, 5, 123.45);
        GameProgress progress2 = new GameProgress(80, 2, 3, 78.99);
        GameProgress progress3 = new GameProgress(90, 4, 7, 200.0);

        // Сохраняем экземпляры GameProgress
        saveGame("/Users/kseniamatveeva/Games/savegames/save1.dat", progress1);
        saveGame("/Users/kseniamatveeva/Games/savegames/save2.dat", progress2);
        saveGame("/Users/kseniamatveeva/Games/savegames/save3.dat", progress3);

        // Создаем архив
        zipFiles("/Users/kseniamatveeva/Games/savegames/zip.zip",
                "/Users/kseniamatveeva/Games/savegames/save1.dat",
                "/Users/kseniamatveeva/Games/savegames/save2.dat",
                "/Users/kseniamatveeva/Games/savegames/save3.dat");

        // Удаляем файлы сохранений
        deleteUnzippedFiles("/Users/kseniamatveeva/Games/savegames/save1.dat",
                "/Users/kseniamatveeva/Games/savegames/save2.dat",
                "/Users/kseniamatveeva/Games/savegames/save3.dat");
    }

    private static void saveGame(String filePath, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(gameProgress);
            System.out.println("GameProgress сохранен в файл: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(String zipFilePath, String... filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String file : filesToZip) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry = new ZipEntry(new File(file).getName());
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
                fis.close();
                zos.closeEntry();
                System.out.println("Файл добавлен в архив: " + file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUnzippedFiles(String... filesToDelete) {
        for (String file : filesToDelete) {
            File f = new File(file);
            if (f.delete()) {
                System.out.println("Файл удален: " + file);
            } else {
                System.out.println("Не удалось удалить файл: " + file);
            }
        }
    }
}
