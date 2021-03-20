import javax.swing.*;
import java.awt.*;

public class EditCandidateView extends JPanel {

    private JButton find = new JButton("Find Candidate");
    private JButton add = new JButton("Add New Candidate");
    private JButton remove = new JButton("Remove");
    private JButton save = new JButton("Save");

    private JLabel idLab = new JLabel("ID: ");
    private JLabel fNameLab = new JLabel("First Name:");
    private JLabel lNameLab = new JLabel("Last Name:");
    private JLabel addressLab = new JLabel("Address:");
    private JLabel cityRegionLab = new JLabel("City Region:");
    private JLabel partyLab = new JLabel("Party:");
    private JLabel constituencyLab = new JLabel("Constituency:");
    private JLabel warningLab = new JLabel();

    private JTextField fNameField = new JTextField();
    private JTextField lNameField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField cityRegionField = new JTextField();
    private JTextField partyField = new JTextField();
    private JTextField constituencyField = new JTextField();

    private JPanel menuPanel = new JPanel();
    private JPanel mainPanel = new JPanel();

    // temporary holder variables
    private String currId;



    public EditCandidateView()
    {
        //this.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        //this.setSize(600, 500);
        //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }


    public void init()
    {
        // adding buttons to the menu panel
//        menuPanel.add(add);
//        menuPanel.add(remove);
//        menuPanel.add(print);
//
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
//
//        // laying out the labels and input fields
//        mainPanel.add(fNameLab);
//        mainPanel.add(fNameField);
//        mainPanel.add(lNameLab);
//        mainPanel.add(lNameField);
//        mainPanel.add(addressLab);
//        mainPanel.add(addressField);
//        mainPanel.add(cityRegionLab);
//        mainPanel.add(cityRegionField);
//        mainPanel.add(partyLab);
//        mainPanel.add(partyField);
//        mainPanel.add(constituencyLab);
//        mainPanel.add(constituencyField);
//
//        // make print area scrollable
////        printPanel.setLayout(new BorderLayout());
////        JScrollPane scroll = new JScrollPane(printArea);
////        printPanel.add(printLab, BorderLayout.NORTH);
////        printPanel.add(scroll, BorderLayout.CENTER);
//
//
//        this.add(menuPanel);
//        this.add(mainPanel);

        // at the beginning you can only find candidate or add new one

        menuPanel.add(find);
        menuPanel.add(add);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(warningLab);
        warningLab.setForeground(new Color(128,0,0));
        this.add(menuPanel);
        this.add(mainPanel);

        this.setVisible(true);
    }

    public void addNewCandidatesButtons() {

        menuPanel.add(remove);
        menuPanel.add(save);

        // laying out the labels and input fields
        mainPanel.add(idLab);
        mainPanel.add(fNameLab);
        mainPanel.add(fNameField);
        mainPanel.add(lNameLab);
        mainPanel.add(lNameField);
        mainPanel.add(addressLab);
        mainPanel.add(addressField);
        mainPanel.add(cityRegionLab);
        mainPanel.add(cityRegionField);
        mainPanel.add(partyLab);
        mainPanel.add(partyField);
        mainPanel.add(constituencyLab);
        mainPanel.add(constituencyField);

        //this.add(mainPanel);
        //this.setVisible(true);
    }

    /**
     * Method is initialising the edit fields with data from candidate model
     * @param candidateModel data that will be displayed in the input fields
     */
    public void initEditFields(CandidateModel candidateModel) {
        idLab.setText("ID: " + candidateModel.getId());
        fNameField.setText(candidateModel.getFirstName());
        lNameField.setText(candidateModel.getLastName());
        addressField.setText(candidateModel.getAddress());
        cityRegionField.setText(candidateModel.getCityRegion());
        partyField.setText(candidateModel.getParty());
        constituencyField.setText(candidateModel.getElecArea());
    }

    /**
     * Method is displaying the error message on the screen
     * @param errMsg the message that will be shown
     */
    public void showError(String errMsg) {
        warningLab.setText(errMsg);
    }

//    public void setWindowsListener(WindowListener w)
//    {
//        this.addWindowListener(w);
//    }

    public JButton getAdd() {
        return add;
    }

    public JButton getRemove() {
        return remove;
    }

    public JButton getSave() {
        return save;
    }

    public JLabel getfNameLab() {
        return fNameLab;
    }

    public JLabel getlNameLab() {
        return lNameLab;
    }

    public JLabel getAddressLab() {
        return addressLab;
    }

    public JLabel getCityRegionLab() {
        return cityRegionLab;
    }

    public JLabel getPartyLab() {
        return partyLab;
    }

    public JLabel getConstituencyLab() {
        return constituencyLab;
    }

    public JLabel getPrintLab() {
        return warningLab;
    }

    public JTextField getfNameField() {
        return fNameField;
    }

    public JTextField getlNameField() {
        return lNameField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JTextField getCityRegionField() {
        return cityRegionField;
    }

    public JTextField getPartyField() {
        return partyField;
    }

    public JTextField getConstituencyField() {
        return constituencyField;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getFind() {
        return find;
    }

    public JLabel getWarningLab() {
        return warningLab;
    }

    public JLabel getIdLab() {
        return idLab;
    }

    public String getCurrId() {
        return currId;
    }
}
