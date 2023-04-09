package p.lodz.kodowanie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloController {

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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        encodedMessage = coding.readMessage(file.getAbsolutePath());
        encodedText.setText(new String(encodedMessage, StandardCharsets.UTF_8));
    }

    @FXML
    protected void saveEncoded() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        coding.saveFileAsBytes(encodedMessage, file.getAbsolutePath());
    }

    // znajdz metode zeby zapisywac do nowo tworzonego pliku(nie ze musi juz istniec)
    @FXML
    protected void loadDecoded() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        decodedMessage = coding.readMessage(file.getAbsolutePath());
        decodedText.setText(new String(decodedMessage, StandardCharsets.UTF_8));
    }

    public void saveDecoded() {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        coding.saveFileAsBytes(decodedMessage, file.getAbsolutePath());
    }
}