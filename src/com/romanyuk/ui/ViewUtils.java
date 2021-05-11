package com.romanyuk.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс для декомпозиции некоторых шаблонных методов для работы с UI.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class ViewUtils {
    /**
     * Построения и показа диалогового окна.
     *
     * @param type    - тип окна.
     * @param title   - название.
     * @param header  - заголовок.
     * @param content - контент.
     */
    public static void buildAlertAndShow(final Alert.AlertType type, final String title, final String header, final String content) {
        Alert alert = new Alert(type);
        alert.initOwner(MainApplication.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Метод для инициализации модального окна.
     *
     * @param loader - загрузчик FXML.
     * @param title  - заголовок окна
     * @return Stage модального окна или null в случае возникновения ошибки.
     */
    public static Stage initModalWindow(final FXMLLoader loader, final String title) {
        try {
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setResizable(false);
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApplication.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            return dialogStage;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }
}
