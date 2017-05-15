package sample;

/**
 * Created by Champ on 9/6/2015.
 */

import insidefx.undecorator.UndecoratorScene;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Uni implements Initializable{
    static String Append="",tempo;
    @FXML
    public ComboBox<String> examType,topic;
    public TextArea question;
    public Button AddItBaby,openFile,deleteFile;
    public Button Symbol;
    ArrayList<String> exams=new ArrayList();


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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exams=examList();
        examType.getItems().addAll(exams);
       // examType.setOnAction(this);
        Tooltip t=new Tooltip();
        t.setText("SYMBOLS");

        Symbol.setTooltip(t);


    }

    public void actionOnType()throws Exception
    {
            System.out.println("agye hum yaha"+examType.getSelectionModel().getSelectedItem());
        if("Add New".equals(examType.getSelectionModel().getSelectedItem()))
        {
            new AddNew().create("Add ExamType");
            if(tempo!=null)
            {
                File f=new File("ExamList/"+tempo);
                if(f.exists())new Message().create("Already Exists.",Main.window);
                else
                {
                    f.mkdir();
                    examType.getItems().add(tempo);
                    tempo=null;
                }

            }
            question.clear();
            topic.setDisable(true);
            question.setDisable(true);
            deleteFile.setDisable(true);
            openFile.setDisable(true);

        }
        else
        {
            topic.setDisable(false);
            topic.getItems().clear();
            File f=new File("ExamList/"+examType.getSelectionModel().getSelectedItem());
            if(f.exists())
            {
                String list[]=f.list();
                topic.getItems().addAll("Add New");
                topic.getItems().addAll(list);

            }
            question.clear();

            question.setDisable(true);
            deleteFile.setDisable(true);
            openFile.setDisable(true);
            AddItBaby.setDisable(true);
            Symbol.setDisable(true);
        }
    }


    public void onTopicSelect()throws Exception
    {
        if("Add New".equals(topic.getSelectionModel().getSelectedItem()))
        {
            new AddNew().create("add Topic");
            if(tempo!=null)
            {
                File f=new File("ExamList/"+examType.getSelectionModel().getSelectedItem()+"/"+tempo);
                if(f.exists())
                    new Message().create("File Already Exists",Main.window);
                else
                {
                    FileOutputStream fin=new FileOutputStream(f);
                    ObjectOutputStream oin=new ObjectOutputStream(fin);
                    fin.close();
                    oin.flush();
                    oin.close();
                }
                topic.getItems().add(tempo);
                tempo=null;

            }
            deleteFile.setDisable(true);
            openFile.setDisable(true);
            question.clear();
            question.setDisable(true);
            AddItBaby.setDisable(true);
            Symbol.setDisable(true);

        }
        else
        {
            deleteFile.setDisable(false);
            openFile.setDisable(false);
            question.setDisable(false);
            AddItBaby.setDisable(false);
            Symbol.setDisable(false);

        }
    }

    public void deleteF()throws Exception
    {   String x=topic.getSelectionModel().getSelectedItem();
        File f=new File("ExamList/"+examType.getSelectionModel().getSelectedItem()+"/"+x);
        if(f.exists())
            while(!f.delete());
        new Message().create("File Deleted",Main.window);
        topic.getSelectionModel().clearSelection();
        topic.getItems().remove(x);
        openFile.setDisable(true);
        question.clear();
        question.setDisable(true);
        deleteFile.setDisable(true);
        Symbol.setDisable(true);
        AddItBaby.setDisable(true);


    }
    public void showQuestions()throws Exception
    {
        new DisplayQuestion().create(examType.getSelectionModel().getSelectedItem(),topic.getSelectionModel().getSelectedItem());
    }
    public void symbolList()throws Exception
    {

        new Symbols().create();
        question.appendText(Append);
        Append="";

    }
    private ArrayList<String> examList()
    {
        File f=new File("ExamList");
        if(!f.exists())f.mkdir();

        ArrayList<String> l=new ArrayList();

        l.add("Add New");
        String x[]=f.list();
        for(String xx:x)
        {
            l.add(xx);
        }

        return l;


    }

    public void addItBoy()throws Exception
    {
        if(!question.getText().trim().isEmpty())
        {   new Availability().create(examType.getSelectionModel().getSelectedItem(),topic.getSelectionModel().getSelectedItem(),question.getText());
        }
        else new Message().create("Write the Question.",Main.window);
    }

}