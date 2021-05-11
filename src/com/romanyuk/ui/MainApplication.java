package com.romanyuk.ui;

import com.romanyuk.data.repositories.PersonRepositoryImpl;
import com.romanyuk.ui.main.MainController;
import com.romanyuk.ui.main.MainPresenterImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Главный Application.
 *
 * @author <a href="mailto:asromanyuk@edu.hse.ru"> Andrey Romanyuk</a>
 */
public class MainApplication extends Application {
    /**
     * Заголовок.
     */
    private static final String TITLE = "Записная книжка";
    /**
     * Ширина при инициализации.
     */
    private static final Integer WIDTH = 900;
    /**
     * Высота при инициализации.
     */
    private static final Integer HEIGHT = 275;

    /**
     * Главный Stage.
     */
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Реализация сервис локатора.
     *
     * @param mainController - контроллер.
     */
    public static void inject(MainController mainController) {
        mainController.injectPresenter(new MainPresenterImpl(mainController, new PersonRepositoryImpl()));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainApplication.primaryStage = primaryStage;

        var root = MainController.getInstance();
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
}
