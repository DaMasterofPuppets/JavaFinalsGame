import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;

public class TitleScreen {

    private final AudioManager audioManager = AudioManager.getInstance();
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;
    private SceneManager sceneManager;

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void showGameOver() {
        cardLayout.show(cardPanel, "GameOver");
    }

    public void showTitleScreen() {
        audioManager.stop(); // Stop current music
        audioManager.playMusic("AUDIO.wav", true); // Play title music
        cardLayout.show(cardPanel, "Title");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TitleScreen().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Horror Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel titlePanel = createTitlePanel();
        JPanel creditsPanel = createCreditsPanel();
        JPanel howToPlayPanel = createHTPPanel();

        cardPanel.add(titlePanel, "Title");
        cardPanel.add(creditsPanel, "Credits");
        cardPanel.add(howToPlayPanel, "HowToPlay");

        gamePanel = new GamePanel(this);
        gameOverPanel = new GameOverPanel(this);
        cardPanel.add(gamePanel, "Game");
        cardPanel.add(gameOverPanel, "GameOver");

        // SceneManager setup
        sceneManager = new SceneManager(this, cardPanel, cardLayout); // ✅

        frame.setContentPane(cardPanel);
        frame.setVisible(true);

        audioManager.playMusic("AUDIO.wav", true); // Play title music initially
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel background = createFullScreenBackground("background.jpg");
        panel.add(background, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton startButton = new JButton("Start Game");
        JButton howToPlayButton = new JButton("How to Play");
        JButton creditsButton = new JButton("Credits");
        JButton exitButton = new JButton("Exit");

        styleButton(startButton, Color.BLACK);
        styleButton(howToPlayButton, Color.BLACK);
        styleButton(creditsButton, Color.BLACK);
        styleButton(exitButton, Color.RED);

        buttonPanel.add(startButton);
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);

        JPanel buttonWrapper = new JPanel(new GridBagLayout());
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonPanel);

        background.setLayout(new BorderLayout());
        background.add(buttonWrapper, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            audioManager.stop();
            audioManager.playMusic("game_music.wav", true);
            sceneManager.startGame(); // Start from Act 1 Scene 1
            cardLayout.show(cardPanel, "SceneManager");
        });

        howToPlayButton.addActionListener(e -> cardLayout.show(cardPanel, "HowToPlay"));
        creditsButton.addActionListener(e -> cardLayout.show(cardPanel, "Credits"));
        exitButton.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel createCreditsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel background = createFullScreenBackground("credits_background.png");
        panel.add(background, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton, Color.RED);
        backButton.addActionListener(e -> showTitleScreen());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        background.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHTPPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel background = createFullScreenBackground("howtoplay_background.png");
        panel.add(background, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton, Color.RED);
        backButton.addActionListener(e -> showTitleScreen());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        background.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static JLabel createFullScreenBackground(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height,
                Image.SCALE_SMOOTH
        );
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        label.setLayout(new BorderLayout());
        return label;
    }

    public void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(250, 60));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
}
