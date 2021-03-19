import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CandidateController implements ActionListener{

    private CandidateView candidateView;
    private ArrayList<CandidateModel> candidates;

    public CandidateController() {

        candidates = new ArrayList<>();
        candidateView = new CandidateView();
        candidateView.init();

        // hook the action listener to Search button
        candidateView.getSearch().addActionListener(this);


        // create a file chooser for selecting files
        // that will be analysed and shown in program
        try {
            // initially set to null
            File selectedFile = null;

            // create file chooser for browsing local files
            JFileChooser fileChooser = new JFileChooser();

            // set the directory where the file chooser will be opened
            fileChooser.setCurrentDirectory(new File(".\\"));

            // if user chooses the file and clicks "ok", we get the selected file
            if (fileChooser.showOpenDialog(candidateView.getContentPane()) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = fileChooser.getSelectedFile();
            }

            // reading in the file
            Scanner in = new Scanner(selectedFile);

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

        for (CandidateModel c : candidates) {
            System.out.println(c);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
