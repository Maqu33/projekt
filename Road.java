import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;



public class Road {
    public int x;
    public int y;
    public int width;
    public int height;

    public Image sprite=(new ImageIcon("images/road.png")).getImage();
    Road(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public void draw(Graphics2D g2D)
    {
        g2D.drawImage(this.sprite, this.x, this.y, this.width, this.height,null);
    }
}
