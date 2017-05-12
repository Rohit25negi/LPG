package sample;

/**
 * Created by Champ on 9/15/2015.
 */

import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;
public class ReadyPaper extends Thread{
    static TestPattern t;
    int diff,distinct;
    ArrayList<Question>encoded_paper;
    static ArrayList<Question> pop[];
    ArrayList<Question> hard2[];
    ArrayList<Question> simple2[];
    ArrayList<Question> normal2[];
    ArrayList<Question> list[];
    Random rand=new Random(),rand2=new Random();
    //################### KIND HANDLING METHOD##################################
    static String temp1;
    static int temp2,temp3;

    public Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                create(temp1,temp2,temp3);
                return null;
            }
        };
    }
    public void run()

    {
        create(temp1,temp2,temp3);
    }
    static  void   create(String name,int n,int m)
    {   ArrayList<Question>fitest=new ArrayList();
        int fitty=100000;
        File f=new File("testPattern");
        try
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream oin=new ObjectInputStream(fin);
            while(true)
            {
                try
                {
                    t=(TestPattern)oin.readObject();
                    if(t.name.equals(name))break;
                }catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"ExamPattern Not Found");
                }
            }

        }catch(Exception e)
        {

        }
        ReadyPaper p=new ReadyPaper();
        pop=p.createInitPop(n,m);
        if(pop!=null)
        {int[][]fitness=new int[pop.length][];


            if(pop!=null) {
                fitness = p.evaluate();
                if(fitness!=null)
                {


                // new Papers().createPaper(pop[fitness[0][1]],p.diff,p.distinct,ReadyPaper.t);
                int times;
                    if(p.distinct<=3)times=50 * p.distinct;
                    else times=50*3;
                for (int i = 1; i <=times ; i++) {


                    p.crossover(fitness);

                    if (new Random().nextInt(10) > 4) ;p.mutation();

                    fitness = p.evaluate();
                    if(fitness[0][0]<fitty){
                        fitty=fitness[0][0];
                        fitest.clear();
                        fitest.addAll(pop[fitness[0][1]]);
                    }
                }
                //for(int []r:fitness)System.out.println("######"+r[0]);

               // new Papers(ReadyPaper.t, p.distinct, p.diff, pop[fitness[0][1]]).insert();

                    ArrayList<Question> pp=fitest;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                new DisplayPaper().create(pp, ReadyPaper.t, p.distinct, p.diff);
                            }catch(Exception e)
                            {
                                System.out.println(e);
                            }
                        }
                    });

            }
            else
            {
              //  new Papers(ReadyPaper.t, p.distinct, p.diff, pop[new Random().nextInt(pop.length)]).insert();
               try
               {

                   ArrayList<Question> pp=pop[new Random().nextInt(pop.length)];
                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               new DisplayPaper().create(pp, ReadyPaper.t, p.distinct, p.diff);
                           } catch (Exception e) {
                               System.out.println(e);
                           }
                       }
                   });

               }catch(Exception e)
               {
                   e.printStackTrace();

               }
            }
            }
        }
    }
//################################## HANDLING METHOD FINISHED############################


//####################################MUTATION STARTS#####################################

    void mutation()
    {

        int x=rand.nextInt((int)(pop.length*.2)),k,add;

        ArrayList<Question> A;
        ArrayList<Integer> selected=new ArrayList();
        ArrayList<Integer>geneSel=new ArrayList();
        ArrayList<Question> holder;

        for(int i=0;i<x;i++)
        {   while(true)
        {
            int m=rand.nextInt(pop.length);
            if(!selected.contains(m))
            {
                selected.add(m);break;
            }

        }
        }

        for(int i=0;i<x;i++)
        {A=pop[selected.get(i)];

            geneSel.clear();
            for(int j=0;j<A.size()*.4;j++)
            {
                while(true)
                {int m=rand.nextInt(A.size());
                    if(!geneSel.contains(m)){geneSel.add(m);break;}
                }
            }

            for(int j=0;j<geneSel.size();j++)
            {int sec=-1;
                if((A.get(geneSel.get(j)).qid+"").equals("null"))continue;

                for(int find=0;find<geneSel.get(j);find++)
                {
                    if((A.get(find).qid+"").equals("null"))sec++;
                }

                holder=new ArrayList(list[sec]);
                for(int del=0;del<A.size();del++)
                {
                    for(int dellist=0;dellist<holder.size();dellist++)
                        if(A.get(del).qid!=null&&(A.get(del).qid+"").equals(holder.get(dellist).qid+""))
                        {
                            holder.remove(dellist);dellist--;
                        }
                }
                ArrayList<Question>tempo=new ArrayList(holder);
                for(int counter=0;counter<tempo.size();counter++)
                    if(!(A.get(geneSel.get(j)).f.toString()).equals(tempo.get(counter).f.toString()))
                    {tempo.remove(counter--);}

                if(!tempo.isEmpty())
                    A.set(geneSel.get(j),tempo.get(rand.nextInt(tempo.size())));



            }
        }
    }
