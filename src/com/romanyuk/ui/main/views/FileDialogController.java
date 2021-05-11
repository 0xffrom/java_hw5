package com.romanyuk.ui.main.views;

import com.romanyuk.ui.MainApplication;
import com.romanyuk.ui.ViewUtils;
import com.romanyuk.ui.listeners.OnFileAction;
import com.romanyuk.ui.utils.FileOption;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Контроллер для диалога с выбором файла.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class FileDialogController {
    /**
     * Расширение файла для экспорта/импорта.
     */
    private static final String EXTENSION = ".pbook";
    /**
     * Подсказка: название расширения для отображения в окне импорта.
     */
    private static final String EXTENSION_NAME = "Phone book";
    /**
     * Единственный FileChooser для экспорта/импорта c единственно допустимым форматом {@code EXTENSION}
     */
    private final FileChooser fileChooser;
    /**
     * Кнопка для выбора директории
     */
    @FXML
    private Button directoryButton;
    /**
     * Текстовое поле для директории.
     */
    @FXML
    private TextField textField;
    /**
     * Текущий Stage
     */
    private Stage dialogStage;
    /**
     * Коллбек для экспорта/импорта.
     */
    private OnFileAction onFileAction;
    /**
     * Опция импорта/экспорта.
     */
    private FileOption fileOption;

    public FileDialogController() {
        fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                String.format("%s (*%s)", EXTENSION_NAME, EXTENSION), String.format("*%s", EXTENSION));

        fileChooser.getExtensionFilters().add(extFilter);
    }

    /**
     * Статический метод для получения экземпляра контроллера
     *
     * @param fileOption   - опция экспорт/импорт.
     * @param onFileAction - слушатель.
     * @return экземпляр контроллера.
     */
    public static FileDialogController getController(FileOption fileOption, OnFileAction onFileAction) {
        String title = getTitleByOptions(fileOption);
        String buttonText = getButtonTextByOptions(fileOption);

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MainApplication.class.getResource(getResourceName()));
        var stage = ViewUtils.initModalWindow(loader, title);
        // Передаём адресата в контроллер.
        FileDialogController controller = loader.getController();
        controller.setDialogStage(stage);
        controller.setFileOption(fileOption);
        controller.directoryButton.setText(buttonText);

        controller.setOnFileAction(onFileAction);
        if (fileOption == FileOption.EXPORT) {
            controller.directoryButton.setOnAction(controller::onExport);
        } else {
            controller.directoryButton.setOnAction(controller::onImport);
        }

        return controller;
    }

    /**
     * Путь до файла разметки
     */
    private static String getResourceName() {
        return "resources/file_dialog.fxml";
    }

    /**
     * Получение заголовка относительно опции экспорта/импорта.
     */
    private static String getTitleByOptions(FileOption fileOption) {
        if (fileOption == FileOption.EXPORT)
            return "Экспорт";
        return "Импорт";
    }

    /**
     * Получение текста на кнопке относительно опции экспорта/импорта.
     */
    private static String getButtonTextByOptions(FileOption fileOption) {
        if (fileOption == FileOption.EXPORT)
            return "Выбрать директорию";
        return "Выбрать файл";
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    private void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Кнопка "Отмена"
     */
    @FXML
    private void onCancel() {
        dialogStage.close();
    }

    /**
     * Кнопка ОК. Забирает из текстового поля путь, и передаёт в коллбэк.
     * Если путь не прошёл валидацию - пользователю отображается диалоговое окно.
     */
    @FXML
    private void onOk() {
        String path = textField.getText();

        File file = new File(path);

        if (validatePath(file)) {
            if (fileOption == FileOption.EXPORT) {
                onFileAction.onFileExport(file);
            } else {
                onFileAction.onFileImport(file);
            }

            onCancel();
        } else {
            ViewUtils.buildAlertAndShow(Alert.AlertType.ERROR,
                    "Произошла ошибка",
                    "Хмм, похоже где-то произошла ошибка",
                    "Проверьте путь, либо обратитесь к специалисту, уважаемая Марья Васильевна из бухгалтерии.");
        }
    }

    /**
     * Открытие FileChooser при экспорте.
     */
    @FXML
    private void onExport(ActionEvent actionEvent) {
        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(MainApplication.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(EXTENSION)) {
                file = new File(file.getPath() + EXTENSION);
            }
            textField.setText(file.getPath());
        }
    }

    /**
     * Открытие FileChooser при импорте.
     */
    @FXML
    private void onImport(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(dialogStage.getScene().getWindow());

        if (file != null) {
            textField.setText(file.getPath());
        }
    }

    private void setFileOption(FileOption fileOption) {
        this.fileOption = fileOption;
    }

    private void setOnFileAction(OnFileAction onFileAction) {
        this.onFileAction = onFileAction;
    }

    /**
     * Валидация пути.
     *
     * @param file - путь до файла.
     * @return true - если валидация успешна, иначе - false.
     */
    private boolean validatePath(File file) {
        return file != null && file.getPath().endsWith(EXTENSION);
    }
}
