package frontDesign;

import javax.swing.*;
import java.awt.*;

public class AboutUs {

    public AboutUs(JPanel contentPanel) {
        createAboutPanel(contentPanel);
    }

    private void createAboutPanel(JPanel contentPanel) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        try {
            String htmlContent = """
        <html>
        <head>
        <title>About FoodIO</title>
        <style>
        body {
            font-family: sans-serif;
            margin: 20px;
        }
        h1 {
            color: #336699;
        }
        p {
            line-height: 1.6;
        }
        </style>
        </head>
        <body>

        <h1>About FoodIO</h1>

        <p>Welcome to FoodIO, your user-friendly food suggestion and collection system.</p>

        <p>Our aim is to simplify food management, providing features for creating and categorizing recipes, searching for dishes, and planning meals.</p>

        <p>FoodIO is built on Java and focuses on efficient food handling, reverse search, and ingredient-based recognition.</p>

        <p>Developed by a team including Skynoxk, we strive to enhance your culinary experience.</p>

        </body>
        </html>
        """;
            editorPane.setText(htmlContent);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}