package sample;

import insidefx.undecorator.UndecoratorScene;
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

public class DisplayPaper2 implements Initializable{

    @FXML
    public TextArea canvas;
    public Button file;

    static ArrayList<Question> paper;
    static Stage stg;
    static TestPattern title;
    static int distinction,difference;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int i=1,count=1;
        for(Question q:paper)
        {
            if("null".equals(q.qid+""))
                canvas.appendText("\n                                                  Section "+(i++)+"\n\n");
            else
                canvas.appendText("\nQues "+count+++": "+q.ques+"\n");


        }
        canvas.setEditable(false);
        Tooltip t=new Tooltip();
        t.setText("export to doc file");
        file.setTooltip(t);


    }
    public void create(ArrayList<Question> p,TestPattern name,int dis,int diff)throws Exception
    {
        paper=p;
        title=name;
        distinction=dis;
        difference=diff;
        stg=new Stage();
        Pane ppe= FXMLLoader.load(getClass().getResource("DisplayPaper2.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(stg,ppe);
        stg.setWidth(775);
        stg.setHeight(685);
        stg.setX(PaperList.st.getX() + PaperList.st.getWidth() / 2 - stg.getWidth() / 2);
        stg.setY(PaperList.st.getY() + PaperList.st.getHeight() / 2 - stg.getHeight() / 2);
        stg.setTitle(name.name);
        stg.setScene(undecoratorScene);
        stg.setResizable(false);
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.showAndWait();

    }
    public  void saveToFile()
    {   int i=1;
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
            new Message().create("Paper has been created.",DisplayPaper2.stg);
        }catch(Exception e)
        {

        }

    }

}
