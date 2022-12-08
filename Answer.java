import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Answer {
    public int x;
    public int y;
    public int width;
    public int height;
    public String text;
    public int speed;

    public int state=-1;
    public Image spriteDefault = (new ImageIcon("images/answer.png")).getImage();
    public Image sprite = spriteDefault;
    public Image spriteCorrect = (new ImageIcon("images/correctAnswer.png")).getImage();
    public Image spriteWrong = (new ImageIcon("images/wrongAnswer.png")).getImage();

    Answer(int x, int y, int width, int height, String text, int speed)
    {
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }
    public void loop()
    {
        this.x -=this.speed;

    }
    public void changeState(int state)
    {
        this.state = state;
        if(this.state==0)
        {
            this.sprite = spriteWrong;
        }
        else if(this.state == 1)
        {
            this.sprite = spriteCorrect;
        }
    }
    public void draw(Graphics2D g2D)
    {
        g2D.drawImage(this.sprite, this.x, this.y, this.width, this.height,null);
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial",Font.BOLD,20));
        g2D.drawString(text,x+5,y+35);
    }
}
