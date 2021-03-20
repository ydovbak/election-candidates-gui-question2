import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CandidateController extends JFrame implements WindowListener {

    private ReadCandidateView readCandidateView;
    private EditCandidateView editCandidateView;
    private SortCandidateView sortCandidateView;
    private WindowView windowView;
    private ArrayList<CandidateModel> candidates;
    private int numCandidates;

    public CandidateController() {

        candidates = new ArrayList<>();
        windowView = new WindowView();
        readCandidateView = windowView.getReadCandidates();
        editCandidateView = windowView.getEditCandidates();
        sortCandidateView = windowView.getSortCandidates();

        editCandidateView.init();
        readCandidateView.init();
        sortCandidateView.init();
        windowView.setWindowsListener(this);

        // create a file chooser for selecting files
        // that will be analysed and shown in program
        try {
            // initially set to null
            //File selectedFile = new File("/Users/yuliiadovbak/Desktop/dcccandidatesforlocalelection2009p20120822-1410.csv");
            File selectedFile = new File("C:\\Users\\Julia\\Documents\\Year 2\\SoftDev\\projects\\election-candidates-gui-question2\\elections-data.csv");
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

        // show all records in the READ and SORT tabs
        readCandidateView.showRecords(candidates);
        sortCandidateView.showRecords(candidates);
        sortCandidateView.setDisplayedCandidates(candidates);

        // save number of candidates
        // this variable will be used to create id for new candidates
        numCandidates = candidates.size();


        /*Implementing ActionListener and checking get source on every click led to
         code with a lot of if/else statements and the code was very hard to read.
         That's why I decided to add anonymous action listeners to each of the buttons*/

        // ****************
        // *** READ TAB ***
        // "Search" button on READ tab click listener
        readCandidateView.getSearch().addActionListener(e -> readSearchClicked());

        // ****************
        // *** EDIT TAB ***
        // "Find Candidate" button click listener
        editCandidateView.getFind().addActionListener(e -> findCandidateClicked());

        // "Add New Candidate" button click listener
        editCandidateView.getAdd().addActionListener(e -> addNewCandidateClicked());

        // "Confirm Add" button click listener
        editCandidateView.getConfirm().addActionListener(e -> confirmAddingNewCandidateClicked());

        // "Remove" button click listener
        editCandidateView.getRemove().addActionListener(e -> removeCandidateClicked());

        // "Save Edit" button click listener
        editCandidateView.getSave().addActionListener(e -> saveEditClicked());

        // ****************
        // *** SORT TAB ***
        // "Sort" button click listener
        sortCandidateView.getSort().addActionListener(e -> sortCandidates());

        // "Search" button click listener
        sortCandidateView.getSearch().addActionListener(e -> sortSearchClicked());

        // "Ascending" radio button clicked
        sortCandidateView.getAscRButton().addActionListener(e -> sortCandidates());

        // "Descending" radio button clicked
        sortCandidateView.getDescRButton().addActionListener(e -> sortCandidates());
    }

    /**
     * Is called when user clicks on "Search" button on the READ tab.
     * filters the records according to the chosen combo box option
     */
    public void readSearchClicked() {

        // get the name of the area that was selected in combo box
        String area = (String) readCandidateView.getElectoralAreaComboBox().getSelectedItem();
        ArrayList<CandidateModel> filteredCandidates = new ArrayList<>();

        if (area.equals("ANY")) {
            // all records correspond to ANY criteria
            filteredCandidates = candidates;
        } else {
            // filter records
            for (CandidateModel c : candidates) {
                if (c.getElecArea().equals(area)) {
                    filteredCandidates.add(c);
                }
            }
        }

        // show filtered records
        readCandidateView.showRecords(filteredCandidates);
    }

    /**
     * Is called when user clicks on "Find Candidate" button on the EDIT tab.
     * Shows dialog prompt asking user to enter ID of the candidate and calls
     * the display of the records data that was found
     */
    public void findCandidateClicked() {
        // reset warning message
        editCandidateView.showMessage("");

        // show dialog prompt to enter candidate id
        String candidateIdPrompt = JOptionPane.showInputDialog("Please enter candidate id: ");

        try {
            int cId = Integer.parseInt(candidateIdPrompt);
            // look for candidate by id
            CandidateModel foundCandidate = this.findCandidateById(cId);

            if (foundCandidate != null) {

                // set id of the record that we are editing now
                editCandidateView.setCurrId(cId);

                // set buttons, labels and text fields that correspond to editing functionality
                editCandidateView.findCandidateButtons();

                // show candidate details in the input fields
                editCandidateView.initEditFields(foundCandidate);
                editCandidateView.repaint();
            } else {
                editCandidateView.showMessage("Candidate with id " + cId + " does not exist.");
            }

        } catch (NumberFormatException nfE) {
            // if user entered string instead of number, show error
            editCandidateView.showMessage("Please enter only numbers");
        }
    }

    /**
     * Is called when user clicks on "Add New Candidate" button on the EDIT tab.
     * Shows the corresponding interface to enable inputs for new record data
     */
    public void addNewCandidateClicked() {
        // reset warning message
        editCandidateView.showMessage("");

        // show add new candidate interface
        editCandidateView.showAddNewCandidatesButtons();

        // empty the input text fields
        editCandidateView.resetInputs();

        // show the changes
        editCandidateView.repaint();
    }

    /**
     * Is called when user clicks on "Confirm Add" button on the EDIT tab
     */
    public void confirmAddingNewCandidateClicked() {

        // add new candidate
        addNewCandidate();

        // update the output in the READ tab
        readCandidateView.showRecords(candidates);
    }

    /**
     * Is called when user clicks on "Remove" button on the EDIT tab.
     * Removes the element that is currently displayed.
     */
    public void removeCandidateClicked() {
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

                // reset the menu buttons
                editCandidateView.setMainMenuButtons();
            } else {
                editCandidateView.showMessage("Candidate with ID " + candidateId + " was not deleted");
            }
        }
    }

    /**
     * Is called when user clicks on "Save Edit" button on the EDIT tab.
     * Saves the changes that were made to the record, that was displayed.
     */
    public void saveEditClicked() {

        // save the changes to the candidate
        int candidateId = editCandidateView.getCurrId();
        saveCandidateEdit(candidateId);

        // update the output in the READ tab
        readCandidateView.showRecords(candidates);

        // reset the menu buttons
        editCandidateView.setMainMenuButtons();
    }

    /**
     * Is called when user clicks on "Search" button on the SORT tab.
     * Searches all the records by the party that was selected in party combo box,
     * then displays the filtered record in the text area
     */
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


    /**
     * Method is saving all the changes to the record that was displayed on the screen.
     * @param id id of the record that has to be updated
     */
    public void saveCandidateEdit(int id) {
        try {
            // validating inputs wil throw an error if one of the fields is empty
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

        } catch (InvalidParameterException ipE) {
            editCandidateView.showMessage(ipE.getMessage());
        }
    }

    /**
     * Fetching all the data from input text fields, creating new object of
     * CandidateModel and adding it to the local copy of candidates list
     */
    public void addNewCandidate() {
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
            editCandidateView.showMessage(newCandidate.getFirstName() + " "
                    + newCandidate.getLastName() + " candidate with ID " + numCandidates + " was added successfully");
            editCandidateView.resetInputs();

            // reset the menu buttons
            editCandidateView.setMainMenuButtons();

        } catch (InvalidParameterException ipE) {
            editCandidateView.showMessage(ipE.getMessage());
        }
    }

    /**
     * Method is setting the sorting method that was chosen in "Sort By" combo box,
     * sorting all the records that are currently displayed on the screen and
     * updates the records displayed.
     */
    public void sortCandidates() {
        String sortBy = (String) sortCandidateView.getSortComboBox().getSelectedItem();

        // set the sorting method according to user choice
        setSortingMethod(sortBy);

        // fetching records that are displayed on the screen right now
        ArrayList<CandidateModel> displayedCandidates = sortCandidateView.getDisplayedCandidates();

        // sorting records asc or desc according to the radio button that were selected
        if (sortCandidateView.getAscRButton().isSelected()) {
            Collections.sort(displayedCandidates);
        }
        else {
            Collections.sort(displayedCandidates, Collections.reverseOrder());
        }

        // update the output
        sortCandidateView.showRecords(displayedCandidates);
    }

    /**
     * Method sets the sorting method of Candidates
     *
     * @param sortBy String representation of the sorting method
     */
    public void setSortingMethod(String sortBy) {
        switch (sortBy) {
            case "Id" -> CandidateModel.setSortingMethod(1);
            case "First Name" -> CandidateModel.setSortingMethod(2);
            case "Last Name" -> CandidateModel.setSortingMethod(3);
            case "Address" -> CandidateModel.setSortingMethod(4);
            case "Party" -> CandidateModel.setSortingMethod(5);
            case "Constituency" -> CandidateModel.setSortingMethod(6);
        }
    }

    /**
     * Finding and returning the CandidateModel object from the local ArrayList of candidates
     * @param id id of the candidate that we are looking for
     * @return CandidateModel object if it was found, and null if it was not in the list
     */
    public CandidateModel findCandidateById(int id) {
        for (CandidateModel candidate : this.candidates) {
            if (candidate.getId() == id) {
                return candidate;
            }
        }
        return null;
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
