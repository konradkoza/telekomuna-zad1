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

public class HelloController {

    Kodowanie coding = new Kodowanie();
    public TextArea encodedText;
    public TextArea decodedText;
    FileChooser fileChooser = new FileChooser();

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
        decodedText.setText(content);
    }

    public void decode(ActionEvent actionEvent) throws IOException {
        byte[] message = coding.readMessage("encoded.txt");
        message = coding.decode(message);
        coding.saveFileAsBytes(message, "decoded.txt");
        String decodedMessage = Files.readString(Path.of("decoded.txt"));
        decodedText.setText(decodedMessage);
    }

    public void encode(ActionEvent actionEvent) throws IOException {
        byte[] message = decodedText.getText().getBytes();
        message = coding.encode(message);
        coding.saveFileAsBytes(message, "encoded.txt");
        String encodedMessage = Files.readString(Path.of("encoded.txt"));
        encodedText.setText(encodedMessage);
    }
}