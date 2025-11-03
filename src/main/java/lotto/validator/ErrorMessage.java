package lotto.validator;

public enum ErrorMessage {
    INVALID_PURCHASE_AMOUNT("[ERROR] 구입 금액은 1,000원 단위 숫자여야 합니다."),
    INVALID_NUMBER_FORMAT("[ERROR] 로또 번호는 숫자여야 합니다."),
    INVALID_NUMBER_RANGE("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다."),
    DUPLICATE_NUMBER("[ERROR] 로또 번호는 중복될 수 없습니다."),
    INVALID_LOTTO_SIZE("[ERROR] 로또 번호는 6개여야 합니다."),
    CONTINUOUS_DELIMITER("[ERROR] 구분자는 연속해서 올 수 없습니다."),
    FIRST_LAST_DELIMITER("[ERROR] 구분자는 처음 또는 마지막에 올 수 없습니다."),
    BONUS_NUMBER_DUPLICATE("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
