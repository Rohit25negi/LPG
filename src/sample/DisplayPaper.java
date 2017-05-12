package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * Created by Champ on 9/17/2015.
 */
public class DisplayPaper implements Initializable{

    @FXML
    public TextArea canvas;
    public Button file,collection;
    static ArrayList<Question>paper;
    static Stage stg;
static TestPattern title;
    static int distinction,difference;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i=1,p=1;
        for(Question q:paper)
        {
            if("null".equals(q.qid+""))
                canvas.appendText("\n                                                  Section "+(i++)+"\n\n");
            else
                canvas.appendText("\nQues "+(p++)+": "+q.ques+"\n");


        }
        canvas.scrollTopProperty();
        Tooltip t=new Tooltip();
        t.setText("EXPORT TO DOC FILE");
        file.setTooltip(t);
        Tooltip tt=new Tooltip();
        tt.setText("ADD TO COLLECTION FOR FUTURE REFERENCE");
        collection.setTooltip(tt);
    }
    public void create(ArrayList<Question> p,TestPattern name,int dis,int diff)throws Exception
    {
      paper=p;
        title=name;
        distinction=dis;
        difference=diff;
        stg=new Stage();
        stg.setTitle(name.name);
        Pane ppe= FXMLLoader.load(getClass().getResource("DisplayingPaper.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(stg,ppe);
        stg.setWidth(775);
        stg.setHeight(690);
        stg.setX(Main.window.getX() + Main.window.getWidth() / 2 - stg.getWidth() / 2);
        stg.setY(Main.window.getY() + Main.window.getHeight() / 2 - stg.getHeight() / 2);
        stg.setScene(undecoratorScene);
        stg.setResizable(false);
        stg.initModality(Modality.APPLICATION_MODAL);
        Platform.runLater(new Runnable() {
            public void run() {
                Genpaper.p.close();Genpaper.p=null;
            }
        });
        stg.showAndWait();

    }
  public  void saveToFile()
    {   int i=1,naming=0;

        FileChooser ff=new FileChooser();
        ff.setTitle("SAVE");
        ff.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOC files (*.doc)", "*.doc"));
        ff.setInitialDirectory(new File("./"));




        File f = ff.showSaveDialog(stg);
        if(f!=null)
       try
       {
         FileOutputStream fout=new FileOutputStream(f);
           PrintStream p=new PrintStream(fout);

        p.print(canvas.getText());
           p.close();
           fout.close();
           new Message().create("Paper has been created.",DisplayPaper.stg);
       }catch(Exception e)
       {

       }

    }
   public void saveToDataBase()throws Exception
    {
        if( new Papers(title,distinction,difference,paper).insert())
            new Message().create("Paper has been added to the Collection.",DisplayPaper.stg);
        else new Message().create("Paper could not be added to the Collection",DisplayPaper.stg);

    }
}
