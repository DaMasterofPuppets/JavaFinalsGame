import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    public GameOverPanel(TitleScreen mainApp) {
        setLayout(new BorderLayout());

        JLabel background = new JLabel(new ImageIcon("gameover_background.jpg")); // your custom image
        background.setLayout(new BorderLayout());
        add(background, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        mainApp.styleButton(backButton, Color.RED);
        backButton.addActionListener(e -> mainApp.showTitleScreen());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        background.add(buttonPanel, BorderLayout.SOUTH);
    }
}
