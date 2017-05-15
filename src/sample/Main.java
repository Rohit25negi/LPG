package sample;

import insidefx.undecorator.UndecoratorScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;

class Check implements Serializable
{
    String timp;
    Check(String t)
    {
        timp=t;
    }
    public boolean equals(Check c)
    {
        return timp.equals(c.timp);
    }
}
public class Main extends Application {

    static Parent root1;

    static Stage window,window2;

    @Override

    public void start(Stage primaryStage) throws Exception{
        window=primaryStage;
        primaryStage.setTitle("Lazy Paper Generator");
        System.out.println(getClass().getResource("MainPage.fxml"));
        Image image = new Image("UI/prototype.png");
        window.getIcons().addAll(image);
        root1 = FXMLLoader.load(getClass().getResource("../UI/MainPage.fxml"));
        final UndecoratorScene undecoratorScene = new UndecoratorScene(primaryStage, (Region)root1);
        // Overrides defaults
        undecoratorScene.addStylesheet("demoapp/demoapp.css");
        // Enable fade transition
        undecoratorScene.setFadeInTransition();



        primaryStage.setWidth(730);
        primaryStage.setHeight(670);

        primaryStage.setScene(undecoratorScene);
        primaryStage.setResizable(false);

        primaryStage.show();


    }

    public static void main(String[] args)
    {
        try
        {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface nwi = NetworkInterface.getByInetAddress(address);
            byte mac[] = nwi.getHardwareAddress();


            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            Check c1=new Check(sb.toString());
            File f=new File("Checksum.lgxp");
            if(!f.exists())
            {
                Desktop.getDesktop().browse(new URI("http://blazeroom-lpg.rhcloud.com/thankyou.html"));

                    FileOutputStream fout=new FileOutputStream(f);
                    ObjectOutputStream out=new ObjectOutputStream(fout);
                    out.writeObject(c1);
                    out.close();


            }
            else
            {
                FileInputStream fin=new FileInputStream(f);
                ObjectInputStream oin=new ObjectInputStream(fin);
                if(!((Check)oin.readObject()).equals(c1))
                {
                    Desktop.getDesktop().browse(new URI("http://blazeroom-lpg.rhcloud.com/thankyou.html"));

                    FileOutputStream fout=new FileOutputStream(f);
                    ObjectOutputStream out=new ObjectOutputStream(fout);
                    out.writeObject(c1);
                    out.close();
                }
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try{FadeApp.mains(null);
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
    }

    public void generatePaper()throws Exception{
        Pane root2=FXMLLoader.load(getClass().getResource("../UI/GenPapers.fxml"));

        UndecoratorScene undecoratorScene=new UndecoratorScene(window, root2);
        undecoratorScene.addStylesheet("demoapp/demoapp.css");




       window.setScene(undecoratorScene);



    }
    public void addQuestion()throws Exception
    {
        Pane root2=FXMLLoader.load(getClass().getResource("../UI/AddQuestion.fxml"));

        UndecoratorScene undecoratorScene=new UndecoratorScene(window, root2);

        undecoratorScene.addStylesheet("demoapp/demoapp.css");





        window.setScene(undecoratorScene);




    }
    public void  viewPaper()throws Exception
    {
new PaperList().create();

    }
    public void openAbout()throws Exception
    {   Stage st;

        Pane p= FXMLLoader.load(getClass().getResource("../UI/about.fxml"));
        st=new Stage();
        UndecoratorScene undecoratorScene=new UndecoratorScene(st,p);
        st.setWidth(650);
        st.setHeight(500);
        st.setX(Main.window.getX() + Main.window.getWidth() / 2 - st.getWidth() / 2);
        st.setY(Main.window.getY() + Main.window.getHeight() / 2 - st.getHeight() / 2);
        st.setScene(undecoratorScene);
        st.initModality(Modality.APPLICATION_MODAL);

        st.showAndWait();
    }
}