//######################################MUTATION ENDS ###########################################3


    //######################################CROSSOVER STARTS#######################################
    void crossover(int fitness[][])
    {

        int x=(int)(fitness.length*0.1),p1,p2,add;

        ArrayList<Question>A;
        Question temp;
        int g,sec=-1;
        ArrayList<Question>list2;

        for(int i=0;i<x;i++)
        {

            A=new ArrayList();
            sec=-1;
            p1=fitness[rand.nextInt(fitness.length/2)][1];

            while((p2=fitness[rand2.nextInt(1+rand.nextInt(fitness.length/2))][1])==p1);

            for(int j=0;j<pop[p1].size();j++) {
                if (pop[p1].get(j).qid == null) {
                    A.add(new Question(null, null, null, 0, null));
                    sec++;
                }
                else{if(pop[p1].get(j).qid != null && pop[p1].get(j).qid.equals(pop[p2].get(j).qid))
                {
                    temp = pop[p1].get(j);

                    A.add(temp);

                }
                else if (rand.nextInt(2) == 0) {
                    temp = pop[p1].get(j);

                    for (g = 0; g < A.size(); g++)
                        if (A.get(g).qid != null && temp.qid != null && (A.get(g).qid).equals(temp.qid)) break;
                    if (g == A.size()) A.add(temp);
                    else {
                        temp = pop[p2].get(j);
                        for (g = 0; g < A.size(); g++)
                            if (A.get(g).qid != null && temp.qid != null && (A.get(g).qid).equals(temp.qid)) break;
                        if (g == A.size()) A.add(temp);
                        else {

                            list2 = new ArrayList(list[sec]);
                            for (int dellist = 0; dellist < list2.size(); dellist++)
                            {for (int del = 0; del < A.size(); del++)
                                if (A.get(del).qid != null && list2.get(dellist).qid != null && (A.get(del).qid).equals(list2.get(dellist).qid)) {
                                        list2.remove(dellist);
                                        dellist--;break;
                                    }
                            }
                            //ensurity of same topic of question
                            ArrayList<Question> tempo = new ArrayList(list2);
                            for (int counter = 0; counter < tempo.size(); counter++)
                                for(int pintu=0;pintu<A.size();pintu++)
                                if (A.get(pintu).qid!=null&&tempo.get(counter).qid.equals(A.get(pintu).qid)) {
                                    tempo.remove(counter--);
                                }

                            if (!tempo.isEmpty()) A.add(tempo.get(rand2.nextInt(1+rand.nextInt(tempo.size()))));
                            else A.add(list2.get(rand2.nextInt(1+rand.nextInt(list2.size()))));

                        }
                    }
                } else {
                    temp = pop[p2].get(j);

                    for (g = 0; g < A.size(); g++)
                        if (A.get(g).qid != null && temp.qid != null && (A.get(g).qid).equals(temp.qid)) break;
                    if (g == A.size()) A.add(temp);
                    else {
                        temp = pop[p1].get(j);
                        for (g = 0; g < A.size(); g++)
                            if (A.get(g).qid != null && (A.get(g).qid).equals(temp.qid)) break;
                        if (g == A.size()) A.add(temp);
                        else {
                            //adding new
                            list2 = new ArrayList(list[sec]);
                            for (int dellist = 0; dellist < list2.size(); dellist++){
                            for (int del = 0; del < A.size(); del++)
                                    if (A.get(del).qid != null && (A.get(del).qid).equals(list2.get(dellist).qid)) {
                                        list2.remove(dellist);
                                        dellist--;break;
                                    }
                            }

                            ArrayList<Question> tempo = new ArrayList(list2);
                            for (int counter = 0; counter < tempo.size(); counter++)
                                for(int pintu=0;pintu<A.size();pintu++)
                                    if (A.get(pintu).qid!=null&&tempo.get(counter).qid.equals(A.get(pintu).qid)) {
                                        tempo.remove(counter--);
                                    }

                            if (!tempo.isEmpty()) A.add(tempo.get(rand2.nextInt(rand.nextInt(tempo.size())+1)));
                            else A.add(list2.get(rand2.nextInt(rand.nextInt(list2.size())+1)));

                            //added new question
                        }

                    }
                }
            }
            }

            pop[fitness[fitness.length-1-i][1]]=A;

        }
    }
    //############################################### CROSSOVER FINISHED######################################

    //###############################################NATURE STARTS SUCKING ##############################
    int[][] evaluate()
    {
        ArrayList<Papers>papers=Papers.read();
        int h=0,e=0,m=0,quess=pop[0].size();
        if(papers!=null)
        {
            int fitness[][] = new int[pop.length][2], x;

            for (int i = 0; i < pop.length; i++) {
                x = 0;h=e=m=0;
                for (int j = 0; j < papers.size(); j++) {
                    for (int k = 0; k < pop[i].size(); k++) {
                        if(pop[i].get(k).qid!=null&&pop[i].get(k).qdifficulty==3)h++;
                        if(pop[i].get(k).qid!=null&&pop[i].get(k).qdifficulty==2)m++;
                        if(pop[i].get(k).qid!=null&&pop[i].get(k).qdifficulty==1)e++;


                        for (int l = 0; l < papers.get(j).encoded_paper.size(); l++) {//System.out.println(pop[i].get(k)[0]+":");
                            if((pop[i].get(k).qid+"").equals(""+papers.get(j).encoded_paper.get(l).qid)) {
                                x++;
                                break;
                            }
                        }
                    }

                }


                fitness[i][0] = x;
                fitness[i][1] = i;

                if(this.diff==3)
                {
                    fitness[i][0]+=(-h);
                }
                else if(this.diff==2)
                {
                    fitness[i][0]+=(-m);
                }
                else if(this.diff==1)
                {
                    fitness[i][0]+=(-e);
                }



            }

            for (int i = 0; i < fitness.length - 1; i++) {
                for (int j = i + 1; j < fitness.length; j++) {
                    if (fitness[i][0] > fitness[j][0]) {
                        fitness[i][0] = fitness[i][0] ^ fitness[j][0];
                        fitness[j][0] = fitness[i][0] ^ fitness[j][0];
                        fitness[i][0] = fitness[i][0] ^ fitness[j][0];
                        fitness[i][1] = fitness[i][1] ^ fitness[j][1];
                        fitness[j][1] = fitness[i][1] ^ fitness[j][1];
                        fitness[i][1] = fitness[i][1] ^ fitness[j][1];
                    }
                }
            }



            return fitness;

        }
        return null;
    }

    //########################################NATURE SUCKED##################################################


    //#####################################BEGINING OF THE UNIVERSE###########################################

    ArrayList<Question>[] createInitPop(int n,int m)
    {
        this.diff=m;
        this.distinct=n;

        ArrayList<Question>A[];//=new ArrayList();
        int Short=0,medium=0,Long=0;
        int temp=0,temp2=0;
        for(String sec[]:t.secs)
        {
            temp+=Integer.parseInt((String)sec[0]);
            if("short".equalsIgnoreCase((String)sec[1]))Short+=Integer.parseInt((String)sec[0]);
            else if("long".equalsIgnoreCase((String)sec[1]))Long+=Integer.parseInt((String)sec[0]);
            else if("medium".equalsIgnoreCase((String)sec[1]))medium+=Integer.parseInt((String)sec[0]);
            else
            {
                Short+=Integer.parseInt((String)sec[0])/3;
                Long+=Integer.parseInt((String)sec[0])/3;
                medium+=Integer.parseInt((String)sec[0])/3;
            }
        }
       Question temp_ppr[]=new Question[temp+2];
        OUTER: for(String o:t.topic)
        {
            try
            {
                FileInputStream fin=new FileInputStream("ExamList/"+t.syl+"/"+o);
                ObjectInputStream oin=new ObjectInputStream(fin);
                while(true)
                {
                    try
                    {
                        temp2++;

                        if(temp<=temp2)break OUTER;
                    }catch(Exception e)
                    {
                        break;
                    }
                }
            } catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(temp2<temp){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try{
                        new Message().create("Not enough questions",Main.window);
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
            });
            return null;}
        else
        {


            int sec_count=t.no_of_sec;
            ArrayList<Question> ques=new ArrayList();
            ArrayList<Question> hard[]=new ArrayList[sec_count];
            ArrayList<Question> simple[]=new ArrayList[sec_count];
            ArrayList<Question> normal[]=new ArrayList[sec_count];
            hard2=new ArrayList[sec_count];
            simple2=new ArrayList[sec_count];
            normal2=new ArrayList[sec_count];
            list=new ArrayList[sec_count];
            for(int i=0;i<sec_count;i++)
            {int x;

                //System.out.println(t.secs[i][1]);
                ques.clear();
                if(t.choice[i].length==0)
                {
                    for(int j=0;j<t.topic.length;j++)
                    {
                        ques.addAll(Question.read(new File("ExamList/" + t.syl + "/" + (String) t.topic[j])));


                    }
                }
                else
                {
                    for(int j=0;j<t.choice[i].length;j++)
                    {
                        ques.addAll(Question.read(new File("ExamList/" + t.syl + "/" + (String) t.choice[i][j])));


                    }

                }
                if(!(t.secs[i][1]).equals("Mixed"))
                    for(int k=0;k<ques.size();k++)
                        if(!ques.get(k).qtype.equals(t.secs[i][1]))
                            ques.remove(k--);


                hard[i]=new ArrayList();
                normal[i]=new ArrayList();
                simple[i]=new ArrayList();

                list[i]=new ArrayList(ques);
                for (Question xx : ques) {
                    if (xx.qdifficulty == 3) hard[i].add(xx);
                    else if (xx.qdifficulty == 2) normal[i].add(xx);
                    else if (xx.qdifficulty == 1) simple[i].add(xx);
                }
                // System.out.println(hard[i].size()+":"+simple[i].size()+":"+normal[i].size());

            }
            if((ques.get(0).qtype).equalsIgnoreCase("short"))
            {if(ques.size()<2*Short){

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            new Message().create("Not enough questions",Main.window);
                        }catch(Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                });
                return null;}}
            else if((ques.get(0).qtype).equalsIgnoreCase("Long"))
            {if(ques.size()<2*Long){

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            new Message().create("Not enough questions",Main.window);
                        }catch(Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                });
                return null;}}

            else if((ques.get(0).qtype).equalsIgnoreCase("Medium"))
            {if(ques.size()<2*medium){Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try{
                        new Message().create("Not enough questions",Main.window);
                    }catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
            });
                return null;}}
            else
            {
                if(ques.size()<2*(medium+Short+Long)){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                new Message().create("Not enough questions",Main.window);
                            }catch(Exception e)
                            {
                                System.out.println(e);
                            }
                        }
                    });return null;}
            }

            for(int i=0;i<sec_count;i++)
            {

                hard2[i]=new ArrayList(hard[i]);
                simple2[i]=new ArrayList(simple[i]);
                normal2[i]=new ArrayList(normal[i]);


            }

            int temp_rand,vin,xx;
            int times;
            if(this.distinct<=3)times=this.distinct*50+10;
            else times=160;
            A=new ArrayList[times];
            for(int i=0;i<A.length;i++)
            {  A[i]=new ArrayList();
                for(int j=0;j<sec_count;j++)
                {

                    vin=3;
                    A[i].add(new Question(null,null,null,0,null));
                    for(int k=0;k<Integer.parseInt(t.secs[j][0]);k++)
                    {   while(true) {
                        temp_rand = rand.nextInt(vin);
                        if (temp_rand == 0 && !hard[j].isEmpty()) {
                            xx =rand2.nextInt(rand.nextInt(hard[j].size())+1);
                            A[i].add(hard[j].get(xx));
                            hard[j].remove(xx);
                            if (hard[j].isEmpty()) vin--; break;

                        } else if (temp_rand == 1 && !simple[j].isEmpty() || (temp_rand == 0 && hard[j].isEmpty() && !simple[j].isEmpty())) {
                            xx = rand2.nextInt(rand.nextInt(simple[j].size())+1);
                            A[i].add(simple[j].get(xx));
                            simple[j].remove(xx);
                            if (simple[j].isEmpty()) vin--;break;

                        } else if (temp_rand == 2 && !normal[j].isEmpty() || (temp_rand == 1 && hard[j].isEmpty() && !normal[j].isEmpty()) || (temp_rand == 1 && simple[j].isEmpty() && !normal[j].isEmpty()) || (temp_rand == 0 && hard[j].isEmpty() && simple[j].isEmpty() && !normal[j].isEmpty())) {
                            xx = rand2.nextInt(rand.nextInt(normal[j].size())+1);
                            A[i].add(normal[j].get(xx));
                            normal[j].remove(xx);
                            if (normal[j].isEmpty()) vin--;break;
                        }

                    }
                    }
                }

                for(int j=0;j<sec_count;j++)
                {
                    hard[j]=new ArrayList(hard2[j]);
                    simple[j]=new ArrayList(simple2[j]);
                    normal[j]=new ArrayList(normal2[j]);
                }

            }


        }

        return A;
    }
//##########################################UNIVERSE FORMED##################################################

}
