package p.lodz.kodowanie;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReadWrite {

    public static byte[] readMessage(String fileName){
        byte[] result;

        try(FileInputStream fis = new FileInputStream(fileName)) {
            result = fis.readAllBytes();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return result;
    }

    public static void saveFileAsBytes(byte[] dane, String fileName)  {
        try(FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(dane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveFileAsBytesString(byte[] dane, String fileName)  {
        StringBuilder builder = new StringBuilder();
        for (byte b : dane) {
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        try{
            Files.writeString(Paths.get(fileName), builder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
