package frontDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class ChatBot {
    private JPanel contentPanel;
    private JTextArea chatArea;
    private JTextField inputField;
    private static final String API_KEY = "lol";
    private static final String API_URL = "lol" + API_KEY;

    public ChatBot(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public void createPanel() {
        contentPanel.removeAll();
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("FoodIO AI ChatBot", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        chatPanel.add(titleLabel, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        
        sendButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = inputField.getText().trim();
       if (!userInput.isEmpty()) {
            chatArea.append("You: " + "by crawling this https://github.com/Ten-IO/Receipt-Organizer/blob/main/src/files/localFood.txt" + userInput + "\n");
            String response = getGeminiResponse(userInput);
            chatArea.append("Gemini: " + response + "\n\n");
            inputField.setText("");
        }
    }
    });

        
        contentPanel.add(chatPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    private static String getGeminiResponse(String prompt) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt + "\" }] }] }";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();
            
            return extractTextFromResponse(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private static String extractTextFromResponse(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray candidates = jsonObject.getJSONArray("candidates");
            if (candidates.length() > 0) {
                JSONObject firstCandidate = candidates.getJSONObject(0);
                JSONArray parts = firstCandidate.getJSONObject("content").getJSONArray("parts");
                if (parts.length() > 0) {
                    return parts.getJSONObject(0).getString("text");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No response text found.";
    }
}
