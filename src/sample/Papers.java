package sample;

/**
 * Created by Champ on 9/15/2015.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
public class Papers implements Serializable{
    private TestPattern t;
    String paperId;
    int distinction,difficulty;
    ArrayList<Question>encoded_paper;
   public TestPattern getT()
    {
        return t;
    }
   public String getPaperId()
    {
        return paperId;
    }
    public int getDistinction()
    {return distinction;}
    public int getDifficulty()
    {return difficulty;}


    Papers(TestPattern t1,int d,int d2,ArrayList<Question>p)
    {

        File f=new File("Papers");
        int x=1;
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {
                try{oin.readObject();
                x++;}catch(Exception e)
                {
                    break;
                }
            }



        }catch(Exception e)
        {
            e.printStackTrace();
        }
        paperId="%$%Paper"+x+"%$%";
        t=t1;
        distinction=d;
        difficulty=d2;
        encoded_paper=p;
    }

 boolean insert()
 {
     File f=new File("Papers");
     if(!f.exists())
     {try

     {   FileOutputStream fout=new FileOutputStream(f);
         ObjectOutputStream out=new ObjectOutputStream(fout);
         out.flush();
             out.close();

     }catch(Exception e)
     {
    e.printStackTrace();
     }
     }


     try
     {
         FileOutputStream fin=new FileOutputStream(f,true);
         ObjectOutputStream oin=new ObjectOutputStream(fin){
             protected void writeStreamHeader()throws IOException
             {
                 reset();
             }
         };
         oin.writeObject(this);
         oin.flush();
         oin.close();
        return true;
     }catch(Exception e)
     {
         e.printStackTrace();
     }


    return false;
 }
    static ArrayList<Papers> read()
    {
        File f=new File("Papers");
        ArrayList<Papers> list=new ArrayList();
        Papers p=null;
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {
                try
                {
                    p=(Papers)oin.readObject();
                    list.add(p);
                }catch(Exception e)
                {
                    oin.close();
                    break;
                }
            }
            return list;
        }catch(Exception e)
        {

            return null;
        }



    }


    static boolean delete(Papers p)
    {
        if(p==null)return false;
        File f=new File("Papers");
        Papers pp=null;
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            FileOutputStream fout=new FileOutputStream(new File(f+"temp"));
            ObjectOutputStream out=new ObjectOutputStream(fout);
            while(true)
            {
                try
                {
                  pp=(Papers)oin.readObject();
                   if(!pp.paperId.equals(p.paperId))
                       out.writeObject(pp);

                }catch(Exception e)
                {
                    oin.close();
                    out.close();
                    break;

                }
            }
            String name=f.toString();
            System.out.println(f);
            Files.delete(Paths.get(f.toString()));
            new File(name+"temp").renameTo(new File(name));
            return true;
        }catch(Exception e)
        {

            e.printStackTrace();
        }
        return false;
    }

}
