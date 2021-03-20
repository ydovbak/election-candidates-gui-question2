import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;

public class CandidateController extends JFrame implements ActionListener, WindowListener {

    private ReadCandidateView candidateReadView;
    private EditCandidateView candidateEditView;
    private WindowView windowView;
    private ArrayList<CandidateModel> candidates;
    private int numCandidates;

    public CandidateController() {

        candidates = new ArrayList<>();
        //candidateView = new CandidateView();
        //candidateView.init();
        windowView = new WindowView();
        candidateReadView = windowView.getReadCandidates();
        candidateEditView = windowView.getEditCandidates();

        candidateEditView.init();
        candidateReadView.init();
        windowView.setWindowsListener(this);

        // hook the action listener to Search button
        candidateReadView.getSearch().addActionListener(this);
        candidateEditView.getFind().addActionListener(this);
        candidateEditView.getAdd().addActionListener(this);
        candidateEditView.getRemove().addActionListener(this);
        candidateEditView.getSave().addActionListener(this);
        candidateEditView.getConfirm().addActionListener(this);

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

        // populate the combo box
        ArrayList<String> areas = new ArrayList<>();
        candidateReadView.getElectoralAreaComboBox().addItem("ANY"); // add option that will include all
        for (CandidateModel c : candidates) {
            String area = c.getElecArea();

            // adding only unique areas
            if (!areas.contains(area)) {
                areas.add(area);
                candidateReadView.getElectoralAreaComboBox().addItem(area);
            }
        }

        // show all
        candidateReadView.showRecords(candidates);

        // save number of candidates
        // this variable will be used to create id for new candidates
        numCandidates = candidates.size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // reset the warning message
        candidateEditView.showMessage("");

        // if user clicked on search
        if (e.getSource() == candidateReadView.getSearch()) {

            // get the name of the area that was selected in combo box
            String area = (String) candidateReadView.getElectoralAreaComboBox().getSelectedItem();
            ArrayList<CandidateModel> filteredCandidates = new ArrayList<>();
            if (area.equals("ANY")) {
                // show all records
                candidateReadView.showRecords(candidates);
            }
            else {
                // show filtered records
                for (CandidateModel c : candidates) {
                    if (c.getElecArea().equals(area)) {
                        filteredCandidates.add(c);
                    }
                }
                candidateReadView.showRecords(filteredCandidates);
            }

        }
        // if user clicked on "Find Candidate" Button
        else if (e.getSource() == candidateEditView.getFind()) {

            // show dialog prompt to enter candidate id
            String candidateIdPrompt = JOptionPane.showInputDialog("Please enter candidate id: ");

            try {
                int cId = Integer.parseInt(candidateIdPrompt);
                // look for candidate by id
                CandidateModel foundCandidate = this.findCandidateById(cId);
                if (foundCandidate != null) {
                    candidateEditView.setCurrId(cId);

                    // find new candidate
                    candidateEditView.findCandidateButtons();

                    // show candidate details in the input fields
                    candidateEditView.initEditFields(foundCandidate);
                    windowView.getJframe().repaint();
                } else {
                    candidateEditView.showMessage("Candidate with id " + cId + " does not exist.");
                }

            } catch (NumberFormatException nfE) {
                // if user entered string instead of number, show error
                candidateEditView.showMessage("Please enter only numbers");
            }

        } else if (e.getSource() == candidateEditView.getAdd()) {

            // show add new candidate interface
            candidateEditView.addNewCandidatesButtons();
            candidateEditView.resetInputs();
            windowView.getJframe().repaint();

        } else if (e.getSource() == candidateEditView.getConfirm()) {

            // add new candidate
            addNewCandidate();
            // update the output
            candidateReadView.showRecords(candidates);

        } else if (e.getSource() == candidateEditView.getRemove()) {
            // remove candidate
            int candidateId = candidateEditView.getCurrId();
            CandidateModel deleteCandidate = findCandidateById(candidateId);

            if (deleteCandidate != null) {
                // if user accepts deletion, delete the candidate
                if (candidateEditView.showConfirmDialog(deleteCandidate)) {
                    candidates.remove(deleteCandidate);
                    candidateEditView.showMessage("Candidate with ID " + candidateId + " was deleted successfully");
                    // update the output
                    candidateEditView.resetInputs();
                    candidateReadView.showRecords(candidates);
                }
                else {
                    candidateEditView.showMessage("Candidate with ID " + candidateId + " was not deleted");
                }
            }
        }
        // if user clicked on "Save" button
        else if (e.getSource() == candidateEditView.getSave()) {
            // save the changes to the candidate
            int candidateId = candidateEditView.getCurrId();
            saveCandidateEdit(candidateId);
            // update the output
            candidateReadView.showRecords(candidates);
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
            candidateEditView.validateInputs();
            CandidateModel editedCandidate = findCandidateById(id);

            editedCandidate.setFirstName(candidateEditView.getfNameField().getText());
            editedCandidate.setLastName(candidateEditView.getlNameField().getText());
            editedCandidate.setAddress(candidateEditView.getAddressField().getText());
            editedCandidate.setCityRegion(candidateEditView.getCityRegionField().getText());
            editedCandidate.setParty(candidateEditView.getPartyField().getText());
            editedCandidate.setElecArea(candidateEditView.getConstituencyField().getText());
            candidateEditView.showMessage("The candidate with ID " + editedCandidate.getId() + " was edited successfully");
            candidateEditView.resetInputs();
            return;

        } catch (InvalidParameterException ipE) {
            candidateEditView.showMessage(ipE.getMessage());
        }

    }

    public void addNewCandidate() {
        // validate inputs
        try {
            // if validate method is not throwing errors, then fields are not empty
            candidateEditView.validateInputs();
            CandidateModel newCandidate = new CandidateModel();
            // increment the local number if candidates, use it as id of new candidate
            numCandidates++;
            newCandidate.setId(numCandidates);
            newCandidate.setFirstName(candidateEditView.getfNameField().getText());
            newCandidate.setLastName(candidateEditView.getlNameField().getText());
            newCandidate.setAddress(candidateEditView.getAddressField().getText());
            newCandidate.setCityRegion(candidateEditView.getCityRegionField().getText());
            newCandidate.setParty(candidateEditView.getPartyField().getText());
            newCandidate.setElecArea(candidateEditView.getConstituencyField().getText());

            // save new candidate in local ArrayList
            candidates.add(newCandidate);
            candidateEditView.showMessage(newCandidate.getFirstName() + " " + newCandidate.getLastName() + " candidate with ID " + numCandidates + " was added successfully");
            candidateEditView.resetInputs();
        } catch (InvalidParameterException ipE) {
            candidateEditView.showMessage(ipE.getMessage());
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
