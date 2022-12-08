import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

public class Main extends JFrame{
    private Timer timer;
    public Menus menus;

    public boolean latch = false;
    public String room = "main";
    public boolean keys[];

    class Loop extends TimerTask {


        public void run()
        {
            if(room.contains("level") && !menus.gameOver)
            {
                menus.loop(keys);
            }
            repaint();
        }

    }
    public void changeButtonsStatus(int x, int y, String ev)
    {
        if(ev.equals("move"))
        {
            menus.move(x,y,room);
        }
        else if(ev.equals("press")) {
            menus.press(x, y, room);
        }
        repaint();
    }
    public void changeRoom(int x, int y)
    {

        room = menus.click(x, y, room);


        repaint();
    }
    Main(){
        super("Running Words"); //Napis na okienku
        setBounds(50,50,1030,803); // Wymiary okienka
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Możliwość wyłączenia programu przez krzyżyk
        setResizable(false); //Zablokowanie możliwości rozciągania okienka
        setVisible(true);
        createBufferStrategy(2);
        this.menus = new Menus();
        keys = new boolean[2];
        timer = new Timer();
        timer.scheduleAtFixedRate(new Loop(),0,1000/60);
        this.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
                if(room.contains("level")) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if(!latch)
                            {

                                keys[0] = true;
                                latch = true;
                            }

                            break;
                        case KeyEvent.VK_DOWN:
                            if(!latch)
                            {
                                keys[1] = true;
                                latch = true;
                            }

                            break;
                    }


                }
            }

            public void keyReleased(KeyEvent e) {
                if(room.contains("level")) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if(latch) {
                                latch = false;
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if(latch) {
                                latch = false;
                            }
                            break;
                    }
                }
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        getContentPane().addMouseListener(new MouseE(){ //Obsługa kliknięć myszy

            public void mouseClicked(MouseEvent e)
            {

                changeRoom(e.getX(), e.getY());
            }
            public void mousePressed(MouseEvent e)
            {

                changeButtonsStatus(e.getX(), e.getY(),"press");

                repaint();
            }
            public void mouseReleased(MouseEvent e) {
                changeRoom(e.getX(), e.getY());

            }
        });
        getContentPane().addMouseMotionListener(new MouseE(){

            public void mouseMoved (MouseEvent e)
            {

                    changeButtonsStatus(e.getX(), e.getY(),"move");

            }
        });




    }

    public static void main(String[] args)
    {
        Main window = new Main();
        window.repaint();
    }

    public void paint(Graphics g)
    {

        BufferStrategy bstrategy = this.getBufferStrategy();
        Graphics2D g2D = (Graphics2D)bstrategy.getDrawGraphics();
        g2D.translate(3,32);
        this.menus.draw(g2D, room);
        g2D.dispose();
        bstrategy.show();

    }
}


