import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowListener;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class WindowView  {

    private JFrame Jframe;
    private ReadCandidateView readCandidates;
    private EditCandidateView editCandidates;
    private SortCandidateView sortCandidates;
    private JTabbedPane jTabbedPane;

    public WindowView() {
        Jframe = new JFrame();

        // create all the panels
        editCandidates = new EditCandidateView();
        readCandidates = new ReadCandidateView();
        sortCandidates = new SortCandidateView();
        editCandidates.setLayout(new BoxLayout(editCandidates, BoxLayout.PAGE_AXIS));

        // create tabbed view
        jTabbedPane = new JTabbedPane();
        jTabbedPane.setBounds(8, 8, 1070, 600);

        // adding panels to the tabbed view
        jTabbedPane.add("Read Candidates", readCandidates);
        jTabbedPane.add("Edit Candidates", editCandidates);
        jTabbedPane.add("Sort Candidates", sortCandidates);

        // Due to the fact that buttons of all the tabs were appearing
        // on the tab that was selected, I came up with this solution.
        // Set ChangeListeners to show and hide panel elements explicitly,
        // depending on what Tab is selected
        jTabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                // Index 0 - first READ tab
                if(jTabbedPane.getSelectedIndex()==0)
                {
                    hideEdit();
                    hideSort();
                }

                // Index 1 - second EDIT tab
                else if(jTabbedPane.getSelectedIndex()==1)
                {
                    hideRead();
                    hideSort();
                }

                // Index 1 - second EDIT tab
                else if(jTabbedPane.getSelectedIndex()==1)
                {
                    hideRead();
                    hideEdit();
                }
            }
        });

        // adding tabbed view to the main frame
        Jframe.add(jTabbedPane);

        Jframe.setSize(1100, 650);
        Jframe.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Jframe.setLayout(null);
        Jframe.setVisible(true);
    }

    public void hideSort() {
        sortCandidates.setVisible(false);
    }

    public void hideRead() {
        readCandidates.setVisible(false);
    }

    public void hideEdit() {
        editCandidates.setVisible(false);
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

    public SortCandidateView getSortCandidates() {
        return sortCandidates;
    }
}