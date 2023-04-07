package p.lodz.kodowanie;

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
    public TextArea encodedText;
    public TextArea decodedText;
    FileChooser fileChooser = new FileChooser();

    @FXML
    protected void loadEncoded() throws IOException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        String content = Files.readString(Path.of(file.getAbsolutePath()), StandardCharsets.UTF_8);
        encodedText.setText(content);
    }

    @FXML
    protected void saveDecoded() throws IOException {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        File file = fileChooser.showOpenDialog(null);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
        writer.write(decodedText.getText());
        writer.close();
    }
}