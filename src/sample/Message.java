package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Champ on 9/20/2015.
 */
public class Message implements Initializable {
    @FXML
    public Button ok;
    public Label message;
    static String ms;
    static Stage st;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

            message.setText(ms);
    }
    public void create(String a,Stage parent) throws Exception
    {
        ms=a;
        Pane p= FXMLLoader.load(getClass().getResource("Ok.fxml"));
        st=new Stage();
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,p);
        st.setWidth(400);
        st.setHeight(190);
        st.setX(parent.getX() + parent.getWidth() / 2 - st.getWidth() / 2);
        st.setY(parent.getY() + parent.getHeight() / 2 - st.getHeight() / 2);
        st.setScene(undecoratorScene);
        st.initModality(Modality.APPLICATION_MODAL);

        {Platform.runLater(new Runnable() {
            public void run() {
                if(Genpaper.p!=null)Genpaper.p.close();
                Genpaper.p=null;
            }
        });}
        st.showAndWait();
    }
    public void close()
    {
        st.close();
    }
}
