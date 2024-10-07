/*
 *  Copyright (C) 2023 Mauricio Brito Teixeira - IFSP/SCL and Vinicius Eduardo Alves Macena - IFSP/SCL
 *  Contact: brito <dot> mauricio <at> aluno <dot> ifsp <dot> edu <dot> br
 *  Contact: macena <dot> v <at> aluno <dot> ifsp <dot> edu <dot> br
 *
 *  This file is part of CTruco (Truco game for didactic purpose).
 *
 *  CTruco is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CTruco is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CTruco.  If not, see <https://www.gnu.org/licenses/>
 */

package com.brito.macena.boteco;

import com.bueno.spi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BotEcoTest {

    private BotEco botEco;

    @BeforeEach
    void setUp() {
        botEco = new BotEco();
    }

    @Test
    @DisplayName("Should select the smallest card necessary to win")
    void selectSmallestCardToWin() {
        List<TrucoCard> botEcoHand = List.of(
                TrucoCard.of(CardRank.THREE, CardSuit.DIAMONDS),
                TrucoCard.of(CardRank.JACK, CardSuit.CLUBS),
                TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS)
        );
        TrucoCard opponentCard = TrucoCard.of(CardRank.QUEEN, CardSuit.HEARTS);
        TrucoCard vira = TrucoCard.of(CardRank.QUEEN, CardSuit.CLUBS);
        GameIntel step = GameIntel.StepBuilder.with()
                .gameInfo(List.of(), List.of(), vira, 1)
                .botInfo(botEcoHand, 0)
                .opponentScore(0).opponentCard(opponentCard)
                .build();

        CardToPlay selectedCard = botEco.chooseCard(step);
        assertThat(selectedCard).isEqualTo(CardToPlay.of(TrucoCard.of(CardRank.KING, CardSuit.DIAMONDS)));
    }
}
