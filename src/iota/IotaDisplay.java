package iota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IotaDisplay extends JPanel {

    JScrollPane sp;
    private DisplayPanel dp;

    public IotaDisplay() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Manager m = new Manager();
        Player p1 = ;
        Player p2 = ;
        m.setPlayers(p1, p2);
        m.play();
        dp = new DisplayPanel(m, 68 * PlayedCard.SIZE, 68 * PlayedCard.SIZE);
        sp = new JScrollPane(dp);
        sp.setPreferredSize(new Dimension(810, 630));
        add(sp);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sp.getViewport().setViewPosition(new Point(dp.width / 2 - 4 * PlayedCard.SIZE, dp.height / 2 - 3 * PlayedCard.SIZE));
        JPanel hands = new JPanel();
        add(hands);
        hands.setLayout(new BoxLayout(hands, BoxLayout.LINE_AXIS));
        HandPanel hp1 = new HandPanel(m, sp.getWidth() / 2, 140, p1);
        HandPanel hp2 = new HandPanel(m, sp.getHeight() / 2, 140, p2);
        hands.add(hp1);
        hands.add(hp2);
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.LINE_AXIS));
        JButton stepButton = new JButton("Step");
        bp.add(stepButton);
        JButton runButton = new JButton("Center");
        bp.add(runButton);
        add(bp);
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (m.isRunning) {
                    m.step();
                    dp.repaint();
                    hp1.repaint();
                    hp2.repaint();
                }
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp.getViewport().setViewPosition(new Point(dp.width / 2 - 4 * PlayedCard.SIZE, dp.height / 2 - 3 * PlayedCard.SIZE));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGui();
            }
        });
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("Iota Display");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JComponent newContentPane = new IotaDisplay();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);


    }
}
