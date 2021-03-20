import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CandidateController extends JFrame implements ActionListener, WindowListener {

    private ReadCandidateView readCandidateView;
    private EditCandidateView editCandidateView;
    private SortCandidateView sortCandidateView;
    private WindowView windowView;
    private ArrayList<CandidateModel> candidates;
    private int numCandidates;

    public CandidateController() {

        candidates = new ArrayList<>();
        //candidateView = new CandidateView();
        //candidateView.init();
        windowView = new WindowView();
        readCandidateView = windowView.getReadCandidates();
        editCandidateView = windowView.getEditCandidates();
        sortCandidateView = windowView.getSortCandidates();

        editCandidateView.init();
        readCandidateView.init();
        sortCandidateView.init();
        windowView.setWindowsListener(this);

        // hook the action listener to Search button
        readCandidateView.getSearch().addActionListener(this);
        editCandidateView.getFind().addActionListener(this);
        editCandidateView.getAdd().addActionListener(this);
        editCandidateView.getRemove().addActionListener(this);
        editCandidateView.getSave().addActionListener(this);
        editCandidateView.getConfirm().addActionListener(this);

        // create a file chooser for selecting files
        // that will be analysed and shown in program
        try {
            // initially set to null
            File selectedFile = new File("/Users/yuliiadovbak/Desktop/dcccandidatesforlocalelection2009p20120822-1410.csv");
//
//            // create file chooser for browsing local files
//            JFileChooser fileChooser = new JFileChooser();
//
//            // set the directory where the file chooser will be opened
//            fileChooser.setCurrentDirectory(new File(".\\"));
//
//            // if user chooses the file and clicks "ok", we get the selected file
//            if (fileChooser.showOpenDialog(getContentPane()) == JFileChooser.APPROVE_OPTION)
//            {
//                selectedFile = fileChooser.getSelectedFile();
//            }
//            System.out.println(selectedFile.getAbsolutePath());
            // reading in the file
            Scanner in = new Scanner(new FileInputStream(selectedFile));

            // the fist line in csv contains headings, so skipping them
            if (in.hasNextLine()) {
                in.nextLine();
                in.nextLine();
            }

            // reading in every line in csv, converting them into Candidate objects
            while (in.hasNextLine()) {
                candidates.add(new CandidateModel(in.nextLine()));
            }

            // close the scanner
            in.close();
        } catch (Exception e) {
            // print the exception message
            e.printStackTrace();
        }

        // populate the combo box in Read and Sort views
        ArrayList<String> areas = new ArrayList<>();
        ArrayList<String> partys = new ArrayList<>();
        // add option that will include all
        readCandidateView.getElectoralAreaComboBox().addItem("ANY");
        sortCandidateView.getPartyComboBox().addItem("ANY");
        for (CandidateModel c : candidates) {
            String area = c.getElecArea();

            // adding only unique areas
            if (!areas.contains(area)) {
                areas.add(area);
                readCandidateView.getElectoralAreaComboBox().addItem(area);
            }

            String party = c.getParty();
            if (!partys.contains(party)) {
                partys.add(party);
                sortCandidateView.getPartyComboBox().addItem(party);
            }
        }

        // show all
        readCandidateView.showRecords(candidates);
        sortCandidateView.showRecords(candidates);
        sortCandidateView.setDisplayedCandidates(candidates);

        // save number of candidates
        // this variable will be used to create id for new candidates
        numCandidates = candidates.size();

        // "Sort" clicked
        sortCandidateView.getSort().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortCandidates();
            }
        });

        // "Search" clicked
        sortCandidateView.getSearch().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortSearchClicked();
            }
        });

        // "Ascending" radio button clicked
        sortCandidateView.getAscRButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortCandidates();
            }
        });

        // "Descending" radio button clicked
        sortCandidateView.getDescRButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortCandidates();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // reset the warning message
        editCandidateView.showMessage("");

        // if user clicked on search
        if (e.getSource() == readCandidateView.getSearch()) {

            // get the name of the area that was selected in combo box
            String area = (String) readCandidateView.getElectoralAreaComboBox().getSelectedItem();
            ArrayList<CandidateModel> filteredCandidates = new ArrayList<>();
            if (area.equals("ANY")) {
                // show all records
                readCandidateView.showRecords(candidates);
            } else {
                // show filtered records
                for (CandidateModel c : candidates) {
                    if (c.getElecArea().equals(area)) {
                        filteredCandidates.add(c);
                    }
                }
                readCandidateView.showRecords(filteredCandidates);
            }

        }
        // if user clicked on "Find Candidate" Button
        else if (e.getSource() == editCandidateView.getFind()) {

            // show dialog prompt to enter candidate id
            String candidateIdPrompt = JOptionPane.showInputDialog("Please enter candidate id: ");

            try {
                int cId = Integer.parseInt(candidateIdPrompt);
                // look for candidate by id
                CandidateModel foundCandidate = this.findCandidateById(cId);
                if (foundCandidate != null) {
                    editCandidateView.setCurrId(cId);

                    // find new candidate
                    editCandidateView.findCandidateButtons();

                    // show candidate details in the input fields
                    editCandidateView.initEditFields(foundCandidate);
                    windowView.getJframe().repaint();
                } else {
                    editCandidateView.showMessage("Candidate with id " + cId + " does not exist.");
                }

            } catch (NumberFormatException nfE) {
                // if user entered string instead of number, show error
                editCandidateView.showMessage("Please enter only numbers");
            }

        } else if (e.getSource() == editCandidateView.getAdd()) {

            // show add new candidate interface
            editCandidateView.addNewCandidatesButtons();
            editCandidateView.resetInputs();
            windowView.getJframe().repaint();

        } else if (e.getSource() == editCandidateView.getConfirm()) {

            // add new candidate
            addNewCandidate();
            // update the output
            readCandidateView.showRecords(candidates);

        } else if (e.getSource() == editCandidateView.getRemove()) {
            // remove candidate
            int candidateId = editCandidateView.getCurrId();
            CandidateModel deleteCandidate = findCandidateById(candidateId);

            if (deleteCandidate != null) {
                // if user accepts deletion, delete the candidate
                if (editCandidateView.showConfirmDialog(deleteCandidate)) {
                    candidates.remove(deleteCandidate);
                    editCandidateView.showMessage("Candidate with ID " + candidateId + " was deleted successfully");
                    // update the output
                    editCandidateView.resetInputs();
                    readCandidateView.showRecords(candidates);
                } else {
                    editCandidateView.showMessage("Candidate with ID " + candidateId + " was not deleted");
                }
            }
        }
        // if user clicked on "Save" button
        else if (e.getSource() == editCandidateView.getSave()) {
            // save the changes to the candidate
            int candidateId = editCandidateView.getCurrId();
            saveCandidateEdit(candidateId);
            // update the output
            readCandidateView.showRecords(candidates);
        }
    }


    public CandidateModel findCandidateById(int id) {
        for (CandidateModel candidate : this.candidates) {
            if (candidate.getId() == id) {
                return candidate;
            }
        }
        return null;
    }

    public void saveCandidateEdit(int id) {
        try {
            editCandidateView.validateInputs();
            CandidateModel editedCandidate = findCandidateById(id);

            editedCandidate.setFirstName(editCandidateView.getfNameField().getText());
            editedCandidate.setLastName(editCandidateView.getlNameField().getText());
            editedCandidate.setAddress(editCandidateView.getAddressField().getText());
            editedCandidate.setCityRegion(editCandidateView.getCityRegionField().getText());
            editedCandidate.setParty(editCandidateView.getPartyField().getText());
            editedCandidate.setElecArea(editCandidateView.getConstituencyField().getText());
            editCandidateView.showMessage("The candidate with ID " + editedCandidate.getId() + " was edited successfully");
            editCandidateView.resetInputs();
            return;

        } catch (InvalidParameterException ipE) {
            editCandidateView.showMessage(ipE.getMessage());
        }

    }

    public void addNewCandidate() {
        // validate inputs
        try {
            // if validate method is not throwing errors, then fields are not empty
            editCandidateView.validateInputs();
            CandidateModel newCandidate = new CandidateModel();
            // increment the local number if candidates, use it as id of new candidate
            numCandidates++;
            newCandidate.setId(numCandidates);
            newCandidate.setFirstName(editCandidateView.getfNameField().getText());
            newCandidate.setLastName(editCandidateView.getlNameField().getText());
            newCandidate.setAddress(editCandidateView.getAddressField().getText());
            newCandidate.setCityRegion(editCandidateView.getCityRegionField().getText());
            newCandidate.setParty(editCandidateView.getPartyField().getText());
            newCandidate.setElecArea(editCandidateView.getConstituencyField().getText());

            // save new candidate in local ArrayList
            candidates.add(newCandidate);
            editCandidateView.showMessage(newCandidate.getFirstName() + " " + newCandidate.getLastName() + " candidate with ID " + numCandidates + " was added successfully");
            editCandidateView.resetInputs();
        } catch (InvalidParameterException ipE) {
            editCandidateView.showMessage(ipE.getMessage());
        }
    }

    public void sortSearchClicked() {

        // get the name of the area that was selected in combo box
        String party = (String) sortCandidateView.getPartyComboBox().getSelectedItem();
        ArrayList<CandidateModel> filteredCandidates = new ArrayList<>();
        if (party.equals("ANY")) {
            // show all records
            sortCandidateView.showRecords(candidates);
            sortCandidateView.setDisplayedCandidates(candidates);
        } else {
            // show filtered records
            for (CandidateModel c : candidates) {
                if (c.getParty().equals(party)) {
                    filteredCandidates.add(c);
                }
            }
            sortCandidateView.showRecords(filteredCandidates);
            sortCandidateView.setDisplayedCandidates(filteredCandidates);
        }

    }

    public void sortCandidates() {
        String sortBy = (String) sortCandidateView.getSortComboBox().getSelectedItem();
        // set the sorting method according to user choice
        setSortingMethod(sortBy);
        ArrayList<CandidateModel> displayedCandidates = sortCandidateView.getDisplayedCandidates();
        if (sortCandidateView.getAscRButton().isSelected()) {
            Collections.sort(displayedCandidates);
        }
        else {
            Collections.sort(displayedCandidates, Collections.reverseOrder());
        }

        sortCandidateView.showRecords(displayedCandidates);
    }

    /**
     * MEthod sets the sorting method of Candidates
     *
     * @param sortBy String representation of the sorting method
     */
    public void setSortingMethod(String sortBy) {
        if (sortBy.equals("Id")) {
            CandidateModel.setSortingMethod(1);
        } else if (sortBy.equals("First Name")) {
            CandidateModel.setSortingMethod(2);
        } else if (sortBy.equals("Last Name")) {
            CandidateModel.setSortingMethod(3);
        } else if (sortBy.equals("Address")) {
            CandidateModel.setSortingMethod(4);
        } else if (sortBy.equals("Party")) {
            CandidateModel.setSortingMethod(5);
        } else if (sortBy.equals("Constituency")) {
            CandidateModel.setSortingMethod(6);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("window started");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }


    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
