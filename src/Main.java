import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(1, 1, 1, 1);
        GameProgress gameProgress2 = new GameProgress(2, 2, 2, 2);
        GameProgress gameProgress3 = new GameProgress(3, 3, 3, 3);
        saveGame("C:\\Games\\savegames\\save1.dat", gameProgress1);
        saveGame("C:\\Games\\savegames\\save2.dat", gameProgress2);
        saveGame("C:\\Games\\savegames\\save3.dat", gameProgress3);
        List<String> listPath = Arrays.asList("C:\\Games\\savegames\\save1.dat", "C:\\Games\\savegames\\save2.dat", "C:\\Games\\savegames\\save3.dat");
        //List<String> listPath = Arrays.asList("save1.dat", "save2.dat", "save3.dat");
        zipFiles("C:\\Games\\savegames\\zip.zip", listPath);
        delFiles(listPath);
    }

    public static void saveGame(String path, GameProgress gameProgress) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void zipFiles(String path, List<String> list) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String arr : list) {
                try (FileInputStream fis = new FileInputStream(arr)) {
                    ZipEntry entry = new ZipEntry(arr);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void delFiles(List<String> list){
        for (String arr:list){
            File currentFile = new File(arr);
            if (currentFile.delete()){
                System.out.println("Файл "+ arr + " успешно удален.");
            }
        }
    }
}