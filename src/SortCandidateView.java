import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.awt.ComponentOrientation.LEFT_TO_RIGHT;

public class SortCandidateView extends JPanel {

    private JComboBox<String> partyComboBox;
    private JComboBox<String> sortComboBox;

    private JLabel partyLabel;
    private JLabel sortByLabel;
    private JButton search;
    private JButton sort;

    private ButtonGroup group;
    private JRadioButton ascRButton;
    private JRadioButton descRButton;

    private JTextArea dataDisplayArea;
    private JScrollPane scroll;

    // local copy of records displayed on the panel
    private ArrayList<CandidateModel> displayedCandidates;

    public SortCandidateView() {
        partyComboBox = new JComboBox<String>();
        sortComboBox = new JComboBox<String>();
        partyLabel = new JLabel("Party");
        sortByLabel = new JLabel(" |      Sort By");
        search = new JButton("Search");
        sort = new JButton("Sort");
        ascRButton = new JRadioButton("Ascending");
        descRButton = new JRadioButton("Descending");

        // only one radio button can be selected, if inside the group
        group = new ButtonGroup();

        // all data displayed here
        dataDisplayArea = new JTextArea(30, 90);

        displayedCandidates = new ArrayList<>();

        // make display area scrollable
        scroll = new JScrollPane(dataDisplayArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
