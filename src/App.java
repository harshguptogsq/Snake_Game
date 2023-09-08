import javax.swing.*;

public class App extends JFrame{

    App()
    {
        super("Snake Game");
        add(new Board());
        pack();
        
        setLocationRelativeTo(null);
        setResizable(false);
    }
    public static void main(String[] args) throws Exception {
        new  App().setVisible(true);;
    }
}
