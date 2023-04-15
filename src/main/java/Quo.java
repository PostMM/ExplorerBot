import javax.swing.*;
import java.awt.*;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.border.LineBorder;

public class Quo extends JFrame {

    private static final Color COLOR_MAIN = Color.DARK_GRAY;
    private static final Color COLOR_CONT = Color.GRAY;
    private static final Color COLOR_TXT = Color.WHITE;
    private static final ImageIcon APP_IMAGE = new ImageIcon(ExplorerBot.RES + "icon.png");
    private final TAOS output;
    private final JTextArea textArea;

    public Quo() {

        super(ExplorerBot.NAME);
        textArea = new JTextArea();
        initialize();
        output = new TAOS(textArea);
    }

    /**
     * Fills this JFrame with Quo UI elements.
     */
    private void initialize() {

        this.setBounds(100, 100, 800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(APP_IMAGE.getImage());

        JMenuBar menuBar = new JMenuBar();
        menuBar.setMargin(new Insets(0, 0, 1, 0));
        menuBar.setBorderPainted(false);
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_MAIN);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new MigLayout("", "[grow]", "[][grow][]"));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(new LineBorder(COLOR_CONT, 2));
        scrollPane.setBackground(COLOR_MAIN);
        JScrollBar vert = new JScrollBar();
        JScrollBar hori = new JScrollBar(JScrollBar.HORIZONTAL);
        vert.setBorder(new LineBorder(COLOR_MAIN));
        hori.setBorder(new LineBorder(COLOR_MAIN));
        vert.setBackground(COLOR_MAIN);
        hori.setBackground(COLOR_MAIN);
        vert.setUnitIncrement(16);
        
        JLabel lblNewLabel = new JLabel("Explore ");
        lblNewLabel.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
        //lblNewLabel.setForeground(new Color(255, 102, 51));
        lblNewLabel.setForeground(Color.WHITE);
        panel.add(lblNewLabel, "cell 0 0");
        
        scrollPane.setVerticalScrollBar(vert);
        scrollPane.setHorizontalScrollBar(hori);
        panel.add(scrollPane, "flowy,cell 0 1,grow");

        textArea.setForeground(COLOR_TXT);
        textArea.setBackground(COLOR_CONT);
        textArea.setEditable(false);
        textArea.setBorder(new LineBorder(COLOR_CONT, 2));
        textArea.setFont(new Font("verdana", Font.PLAIN, 14));
        scrollPane.setViewportView(textArea);


        JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
        mntmNewMenuItem.setBackground(Color.WHITE);
        mntmNewMenuItem.addActionListener(e -> System.exit(0));

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Clear");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        fileMenu.add(mntmNewMenuItem_1);
        fileMenu.add(mntmNewMenuItem);


        JLabel lb1NewLabel_1 = new JLabel(ExplorerBot.VERSION + " - Triple");
        lb1NewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
        lb1NewLabel_1.setForeground(Color.GRAY);
        panel.add(lb1NewLabel_1, "cell 0 2,alignx right");

        this.setVisible(true);

    }

    public TAOS getOut() {
        return output;
    }
}

final class TAOS extends OutputStream {

    private final JTextArea textArea;

    public TAOS(JTextArea area) {
        textArea = area;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(("" + (char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

}