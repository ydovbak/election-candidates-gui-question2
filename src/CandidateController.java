import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class CandidateController extends JFrame implements ActionListener, WindowListener {

    private ReadCandidateView candidateReadView;
    private EditCandidateView candidateEditView;
    private WindowView windowView;
    private ArrayList<CandidateModel> candidates;

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
            if(in.hasNextLine()) {
                in.nextLine();
                in.nextLine();
            }

            // reading in every line in csv, converting them into Candidate objects
            while (in.hasNextLine()) {
                candidates.add(new CandidateModel(in.nextLine()));
            }

            // close the scanner
            in.close();
        }
        catch (Exception e) {
            // print the exception message
            e.printStackTrace();
        }

        // populate the combo box
        ArrayList<String> areas = new ArrayList<>();
        for (CandidateModel c : candidates) {
            String area = c.getElecArea();

            // adding only unique areas
            if(!areas.contains(area)) {
                areas.add(area);
                candidateReadView.getElectoralAreaComboBox().addItem(area);
            }
        }

        // show all
        candidateReadView.showRecords(candidates);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        // if user clicked on search
        if(e.getSource() == candidateReadView.getSearch()) {

            // get the name of the area that was selected in combo box
            String area = (String) candidateReadView.getElectoralAreaComboBox().getSelectedItem();
            ArrayList<CandidateModel> filteredCandidates = new ArrayList<>();
            for (CandidateModel c : candidates) {
                if (c.getElecArea().equals(area)) {
                    filteredCandidates.add(c);
                }
            }
            candidateReadView.showRecords(filteredCandidates);
        }
        // if user clicked on "Find Candidate" Button
        else if (e.getSource() == candidateEditView.getFind()) {

            // reset(empty) the error message
            candidateEditView.showError("");

            // show dialog prompt to enter candidate id
            String candidateIdPrompt = JOptionPane.showInputDialog("Please enter candidate id: ");

            try {
                int cId = Integer.parseInt(candidateIdPrompt);
                // look for candidate by id
                CandidateModel foundCandidate = this.findCandidateById(cId);
                if (foundCandidate != null) {
                    System.out.println("Id of the candidate:" + cId);
                    System.out.println("Id of the candidate:" + foundCandidate);
                    // find new candidate
                    candidateEditView.addNewCandidatesButtons();
                    candidateEditView.initEditFields(foundCandidate);
                    windowView.getJframe().repaint();
                }
                else {
                    candidateEditView.showError("Candidate with id " + cId + " does not exist.");
                }

            }
            catch (NumberFormatException nfE ) {
                // if user entered string instead of number, show error
                candidateEditView.showError("Please enter only numbers");
            }

        }
        else if (e.getSource() == candidateEditView.getAdd()) {
            // add new candidate
        }
        else if (e.getSource() == candidateEditView.getRemove()) {
            // remove candidate
        }
        // if user clicked on "Save" button
        else if (e.getSource() == candidateEditView.getSave()) {
           // save the changes to the candidate
        }
    }


    public CandidateModel findCandidateById(int id) {
        for (CandidateModel candidate: this.candidates) {
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
