package pl.olointeria.wyrok.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.olointeria.wyrok.database.dbutils.DbManager;
import pl.olointeria.wyrok.utils.FxmlUtils;

public class Main extends Application {
    private static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       // Locale.setDefault(new Locale("en"));
        // FXMLLoader loader =new FXMLLoader(this.getClass().getResource(BORDER_PANE_MAIN_FXML));
        // ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
        // loader.setResources(bundle);
        Pane borderPane =  FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
        // BorderPane borderPane=loader.load();
        //assert borderPane != null;
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("tittle.application"));
       // primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("tittle.application"));
        primaryStage.show();
        DbManager.initDatabase();
        FillDataBase.fillDatabase();
    }
}
