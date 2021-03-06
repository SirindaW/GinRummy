/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class UpCard {

    private ObservableList<Node> cards;

    public UpCard(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public void keepCard(Card card) {
        cards.add(card);
    }

    public Node drawCard() {
        Node card;
        card = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return card;
    }

    public char getSuit(int index) {
        return cards.get(index).toString().charAt(1);
    }

    public char getRank(int index) {
        return cards.get(index).toString().charAt(0);
    }
    
    public Node getNode(int index){
        return cards.get(index);
    }

    public int getRankValue(int index) {
        int cardValue = 0;
        if (cards.get(index).toString().charAt(0) == 'm'
                || cards.get(index).toString().charAt(0) == 'n'
                || cards.get(index).toString().charAt(0) == 'o'
                || cards.get(index).toString().charAt(0) == 'p') {
            cardValue = 10;
        } else {
            cardValue = Character.getNumericValue(cards.get(index).toString().charAt(0));
        }
        return cardValue;
    }

    public int getSize() {
        return cards.size();
    }

    public int getRankValueForCheckKind(int index) {
        int cardValue = 0;
        if (cards.get(index).toString().charAt(0) == 'm') {
            cardValue = 10;
        } else if (cards.get(index).toString().charAt(0) == 'n') {
            cardValue = 11;
        } else if (cards.get(index).toString().charAt(0) == 'o') {
            cardValue = 12;
        } else if (cards.get(index).toString().charAt(0) == 'p') {
            cardValue = 13;
        } else {
            cardValue = Character.getNumericValue(cards.get(index).toString().charAt(0));
        }
        return cardValue;
    }

    public void reset() {
        cards.clear();
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
