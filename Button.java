
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Button {
    public int x;
    public int y;
    public int width;
    public int height;
    public String text;
    public Image defaultSprite = (new ImageIcon("images/button.png")).getImage();
    public Image hoveredSprite = (new ImageIcon("images/buttonHovered.png")).getImage();
    public Image pressedSprite = (new ImageIcon("images/buttonPressed.png")).getImage();
    public Image sprite=defaultSprite;
    Button(int x, int y, int width, int height, String text)
    {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean Collision(int x, int y)
    {
        return x >=this.x && x<=this.x+this.width && y>=this.y && y<=this.y+this.height;
    }
    public boolean clicked(int x, int y)
    {
        return Collision(x,y);


    }
    public void hovered(int x, int y)
    {

        if(Collision(x, y))
        {
            this.sprite =hoveredSprite;

        }
        else
        {
            this.sprite = defaultSprite;
        }

    }
    public void pressed(int x, int y)
    {
        if(Collision(x, y))
        {
            this.sprite = pressedSprite;

        }
        else
        {
            this.sprite = defaultSprite;
        }

    }
    public void refresh()
    {

        this.sprite = defaultSprite;


    }
    public void draw(Graphics2D g2D)
    {
        g2D.drawImage(this.sprite, this.x, this.y, this.width, this.height,null);
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Arial",Font.BOLD,20));
        g2D.drawString(text,x+5,y+35);
    }
}
