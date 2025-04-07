import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.*;
import java.awt.event.*;

public class SceneManager {
    private TitleScreen mainApp;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private int sceneCounter = 0;

    public SceneManager(TitleScreen mainApp, JPanel cardPanel, CardLayout cardLayout) {
        this.mainApp = mainApp;
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        defineScenes();
    }

    public void startGame() {
        nextScene("Act1_Dialogue_0");
    }

    public void nextScene(String key) {
        cardLayout.show(cardPanel, key);
    }

    private void defineScenes() {
        createDialogueSeries("Act1_Dialogue_", 5, () -> nextScene("ChoiceScene1"));

        cardPanel.add(new ChoiceScene(mainApp, "images/scene1.png",
                new String[]{"Look Behind You", "Move Forward"},
                new Runnable[]{
                        () -> nextScene("ExtraAct1"),
                        () -> nextScene("Act2_Dialogue_0")
                }), "ChoiceScene1");

        createDialogueSeries("Act2_Dialogue_", 10, () -> nextScene("ChoiceScene2"));

        cardPanel.add(new ChoiceScene(mainApp, "images/scene2.png",
                new String[]{"Trust Him", "Don't Trust Him"},
                new Runnable[]{
                        () -> nextScene("Act3_Dialogue_0"),
                        () -> nextScene("ExtraAct2_Dialogue_0")
                }), "ChoiceScene2");

        createDialogueSeries("Act3_Dialogue_", 5, () -> nextScene("ChoiceScene3"));

        cardPanel.add(new ChoiceScene(mainApp, "images/scene3.png",
                new String[]{"Yell For Help", "Push The Man", "Follow The Light"},
                new Runnable[]{
                        () -> nextScene("Act3_Dialogue_0"),
                        () -> nextScene("ExtraAct2_Dialogue_0"),
                        () -> nextScene("Act4_Dialogue_0")
                }), "ChoiceScene3");

        createDialogueSeries("Act4_Dialogue_", 10, () -> nextScene("ChoiceScene4"));

        cardPanel.add(new ChoiceScene(mainApp, "images/scene4.png",
                new String[]{"Carry Him", "Leave Him Alone"},
                new Runnable[]{
                        () -> nextScene("Act5_Dialogue_0"),
                        () -> nextScene("ExtraAct2_Dialogue_0")
                }), "ChoiceScene4");

        createDialogueSeries("Act5_Dialogue_", 20, () -> nextScene("WinEndScene"));

        createDialogueSeries("ExtraAct2_Dialogue_", 5, () -> nextScene("ExtraAct2Choice"));

        cardPanel.add(new ChoiceScene(mainApp, "images/extraact2.png",
                new String[]{"Ask For Help", "Hide"},
                new Runnable[]{
                        () -> nextScene("EndSceneD"),
                        () -> nextScene("EndSceneP")
                }), "ExtraAct2Choice");

        // Win, Lose, Endings
        cardPanel.add(createEndScene("images/win_scene.png"), "WinEndScene");
        cardPanel.add(createEndScene("images/ending_d.png"), "EndSceneD");
        cardPanel.add(createEndScene("images/ending_p.png"), "EndSceneP");
    }

    private void createDialogueSeries(String baseKey, int count, Runnable nextSceneAction) {
        for (int i = 0; i < count; i++) {
            final int idx = i;
            Runnable next = (i == count - 1) ? nextSceneAction : () -> nextScene(baseKey + (idx + 1));
            GameScene scene = new GameScene(mainApp, "images/" + baseKey + idx + ".png", next);
            cardPanel.add(scene, baseKey + idx);
        }
    }

    private JPanel createEndScene(String imagePath) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel background = mainApp.createFullScreenBackground(imagePath);
        panel.add(background, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        mainApp.styleButton(backButton, Color.RED);
        backButton.addActionListener(e -> mainApp.showTitleScreen());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        buttonPanel.add(backButton);

        background.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}
