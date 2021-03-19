import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;

public class EditCandidateView extends JPanel {

    private JButton add = new JButton("Add");
    private JButton remove = new JButton("Remove");
    private JButton print = new JButton("Print");



    private JLabel fNameLab = new JLabel("First Name:");
    private JLabel lNameLab = new JLabel("Last Name:");
    private JLabel addressLab = new JLabel("Address:");
    private JLabel cityRegionLab = new JLabel("City Region:");
    private JLabel partyLab = new JLabel("Party:");
    private JLabel constituencyLab = new JLabel("Constituency:");

    private JLabel printLab = new JLabel("Print Candidates:");

    private JTextField fNameField = new JTextField();
    private JTextField lNameField = new JTextField();
    private JTextField addressField = new JTextField();
    private JTextField cityRegionField = new JTextField();
    private JTextField partyField = new JTextField();
    private JTextField constituencyField = new JTextField();

    private JTextArea printArea = new JTextArea();

    private JPanel basePanel = new JPanel();
    private JPanel menuPanel = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JPanel printPanel = new JPanel();



    public EditCandidateView()
    {
        //this.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        this.setSize(600, 600);
        //this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }


    public void init()
    {
        // adding buttons to the menu panel
        menuPanel.add(add);
        menuPanel.add(remove);
        menuPanel.add(print);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        // laying out the labels and input fields
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

        // make print area scrollable
        printPanel.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(printArea);
        printPanel.add(printLab, BorderLayout.NORTH);
        printPanel.add(scroll, BorderLayout.CENTER);


        this.add(menuPanel);
        this.add(mainPanel);
        this.add(printPanel);


        this.setVisible(true);
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

    public JButton getPrint() {
        return print;
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
        return printLab;
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

    public JTextArea getPrintArea() {
        return printArea;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getPrintPanel() {
        return printPanel;
    }

}
