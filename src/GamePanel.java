import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel
{
    private TitleScreen mainApp;
    private int timeLeft = 300;
    private JLabel timerLabel;

    public GamePanel(TitleScreen mainApp)
    {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        timerLabel = new JLabel("05:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setOpaque(true);
        timerLabel.setBackground(new Color(0, 0, 0, 150));
        timerLabel.setPreferredSize(new Dimension(100, 40));
        add(timerLabel, BorderLayout.NORTH);

        startCountdown();
    }

    private void startCountdown()
    {
        Timer timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int minutes = timeLeft / 60;
                int seconds = timeLeft % 60;
                timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                if (timeLeft <= 0)
                {
                    ((Timer) e.getSource()).stop();
                    mainApp.showGameOver();
                } else
                {
                    timeLeft--;
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
}
