import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileMover extends JFrame implements ActionListener {
    private JLabel sourceLabel;
    private JLabel destinationLabel;
    private JTextField sourceTextField;
    private JTextField destinationTextField;
    private JButton browseSourceButton;
    private JButton browseDestinationButton;
    private JButton moveButton;

    public FileMover() {
        setTitle("File Mover");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);

        // Create UI components
        sourceLabel = new JLabel("Source:");
        destinationLabel = new JLabel("Destination:");
        sourceTextField = new JTextField(30);
        destinationTextField = new JTextField(30);
        browseSourceButton = new JButton("Browse");
        browseSourceButton.addActionListener(this);
        browseDestinationButton = new JButton("Browse");
        browseDestinationButton.addActionListener(this);
        moveButton = new JButton("Move");
        moveButton.addActionListener(this);

        // Add UI components to content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3, 2));
        contentPane.add(sourceLabel);
        contentPane.add(sourceTextField);
        contentPane.add(browseSourceButton);
        contentPane.add(destinationLabel);
        contentPane.add(destinationTextField);
        contentPane.add(browseDestinationButton);
        contentPane.add(new JLabel());
        contentPane.add(moveButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseSourceButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                sourceTextField.setText(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == browseDestinationButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                destinationTextField.setText(selectedDirectory.getAbsolutePath());
            }
        } else if (e.getSource() == moveButton) {
            String sourcePathString = sourceTextField.getText();
            String destinationPathString = destinationTextField.getText();
            if (!sourcePathString.isEmpty() && !destinationPathString.isEmpty()) {
                Path sourcePath = Paths.get(sourcePathString);
                Path destinationPath = Paths.get(destinationPathString, sourcePath.getFileName().toString());
                try {
                    Files.move(sourcePath, destinationPath);
                    JOptionPane.showMessageDialog(this, "File moved successfully.");
                    sourceTextField.setText("");
                    destinationTextField.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error moving file: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select both source and destination paths.");
            }
        }
    }

    public static void main(String[] args) {
        FileMover fileMover = new FileMover();
        fileMover.setVisible(true);
    }
}
