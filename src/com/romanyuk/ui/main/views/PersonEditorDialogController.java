package com.romanyuk.ui.main.views;

import com.romanyuk.domain.entity.Person;
import com.romanyuk.ui.MainApplication;
import com.romanyuk.ui.ViewUtils;
import com.romanyuk.ui.listeners.OnClickPersonListener;
import com.romanyuk.ui.main.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Контроллер для работы с полями пользователя.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class PersonEditorDialogController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField secondNameTextField;
    @FXML
    private TextField thirdNameTextField;
    @FXML
    private TextField mobilePhoneTextField;
    @FXML
    private TextField homePhoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private DatePicker birthDayDatePicker;

    /**
     * Текущий пользователь.
     */
    private Person person;
    /**
     * Слушатель для кнопки Ok по клику.
     */
    private OnClickPersonListener onClickPersonListener;
    /**
     * Текущий stage.
     */
    private Stage dialogStage;

    /**
     * Получить экземпляр текущего контроллера.
     *
     * @param title - заголовок окошка.
     * @return экземпляр контроллера.
     */
    public static PersonEditorDialogController getInstance(String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(getResourceName()));

        // Передаём адресата в контроллер.
        var stage = ViewUtils.initModalWindow(loader, title);
        PersonEditorDialogController controller = loader.getController();
        controller.setDialogStage(stage);

        return controller;
    }

    /**
     * Путь до файла разметки
     */
    private static String getResourceName() {
        return "resources/person_editor_dialog.fxml";
    }

    /**
     * Кнопка "Продолжить"
     */
    @FXML
    private void onContinue() {
        if (validateName() && validatePhone() && validateDate()) {
            onClickPersonListener.onClick(getPersonByInput());
            dialogStage.close();
        }
    }

    /**
     * Кнопка "Сбросить"
     */
    @FXML
    private void onReset() {
        if (person == null) {
            clearFields();
        } else {
            setCurrentPerson(person);
        }
    }

    /**
     * Кнопка "Отмена"
     */
    @FXML
    private void onCancel() {
        dialogStage.close();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    private void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setOnClickListener(OnClickPersonListener onClickPersonListener) {
        this.onClickPersonListener = onClickPersonListener;
    }

    /**
     * Логика валидации телефонных номеров.
     * Главное: Присутствие хотя бы одного телефонного номера.
     *
     * @return true - если валидация успешна, иначе - false.
     */
    private boolean validatePhone() {
        String mobilePhoneText = mobilePhoneTextField.getText();
        String homePhoneText = homePhoneTextField.getText();

        // Если оба отсутствуют:
        if (mobilePhoneText.isBlank() && homePhoneText.isBlank()) {
            showErrorDialog("Введите хотя бы один номер телефона: домашний или мобильный");
            setError(mobilePhoneTextField);
            return false;
        }

        // Если домашний пустой или домашний не пустой и мобильный не пуст.
        if (homePhoneText.isBlank() || (!homePhoneText.isBlank() && !mobilePhoneText.isBlank())) {
            if (Validator.isNotValidPhone(mobilePhoneText)) {
                showErrorDialog("Введите настоящий мобильный телефон в формате: +79887026010");
                setError(mobilePhoneTextField);
                return false;
            }
        }

        // Если мобильный пустой или домашний и мобильный не пусты.
        if (mobilePhoneText.isBlank() || (!homePhoneText.isBlank() && !mobilePhoneText.isBlank())) {
            if (Validator.isNotValidPhone(homePhoneText)) {
                showErrorDialog("Введите настоящий домашний телефон в формате: +79887026010");
                setError(homePhoneTextField);
                return false;
            }
        }

        clearError(homePhoneTextField);
        clearError(mobilePhoneTextField);
        return true;
    }

    /**
     * Валидация DatePicker'a
     *
     * @return true - если валидация успешна, иначе - false.
     */
    private boolean validateDate() {
        LocalDate date = birthDayDatePicker.getValue();
        if (Validator.isNotValidDate(date)) {
            setError(birthDayDatePicker);
            showErrorDialog("Введите настоящую дату рождения!");
            return false;

        }

        clearError(birthDayDatePicker);
        return true;
    }

    /**
     * Валидация текстовых полей в имени.
     *
     * @return true - если валидация успешна, иначе - false.
     */
    private boolean validateName() {
        String firstName = firstNameTextField.getText();
        String secondName = secondNameTextField.getText();

        if (Validator.isNotValidName(firstName)) {
            setError(firstNameTextField);
            showErrorDialog("Введите настоящее имя!");
            return false;
        }
        clearError(firstNameTextField);

        if (Validator.isNotValidName(secondName)) {
            setError(secondNameTextField);
            showErrorDialog("Введите настоящую фамилию!");
            return false;
        }

        clearError(secondNameTextField);
        return true;
    }

    /**
     * @return экземпляр {@code Person} на основе текстовых полей.
     */
    private Person getPersonByInput() {
        return new Person().setFirstName(firstNameTextField.getText())
                .setSecondName(secondNameTextField.getText())
                .setThirdName(thirdNameTextField.getText())
                .setMobilePhone(mobilePhoneTextField.getText())
                .setHomePhone(homePhoneTextField.getText())
                .setAddress(addressTextField.getText())
                .setBirthDay(birthDayDatePicker.getValue())
                .setComment(commentTextArea.getText());
    }

    /**
     * Установка {@code Person} в текстовые поля.
     */
    public void setCurrentPerson(Person person) {
        this.person = person;
        firstNameTextField.setText(person.getFirstName());
        secondNameTextField.setText(person.getSecondName());
        thirdNameTextField.setText(person.getThirdName());
        mobilePhoneTextField.setText(person.getMobilePhone());
        homePhoneTextField.setText(person.getHomePhone());
        addressTextField.setText(person.getAddress());
        commentTextArea.setText(person.getComment());
        birthDayDatePicker.setValue(person.getBirthDay());
    }

    /**
     * Очищение текстовых полей.
     */
    private void clearFields() {
        firstNameTextField.clear();
        secondNameTextField.clear();
        thirdNameTextField.clear();
        mobilePhoneTextField.clear();
        homePhoneTextField.clear();
        addressTextField.clear();
        commentTextArea.clear();
        birthDayDatePicker.getEditor().clear();
    }

    /**
     * Показать пользователю сообщение об ошибке.
     *
     * @param message - сообщение об ошибке.
     */
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(MainApplication.getPrimaryStage());
        alert.setTitle("Ошибка");
        alert.setHeaderText("Невозможно внести в записную книжку данного пользователя.");
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Установить красную обводку UI элементу.
     */
    private void setError(Control control) {
        control.setStyle("-fx-text-box-border: red; -fx-focus-color: red ; -fx-border-color: red");
    }

    /**
     * Очистить стиль у UI-элемента.
     */
    private void clearError(Control control) {
        control.setStyle("");
    }
}
