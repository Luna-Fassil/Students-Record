
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GradesForm extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private String currentFilename = null;

    public GradesForm(JFrame frame) {
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"SID", "Assignments", "Midterm", "Final Exam", "Final Mark", "Letter Grade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(220, 220, 220);
                Color whiteColor = Color.WHITE;
                if (!comp.getBackground().equals(getSelectionBackground())) {
                    comp.setBackground(row % 2 == 0 ? alternateColor : whiteColor);
                }
                return comp;
            }
        };
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom form for adding records
        JPanel inputPanel = new JPanel(new GridLayout(1, 5));
        JTextField idField = new JTextField();
        JTextField assignmentsField = new JTextField();
        JTextField midtermField = new JTextField();
        JTextField finalExamField = new JTextField();
        JButton addButton = new JButton("Add");

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Assignments:"));
        inputPanel.add(assignmentsField);
        inputPanel.add(new JLabel("Midterm:"));
        inputPanel.add(midtermField);
        inputPanel.add(new JLabel("Final Exam:"));
        inputPanel.add(finalExamField);
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                String id = idField.getText();
                float assignments = Float.parseFloat(assignmentsField.getText());
                float midterm = Float.parseFloat(midtermField.getText());
                float finalExam = Float.parseFloat(finalExamField.getText());
                StudentRecord student = new StudentRecord(id, assignments, midterm, finalExam);
                tableModel.addRow(new Object[]{id, assignments, midterm, finalExam, student.getFinalGrade(), student.getLetterGrade()});
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        newItem.addActionListener(e -> {
            tableModel.setRowCount(0);
            currentFilename = null;
        });

        openItem.addActionListener(e -> openFile(frame));
        saveItem.addActionListener(e -> saveFile(frame, false));
        saveAsItem.addActionListener(e -> saveFile(frame, true));
        exitItem.addActionListener(e -> System.exit(0));
    }

    private void openFile(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            currentFilename = file.getAbsolutePath();
            tableModel.setRowCount(0);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    tableModel.addRow(data);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error loading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile(JFrame frame, boolean saveAs) {
        if (currentFilename == null || saveAs) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                currentFilename = fileChooser.getSelectedFile().getAbsolutePath();
            } else {
                return;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(currentFilename))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                List<String> row = new ArrayList<>();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    row.add(tableModel.getValueAt(i, j).toString());
                }
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}