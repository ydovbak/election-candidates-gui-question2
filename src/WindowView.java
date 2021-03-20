import javax.swing.*;
import java.awt.event.WindowListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class WindowView  {

    private JFrame Jframe;
    private ReadCandidateView readCandidates;
    private EditCandidateView editCandidates;
    private JTabbedPane jTabbedPane;


    public WindowView() {
        Jframe = new JFrame();
        editCandidates = new EditCandidateView();
        readCandidates = new ReadCandidateView();
        editCandidates.setLayout(new BoxLayout(editCandidates, BoxLayout.PAGE_AXIS));

        jTabbedPane = new JTabbedPane();
        jTabbedPane.setBounds(5, 5, 1200, 600);
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

    public ReadCandidateView getReadCandidates() {
        return readCandidates;
    }

    public EditCandidateView getEditCandidates() {
        return editCandidates;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }
}