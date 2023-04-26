package p.lodz.kodowanie;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class Kodowanie {


    private final short[] matrixFull = {
            (short) 0xF080,  // 1 1 1 1 0 0 0 0 1 0 0 0 0 0 0 0
            (short) 0xCC40,  // 1 1 0 0 1 1 0 0 0 1 0 0 0 0 0 0
            (short) 0xAA20,  // 1 0 1 0 1 0 1 0 0 0 1 0 0 0 0 0
            (short) 0x5610,  // 0 1 0 1 0 1 1 0 0 0 0 1 0 0 0 0
            (short) 0xE908,  // 1 1 1 0 1 0 0 1 0 0 0 0 1 0 0 0
            (short) 0x9504,  // 1 0 0 1 0 1 0 1 0 0 0 0 0 1 0 0
            (short) 0x7B02,  // 0 1 1 1 1 0 1 1 0 0 0 0 0 0 1 0
            (short) 0xE701   // 1 1 1 0 0 1 1 1 0 0 0 0 0 0 0 1
    };


    private final byte[] matrixPart =  {
            (byte) 0xF0,   // 1 1 1 1 0 0 0 0
            (byte) 0xCC,   // 1 1 0 0 1 1 0 0
            (byte) 0xAA,   // 1 0 1 0 1 0 1 0
            (byte) 0x56,   // 0 1 0 1 0 1 1 0
            (byte) 0xE9,   // 1 1 1 0 1 0 0 1
            (byte) 0x95,   // 1 0 0 1 0 1 0 1
            (byte) 0x7B,   // 0 1 1 1 1 0 1 1
            (byte) 0xE7    // 1 1 1 0 0 1 1 1
    };

    private final byte[] matrixT = {
            (byte) 0xED,  // 1 1 1 0 1 1 0 1
            (byte) 0xDB,  // 1 1 0 1 1 0 1 1
            (byte) 0xAB,  // 1 0 1 0 1 0 1 1
            (byte) 0x96,  // 1 0 0 1 0 1 1 0
            (byte) 0x6A,  // 0 1 1 0 1 0 1 0
            (byte) 0x55,  // 0 1 0 1 0 1 0 1
            (byte) 0x33,  // 0 0 1 1 0 0 1 1
            (byte) 0xF,   // 1 1 1 1 0 0 0 0
            (byte) 0x80,  // 1 0 0 0 0 0 0 0
            (byte) 0x40,  // 0 1 0 0 0 0 0 0
            (byte) 0x20,  // 0 0 1 0 0 0 0 0
            (byte) 0x10,  // 0 0 0 1 0 0 0 0
            (byte) 0x08,  // 0 0 0 0 1 0 0 0
            (byte) 0x04,  // 0 0 0 0 0 1 0 0
            (byte) 0x02,  // 0 0 0 0 0 0 1 0
            (byte) 0x01   // 0 0 0 0 0 0 0 1
    };

    public byte[] readMessage(String fileName){
        byte[] result;

        try(FileInputStream fis = new FileInputStream(fileName)) {
            result = fis.readAllBytes();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        return result;
    }

    public void saveFileAsBytes(byte[] dane, String fileName)  {
        try(FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(dane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFileAsBytesString(byte[] dane, String fileName)  {
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

    private boolean parity(byte msg){
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            if(((msg >> i) & 1) == 1){
                sum += 1;
            }
        }
        return sum % 2 != 0;

    }

    private boolean parity(short msg){
        int sum = 0;
        for (int i = 0; i < 16; i++) {
            if((((msg & 0xFFFF) >> i) & 1) == 1){
                sum += 1;
            }
        }
        return sum % 2 != 0;

    }


    private byte addParity(byte msg){
        byte p = 0;
        byte parityByte = 0;
        for (int i = 0; i < 8; i++) {
            p =(byte) (msg & matrixPart[i]);
            if(parity(p)) {
                parityByte = (byte) (parityByte ^ (1 << (7 - i)));

            }
        }
        return parityByte;
    }

    private byte error(short msg){
        short p = 0;
        byte parityByte = 0;
        for (int i = 0; i < 8; i++) {
            p = (short) ( (msg & 0xFFFF) & (matrixFull[i] & 0xFFFF));
            if(parity(p)){
                parityByte = (byte) (parityByte ^ (1 << (7 - i)));

            }
        }
        return parityByte;
    }

    private byte verify(short msg) {
        byte err = error(msg);
        byte msgByte = (byte) ((msg & 0xFFFF) >> 8);
        if(err != 0){
            for (int i = 0; i < 8; i++) {
                if(err  == matrixT[i] ){
                    System.out.println("błąd");
                    msgByte = (byte) ((msgByte & 0xFF) ^ (0x1 << (7 - i)));
                    err = 0;
                }
            }
            if (err != 0){
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        byte mistakeCombination = (byte) ((matrixT[i] & 0xFF) ^ (matrixT[j] & 0xFF));
                        if(err == mistakeCombination) {
                            System.out.println("błąd");
                            msgByte = (byte) ((msgByte & 0xFF) ^ (0x1 << (7 - i)));
                            msgByte = (byte) ((msgByte & 0xFF) ^ (0x1 << (7 - j)));
                            return msgByte;
                        }
                    }
                }
            }
        }
        return msgByte;
    }

    public byte[] encode(byte[] msg){
        byte[] encoded = new byte[msg.length * 2];
        for (int i = 0; i < msg.length * 2; i++) {
            encoded[i] = msg[i/2];
            i++;
            encoded[i] = addParity(msg[i/2]);
        }
        return encoded;
    }

    public byte[] decode(byte[] msg){
        byte[] decoded = new byte[msg.length / 2];

        short[] message = new short[msg.length/2];
        for (int i = 0; i < msg.length; i++) {
            message[i/2] = (short) (((msg[i++] & 0xFF) << 8) | (msg[i] & 0xFF));
        }

        for (int i = 0; i < message.length; i++ ) {
            decoded[i] = verify(message[i]);
        }
        return decoded;
    }

}
