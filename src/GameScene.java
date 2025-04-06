import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameScene extends JPanel {
    private TitleScreen mainApp;
    private String imagePath;
    private Runnable nextAction;

    public GameScene(TitleScreen mainApp, String imagePath, Runnable nextAction) {
        this.mainApp = mainApp;
        this.imagePath = imagePath;
        this.nextAction = nextAction;
        setupScene();
    }

    private void setupScene() {
        setLayout(new BorderLayout());

        JLabel background = mainApp.createFullScreenBackground(imagePath);
        add(background, BorderLayout.CENTER);

        JLabel clickLabel = new JLabel("Click anywhere to continue", SwingConstants.CENTER);
        clickLabel.setFont(new Font("Arial", Font.BOLD, 30));
        clickLabel.setForeground(Color.WHITE);

        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setOpaque(false);
        overlayPanel.add(clickLabel, BorderLayout.SOUTH);

        background.setLayout(new BorderLayout());
        background.add(overlayPanel, BorderLayout.SOUTH);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextAction.run();
            }
        });
    }
}
