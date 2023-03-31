package p.lodz.kodowanie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KodowanieTest {

    @Test
    void encode() {
        Kodowanie kodowanie = new Kodowanie();

        byte[] message = kodowanie.readMessage("textFiles\\test.txt");
        byte[] encoded =  kodowanie.encode(message);
        kodowanie.saveFileAsBytes(encoded, "textFiles\\zakodowany.txt");
        kodowanie.saveFileAsBytesString(message, "textFiles\\message-binary.txt");
        kodowanie.saveFileAsBytesString(encoded, "textFiles\\zakodowany-binary.txt");



    }


    @Test
    void decode(){

        Kodowanie kodowanie = new Kodowanie();
        byte[] encoded = kodowanie.readMessage("textFiles\\zakodowany.txt");
        byte[] decoded =  kodowanie.decode(encoded);
        kodowanie.saveFileAsBytes(decoded, "textFiles\\odkodowany.txt");
        kodowanie.saveFileAsBytesString(decoded, "textFiles\\odkodowany-binary.txt");
        assertTrue(true);
    }
}