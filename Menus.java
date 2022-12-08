import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Menus {
    public Image background;
    public Image backgroundCroppedRight;
    public Image backgroundCroppedLeft;
    public ArrayList<Button> buttonsMainMenu = new ArrayList<Button>();
    public ArrayList<Button> buttonsInstructionMenu = new ArrayList<Button>();

    public ArrayList<Button> buttonsChoiceMenu = new ArrayList<Button>();

    public ArrayList<Button> buttonsGame = new ArrayList<Button>();
    public String rules[]= {"Jakiś opis do gry",
            "tutaj ma się znajdować."};

    public boolean gameInit = false;
    public boolean gameOver = false;

    public Question question;
    public Player player;


    Menus()
    {

        buttonsMainMenu.add(new Button(462,280,130,60, "Play"));
        buttonsMainMenu.add(new Button(462,360,130,60, "Instruction"));
        buttonsMainMenu.add(new Button(462,440,130,60, "Quit"));

        this.background = new ImageIcon("images/background.png").getImage();
        this.backgroundCroppedRight = new ImageIcon("images/backgroundCroppedRight.png").getImage();
        this.backgroundCroppedLeft = new ImageIcon("images/backgroundCroppedLeft.png").getImage();

        buttonsInstructionMenu.add(new Button(462, 600,130,60, "return"));

        for(int i=0; i<3; i++)
        {
            buttonsChoiceMenu.add(new Button(222+150*i,200,130,60, "Level "+(i+1)));
        }

        buttonsChoiceMenu.add(new Button(462,600,130,60, "Return"));

        buttonsGame.add(new Button(20, 20,130,60,"Quit"));

        question = new Question(250,50,530,100,0,0,0);

        player = new Player(80,80);

    }

    public void gameInitialization(int hardship)
    {
        player = new Player(80,80);
        question = new Question(250,50,530,100,hardship, hardship-1, hardship);
        player.changeRoad(this.question.roads.get(0),0);
        gameOver = false;
        gameInit = true;
    }

    public void loop(boolean[] keys)
    {

        this.question.loop();
        this.player.loop(keys,this.question.roads);
        this.question.checkAnswer(this.player);
        if(this.player.lives==0)
        {
            this.gameOver = true;
        }
    }

    public String checkMainMenuButtons(int x, int y)
    {
        for(int i = 0; i<this.buttonsMainMenu.size();i++)
        {
            if(this.buttonsMainMenu.get(i).clicked(x,y))
            {
                if(i == this.buttonsMainMenu.size()-1){
                    System.exit(0);
                }
                else if(i == 0){
                    this.buttonsMainMenu.get(i).refresh();
                    return "choices";
                }
                else if(i == 1){
                    this.buttonsMainMenu.get(i).refresh();
                    return "instruction";
                }
            }
        }
        return "main";
    }
    public String checkInstructionMenuButtons(int x, int y)
    {
        if(this.buttonsInstructionMenu.get(0).clicked(x,y))
        {
            this.buttonsInstructionMenu.get(0).refresh();
            return "main";
        }

        return "instruction";
    }
    public String checkGameButtons(int x, int y, String room)
    {
        if(this.buttonsGame.get(0).clicked(x,y))
        {
            this.buttonsGame.get(0).refresh();
            return "choices";
        }

        return room;
    }
    public String checkChoiceMenuButtons(int x, int y)
    {
        for(int i = 0; i<this.buttonsChoiceMenu.size()-1;i++)
        {
            if(this.buttonsChoiceMenu.get(i).clicked(x,y))
            {
                this.buttonsChoiceMenu.get(i).refresh();
                gameInitialization(i+3);
                return "level"+(i + 3);
            }
        }
        if(this.buttonsChoiceMenu.get(this.buttonsChoiceMenu.size()-1).clicked(x,y))
        {
            this.buttonsChoiceMenu.get(this.buttonsChoiceMenu.size()-1).refresh();
            return "main";
        }
        return "choices";
    }
    public String click(int x, int y,String room)
    {
        if(room.equals("main"))
        {
            return checkMainMenuButtons(x,y);
        }
        else if(room.equals("instruction"))
        {
            return checkInstructionMenuButtons(x,y);
        }
        else if(room.equals("choices"))
        {
            return checkChoiceMenuButtons(x,y);
        }
        else if (room.contains("level"))
        {
            return checkGameButtons(x,y, room);
        }
        return room;

    }
    public void move(int x, int y,String room)
    {
        if(room.equals("main")) {
            for (int i = 0; i < this.buttonsMainMenu.size(); i++) {
                this.buttonsMainMenu.get(i).hovered(x, y);
            }
        }
        else if(room.equals("instruction"))
        {
            for (int i = 0; i < this.buttonsInstructionMenu.size(); i++) {
                this.buttonsInstructionMenu.get(i).hovered(x, y);
            }
        }
        else if(room.equals("choices"))
        {
            for (int i = 0; i < this.buttonsChoiceMenu.size(); i++) {
                this.buttonsChoiceMenu.get(i).hovered(x, y);
            }
        }
        else if(room.contains("level"))
        {
            for (int i = 0; i < this.buttonsGame.size(); i++) {
                this.buttonsGame.get(i).hovered(x, y);
            }
        }
    }
    public void press(int x, int y, String room)
    {
        if(room.equals("main")) {
            for (int i = 0; i < this.buttonsMainMenu.size(); i++) {
                this.buttonsMainMenu.get(i).pressed(x, y);
            }
        }
        else if(room.equals("instruction"))
        {
            for (int i = 0; i < this.buttonsInstructionMenu.size(); i++) {
                this.buttonsInstructionMenu.get(i).pressed(x, y);
            }
        }
        else if(room.equals("choices"))
        {
            for (int i = 0; i < this.buttonsChoiceMenu.size(); i++) {
                this.buttonsChoiceMenu.get(i).pressed(x, y);
            }
        }
        else if(room.contains("level"))
        {
            for (int i = 0; i < this.buttonsGame.size(); i++) {
                this.buttonsGame.get(i).pressed(x, y);
            }
        }
    }

    public void draw(Graphics2D g2D, String room)
    {
        switch (room) {
            case "main":
                this.drawMainMenu(g2D);
                break;
            case "choices":
                this.drawChoiceMenu(g2D);
                break;
            case "instruction":
                this.drawInstructionMenu(g2D);
                break;
            default:
                this.drawGame(g2D);
                break;
        }

    }
    public void drawMainMenu(Graphics2D g2D)
    {
        g2D.setColor(Color.white);
        g2D.drawImage(background, 0,0,1024,768,null);
        for(int i =0;i < this.buttonsMainMenu.size();i++)
        {
            buttonsMainMenu.get(i).draw(g2D);
        }
    }


    public void drawInstructionMenu(Graphics2D g2D)
    {
        g2D.drawImage(background, 0,0,1024,768,null);
        g2D.setColor(Color.white);
        g2D.setFont(new Font("Arial",Font.BOLD,20));
        //Wypisanie kolejnych linijek zasad
        for(int i=0; i<rules.length;i++)
        {
            g2D.drawString(rules[i],50,50+20*i);
        }
        this.buttonsInstructionMenu.get(0).draw(g2D);

    }
    public void drawChoiceMenu(Graphics2D g2D)
    {
        g2D.setColor(Color.white);
        g2D.drawImage(background, 0,0,1024,768,null);
        for(int i =0;i < this.buttonsChoiceMenu.size();i++)
        {
            buttonsChoiceMenu.get(i).draw(g2D);
        }
    }
    public void drawGame(Graphics2D g2D)
    {


        g2D.drawImage(background, 0,0,1024,768,null);


        this.question.draw(g2D, backgroundCroppedRight, backgroundCroppedLeft,this.gameOver);

        this.player.draw(g2D);

        for(int i =0;i < this.buttonsGame.size();i++)
        {
            buttonsGame.get(i).draw(g2D);
        }

    }
}
