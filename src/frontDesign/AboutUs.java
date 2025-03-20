package frontDesign;

import javax.swing.*;
import java.awt.*;

public class AboutUs {
    private JPanel contentPanel;

    public AboutUs(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public void createPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        try {
            String htmlContent = """
                    <html>
                        <head>
                            <style>
                                body {
                                    font-family: Roboto;
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

                            <p>Our aim is to propose a management system, providing features for creating and categorizing recipes, searching for dishes, and picking meals.</p>

                            <p>FoodIO is built on Java and focuses on efficient food handling, reverse search, and ingredient-based recognition.</p>

                            <p>Developed by our team of Ten & Skynoxk, we strive to ease your daily routines.</p>

                        </body>
                    </html>
                    """;
            editorPane.setText(htmlContent);

        } catch (Exception e) {
            System.err.println("HTML string error");
            ;
        }

        JScrollPane scrollPane = new JScrollPane(editorPane);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}