package com.romanyuk.ui.main;

import com.romanyuk.domain.entity.Person;
import com.romanyuk.ui.MainApplication;
import com.romanyuk.ui.ViewUtils;
import com.romanyuk.ui.listeners.OnFileAction;
import com.romanyuk.ui.main.views.FileDialogController;
import com.romanyuk.ui.main.views.PersonEditorDialogController;
import com.romanyuk.ui.utils.FileOption;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Главный контроллер странички с таблицей.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class MainController implements OnFileAction, MainView {
    private MainPresenter mainPresenter;
    /**
     * Текущий список контактов.
     */
    @FXML
    private ObservableList<Person> people = FXCollections.observableArrayList();
    /**
     * Тестовое поле для поиска.
     */
    @FXML
    private TextField searchField;
    /**
     * Табличка.
     */
    @FXML
    private TableView<Person> tableView;


    /**
     * Путь до файла разметки
     */
    private static String getResourceName() {
        return "resources/main_page.fxml";
    }

    public static Parent getInstance() throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(getResourceName())));
    }

    @FXML
    public void initialize() {
        MainApplication.inject(this);
        mainPresenter.loadPersons();
    }

    @Override
    public void setPeople(final List<Person> people) {
        // Читаем сохранённый файлик с контактами и загружаем их.
        this.people = FXCollections.observableArrayList(people);
        // Назначаем табличке контакты
        tableView.setItems(this.people);
    }

    @Override
    public void addPeople(final Person person) {
        this.people.add(person);
    }

    @Override
    public void successImportPeople(final List<Person> people) {
        this.people.addAll(people);
    }

    @Override
    public void failedOperation(final String message) {
        ViewUtils.buildAlertAndShow(Alert.AlertType.ERROR,
                "Ошибка",
                message,
                "Повторите попытку снова или обратитесь к техническому специалисту.");
    }

    @Override
    public void showFailedPeopleAfterImport(final List<Person> people) {
        showCollisionElements(people);
    }

    /**
     * Экспорт файла в локальное хранилище.
     *
     * @param file - файл, куда нужно экспортировать.
     */
    @Override
    public void onFileExport(final File file) {
        mainPresenter.exportFile(file, people);
    }

    /**
     * Импорта файла.
     * <p>
     * Если у двух {@code Person} совпадает ФИО, то такой Person не берётся.
     *
     * @param file - файл, от куда нужно импортировать.
     * @see Person equals()
     */
    @Override
    public void onFileImport(final File file) {
        mainPresenter.importFile(file, people);
    }

    public void injectPresenter(final MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    /**
     * Открытие {@code PersonEditDialogController}, а также назначения слушателя при добавлении пользователя.
     * В случае, если пользователь с таким ФИО уже существует, то добавление не происходит, а происходит уведомления
     * пользователя об ошибке.
     */
    @FXML
    private void onAddPerson() {
        var controller = PersonEditorDialogController.getInstance("Добавить пользователя");

        Objects.requireNonNull(controller).setOnClickListener((person -> mainPresenter.addPerson(person, people)));

        controller.getDialogStage().showAndWait();
    }

    /**
     * Удаление выделенного в таблице пользователя.
     * Если пользователь никого не выбрал - показывается уведомление об ошибке.
     */
    @FXML
    private void onRemovePerson() {
        // Выбранный индекс элемента:
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            // Убираем его из главного списка people
            // Это сделано, так как в tabView могут отображаться фильтрованный элементы
            // и индекс элемента в people может не совпадать с индексом того же элемента в таблице.
            var person = tableView.getItems().get(selectedIndex);
            people.remove(person);
            mainPresenter.removePerson(person);
        } else {
            failedOperation("Учётная запись не выбрана");
        }
    }

    /**
     * Редактирование пользователя.
     * <p>
     * В случае если пользователь отредактировал имя так, что оно совпало уже с существуем - ничего не произойдёт.
     * Данная ошибка не прописана в ТЗ.
     */
    @FXML
    private void onEditPerson() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            var controller = PersonEditorDialogController.getInstance("Редактировать пользователя");

            controller.setOnClickListener((person -> {
                people.set(people.indexOf(tableView.getItems().get(selectedIndex)), person);
                mainPresenter.updatePerson(person);
            }));

            controller.setCurrentPerson(tableView.getItems().get(selectedIndex));
            controller.getDialogStage().showAndWait();
        } else {
            failedOperation("Учётная запись не выбрана");
        }
    }

    /**
     * Фильтрация контактов.
     */
    @FXML
    private void onFilter() {
        String newValue = searchField.getText();

        tableView.setItems(people.filtered((person -> person.getFirstName().startsWith(newValue) ||
                person.getSecondName().startsWith(newValue) ||
                person.getThirdName().startsWith(newValue))));
    }

    /**
     * Открытие окна с информацией.
     */
    @FXML
    private void onOpenInfo() {
        ViewUtils.buildAlertAndShow(Alert.AlertType.CONFIRMATION,
                "О приложении",
                "О приложении",
                "ФИО: Романюк Андрей\nГруппа: БПИ-194\nПослание потомкам: Фёдор Дерябин мой куратор!");
    }

    /**
     * Закрытие окна.
     */
    @FXML
    private void onExit() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    /**
     * Экспорт контактов.
     */
    @FXML
    private void onExport() {
        openFileDialog(FileOption.EXPORT);
    }

    /**
     * Импорт контактов.
     */
    @FXML
    private void onImport() {
        openFileDialog(FileOption.IMPORT);
    }

    /**
     * Уведомляет пользователя об ошибочных контактах, если они существуют.
     *
     * @param errorPersons - ошибочные контакты.
     */
    private void showCollisionElements(List<Person> errorPersons) {
        if (!errorPersons.isEmpty()) {
            var secondNames = errorPersons
                    .stream()
                    .map(Person::getSecondName)
                    .collect(Collectors.joining(", "));

            ViewUtils.buildAlertAndShow(Alert.AlertType.INFORMATION,
                    "Неувязочка",
                    "Некоторые записи не были добавлены, так уже содержатся в телефонной книге!",
                    "Фамилии тех, кто не был добавлен: " + secondNames);
        }
    }

    /**
     * Открытие диалогового окна для экспорта/импорта.
     *
     * @param fileOption - экспорт или импорт.
     */
    private void openFileDialog(final FileOption fileOption) {
        Objects.requireNonNull(FileDialogController
                .getController(fileOption, this))
                .getDialogStage()
                .showAndWait();
    }
}
