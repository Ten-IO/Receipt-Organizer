package frontDesign;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Identity;

import javax.swing.*;
import org.json.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chatBot {
    private static final String API_KEY = "AIzaSyCTvaj10eb2OtYD-CeYV2njL7kozqyo6_U";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
    private JPanel chatPanel;
    private JTextArea chatArea;
    private JTextField inputField;

    public chatBot(JPanel contentPanel) {
        contentChat(contentPanel);
    }

    private void contentChat(JPanel contentPanel) {
    	contentPanel.removeAll();
        chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Gemini AI ChatBot", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText().trim();
                if (!userInput.isEmpty()) {
                    chatArea.append("You: " + userInput + "\n");
                    String response = getGeminiResponse(userInput);
                    String[] array = response.split("\"");
                    int i = 0;
                    for (String x : array) {
                    	if ( i == 9 ) {
	                    	chatArea.append("Gemini: " + x.replaceAll("\n", " ") );
	                    	System.out.println(x);
	                    	inputField.setText("");
                    	}
                    	i++;
                    }
                }
            }
        });

        chatPanel.add(titlePanel, BorderLayout.NORTH);
        chatPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        contentPanel.add(chatPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private String getGeminiResponse(String prompt) {
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

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}