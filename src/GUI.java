import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class GUI extends JFrame {

    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private ButtonGroup endpointGroup;
    private Map<String, String> endpointMap;

    public GUI() {
        setTitle("JSON Use Case Viewer");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        inputTextArea = new JTextArea(15, 50);
        outputTextArea = new JTextArea(20, 50);
        outputTextArea.setEditable(false);

        JButton sendButton = new JButton("Send to Server");
        sendButton.addActionListener((ActionEvent e) -> sendJsonToServer());

        endpointMap = new LinkedHashMap<>();
        endpointMap.put("Count Steps", "http://127.0.0.1:8080/count_steps");
        endpointMap.put("Count Keywords", "http://127.0.0.1:8080/count_keywords");
        // endpointMap.put("Max Depth", "http://127.0.0.1:8080/max_depth");
        // endpointMap.put("Download", "http://127.0.0.1:8080/download");
        // endpointMap.put("Invalid steps", "http://127.0.0.1:8080/actor_action");

        endpointGroup = new ButtonGroup();
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Choose Endpoint"));

        boolean first = true;
        for (Map.Entry<String, String> entry : endpointMap.entrySet()) {
            JRadioButton button = new JRadioButton(entry.getKey());
            button.setActionCommand(entry.getValue());
            endpointGroup.add(button);
            radioPanel.add(button);
            if (first) {
                button.setSelected(true);
                first = false;
            }
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Paste JSON here:"), BorderLayout.NORTH);
        topPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JLabel("Server Response:"), BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(radioPanel, BorderLayout.NORTH);
        centerPanel.add(sendButton, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void sendJsonToServer() {
        String jsonInput = inputTextArea.getText();
        String selectedUrl = endpointGroup.getSelection().getActionCommand();
        outputTextArea.setText("Sending request to: " + selectedUrl);

        try {
            URL url = new URL(selectedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes("UTF-8"));
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line).append("\n");
                }
            }

            outputTextArea.setText(response.toString());
        } catch (Exception ex) {
            outputTextArea.setText("Error:\n" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
