package uno;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private int currentPlayer;
    private String[] playerIds;

    private UnoDeck deck;
    private ArrayList<ArrayList<UnoCard>> playerHand;
    private ArrayList<UnoCard> stockPile;

    private UnoCard.Color validColor;
    private UnoCard.Value validValie;

    boolean gameDirection;
    public Game(String[] pids){
        deck.shuffle();
        stockPile = new ArrayList<UnoCard>();
        playerIds = pids;
        currentPlayer = 0;
        gameDirection = false;
        playerHand = new ArrayList<ArrayList<UnoCard>>();
        for (int i = 0; i < pids.length; i++){
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(deck.drawCard(7)));
            playerHand.add(hand);
        }
    }
    public void start(Game game){
        UnoCard card  = deck.drawCard();
        validColor = card.getColor();
        validValie = card.getValue();

        if (card.getValue() == UnoCard.Value.Wild){
            start(game);
        }

        if (card.getValue() == UnoCard.Value.Wild_Four || card.getValue() == UnoCard.Value.DrawTwo){
            start(game);
        }
        if(card.getValue() == UnoCard.Value.Skip){
            JLabel message = new JLabel(playerIds[currentPlayer] + "был отброшен!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);

            if(gameDirection == false){
                currentPlayer = (currentPlayer + 1) % playerIds.length;
            }
            else  if(gameDirection == true){
                currentPlayer = (currentPlayer - 1) % playerIds.length;
                if (currentPlayer == -1){
                    currentPlayer = playerIds.length - 1;
                }
            }
        }
        if(card.getValue() == UnoCard.Value.Reverse){
            JLabel message = new JLabel(playerIds[currentPlayer] + "направление игры изменилось!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            gameDirection ^= true;
            currentPlayer = playerIds.length -1;
        }
        stockPile.add(card);
    }
    public UnoCard getTopCard(){
        return new UnoCard(validColor, validValie);
    }
    public ImageIcon getTopCardImage(){
        return new ImageIcon(validColor + "-" + validValie + ".png");
    }
    public boolean isGameOver() {
        for (String player : this.playerIds) {
            if (hasEmptyHand(player)) {
                return true;
            }
        }
        return false;
    }
    public String getCurrentPlayer(){
        return this.playerIds[this.currentPlayer];
    }
    public String getPreviousPlayer(int i){
        int index = this.currentPlayer - i;
        if(index == -1){
            index = playerIds.length - 1;
        }
        return this.playerIds[index];
    }
    public String[] getPlayers(){
        return playerIds;
    }
    public ArrayList<UnoCard> getPlayerHand(String pid){
        int index = Arrays.asList(playerIds).indexOf(pid);
        return playerHand.get(index);
    }
    public int getPlayerHandSize(String pid){
        return getPlayerHand(pid).size();
    }
    public UnoCard getPlayerCard(String pid, int choice){
        ArrayList<UnoCard> hand  = getPlayerHand(pid);
        return hand.get(choice);
    }
    public boolean hasEmptyHand(String pid){
        return getPlayerHand(pid).isEmpty();
    }
    public  boolean validCardPlay(UnoCard card){
        return card.getColor() == validColor || card.getValue() == validValie;
    }
    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException{
        if(this.playerIds[this.currentPlayer] !=pid){
            throw new InvalidPlayerTurnException("Это не" + pid + "'s поворот", pid);
        }
    }

    public void submitDrasw(String pid) throws InvalidPlayerTurnException{
        checkPlayerTurn(pid);
        if (deck.isEmpty()){
            deck.replaceDeckWith(stockPile);
            deck.shuffle();
        }
        getPlayerHand(pid).add(deck.drawCard());
        if(gameDirection == false){
            currentPlayer = (currentPlayer + 1)% playerIds.length;
        }
        else if (gameDirection == true){
            currentPlayer = (currentPlayer - 1) % playerIds.length;
            if (currentPlayer == -1){
                currentPlayer = playerIds.length -  1;
            }
        }
    }
    public void setCardColor(UnoCard.Color color){
        validColor = color;
    }

    private class InvalidPlayerTurnException extends Exception {
        public InvalidPlayerTurnException(String s, String pid) {
        }
    }
}
