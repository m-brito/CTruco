package com.contiero.lemes.newbot.services.choose_card;

import com.bueno.spi.model.CardToPlay;
import com.bueno.spi.model.GameIntel;
import com.bueno.spi.model.TrucoCard;
import com.contiero.lemes.newbot.interfaces.Choosing;

import java.util.List;

public class AgressiveChoosing implements Choosing {

    private final GameIntel intel;
    private TrucoCard vira;
    private TrucoCard bestCard;
    private TrucoCard secondBestCard;
    private TrucoCard worstCard;
    public AgressiveChoosing(GameIntel intel){
        this.intel = intel;
    }

    public CardToPlay firstRoundChooseCard(GameIntel intel){
        int bestCardValue = bestCard.relativeValue(vira);
        int secondBestCardValue = secondBestCard.relativeValue(vira);
        int worstCardValue = worstCard.relativeValue(vira);
        CardToPlay playedCard = CardToPlay.of(bestCard);
        if (intel.getOpponentCard().isPresent()){
            int opponentCardOnTableValue = intel.getOpponentCard().get().relativeValue(vira);
            if (worstCardValue >= opponentCardOnTableValue) playedCard = CardToPlay.of(worstCard);
            else if (secondBestCardValue >= opponentCardOnTableValue) playedCard = CardToPlay.of(secondBestCard);
            else if (bestCardValue > opponentCardOnTableValue) playedCard = CardToPlay.of(bestCard);
        }
        else {
            if (haveAtLeastTwoManilhas()){
                if (secondBestCardValue == 12 || secondBestCardValue == 11){
                    playedCard = CardToPlay.of(worstCard);
                }
                else if (secondBestCardValue == 10){
                    playedCard = CardToPlay.of(secondBestCard);
                }
            }
            else if (haveAtLeastOneManilha()){
                if (bestCardValue >= 11){
                    playedCard = CardToPlay.of(secondBestCard);
                }
                else if (bestCardValue == 10){
                    playedCard = CardToPlay.of(bestCard);
                }
            }
            else {
                long handPower = powerOfTheTwoBestCards();
                if (handPower >= 16 && secondBestCardValue >= 8){
                    playedCard = CardToPlay.of(secondBestCard);
                }
                else{
                    playedCard = CardToPlay.of(bestCard);
                }
            }
        }
        return playedCard;
    }
    @Override
    public CardToPlay choose(GameIntel intel) {
        return null;
    }

    private boolean haveAtLeastTwoManilhas(){
        return getManilhaAmount() >= 2;
    }

    private boolean haveAtLeastOneManilha(){
        return getManilhaAmount() >= 1;
    }

    private long getManilhaAmount() {
        List<TrucoCard> myCards = intel.getCards();
        return myCards.stream()
                .filter(card-> card.isManilha(intel.getVira()))
                .count();
    }

    private long powerOfTheTwoBestCards(){
        List<TrucoCard> myCards = intel.getCards();
        return myCards.stream()
                .mapToLong(card -> card.relativeValue(intel.getVira()))
                .sorted()
                .limit(2)
                .sum();
    }
}
