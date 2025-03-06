package frontDesign;

import javax.swing.*;
import java.awt.*;

public class Tutorial {
    private JPanel contentPanel;

    public Tutorial(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public void createPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        String htmlContent = """
            <html>
                <head>
                    <title>FoodIO Tutorial</title>
                    <style>
                        body {
                            font-family: sans-serif;
                            margin: 20px;
                        }
                        h1, h2 {
                            color: #336699;
                        }
                        ol {
                            line-height: 1.6;
                        }
                        .step {
                            margin-bottom: 20px;
                        }
                        .tip {
                            background-color: #f0f0f0;
                            padding: 10px;
                            border-radius: 5px;
                            margin-top: 10px;
                        }
                    </style>
                </head>
                <body>
                    <h1>FoodIO Tutorial</h1>
                    <p>Welcome to FoodIO! This app simplifies food suggestion and management.</p>
                    <div class="step">
                        <h2>Step 1: Create Recipes</h2>
                        <ol>
                            <li>Navigate to "New Recipe".</li>
                            <li>Enter food name, category, ingredients, and instructions.</li>
                            <li>Add a photo.</li>
                            <li>Click "Submit".</li>
                        </ol>
                    </div>
                    <div class="step">
                        <h2>Step 2: Search Recipes</h2>
                        <ol>
                            <li>Use the search bar.</li>
                            <li>Enter ingredients.</li>
                            <li>View suggested recipes.</li>
                        </ol>
                    </div>
                    <div class="tip">
                        <h3>Tips:</h3>
                        <ul>
                            <li>Categorize recipes for easy access.</li>
                            <li>Use specific ingredients for better search results.</li>
                        </ul>
                    </div>
                </body>
            </html>
        """;

        editorPane.setText(htmlContent);
        JScrollPane scrollPane = new JScrollPane(editorPane);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
