package quevedo.ClientBasket.ui;

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
    public void start(Stage primaryStage) throws Exception {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.setResizable(true);
        container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {
        });
    }


}
