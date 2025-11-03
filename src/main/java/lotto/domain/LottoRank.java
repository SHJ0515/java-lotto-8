package lotto.domain;

public enum LottoRank {

    FIRST(6, false, 2_000_000_000L),
    SECOND(5, true, 30_000_000L),
    THIRD(5, false, 1_500_000L),
    FOURTH(4, false, 50_000L),
    FIFTH(3, false, 5_000L),
    UNRANKED(0, false, 0L);

    private final int matchCount;
    private final boolean requireBonusMatch;
    private final long receiveAmount;

    LottoRank(int matchCount, boolean requireBonusMatch, long receiveAmount) {
        this.matchCount = matchCount;
        this.requireBonusMatch = requireBonusMatch;
        this.receiveAmount = receiveAmount;
    }

    public static LottoRank valueOf(int matchCount, boolean bonusMatch) {
        if (matchCount == 6) {
            return FIRST;
        }
        if (matchCount == 5 && bonusMatch) {
            return SECOND;
        }
        if (matchCount == 5) {
            return THIRD;
        }
        if (matchCount == 4) {
            return FOURTH;
        }
        if (matchCount == 3) {
            return FIFTH;
        }
        return UNRANKED;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isRequireBonusMatch() {
        return requireBonusMatch;
    }

    public long getReceiveAmount() {
        return receiveAmount;
    }
}
