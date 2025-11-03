package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LottoServiceTest {

    private LottoService lottoService;

    @BeforeEach
    void setUp() {
        lottoService = new LottoService();
    }

    @DisplayName("로또 생성 기능")
    @Test
    void generateLottos() {
        //given
        int purchaseAmount = 5000;

        //when
        List<Lotto> lottos = lottoService.generateLottos(purchaseAmount);

        // then
        for (Lotto lotto : lottos) {
            assertThat(lotto.getNumbers()).hasSize(6);
        }
        assertThat(lottos.size()).isEqualTo(purchaseAmount / 1000);
    }

    @DisplayName("로또 당첨 확인 기능")
    @Test
    void checkRanking() {
        // given
        List<Lotto> lottos = List.of(
                new Lotto(new ArrayList<>(List.of(1, 2, 3, 4, 5, 6))),   // 1등 (6개 일치)
                new Lotto(new ArrayList<>(List.of(1, 2, 3, 4, 5, 7))),   // 2등 (5개 + 보너스)
                new Lotto(new ArrayList<>(List.of(1, 2, 3, 4, 5, 8))),   // 3등 (5개 일치)
                new Lotto(new ArrayList<>(List.of(1, 2, 3, 4, 10, 11))), // 4등 (4개 일치)
                new Lotto(new ArrayList<>(List.of(1, 2, 3, 10, 11, 12))),// 5등 (3개 일치)
                new Lotto(new ArrayList<>(List.of(10, 11, 12, 13, 14, 15))) // 낙첨
        );

        List<Integer> winningNumbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        // when
        Map<LottoRank, Integer> result = lottoService.checkRanking(lottos, winningNumbers, bonusNumber);

        // then
        assertThat(result.get(LottoRank.FIRST)).isEqualTo(1);
        assertThat(result.get(LottoRank.SECOND)).isEqualTo(1);
        assertThat(result.get(LottoRank.THIRD)).isEqualTo(1);
        assertThat(result.get(LottoRank.FOURTH)).isEqualTo(1);
        assertThat(result.get(LottoRank.FIFTH)).isEqualTo(1);
        assertThat(result.get(LottoRank.UNRANKED)).isEqualTo(1);
    }

    @DisplayName("수익률 계산 기능")
    @Test
    void calculateProfitRate() {
        // given
        Map<LottoRank, Integer> result = Map.of(
                LottoRank.FIFTH, 1,
                LottoRank.FOURTH, 0,
                LottoRank.THIRD, 0,
                LottoRank.SECOND, 0,
                LottoRank.FIRST, 0
        );
        int purchaseAmount = 8000;

        // when
        double profitRate = lottoService.calculateProfitRate(result, purchaseAmount);

        // then
        assertThat(profitRate).isEqualTo(62.5);  // 2,030,000,000 / 10,000 * 100
    }

    @DisplayName("오버플로우 없이 처리")
    @Test
    void calculateProfitRateWithoutOverflow() {
        // given
        Map<LottoRank, Integer> result = Map.of(
                LottoRank.FIFTH, 0,
                LottoRank.FOURTH, 0,
                LottoRank.THIRD, 0,
                LottoRank.SECOND, 1,
                LottoRank.FIRST, 1
        );
        int purchaseAmount = 10000;

        // when
        double profitRate = lottoService.calculateProfitRate(result, purchaseAmount);

        // then
        assertThat(profitRate).isEqualTo(20300000.0);  // 2,030,000,000 / 10,000 * 100
    }
}