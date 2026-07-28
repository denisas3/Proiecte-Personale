package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainWindow extends JFrame {

    private ClientCtrl ctrl;

    private JTable allShowsTable;
    private JTable filteredShowsTable;
    private JTable ticketsTable;

    private JTextField buyerNameField;
    private JTextField seatCountField;
    private JTextField newSeatCountField;

    private JButton searchButton;
    private JButton buyButton;
    private JButton loadTicketsButton;
    private JButton updateTicketButton;
    private JButton logoutButton;

    private JComboBox<String> showComboBox;

    public MainWindow(String title,     ClientCtrl ctrl) {
        super(title);
        this.ctrl = ctrl;

        getContentPane().add(createMainPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(new JLabel("Welcome!"));
        main.add(Box.createVerticalStrut(10));

        main.add(new JLabel("Toate spectacolele"));
        allShowsTable = new JTable(new DefaultTableModel(
                new Object[]{"Artist", "Data", "Locatie", "Locuri disponibile", "Locuri vandute"}, 0
        ));
        main.add(new JScrollPane(allShowsTable));

        main.add(Box.createVerticalStrut(10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JTextField(15));
        searchButton = new JButton("Cauta");
        searchPanel.add(searchButton);
        main.add(searchPanel);

        main.add(new JLabel("Rezultate cautare"));
        filteredShowsTable = new JTable(new DefaultTableModel(
                new Object[]{"Artist", "Locatie", "Ora", "Locuri disponibile"}, 0
        ));
        main.add(new JScrollPane(filteredShowsTable));

        main.add(Box.createVerticalStrut(10));

        main.add(new JLabel("Cumparare bilet"));
        JPanel buyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buyerNameField = new JTextField(15);
        buyerNameField.setBorder(BorderFactory.createTitledBorder("Nume cumparator"));
        seatCountField = new JTextField(10);
        seatCountField.setBorder(BorderFactory.createTitledBorder("Nr locuri"));
        buyButton = new JButton("Cumpara");

        buyPanel.add(buyerNameField);
        buyPanel.add(seatCountField);
        buyPanel.add(buyButton);
        main.add(buyPanel);

        main.add(Box.createVerticalStrut(10));

        main.add(new JLabel("Modificare bilet"));
        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        showComboBox = new JComboBox<>();
        showComboBox.setPreferredSize(new Dimension(350, 25));
        loadTicketsButton = new JButton("Incarca bilete");
        comboPanel.add(showComboBox);
        comboPanel.add(loadTicketsButton);
        main.add(comboPanel);

        ticketsTable = new JTable(new DefaultTableModel(
                new Object[]{"ID bilet", "Cumparator", "Nr locuri"}, 0
        ));
        main.add(new JScrollPane(ticketsTable));

        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newSeatCountField = new JTextField(12);
        newSeatCountField.setBorder(BorderFactory.createTitledBorder("Noul nr locuri"));
        updateTicketButton = new JButton("Modifica bilet selectat");
        updatePanel.add(newSeatCountField);
        updatePanel.add(updateTicketButton);
        main.add(updatePanel);

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutButton = new JButton("Logout");
        logoutPanel.add(logoutButton);
        main.add(logoutPanel);

        return main;
    }
}