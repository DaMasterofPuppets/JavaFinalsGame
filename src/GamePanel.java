import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel
{

    private final AudioManager audioManager = AudioManager.getInstance();

    private TitleScreen mainApp;
    private JLabel timerLabel;
    private Timer countdownTimer;
    private int timeLeft = 30; // seconds

    public GamePanel(TitleScreen mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        // Timer Label
        timerLabel = new JLabel("Time left: " + timeLeft + "s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        timerLabel.setForeground(Color.RED);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timerLabel, BorderLayout.NORTH);

        // Scene Image
        JLabel sceneImage = new JLabel(new ImageIcon("scene1.png")); // replace with your own image/gif
        sceneImage.setLayout(new BorderLayout());
        add(sceneImage, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setOpaque(false);


        JButton choice1 = new JButton("Choice 1");
        JButton choice2 = new JButton("Choice 2");
        mainApp.styleButton(choice1, Color.BLACK);
        mainApp.styleButton(choice2, Color.BLACK);

        buttonPanel.add(choice1);
        buttonPanel.add(choice2);

        sceneImage.add(buttonPanel, BorderLayout.SOUTH);

        // Choice button actions
        choice1.addActionListener(e -> {
            stopTimer();
            // TODO: Add transition to another scene
            JOptionPane.showMessageDialog(mainApp.getFrame(), "Next Scene: You chose 1!");
        });

        choice2.addActionListener(e -> {
            stopTimer();
            mainApp.showGameOver(); // Simulate bad choice = game over
        });

        startTimer();
    }

    private void startTimer()
    {
        countdownTimer = new Timer();
        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft + "s");
                    if (timeLeft <= 0)
                    {
                        stopTimer();
                        mainApp.showGameOver();
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
    }
}
