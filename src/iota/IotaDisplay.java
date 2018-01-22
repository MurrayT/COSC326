package iota;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class IotaDisplay extends JPanel {

    JScrollPane sp;
    private DisplayPanel dp;

    public IotaDisplay() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Manager m = new Manager();
        Player p1 = new Player(m) {
            @Override
            public ArrayList<PlayedCard> makeMove() {
                return null;
            }

            @Override
            public ArrayList<Card> discard() {
                return null;
            }

            @Override
            public String getName() {
                return "Steve";
            }
        };
        Player p2 = new Player(m) {
            @Override
            public ArrayList<PlayedCard> makeMove() {
                return null;
            }

            @Override
            public ArrayList<Card> discard() {
                return null;
            }

            @Override
            public String getName() {
                return "Dave";
            }
        };
        m.setPlayers(p1, p2);
        m.play();
        dp = new DisplayPanel(m, 36 * 150, 36 * 150);
        sp = new JScrollPane(dp);
        sp.setPreferredSize(new Dimension(800, 600));
        add(sp);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        sp.getViewport().setViewPosition(new Point(dp.width / 2, dp.height / 2));
        JPanel hands = new JPanel();
        add(hands);
        hands.setLayout(new BoxLayout(hands, BoxLayout.LINE_AXIS));
        HandPanel hp1 = new HandPanel(m, sp.getWidth() / 2, 150, p1);
        HandPanel hp2 = new HandPanel(m, sp.getHeight() / 2, 150, p2);
        hands.add(hp1);
        hands.add(hp2);

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
        System.out.println("Created GUI on EDT? " +
                SwingUtilities.isEventDispatchThread());
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
