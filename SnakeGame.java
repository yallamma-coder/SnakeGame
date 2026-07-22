import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int UNIT = 20;
    static final int DELAY = 100;

    int[] x = new int[900];
    int[] y = new int[900];

    int body = 5;
    int appleX;
    int appleY;
    int score = 0;

    char direction = 'R';

    Timer timer;
    Random random = new Random();

    SnakeGame() {
        JFrame frame = new JFrame("Snake Game");

        frame.add(this);
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R') direction = 'L';
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L') direction = 'R';
                        break;
                    case KeyEvent.VK_UP:
                        if (direction != 'D') direction = 'U';
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != 'U') direction = 'D';
                        break;
                }
            }
        });

        setBackground(Color.BLACK);

        newApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    void newApple() {
        appleX = random.nextInt(WIDTH / UNIT) * UNIT;
        appleY = random.nextInt(HEIGHT / UNIT) * UNIT;
    }

    void move() {

        for (int i = body; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= UNIT;
                break;
            case 'D':
                y[0] += UNIT;
                break;
            case 'L':
                x[0] -= UNIT;
                break;
            case 'R':
                x[0] += UNIT;
                break;
        }
    }

    void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            body++;
            score++;
            newApple();
        }
    }

    void checkCollision() {

        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            timer.stop();
            JOptionPane.showMessageDialog(this,
                    "Game Over\nScore: " + score);
            System.exit(0);
        }

        for (int i = body; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                timer.stop();
                JOptionPane.showMessageDialog(this,
                        "Game Over\nScore: " + score);
                System.exit(0);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        move();
        checkApple();
        checkCollision();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Apple (Red)
        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, UNIT, UNIT);

        // Snake (Green)
        for (int i = 0; i < body; i++) {
            g.setColor(Color.GREEN);
            g.fillRect(x[i], y[i], UNIT, UNIT);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score : " + score, 10, 20);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame());
    }
}