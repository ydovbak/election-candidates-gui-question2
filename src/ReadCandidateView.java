import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReadCandidateView extends JPanel {


    private JComboBox<String> electoralAreaComboBox;
    private JLabel constituencyLabel;
    private JButton search;
    private JTextArea dataDisplayArea;
    private JScrollPane scroll;

    public ReadCandidateView() {

        // search criteria combo box
        electoralAreaComboBox = new JComboBox<String>();
        constituencyLabel = new JLabel("Constituency");
        search = new JButton("Search");

        // all data displayed here
        dataDisplayArea = new JTextArea(30, 90);

        // make display area scrollable
        scroll = new JScrollPane(dataDisplayArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void init() {
        // set the layouts
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel optionsPanel = new JPanel();  //will contain first combo box and button
        optionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // add buttons and combo box to the options panel
        optionsPanel.add(constituencyLabel);
        optionsPanel.add(electoralAreaComboBox);
        optionsPanel.add(search);

        // add everything to main content pane
        this.add(optionsPanel);
        this.add(scroll);
        this.setVisible(true);
    }

    public void showRecords(ArrayList<CandidateModel> candidates) {

        String textToPrint = "";
        for (CandidateModel c : candidates) {
            textToPrint += c + "\n";
        }

        dataDisplayArea.setText(textToPrint);
        dataDisplayArea.setCaretPosition(0); // display at start
    }


    public JComboBox<String> getElectoralAreaComboBox() {
        return electoralAreaComboBox;
    }

    public JLabel getConstituencyLabel() {
        return constituencyLabel;
    }

    public JButton getSearch() {
        return search;
    }

    public JTextArea getDataDisplayArea() {
        return dataDisplayArea;
    }

}
