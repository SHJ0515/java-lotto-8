package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;

import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String PURCHASE_INFO = "\n%d개를 구매했습니다.";
    private static final String LOTTO_NUMBERS_FORMAT = "%s";
    private static final String STATISTICS = "\n당첨 통계\n---";
    private static final String WINNING_RANK_FORMAT = "%s - %d개";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.";
    private static final String SECOND_RANK = "%d개 일치, 보너스 볼 일치 (%s원)";
    private static final String OTHER_RANK = "%d개 일치 (%s원)";

    public void printPurchaseInfo(List<Lotto> lottos) {
        System.out.println(String.format(PURCHASE_INFO, lottos.size()));
        for (Lotto lotto : lottos) {
            System.out.println(String.format(LOTTO_NUMBERS_FORMAT, lotto.getNumbers()));
        }
    }

    public void printStatistics(Map<LottoRank, Integer> result) {
        System.out.println(STATISTICS);
        printRankResult(LottoRank.FIFTH, result.get(LottoRank.FIFTH));
        printRankResult(LottoRank.FOURTH, result.get(LottoRank.FOURTH));
        printRankResult(LottoRank.THIRD, result.get(LottoRank.THIRD));
        printRankResult(LottoRank.SECOND, result.get(LottoRank.SECOND));
        printRankResult(LottoRank.FIRST, result.get(LottoRank.FIRST));
    }

    public void printProfitRate(double profitRate) {
        System.out.println(String.format(PROFIT_RATE_FORMAT, profitRate));
    }

    private void printRankResult(LottoRank rank, int count) {
        String rankDescription = getRankDescription(rank);
        System.out.println(String.format(WINNING_RANK_FORMAT, rankDescription, count));
    }

    private String getRankDescription(LottoRank rank) {
        if (rank == LottoRank.SECOND) {
            return String.format(SECOND_RANK, rank.getMatchCount(), formatPrize(rank.getReceiveAmount()));
        }
        return String.format(OTHER_RANK, rank.getMatchCount(), formatPrize(rank.getReceiveAmount()));
    }

    private String formatPrize(int prize) {
        return String.format("%,d", prize);
    }

}
