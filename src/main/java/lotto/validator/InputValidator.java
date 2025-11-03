package lotto.validator;

import java.util.*;

public class InputValidator {

    private final static String DELIMITER = ",";
    private final static int LOTTO_PRICE_UNIT = 1000;
    private final static int LOTTO_MIN_NUMBER = 1;
    private final static int LOTTO_MAX_NUMBER = 45;
    private final static int LOTTO_SIZE = 6;

    //구매 금액 검증
    public int validatePurchaseAmount(String input) {
        validateEmpty(input);
        int amount = validateNumeric(input);
        if (amount % LOTTO_PRICE_UNIT != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
        }
        return amount;
    }

    //입력 당첨 번호 검증
    public List<Integer> validateLottoNumbers(String input) {
        validateEmpty(input);
        validateDelimiterLocation(input);
        validateDelimiterContinuous(input);

        String[] tokens = input.split(DELIMITER);
        List<Integer> numbers = parseNumbers(tokens);

        validateLottoSize(numbers);
        validateDuplicate(numbers);
        validateNumberRange(numbers);

        return numbers;
    }

    //입력 보너스 번호 검증
    public int validateBonusNumber(String input, List<Integer> winningNumbers) {
        validateEmpty(input);
        int bonusNumber = validateNumeric(input);
        validateNumberRange(List.of(bonusNumber));

        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        }

        return bonusNumber;
    }

    private void validateEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_INPUT.getMessage());
        }
    }

    private int validateNumeric(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
        }
    }

    private List<Integer> parseNumbers(String[] tokens) {
        List<Integer> numbers = new ArrayList<>();

        for (String token : tokens) {
            String trimmedToken = token.trim();
            int number = this.validateNumeric(trimmedToken);
            numbers.add(number);
        }
        return numbers;
    }

    private void validateLottoSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_SIZE.getMessage());
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_NUMBER.getMessage());
        }
    }

    private void validateNumberRange(List<Integer> numbers) {
        for (int number : numbers) {
            if (number < LOTTO_MIN_NUMBER || number > LOTTO_MAX_NUMBER) {
                throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_RANGE.getMessage());
            }
        }
    }

    private void validateDelimiterLocation(String input) {
        String trimmedInput = input.trim();

        if (trimmedInput.startsWith(DELIMITER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DELIMITER_LOCATION.getMessage());
        }

        if (trimmedInput.endsWith(DELIMITER)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DELIMITER_LOCATION.getMessage());
        }
    }

    private void validateDelimiterContinuous(String input) {
        String delimiterPattern = DELIMITER + DELIMITER;

        if (input.contains(delimiterPattern)) {
            throw new IllegalArgumentException(ErrorMessage.CONTINUOUS_DELIMITER.getMessage());
        }
    }
}
