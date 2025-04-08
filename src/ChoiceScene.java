import javax.swing.*;
import java.awt.*;

public class ChoiceScene extends JPanel {
    public ChoiceScene(TitleScreen mainApp, String imagePath, String[] choices, Runnable[] actions) {
        setLayout(new BorderLayout());

        JLabel background = mainApp.createFullScreenBackground(imagePath);
        add(background, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(choices.length, 1, 10, 10));

        for (int i = 0; i < choices.length; i++)
        {
            JButton button = new JButton(choices[i]);
            mainApp.styleButton(button, Color.BLACK);
            int index = i;
            button.addActionListener(e -> actions[index].run());
            buttonPanel.add(button);
        }

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setOpaque(false);
        centerWrapper.add(buttonPanel);

        background.setLayout(new BorderLayout());
        background.add(centerWrapper, BorderLayout.SOUTH);
    }
}
