package sample;

/**
 * Created by Champ on 9/24/2015.
 */

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Wait extends Service {

public Task<Void> createTask() {
    return new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            Genpaper.p = new Stage();
            Genpaper.p.setHeight(100);
            Genpaper.p.setWidth(300);
            System.out.println(getClass().getResource("Waiting.fxml"));
            try {
                Pane sc = FXMLLoader.load(getClass().getResource("Waiting.fxml"));
                Scene scene=new Scene(sc, 300, 100);
                Genpaper.p.setX(Main.window.getX()+Main.window.getWidth()/2-Genpaper.p.getWidth()/2);
                Genpaper.p.setY(Main.window.getY() + Main.window.getHeight() / 2 - Genpaper.p.getHeight() / 2);
                Genpaper.p.setScene(scene);

                Genpaper.p.initModality(Modality.APPLICATION_MODAL);
                Genpaper.p.initStyle(StageStyle.UNDECORATED);

                Genpaper.p.show();
            } catch (Exception e) {
                System.out.println(e);
            }
            return null;
        }
    };
}
}
