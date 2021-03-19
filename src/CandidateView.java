import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CandidateView extends JPanel{

    // search criteria
    private JComboBox<String> electoralAreaComboBox = new JComboBox<String>();
    private JLabel constituencyLabel = new JLabel( "Constituency");
    private JButton search = new JButton("Search");

    // all data displayed here
    private JTextArea dataDisplayArea = new JTextArea(40, 100);

    // make display area scrollable
    JScrollPane scroll = new JScrollPane (dataDisplayArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    public CandidateView() {
        //this.contentPane = contentPane;
        //this.setTitle("Elections Registry");
        this.setSize(1200, 650);
        //this.setResizable(false);
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
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
