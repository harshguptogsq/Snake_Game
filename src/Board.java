import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{

    private int dots;

    private Image apple;
    private Image dot;
    private Image head;

    private final int al_dot=900;
    private final int dot_size=10;

    private final int x[]=new int[al_dot];
    private final int y[]=new int[al_dot];

    private final int Random_Position= 29;
    private Timer timer;

    private int apple_x;
    private int apple_y;
    private boolean inGame=true;

    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;
    private int count=0;

    Board()
    {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));
        setFocusable(true);

        loadImages();
        initGame();
    }

    public void initGame()
    {
        dots=3;

        for(int i=0;i<dots;i++)
        {
            y[i]=50;
            x[i]= 50 - i*dot_size;
        }
        locateApple();

        timer=new Timer(140, this);
        timer.start();
    }

    public void locateApple()
    {
        int r=(int)(Math.random()* Random_Position);
        apple_x=r*dot_size;

        r=(int)(Math.random()* Random_Position);
        apple_y=r*dot_size;
    }

    public void loadImages()
    {
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icon/apple.png"));
        apple=i1.getImage();

        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icon/dot.png"));
        dot=i2.getImage();

        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icon/head.png"));
        head=i3.getImage();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g)
    {
        if(inGame)
        {
            g.drawImage(apple, apple_x, apple_y, this);
            for(int i=0;i<dots;i++)
            {
                if(i==0)
                {
                    g.drawImage(head,x[i],y[i],this);
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }

    public void gameOver(Graphics g)
    {
        String msg="Game Over";
        String score="Score="+count;
        Font font=new Font("SAN SERIF",Font.BOLD,14);
        //FontMetrics metrices=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, 100, 100);
        g.drawString(score, 100, 120);

        // JButton b1=new JButton("Restart");
        // b1.setBounds(150, 150, 20, 50);
        // b1.setBackground(Color.gray);
        // b1.setForeground(Color.BLACK);
        // add(b1);
        // b1.setFont(new Font("",Font.TYPE1_FONT,35));

        // JButton b2=new JButton("Exit");
        // b2.setBounds(50, 150, 20, 50);
        // b2.setBackground(Color.gray);
        // b2.setForeground(Color.BLACK);
        // add(b2);
        // b2.setFont(new Font("",Font.TYPE1_FONT,35));
       // b2.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae)
    {
        if(inGame)
        {
            checkApple();
            move();
            checkCollision();
        }
        repaint();
    }

    public void checkApple()
    {
        if((x[0]==apple_x) && y[0]==apple_y)
        {
            dots++;
            count++;
            locateApple();
        }
    }

    public void checkCollision()
    {
        for(int i=dots;i>0;i--)
        {
            if((i>4) && (x[0]==x[i]) && (y[0]==y[i]))
                inGame=false;
        }
        if(y[0]>=300)
            inGame=false;
        if(x[0]<0)
            inGame=false;
        if(x[0]>=300)
            inGame=false;
        if(y[0]<0)
            inGame=false;
        if(!inGame)
            timer.stop();

    }

    public void move()
    {
        for(int i=dots;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        if(leftDirection)
        {
            x[0]=x[0]-dot_size;
        }
        if(rightDirection)
        {
            x[0]=x[0]+dot_size;
        }
        if(upDirection)
        {
            y[0]=y[0]-dot_size;
        }
        if(downDirection)
        {
            y[0]=y[0]+dot_size;
        }
        
    }

    public class TAdapter extends KeyAdapter{

        public void keyPressed(KeyEvent e)
        {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && (!rightDirection))
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }

            if(key==KeyEvent.VK_RIGHT && (!leftDirection))
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }

            if(key==KeyEvent.VK_UP && (!downDirection))
            {
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }

            if(key==KeyEvent.VK_DOWN && (!upDirection))
            {
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }
    }
}

