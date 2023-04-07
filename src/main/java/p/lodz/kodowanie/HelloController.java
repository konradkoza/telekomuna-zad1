package p.lodz.kodowanie;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.nio.file.Files;

public class HelloController {
    public TextArea encodedText;

    @FXML
    protected void loadEncoded() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        encodedText.setText("Wczytalem.");
    }
}