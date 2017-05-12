package sample;

/**
 * Created by Champ on 9/10/2015.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
public class Question implements Serializable{

    String qid,ques,qtype;
    int qdifficulty;
    File f=null;
    ArrayList<String>keyword;
    Question(String a,String b,String c,int d,File f)
    {
        qid=a;
        ques=b;
        qtype=c;
        qdifficulty=d;
        keyword=findKeyWord(ques);

        this.f=f;
    }
   static ArrayList<String> findKeyWord(String q)
    {ArrayList<String> list=new ArrayList();
        int j;
       if(q!=null) {
           String []messi={"is","the","what","how","why","when","where","that","of","a","an","do","there","can","should",
        "would","could","must","might","may","describe","explain","list","tell","will","has","have","had","are","you","was","were",
        "be","than","then","from","to","use","disadvantage","advantage","explain","example","difference","different"};
        String A[]=q.split(" ");
        StringBuffer x;
        for(int i=0;i<A.length;i++)
        {   x=new StringBuffer(A[i].toLowerCase());
            for(int l=0;l<x.length();l++)
                if(x.charAt(l)<97||x.charAt(l)>112)x.deleteCharAt(l--);
            if(!list.contains(A[i]))
            {
                for(j=0;j<messi.length;j++)
                    if(messi[j].equalsIgnoreCase(A[i]))break;
                if(j==messi.length)
                list.add(A[i].toLowerCase());}
        }

        return list;}
        else return null;
    }
    public String getQid()
    {

        return qid;
    }
    public String getQues()

    {
        return ques;
    }
    public String getQtype()
    {
        return qtype;
    }

    public int getQdifficulty()
    {

        return qdifficulty;
    }
    static void createnew(File f)
    {
        try
        {FileOutputStream fout=new FileOutputStream(f);
            ObjectOutputStream out=new ObjectOutputStream(fout);

            out.flush();
            out.close();
        }
        catch(Exception e)
        {

        }
    }
    static boolean insert(File f,String...Qdetails)
    {
       int i=0;
        Question qq=null;
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream ois=new ObjectInputStream(fin);
            while(true)
            {
                try
                {
                    qq=(Question)ois.readObject();

                    i++;
                }catch(Exception e)
                {
                    break;
                }
            }
            ois.close();
        }catch(Exception e)
        {
            createnew(f);
        }

        String id;
        if(qq==null)id="ques"+(f+"").substring((f+"").indexOf("\\")+1,((f+"").lastIndexOf("\\"))).substring(0,2)+(f+"").substring((f+"").lastIndexOf("\\")+1).substring(0,2)+1;
        else
        {   int k;
            for( k=qq.qid.length()-1;k>=0;k--)
            {
                if(qq.qid.charAt(k)<'0'||qq.qid.charAt(k)>'9')break;
            }
            k++;
            id=qq.qid.substring(0,k)+(Integer.parseInt(qq.qid.substring(k))+1);

        }
        Question Q=new Question(id,Qdetails[2],Qdetails[1],Integer.parseInt(Qdetails[0]),f);
        try
        {FileOutputStream fout=new FileOutputStream(f,true);
            ObjectOutputStream out=new ObjectOutputStream(fout) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };

            out.writeObject(Q);
            fout.close();
            out.flush();
            out.close();
        return true;

        }catch(Exception Ee)
        {
           System.out.println(Ee);
        }
        return false;
    }

   static ArrayList<Question> read(File f)
    {    ArrayList<Question>list=new ArrayList();
        try
        {FileInputStream fin=new FileInputStream(f);

            ObjectInputStream oin=new ObjectInputStream(fin);


            Question q=null;
            while(true)
            {
                try
                {
                    q=(Question)oin.readObject();
                    list.add(q);
                   }catch(Exception e)
                {

                    break;
                }
            }
            oin.close();
            fin.close();
            return list;
        }catch(Exception e)
        {
          e.printStackTrace();
        }

        return null;
    }
     boolean delete(File f)
    {       Question q;
        try {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream obj=new ObjectInputStream(fin);
            FileOutputStream fout = new FileOutputStream(f+"temp");
            ObjectOutputStream obj2=new ObjectOutputStream(fout);
            while(true)
            {
                try{
                        q=(Question)obj.readObject();
                        if(!q.qid.equals(this.getQid()))
                            obj2.writeObject(q);
                }
                catch(Exception e)
                {       fin.close();
                    obj.close();
                    fout.close();
                    obj2.flush();
                    obj2.close();
                    String name=f.toString();
                   // System.out.println(f);
                    Files.delete(Paths.get(f.toString()));
                    new File(name+"temp").renameTo(new File(name));




                break;
            }

            }
            return true;

        }catch(Exception e)
        {
            System.out.println("file not found"+e);
        }

        return false;
    }


}
