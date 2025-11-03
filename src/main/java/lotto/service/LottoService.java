package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoService {

    private static final int LOTTO_PRICE_UNIT = 1000;
    private final static int LOTTO_MIN_NUMBER = 1;
    private final static int LOTTO_MAX_NUMBER = 45;
    private final static int LOTTO_SIZE = 6;

    //구매 금액에 따른 로또 생성
    public List<Lotto> generateLottos(int purchaseAmount) {
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < purchaseAmount / LOTTO_PRICE_UNIT; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER, LOTTO_SIZE);
            lottos.add(new Lotto(numbers));
        }
        return lottos;
    }

    //구매한 로또들의 당첨 결과 체크
    public Map<LottoRank, Integer> checkRanking(List<Lotto> lottos, List<Integer> winningNumbers, int bonusNumber) {
        Map<LottoRank, Integer> result = initializeResult();

        for (Lotto lotto : lottos) {
            LottoRank rank = findLottoRank(lotto, winningNumbers, bonusNumber);
            int currentCount = result.getOrDefault(rank, 0);
            result.put(rank, currentCount + 1);
        }

        return result;
    }

    //수익률 계산
    public double calculateProfitRate(Map<LottoRank, Integer> result, int purchaseAmount) {
        long totalPrize = calculateTotalPrize(result);
        return (double) totalPrize / purchaseAmount * 100;
    }

    private Map<LottoRank, Integer> initializeResult() {
        Map<LottoRank, Integer> result = new HashMap<>();
        result.put(LottoRank.FIFTH, 0);
        result.put(LottoRank.FOURTH, 0);
        result.put(LottoRank.THIRD, 0);
        result.put(LottoRank.SECOND, 0);
        result.put(LottoRank.FIRST, 0);
        return result;
    }

    private LottoRank findLottoRank(Lotto lotto, List<Integer> winningNumbers, int bonusNumber) {
        int matchCount = lotto.countMatchingNumbers(winningNumbers);
        boolean bonusMatch = lotto.containsBonusNumber(bonusNumber);
        return LottoRank.valueOf(matchCount, bonusMatch);
    }

    private long calculateTotalPrize(Map<LottoRank, Integer> result) {
        long totalPrize = 0L;
        for (Map.Entry<LottoRank, Integer> entry : result.entrySet()) {
            int receiveAmount = entry.getKey().getReceiveAmount();
            int count = entry.getValue();
            totalPrize += (long) receiveAmount * count;
        }
        return totalPrize;
    }
}
