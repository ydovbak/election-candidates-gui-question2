import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class AddNewCandidateController implements ActionListener, WindowListener {


    private AddNewCandidateView view;

    public AddNewCandidateController() {

        view = new AddNewCandidateView();
        view.init();

        view.setWindowsListener(this);
        view.getAdd().addActionListener(this);
        view.getRemove().addActionListener(this);
        view.getPrint().addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("button pressed");
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