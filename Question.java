import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.ImageIcon;
import java.util.Random;
public class Question {
    public int x;
    public int y;
    public int width;
    public int height;
    public String question;
    public Image sprite = (new ImageIcon("images/Answer.png")).getImage();
    public boolean newTask;
    public int frame;
    public int period;
    public int speed;
    public ArrayList<Road> roads = new ArrayList<Road>();
    public ArrayList<Answer> answers = new ArrayList<Answer>();
    public ArrayList<TaskSet> taskSets = new ArrayList<TaskSet>();
    public int hardship;
    public int taskSetIndex;
    public boolean newQuestion;
    public boolean chosen = false;
    Question(int x, int y, int width, int height, int roadsNumber, int speed, int hardship)
    {

        this.question = "";
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.frame = 0;
        this.period = 200;
        this.speed = speed;
        this.newTask =true;
        this.hardship = hardship;
        this.newQuestion = true;
        for(int i=0; i<roadsNumber; i++)
        {
            roads.add(new Road(50,210+110*i,924,100));
        }
        loadTasks();

    }
    public void loop()
    {

        if(this.newTask)
        {
            this.frame++;
            if(newQuestion) {
                createQuestion();
                newQuestion = false;
            }
            if(this.frame >= this.period)
            {
                this.chosen = false;
                this.newTask = false;
                this.frame =0;
                createAnswers();
            }
        }




        if(answers.size()>0)
        {
            for(int i =0;i<answers.size();i++)
            {
                answers.get(i).loop();

            }
            if(answers.get(0).x+answers.get(0).width<50 && !newTask)
            {

                while(answers.size()>0)
                {
                    answers.remove(0);
                }
                this.newTask = true;
                newQuestion = true;
            }
        }
    }

    public void checkAnswer(Player player)
    {

        int i =0;
        while(!chosen && i< this.answers.size()) {
            if (this.answers.get(i).x + this.answers.get(i).width > player.x &&
                    this.answers.get(i).x < player.x + player.width &&
                    this.answers.get(i).y + this.answers.get(i).height > player.y &&
                    this.answers.get(i).y < player.y + player.height)
            {
                chosen = true;
                for(int j =0;j< this.answers.size();j++)
                {
                    if(this.answers.get(j).text.equals(this.taskSets.get(this.taskSetIndex).correctAnswer))
                    {
                        this.answers.get(j).changeState(1);
                    }
                    else
                    {
                        this.answers.get(j).changeState(0);
                        if(j==i)
                        {
                            player.lives--;
                        }
                    }
                }


            }
            i++;
        }
    }


    public void createQuestion()
    {
        this.taskSetIndex = (int)(Math.random()*this.taskSets.size());
        this.question = this.taskSets.get(this.taskSetIndex).question;
        Collections.shuffle(this.taskSets.get(this.taskSetIndex).answers);
    }
    public void createAnswers()
    {
        for(int i =0;i<roads.size();i++)
        {
            answers.add(new Answer(roads.get(i).x+roads.get(i).width,roads.get(i).y+25,100,50,this.taskSets.get(this.taskSetIndex).answers.get(i) , speed));

        }

    }
    public void loadTasks()
    {

        File f = new File("Level_"+(this.hardship-2)+"_tasks.txt");
        if(f.exists())
        {
            if(f.canRead()) {
                try {
                    //wczytanie wyników z pliku
                    Scanner sf = new Scanner(f);
                    while (sf.hasNextLine()) {
                        this.taskSets.add(new TaskSet());
                        this.taskSets.get(this.taskSets.size()-1).question = sf.nextLine();
                        for(int i=0;i<this.hardship;i++)
                        {
                            this.taskSets.get(this.taskSets.size()-1).answers.add(sf.nextLine());
                        }
                        this.taskSets.get(this.taskSets.size()-1).correctAnswer =sf.nextLine();
                    }
                    sf.close();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    public void draw(Graphics2D g2D, Image bgCR, Image bgCL, boolean gameOver)
    {



        for(int i =0;i<roads.size();i++)
        {
            roads.get(i).draw(g2D);

        }
        for(int i =0;i<answers.size();i++)
        {
            answers.get(i).draw(g2D);

        }
        g2D.drawImage(bgCR,978,0,50,768,null);
        g2D.drawImage(bgCL,0,0,50,768,null);


        g2D.drawImage(this.sprite, this.x, this.y, this.width, this.height,null);


        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial",Font.BOLD,20));
        g2D.drawString(this.question,this.x+5,this.y+35);

        if(gameOver)
        {
            g2D.setColor(Color.RED);
            g2D.setFont(new Font("Arial",Font.BOLD,20));
            g2D.drawString("Game over",this.x+200,this.y-35);
        }

    }
}
