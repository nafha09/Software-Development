import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the card game.
 * Each player has:
 *  - a unique ID
 *  - a hand of cards
 *  - a left deck (to discard to)
 *  - a right deck (to draw from)
 *
 * The Player can draw and discard cards during their turn,
 * and can check if they have won (when all cards in hand have the same value).
 *
 * This class implements Runnable, allowing multiple players
 * to run in parallel threads if needed.
 */

