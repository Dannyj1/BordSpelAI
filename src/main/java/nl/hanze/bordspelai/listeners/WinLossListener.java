package nl.hanze.bordspelai.listeners;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import nl.hanze.bordspelai.builder.AlertBuilder;
import nl.hanze.bordspelai.controllers.LobbyController;
import nl.hanze.bordspelai.enums.GameState;
import nl.hanze.bordspelai.events.NetEventListener;
import nl.hanze.bordspelai.events.NetEventManager;
import nl.hanze.bordspelai.games.Game;
import nl.hanze.bordspelai.games.Reversi;
import nl.hanze.bordspelai.managers.GameManager;
import nl.hanze.bordspelai.managers.SceneManager;
import nl.hanze.bordspelai.notifications.Notification;
import nl.hanze.bordspelai.views.LobbyView;

public class WinLossListener implements NetEventListener {

    @Override
    public void update(Notification notification) {
        Platform.runLater(() -> {
            GameManager manager = GameManager.getInstance();
            AlertBuilder builder = new AlertBuilder(Alert.AlertType.INFORMATION);

            switch (notification.getNotificationType()) {
                case "LOSS":
                    manager.setState(GameState.GAME_LOST);
                    builder.setTitle("You lost the game...")
                            .setHeader("You lost the game...")
                            .setContent(notification.getDataMap().get("COMMENT"))
                            .build().showAndWait();
                    break;
                case "DRAW":
                    manager.setState(GameState.GAME_TIE);
                    builder.setTitle("It's a draw.")
                            .setHeader("It's a draw.")
                            .setContent(notification.getDataMap().get("COMMENT"))
                            .build().showAndWait();
                    break;
                case "WIN":
                    manager.setState(GameState.GAME_WON);
                    builder.setTitle("You won the game!")
                            .setHeader("You won the game!")
                            .setContent(notification.getDataMap().get("COMMENT"))
                            .build().showAndWait();
                    break;
                default:
                    return;
            }

            Game game = GameManager.getInstance().getGameController().getGame();

            if (game instanceof Reversi) {
                Reversi reversi = (Reversi) game;
                reversi.reset();
            }

            NetEventManager.getInstance().unregister(GameManager.getInstance().getGameController());
            LobbyView view = new LobbyView("/views/lobby.fxml", new LobbyController(SceneManager.getLobbyModel()));
            SceneManager.switchScene(view, "Hotel Lobby");
        });
    }
}
