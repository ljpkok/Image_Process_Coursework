import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author Jiping Lyu
 * http://c.biancheng.net/view/1245.html
 */
public class JMenuBar1 extends JMenuBar{
    App app;
    public JMenuBar1()
    {
        add(createFileMenu());
        add(createEditMenu());
        App app = new App();
        setVisible(true);
    }
    /* Menu Creation
     */
    private JMenu createFileMenu()
    {
        JMenu menu=new JMenu("File(F)");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem item=new JMenuItem("Open(N)",KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menu.add(item);
        item=new JMenuItem("Open2(O)",KeyEvent.VK_O);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        item.setActionCommand("mOpen2");
        item.addActionListener(app);
        menu.add(item);
        item=new JMenuItem("Save(S)",KeyEvent.VK_S);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
        item.setActionCommand("mSave");
        item.addActionListener(app);
        menu.add(item);
        menu.addSeparator();
        item=new JMenuItem("Exit(E)",KeyEvent.VK_E);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
        item.setActionCommand("mExit");
        item.addActionListener(app);
        menu.add(item);
        return menu;
    }

    private JMenu createEditMenu()
    {
        JMenu menu=new JMenu("Edit(N)");
        menu.setMnemonic(KeyEvent.VK_F);
        JMenuItem item=new JMenuItem("Undo(N)",KeyEvent.VK_N);
        item.setActionCommand("mUndo");
        item.addActionListener(app);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        menu.add(item);
        return menu;
    }

}
