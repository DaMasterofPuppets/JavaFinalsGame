import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;

public class TitleScreen
{
    private JFrame frame;
    private JPanel CardPanel;
    private CardLayout CardLayout;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new TitleScreen().CreateAndShowGUI());
    }

    private void CreateAndShowGUI()
    {
        frame = new JFrame("Horror Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);

        CardLayout = new CardLayout();
        CardPanel = new JPanel(CardLayout);

        JPanel titlePanel = CreateTitlePanel();
        JPanel creditsPanel = CreateCreditsPanel();
        JPanel howToPlayPanel = CreateHTPPanel();

        CardPanel.add(titlePanel, "Title");
        CardPanel.add(creditsPanel, "Credits");
        CardPanel.add(howToPlayPanel, "HowToPlay");

        frame.setContentPane(CardPanel);
        frame.setVisible(true);

        BackgroundMusicTitle("AUDIO.wav");
    }

    private JPanel CreateTitlePanel()
    {
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

        ButtonStyles(startButton, Color.BLACK);
        ButtonStyles(howToPlayButton, Color.BLACK);
        ButtonStyles(creditsButton, Color.BLACK);
        ButtonStyles(exitButton, Color.RED);

        buttonPanel.add(startButton);
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);

        JPanel buttonWrapper = new JPanel(new GridBagLayout());
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(buttonPanel);

        background.setLayout(new BorderLayout());
        background.add(buttonWrapper, BorderLayout.CENTER);

        startButton.addActionListener(e -> CardLayout.show(CardPanel, "Title"));
        howToPlayButton.addActionListener(e -> CardLayout.show(CardPanel, "HowToPlay"));
        creditsButton.addActionListener(e -> CardLayout.show(CardPanel, "Credits"));
        exitButton.addActionListener(e -> System.exit(0));

        return panel;
    }

    private JPanel CreateCreditsPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel background = createFullScreenBackground("credits_background.png");
        panel.add(background, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        ButtonStyles(backButton, Color.RED);
        backButton.addActionListener(e -> CardLayout.show(CardPanel, "Title"));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        buttonPanel.add(backButton);
        background.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel CreateHTPPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel background = createFullScreenBackground("howtoplay_background.png");
        panel.add(background, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        ButtonStyles(backButton, Color.RED);
        backButton.addActionListener(e -> CardLayout.show(CardPanel, "Title"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        buttonPanel.add(backButton);
        background.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel createFullScreenBackground(String imagePath)
    {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        label.setLayout(new BorderLayout());
        return label;
    }

    private void ButtonStyles(JButton button, Color color)
    {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(250, 60));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void BackgroundMusicTitle(String musicPath)
    {
        try
        {
            File soundFile = new File(musicPath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
