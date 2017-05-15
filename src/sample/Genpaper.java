package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Created by Champ on 9/6/2015.
 */

public class Genpaper implements Initializable{
   @FXML
    public ComboBox<String>ComboBox1;
    public Button delete,createPaper,addPattern;
    public Slider distinction,difficulty;
    @FXML
    public Button about;

  public  void backToMain()throws Exception
    {
        Pane root= FXMLLoader.load(getClass().getResource("../UI/MainPage.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(Main.window,root);
        undecoratorScene.addStylesheet("demoapp/demoapp.css");


        Main.window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                we.consume();   // Do not hide yet
                undecoratorScene.setFadeOutTransition();
            }
        });
        Main.window.setScene(undecoratorScene);
    }

    public void comboAction()throws Exception
    {
        distinction.setDisable(false);
        difficulty.setDisable(false);
        createPaper.setDisable(false);
        delete.setDisable(false);

    }
    //to load pattern screen
    public void addNewPattern()throws Exception
    {
        Pane root=FXMLLoader.load(getClass().getResource("../UI/GenPattern.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(Main.window,root);


        Main.window.setScene(undecoratorScene);

    }
    public void deletePattern()
    {
       int c=ComboBox1.getSelectionModel().getSelectedIndex(),i=0;
       File f=new File("testPattern");
        TestPattern t=null;
        System.out.println(c);
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {   try
            {if(i++==c){t=(TestPattern)oin.readObject();oin.close();break;}
                oin.readObject();}
            catch(Exception e)
            {oin.close();
                fin.close();
                break;
            }
            }
                if(t.delete(new File("testPattern")))
                {ComboBox1.getItems().remove(ComboBox1.getValue()); new Message().create("Pattern has been delete",Main.window);
                    ComboBox1.getSelectionModel().clearSelection();createPaper.setDisable(true);delete.setDisable(true);
                distinction.setDisable(true);difficulty.setDisable(true);}
        }catch(Exception e)
        {

            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      File f=new File("testPattern");
        String name=null;

        try
        {

            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {
                try
                {
                    name=((TestPattern)oin.readObject()).name;
                    ComboBox1.getItems().add(name);
                }catch(Exception e)
                {
                    oin.close();
                    break;
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();

        }

    }
    public void generateThePattern()throws Exception
    {

        new Wait().createTask().run();


        ReadyPaper.temp1=ComboBox1.getSelectionModel().getSelectedItem();
        ReadyPaper.temp2=(int)distinction.getValue();
        ReadyPaper.temp3=(int)difficulty.getValue();
        new Thread(new ReadyPaper()).start();
    }
    static Stage p;
    public void openAbout()throws Exception{

    }
}
