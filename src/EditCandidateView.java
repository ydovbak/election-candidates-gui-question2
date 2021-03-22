import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.security.InvalidParameterException;

public class EditCandidateView extends JPanel {

    private JButton find = new JButton("Find Candidate");
    private JButton add = new JButton("Add New Candidate");
    private JButton remove = new JButton("Remove");
    private JButton save = new JButton("Save Edit");
    private JButton confirm = new JButton("Confirm Add");

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
    private int currId;

    public void init() {
        menuPanel.add(find);
        menuPanel.add(add);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(warningLab);

        // set color of the warning message
        warningLab.setForeground(new Color(128, 0, 0));

        this.add(menuPanel);
        this.add(mainPanel);
        this.setVisible(true);
    }

    /**
     * Removing all labels and input fields, leaving only two options
     * available "Find Candidate" and "Add New Candidate"
     */
    public void setMainMenuButtons() {
        menuPanel.remove(confirm);
        menuPanel.remove(save);
        menuPanel.remove(remove);
        menuPanel.remove(confirm);
        mainPanel.remove(idLab);
        mainPanel.remove(fNameLab);
        mainPanel.remove(fNameField);
        mainPanel.remove(lNameLab);
        mainPanel.remove(lNameField);
        mainPanel.remove(addressLab);
        mainPanel.remove(addressField);
        mainPanel.remove(cityRegionLab);
        mainPanel.remove(cityRegionField);
        mainPanel.remove(partyLab);
        mainPanel.remove(partyField);
        mainPanel.remove(constituencyLab);
        mainPanel.remove(constituencyField);
    }

    /**
     * Showing buttons, labels and input fields after
     * "Find Candidate" button clicked
     */
    public void findCandidateButtons() {
        menuPanel.remove(confirm);
        menuPanel.add(add);
        menuPanel.add(remove);
        menuPanel.add(save);
        addInputsAndLabels();
    }

    /**
     * Showing buttons, labels and input fields after
     * "Add New Candidate" button clicked
     */
    public void showAddNewCandidatesButtons() {
        menuPanel.remove(remove);
        menuPanel.remove(save);
        menuPanel.add(add);
        menuPanel.add(confirm);
        addInputsAndLabels();
    }

    /**
     * Showing all inputs and labels
     */
    public void addInputsAndLabels() {
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
    }


    /**
     * Method is initialising the edit fields with data from candidate model
     *
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
     * Method is checking if all input fields are not empty
     *
     * @throws InvalidParameterException is thrown if one of the input fields is empty
     */
    public void validateInputs() throws InvalidParameterException {
        if (fNameField.getText().length() == 0) {
            throw new InvalidParameterException("First name field cannot be empty");
        } else if (lNameField.getText().length() == 0) {
            throw new InvalidParameterException("Last name field cannot be empty");
        } else if (addressField.getText().length() == 0) {
            throw new InvalidParameterException("Address field cannot be empty");
        } else if (cityRegionField.getText().length() == 0) {
            throw new InvalidParameterException("City region field cannot be empty");
        } else if (partyField.getText().length() == 0) {
            throw new InvalidParameterException("Party field cannot be empty");
        } else if (constituencyField.getText().length() == 0) {
            throw new InvalidParameterException("Constituency field cannot be empty");
        }
    }

    /**
     * Clear all input fields
     */
    public void resetInputs() {
        idLab.setText("ID: ");
        fNameField.setText("");
        lNameField.setText("");
        addressField.setText("");
        cityRegionField.setText("");
        partyField.setText("");
        constituencyField.setText("");
    }

    /**
     * Shows the dialog that confirms if user wants to delete the record or not
     *
     * @param candidate object that may be deleted
     * @return true if user slicks "Yes", false if "No"
     */
    public boolean showConfirmDialog(CandidateModel candidate) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this candidate: \n" + candidate,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        } else if (result == JOptionPane.NO_OPTION) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Shows the dialog that confirms if user wants to do something
     *
     * @return true if user slicks "Yes", false if "No"
     */
    public boolean showConfirmDialog(String msg) {
        int result = JOptionPane.showConfirmDialog(this,
                msg, "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        } else if (result == JOptionPane.NO_OPTION) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * Shows a notification dialog that sais that an action was done successfully/not successfully
     */
    public void showDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * Method is displaying the error message on the screen
     *
     * @param errMsg the message that will be shown
     */
    public void showMessage(String errMsg) {
        warningLab.setText(errMsg);
    }

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

    public int getCurrId() {
        return currId;
    }

    public JButton getConfirm() {
        return confirm;
    }

    public void setCurrId(int id) {
        this.currId = id;
    }
}
