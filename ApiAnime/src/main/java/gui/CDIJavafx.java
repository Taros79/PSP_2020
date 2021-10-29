package gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;

public class CDIJavafx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        primaryStage.setWidth(640);
        primaryStage.setHeight(500);
        primaryStage.setResizable(true);
        container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {
        });
    }

}
