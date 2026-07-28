package gui;

import org.example.lab03.services.LabException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, clearButton;
    private ClientCtrl ctrl;

    public LoginWindow(String title, ClientCtrl ctrl) {
        super(title);
        this.ctrl = ctrl;
        getContentPane().add(createLoginPanel());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        // Username
        JPanel line1 = new JPanel();
        line1.add(new JLabel("Username:"));
        line1.add(usernameField = new JTextField(15));
        panel.add(line1);

        // Password
        JPanel line2 = new JPanel();
        line2.add(new JLabel("Password:"));
        line2.add(passwordField = new JPasswordField(15));
        panel.add(line2);

        // Buttons
        JPanel line3 = new JPanel();
        line3.add(loginButton = new JButton("Login"));
        line3.add(clearButton = new JButton("Clear"));

        ActionListener listener = new ButtonListener();
        loginButton.addActionListener(listener);
        clearButton.addActionListener(listener);

        panel.add(line3);

        return panel;
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                System.out.println("Login button pressed");

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    ctrl.login(username, password);

                    // deschizi fereastra principală
                    MainWindow main = new MainWindow("Main window - " + username, ctrl);
                    main.setSize(600, 400);
                    main.setLocation(200, 200);
                    main.setVisible(true);

                    // închizi login-ul
                    LoginWindow.this.dispose();

                } catch (LabException ex) {
                    JOptionPane.showMessageDialog(
                            LoginWindow.this,
                            "Login failed: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } else {
                // Clear
                usernameField.setText("");
                passwordField.setText("");
            }
        }
    }
}
