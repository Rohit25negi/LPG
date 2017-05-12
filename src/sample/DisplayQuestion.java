package sample;

/**
 * Created by Champ on 9/12/2015.
 */

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

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;
public class DisplayQuestion implements Initializable {

    @FXML
    public TableView<Question> table;
    public Pane anchor;
    static Stage st;
    static File f;
    public ObservableList<Question>quesList()
    {
        ObservableList<Question>list=FXCollections.observableArrayList();
        if(f.exists())
        {   System.out.println("exits");
            try {
                FileInputStream fin = new FileInputStream(f);
                ObjectInputStream oin=new ObjectInputStream(fin);

                while(true)
                {
                    try
                    {
                        Question c=(Question)oin.readObject();
                        list.add(c);
                    }catch(Exception e)
                    {   fin.close();
                        oin.close();
                        break;
                    }
                }

            }catch(Exception e)
            {

            }
        }
        return list;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        TableColumn<Question,String> Id =new TableColumn("Id");
        Id.setMinWidth(100);
        Id.setCellValueFactory(new PropertyValueFactory<>("qid"));
        Id.setResizable(false);
        TableColumn<Question,String> ques =new TableColumn<>("Ques");
        ques.setMinWidth(400);
        ques.setCellValueFactory(new PropertyValueFactory<>("ques"));
        ques.setResizable(false);




        TableColumn<Question,String> Type =new TableColumn<>("Type");
        Type.setMinWidth(100);
        Type.setCellValueFactory(new PropertyValueFactory<>("qtype"));
        Type.setResizable(false);
        TableColumn<Question,Integer> Diff =new TableColumn<>("Difficulty");
        Diff.setMinWidth(100);
        Diff.setCellValueFactory(new PropertyValueFactory<>("qdifficulty"));
        Diff.setResizable(false);
        table=new TableView();
        table.setItems(quesList());

        table.getColumns().addAll(Id, ques, Type, Diff);

        anchor.getChildren().add(table);

        System.out.println("hello");
    }
    void create(String file1,String file)throws Exception
    {   f=new File("ExamList/"+file1+"/"+file);
        st=new Stage();
        st.setTitle(file);
        Pane p= FXMLLoader.load(getClass().getResource("DisplayQuestion.fxml"));
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,p);

       st.setWidth(780);
        st.setHeight(570);
        st.setX(Main.window.getX() + Main.window.getWidth() / 2 - st.getWidth() / 2);
        st.setY(Main.window.getY() + Main.window.getHeight() / 2 - st.getHeight() / 2);
        st.setScene(undecoratorScene);
        st.setResizable(false);
        st.initModality(Modality.APPLICATION_MODAL);
        st.showAndWait();
    }
   public void deleteQues()throws Exception
    {
        Question s=table.getSelectionModel().getSelectedItem();
        if(s!=null)
        {   if(s.delete(f))
        {
            new Message().create("Question has been deleted",DisplayQuestion.st);
            table.getItems().remove(table.getSelectionModel().getSelectedIndex());
        }
        }
    }

}
