package p.lodz.kodowanie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class HelloController {

    Kodowanie coding = new Kodowanie();
    public TextArea encodedText;
    public TextArea decodedText;
    FileChooser fileChooser = new FileChooser();

    byte[] encodedMessage;
    byte[] decodedMessage;

    public void decode(ActionEvent actionEvent) throws IOException {
        byte[] message = coding.decode(encodedMessage);
        decodedMessage = message;
        coding.saveFileAsBytes(message, "decoded.txt");
        String decodedMessage = new String(Files.readAllBytes(Paths.get("decoded.txt")));
        decodedText.setText(decodedMessage);
    }

    public void encode(ActionEvent actionEvent) throws IOException {
        byte[] message = coding.encode(decodedMessage);
        encodedMessage = message;
        coding.saveFileAsBytes(message, "encoded.txt");
        String encodedMessage = new String(Files.readAllBytes(Paths.get("encoded.txt")));
        encodedText.setText(encodedMessage);
    }

    public void loadEncoded(ActionEvent actionEvent) throws IOException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        String content = Files.readString(Path.of(file.getAbsolutePath()), StandardCharsets.UTF_8);
        encodedMessage = content.getBytes();
        encodedText.setText(content);
    }

    @FXML
    protected void saveEncoded() throws IOException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
        writer.write(encodedText.getText());
        writer.close();
    }

    // znajdz metode zeby zapisywac do nowo tworzonego pliku(nie ze musi juz istniec)
    @FXML
    protected void loadDecoded() throws IOException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        String content = Files.readString(Path.of(file.getAbsolutePath()), StandardCharsets.UTF_8);
        decodedMessage = content.getBytes();
        decodedText.setText(content);
    }

    public void saveDecoded(ActionEvent actionEvent) throws IOException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
        writer.write(decodedText.getText());
        writer.close();
    }
}