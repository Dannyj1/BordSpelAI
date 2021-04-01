package nl.hanze.bordspelai.listeners;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import nl.hanze.bordspelai.BordspelAI;
import nl.hanze.bordspelai.controllers.GameController;
import nl.hanze.bordspelai.events.NetEventListener;
import nl.hanze.bordspelai.managers.SceneManager;
import nl.hanze.bordspelai.models.TicTacToeModel;
import nl.hanze.bordspelai.notifications.Notification;

import java.util.Map;

public class MatchStartListener implements NetEventListener {

    @Override
    public void update(Notification notification) {
        if (notification.getNotificationType().equals("MATCH")) {
            Map<String, String> dataMap = notification.getDataMap();
            String gameType = dataMap.get("GAMETYPE");

            if (gameType.equalsIgnoreCase("Tic-tac-toe")) {
                BordspelAI.getThreadPool().submit(() -> Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    String opponent = dataMap.get("OPPONENT");

                    alert.setTitle("Match started");
                    alert.setHeaderText(null);
                    alert.setContentText("A match of " + gameType + " has started. Playing against: " + opponent);
                    alert.show();

                    SceneManager.switchScene("/views/game.fxml", new GameController(new TicTacToeModel("test", "test")));
                }));
            }
        }
    }
}