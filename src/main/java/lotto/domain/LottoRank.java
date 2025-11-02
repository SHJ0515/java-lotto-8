package lotto.domain;

public enum LottoRank {

    FIRST(6, false, 2_000_000_000),
    SECOND(5, true, 30_000_000),
    THIRD(5, false, 1_500_000),
    FOURTH(4, false, 50_000),
    FIFTH(3, false, 5_000),
    UNRANKED(0, false, 0);

    private final int matchCount;
    private final boolean requireBonusMatch;
    private final int receiveAmount;

    LottoRank(int matchCount, boolean requireBonusMatch, int receiveAmount) {
        this.matchCount = matchCount;
        this.requireBonusMatch = requireBonusMatch;
        this.receiveAmount = receiveAmount;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isRequireBonusMatch() {
        return requireBonusMatch;
    }

    public int getReceiveAmount() {
        return receiveAmount;
    }
}
