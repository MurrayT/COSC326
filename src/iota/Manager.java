package iota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manager class for an Iota game
 *
 * @author Michael Albert
 */
public class Manager {

    private ArrayList<PlayedCard> board = new ArrayList<>();
    private Player p1;
    private Player p2;
    private HashMap<Player, ArrayList<Card>> hands = new HashMap<>();
    private HashMap<Player, Integer> score = new HashMap<>();
    private Deck deck;
    public boolean isAutorun = false;
    int playernumber = 1;
    boolean isRunning = true;

    public Manager() {
        this.deck = new Deck();
    }

    void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        score.put(p1, 0);
        score.put(p2, 0);
    }

    /**
     * Get (a copy of) the current state of the board.
     *
     * @return A copy of the board. Cards in the array list will be in the
     * order they were played.
     */
    public ArrayList<PlayedCard> getBoard() {
        ArrayList<PlayedCard> result = new ArrayList<>();
        for (PlayedCard c : board) {
            result.add(c.copy());
        }
        return result;
    }

    /**
     * Return the hand of the given player.
     *
     * @param p The player.
     * @return The hand of the given player.
     */
    public ArrayList<Card> getHand(Player p) {
        return hands.get(p);
    }

    /**
     * Compute the net score of the given player.
     *
     * @param p the player.
     * @return The net score for the player.
     */
    public int getScore(Player p) {
        if (p == p1) {
            return score.get(p1) - score.get(p2);
        } else {
            return score.get(p2) - score.get(p1);
        }
    }

    public int getRawScore(Player p) {
        if (p == p1) {
            return score.get(p1);
        } else {
            return score.get(p2);
        }
    }

    /**
     * Compute the hand size of the player's opponent.
     *
     * @param p the player
     * @return the opponent's hand size.
     */
    public int opponentsHandSize(Player p) {
        if (p == p1) {
            return hands.get(p2).size();
        } else {
            return hands.get(p1).size();
        }
    }

    private void dealHands() {
        ArrayList<Card> h = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) h.add(deck.dealCard());
        hands.put(p1, h);
        h = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) h.add(deck.dealCard());
        hands.put(p2, h);
    }

    private void seedBoard() {
        board.add(new PlayedCard(deck.dealCard(), null, 0, 0));
    }

    void play() {
        dealHands();
        seedBoard();
        // More stuff
    }

    void deal_to_player(Player player) {
        ArrayList<Card> smallhand = hands.get(player);
        int sizediff = 4 - smallhand.size();
        for (int i = 0; i < sizediff; i++) {
            if (deck.hasCard())
                smallhand.add(deck.dealCard());
        }
    }

    void discardStep(Player player, ArrayList<Card> discardPile) {
        deck.addCards(discardPile);
        hands.get(player).removeAll(discardPile);
    }

    void playerStep(Player player) {
        ArrayList<PlayedCard> proposedMove = player.makeMove();
        if (proposedMove.isEmpty()) {
            // move empty, make player discard.
            ArrayList<Card> discardPile = player.discard();
            if (hands.get(player).containsAll(discardPile)) {
                discardStep(player, discardPile);
                deal_to_player(player);
            } else {
                System.err.println("Some cards are not in " + player.getName() + "'s hand. Failed to Discard");
            }
        } else {
            List cards = proposedMove.stream().map(i -> i.card).collect(Collectors.toList());
            if (hands.get(player).containsAll(cards)) {
                if (Utilities.isLegalMove(proposedMove, board)) {
                    int multiplier = 1;
                    //Completely legal
                    int moveScore = Utilities.scoreForMove(proposedMove, board);
                    board.addAll(proposedMove);
                    hands.get(player).removeAll(cards);
                    deal_to_player(player);
                    if (!deck.hasCard() && getHand(player).isEmpty())
                        multiplier = 2;
                    score.replace(player, score.get(player) + moveScore * multiplier);
                } else {
                    System.err.println(player.getName() + " tried to play an illegal move.");
                }
            } else {
                System.err.println("Some cards are not in " + player.getName() + "'s hand. Failed to Play");
            }

        }
    }

    void step() {
        if ((!getHand(p1).isEmpty() && !getHand(p2).isEmpty())) {
            if (playernumber == 1) {
                playerStep(p1);
                playernumber = 2;
            } else {
                playerStep(p2);
                playernumber = 1;
            }
        } else {
            System.err.println("GAME OVER");
            isRunning = false;
        }
    }


}
