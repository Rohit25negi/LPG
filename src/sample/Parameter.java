package sample;

/**
 * Created by Champ on 9/10/2015.
 */

import insidefx.undecorator.UndecoratorScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Parameter implements Initializable{
    @FXML
    public Button insert;
    public ComboBox<String>type,difficulty;
    static String examtype,examtopic,examQues;
    static Stage vision;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.getItems().addAll("short","medium","long");
        type.setValue("short");
        difficulty.getItems().addAll("1", "2", "3");
        difficulty.setValue("1");

    }
    public void create(String type,String topic,String Question)throws Exception
    {   examtype=type;
        examtopic=topic;
        examQues=Question;
        Stage st=new Stage();
        Pane s= FXMLLoader.load(getClass().getResource("Parameter.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,s);

        st.setTitle("Set Parameters");
        st.setWidth(500);
        st.setHeight(300);
        st.setX(Main.window.getX() + Main.window.getWidth() / 2 - st.getWidth() / 2);
        st.setY(Main.window.getY() + Main.window.getHeight() / 2 - st.getHeight() / 2);

        st.setScene(undecoratorScene);
        st.initModality(Modality.APPLICATION_MODAL);

        vision=st;

        st.showAndWait();

    }
    public void addThisFinally()throws  Exception
    {   System.out.println(examtype+":"+examtopic);
        boolean ornot=Question.insert(new File("ExamList/"+examtype+"/"+examtopic),difficulty.getSelectionModel().getSelectedItem(),type.getSelectionModel().getSelectedItem(),examQues);
        if(ornot)new Message().create("Question has been added",Main.window);
        else new Message().create("Question has not been added",Main.window);

        vision.close();
        Availability.st.close();
    }

}
