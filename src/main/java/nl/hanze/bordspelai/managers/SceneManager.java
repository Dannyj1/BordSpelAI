package nl.hanze.bordspelai.managers;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.hanze.bordspelai.models.LobbyModel;
import nl.hanze.bordspelai.views.View;

import java.io.IOException;

public class SceneManager {

  private static LobbyModel lobbyModel;
  private static Stage stage;

  public static void setStage(Stage newStage) {
    stage = newStage;
    stage.setHeight(400);
    stage.setWidth(600);
  }

  public static void switchScene(View view) {
    switchScene(view, "");
  }

  public static void switchScene(View view , String title) {
    Platform.runLater(() -> {
      try {
        Parent root = view.getLoader().load();
        Scene scene = new Scene(root);

        if (stage.getScene() != null) {
          Stage stage2 = (Stage) stage.getScene().getWindow();

          if (stage2 != null) {
            stage2.close();
          }
        }

        // stage.setMaximized(true);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  public static LobbyModel getLobbyModel() {
    if (lobbyModel == null) {
      lobbyModel = new LobbyModel();
    }

    return lobbyModel;
  }
}
