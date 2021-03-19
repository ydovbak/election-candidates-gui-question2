import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class WindowView  {

    private JFrame Jframe;
    private CandidateView readCandidates;
    private AddNewCandidateView editCandidates;
    private JTabbedPane jTabbedPane;


    public WindowView() {
        Jframe = new JFrame();
        editCandidates = new AddNewCandidateView(Jframe.getContentPane());
        readCandidates = new CandidateView();
        editCandidates.setLayout(new BoxLayout(editCandidates, BoxLayout.PAGE_AXIS));

        jTabbedPane = new JTabbedPane();
        jTabbedPane.setBounds(10, 10, 1200, 700);
        jTabbedPane.add("Read Candidates", readCandidates);
        jTabbedPane.add("Edit Candidates", editCandidates);

        Jframe.add(jTabbedPane);

        //readCandidates.init();
        //editCandidates.init();

        //this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        Jframe.setSize(1200, 750);
        Jframe.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Jframe.setLayout(null);

        Jframe.setVisible(true);
    }

    public void setWindowsListener(WindowListener w)
    {
        Jframe.addWindowListener(w);
    }

    public JFrame getJframe() {
        return Jframe;
    }

    public CandidateView getReadCandidates() {
        return readCandidates;
    }

    public AddNewCandidateView getEditCandidates() {
        return editCandidates;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }
}