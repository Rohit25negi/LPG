package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Champ on 9/17/2015.
 */
public class PaperList implements Initializable {
    @FXML
    public Pane anchor;
    static Stage st;
   public TableView<Papers> table;
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        TableColumn<Papers,String> Id =new TableColumn("Id");
        Id.setMinWidth(200);
        Id.setCellValueFactory(new PropertyValueFactory<>("paperId"));

        TableColumn<Papers,String> pattern =new TableColumn<>("Pattern");
        pattern.setMinWidth(300);
        pattern.setCellValueFactory(new PropertyValueFactory<>("t"));
        pattern.setResizable(false);




        TableColumn<Papers,Integer> dis =new TableColumn<>("Distinction");
        dis.setMinWidth(100);
        dis.setCellValueFactory(new PropertyValueFactory<>("distinction"));

        TableColumn<Papers,Integer> Diff =new TableColumn<>("Difficulty");
        Diff.setMinWidth(100);
        Diff.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        table=new TableView();

            table.setItems(paperList());
        table.getColumns().addAll(Id,pattern,dis,Diff);
        anchor.getChildren().add(table);

    }
    ObservableList<Papers> paperList()
    {
        ObservableList<Papers> o= FXCollections.observableArrayList();
        File f=new File("Papers");
        if(!f.exists())
        {
            try {
                FileOutputStream fout = new FileOutputStream(f);

                ObjectOutputStream out = new ObjectOutputStream(fout);
                out.close();
            }catch(Exception e)
            {
            e.printStackTrace();
            }
        }
        Papers p;
        try {
            FileInputStream fin = new FileInputStream("Papers");
            ObjectInputStream oin = new ObjectInputStream(fin);
            while (true) {
                try {
                    p=(Papers)oin.readObject();
                    o.add(p);

                } catch (Exception e) {
                    oin.close();break;
                }

            }
            return o;
        }catch(Exception e)
        {
        e.printStackTrace();
        }
        return null;
    }
    public void create()throws Exception
    {

        st=new Stage();
        st.setTitle("Papers");
        Pane p= FXMLLoader.load(getClass().getResource("PaperList.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,p);
        st.setHeight(600);
        st.setWidth(770);

        st.setX(Main.window.getX() + Main.window.getWidth()/2 - st.getWidth()/2);
        st.setY(Main.window.getY() + Main.window.getHeight()/2 - st.getHeight()/2);
        st.setScene(undecoratorScene);
        st.initModality(Modality.APPLICATION_MODAL);

        st.initOwner(Main.window);
        st.showAndWait();
    }
    public void deletePaper()throws Exception
    {
        Papers x=table.getSelectionModel().getSelectedItem();

        if(Papers.delete(x))
        {new Message().create("Paper has been Deleted From Collection",PaperList.st);
            table.getItems().remove(x);
            table.getSelectionModel().clearSelection();
        }
        else new Message().create("Paper Could not be deleted",PaperList.st);


    }
    public void OnView()throws Exception
    {
            Papers x=table.getSelectionModel().getSelectedItem();




            if(x!=null);
            try {
                new DisplayPaper2().create(x.encoded_paper, x.getT(), x.distinction, x.difficulty);
            }catch(Exception e)
            {
            e.printStackTrace();
            }
    }

}
