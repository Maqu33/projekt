import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Player {
    public int x;
    public int y;
    public int width;
    public int height;
    public Image sprite = (new ImageIcon("images/player.png")).getImage();
    public Image spriteHeart = (new ImageIcon("images/heart.png")).getImage();

    public int roadIndex;
    public int lives = 3;
    Player(int width,int height)
    {

        this.width = width;
        this.height = height;

    }
    public void changeRoad(Road road, int index)
    {
        this.x = road.x+20;
        this.y = road.y+(road.height-this.height)/2;
        this.roadIndex = index;
    }

    public void loop(boolean[] keys, ArrayList<Road> roads)
    {
        if(keys[0])
        {
            keys[0]=false;
            if(roadIndex>0)
            {
                changeRoad(roads.get(this.roadIndex-1), this.roadIndex-1);
            }
        }
        else if(keys[1])
        {
            keys[1]=false;
            if(roadIndex<roads.size()-1)
            {
                changeRoad(roads.get(this.roadIndex+1), this.roadIndex+1);
            }
        }

    }
    public void draw(Graphics2D g2D)
    {
        g2D.drawImage(this.sprite, this.x, this.y, this.width, this.height,null);

        for(int i =0;i<this.lives;i++)
        {
            g2D.drawImage(this.spriteHeart, 950-i*60,20 , 50, 50,null);
        }

    }
}
