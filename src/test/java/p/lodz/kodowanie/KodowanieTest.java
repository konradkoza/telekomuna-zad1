package p.lodz.kodowanie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KodowanieTest {

    @Test
    void encode() {
        Kodowanie kodowanie = new Kodowanie();

        byte[] message = FileReadWrite.readMessage("textFiles\\test.txt");
        byte[] encoded =  kodowanie.encode(message);
        FileReadWrite.saveFileAsBytes(encoded, "textFiles\\zakodowany.txt");
        FileReadWrite.saveFileAsBytesString(message, "textFiles\\message-binary.txt");
        FileReadWrite.saveFileAsBytesString(encoded, "textFiles\\zakodowany-binary.txt");



    }


    @Test
    void decode(){

        Kodowanie kodowanie = new Kodowanie();
        byte[] encoded = FileReadWrite.readMessage("textFiles\\zakodowany.txt");
        byte[] decoded =  kodowanie.decode(encoded);
        FileReadWrite.saveFileAsBytes(decoded, "textFiles\\odkodowany.txt");
        FileReadWrite.saveFileAsBytesString(decoded, "textFiles\\odkodowany-binary.txt");
        assertTrue(true);
    }
}