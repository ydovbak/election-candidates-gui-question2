import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.ComponentOrientation.LEFT_TO_RIGHT;

public class SortCandidateView extends JPanel{

    private JComboBox<String> partyComboBox = new JComboBox<String>();
    private JComboBox<String> sortComboBox = new JComboBox<String>();
    private JLabel partyLabel = new JLabel( "Party");
    private JLabel sortByLabel = new JLabel( " |      Sort By");
    private JButton search = new JButton("Search");
    private JButton sort = new JButton("Sort");
    private JRadioButton ascRButton = new JRadioButton("Ascending");
    private JRadioButton descRButton = new JRadioButton("Descending");
    ButtonGroup group = new ButtonGroup();      // only one radio button can be selected, if inside the group

    // all data displayed here
    private JTextArea dataDisplayArea = new JTextArea(30, 90);

    private ArrayList<CandidateModel> displayedCandidates = new ArrayList<>();

    // make display area scrollable
    JScrollPane scroll = new JScrollPane (dataDisplayArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


    public SortCandidateView() {
        //this.setSize(1200, 650);
    }

    public void init() {
        // populate sorting combo box
        sortComboBox.addItem("Id");
        sortComboBox.addItem("First Name");
        sortComboBox.addItem("Last Name");
        sortComboBox.addItem("Address");
        sortComboBox.addItem("Party");
        sortComboBox.addItem("Constituency");
        // set the layouts
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        // combo box panel
        JPanel comboBoxPanel = new JPanel();  //will contain first combo box and button
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.add(partyLabel);
        comboBoxPanel.add(partyComboBox);
        comboBoxPanel.add(search);

        JPanel sortingPanel = new JPanel();
        sortingPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        ascRButton.setSelected(true);
        group.add(ascRButton);
        group.add(descRButton);
        sortingPanel.add(sortByLabel);
        sortingPanel.add(sortComboBox);
        sortingPanel.add(ascRButton);
        sortingPanel.add(descRButton);
        sortingPanel.add(sort);


        // add everything to main content pane
        this.add(comboBoxPanel);
        this.add(sortingPanel);
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


    public JComboBox<String> getPartyComboBox() {
        return partyComboBox;
    }

    public JLabel getPartyLabel() {
        return partyLabel;
    }

    public JButton getSearch() {
        return search;
    }

    public JTextArea getDataDisplayArea() {
        return dataDisplayArea;
    }

    public JComboBox<String> getSortComboBox() {
        return sortComboBox;
    }

    public JLabel getSortByLabel() {
        return sortByLabel;
    }

    public JButton getSort() {
        return sort;
    }

    public JRadioButton getAscRButton() {
        return ascRButton;
    }

    public JRadioButton getDescRButton() {
        return descRButton;
    }

    public JScrollPane getScroll() {
        return scroll;
    }

    public void setDisplayedCandidates(ArrayList<CandidateModel> candidates) {
        this.displayedCandidates = candidates;
    }

    public ArrayList<CandidateModel> getDisplayedCandidates() {
        return displayedCandidates;
    }
}
