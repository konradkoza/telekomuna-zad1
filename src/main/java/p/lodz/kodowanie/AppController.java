package p.lodz.kodowanie;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class AppController {

    Kodowanie coding = new Kodowanie();
    public TextArea encodedText;
    public TextArea decodedText;
    FileChooser fileChooser = new FileChooser();

    byte[] encodedMessage;
    byte[] decodedMessage;

    public void decode() {
        decodedMessage =  coding.decode(encodedMessage);
        decodedText.setText(new String(decodedMessage, StandardCharsets.UTF_8));
    }

    public void encode() {
        encodedMessage = coding.encode(decodedMessage);
        encodedText.setText(new String(encodedMessage, StandardCharsets.UTF_8));
    }

    public void loadEncoded() {
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        encodedMessage = FileReadWrite.readMessage(file.getAbsolutePath());
        encodedText.setText(new String(encodedMessage, StandardCharsets.UTF_8));
    }

    @FXML
    protected void saveEncoded() {
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showSaveDialog(null);
        FileReadWrite.saveFileAsBytes(encodedMessage, file.getAbsolutePath());
    }

    // znajdz metode zeby zapisywac do nowo tworzonego pliku(nie ze musi juz istniec)
    @FXML
    protected void loadDecoded() {
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        decodedMessage = FileReadWrite.readMessage(file.getAbsolutePath());
        decodedText.setText(new String(decodedMessage, StandardCharsets.UTF_8));
    }

    public void saveDecoded() {
        fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showSaveDialog(null);
        FileReadWrite.saveFileAsBytes(decodedMessage, file.getAbsolutePath());
    }
}