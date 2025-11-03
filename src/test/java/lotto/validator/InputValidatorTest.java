package lotto.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {

    @DisplayName("구매 금액 검증: 정상 입력")
    @Test
    void 구매_금액_정상_입력() {
        int result = InputValidator.validatePurchaseAmount("8000");
        assertThat(result).isEqualTo(8000);
    }

    @DisplayName("구매 금액 검증: 1000원 단위가 아니면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"1500", "2200", "999", "10001"})
    void 구매_금액이_1000원_단위가_아니면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_PURCHASE_AMOUNT.getMessage());
    }

    @DisplayName("구매 금액 검증: 숫자가 아니면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"1000j", "abc", "1000원", "천원"})
    void 구매_금액이_숫자가_아니면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
    }

    @DisplayName("구매 금액 검증: 빈 입력이면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "  "})
    void 구매_금액이_빈_입력이면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_INPUT.getMessage());
    }

    @DisplayName("당첨 번호 검증: 정상 입력")
    @Test
    void 당첨_번호_정상_입력() {
        List<Integer> result = InputValidator.validateLottoNumbers("1,2,3,4,5,6");
        assertThat(result).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("당첨 번호 검증: 6개가 아니면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7", "1,2,3"})
    void 당첨_번호가_6개가_아니면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_LOTTO_SIZE.getMessage());
    }

    @DisplayName("당첨 번호 검증: 중복된 숫자가 있으면 예외 발생")
    @Test
    void 당첨_번호에_중복된_숫자가_있으면_예외_발생() {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers("1,2,3,4,5,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_NUMBER.getMessage());
    }

    @DisplayName("당첨 번호 검증: 범위를 벗어나면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"0,1,2,3,4,5", "1,2,3,4,5,46", "-1,2,3,4,5,6"})
    void 당첨_번호가_범위를_벗어나면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_RANGE.getMessage());
    }

    @DisplayName("당첨 번호 검증: 구분자가 맨 앞에 있으면 예외 발생")
    @Test
    void 구분자가_맨_앞에_있으면_예외_발생() {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers(",1,2,3,4,5,6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_DELIMITER_LOCATION.getMessage());
    }

    @DisplayName("당첨 번호 검증: 구분자가 맨 뒤에 있으면 예외 발생")
    @Test
    void 구분자가_맨_뒤에_있으면_예외_발생() {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers("1,2,3,4,5,6,"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_DELIMITER_LOCATION.getMessage());
    }

    @DisplayName("당첨 번호 검증: 연속된 구분자가 있으면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"1,,2,3,4,5,6", "1,2,,3,4,5,6", "1,2,3,4,5,,6"})
    void 연속된_구분자가_있으면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CONTINUOUS_DELIMITER.getMessage());
    }

    @DisplayName("당첨 번호 검증: 숫자가 아닌 값이 있으면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,a", "일,2,3,4,5,6", "1,2.5,3,4,5,6"})
    void 숫자가_아닌_값이_있으면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
    }

    @DisplayName("당첨 번호 검증: 빈 입력이면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 당첨_번호가_빈_입력이면_예외_발생(String input) {
        assertThatThrownBy(() -> InputValidator.validateLottoNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_INPUT.getMessage());
    }

    @DisplayName("보너스 번호 검증: 정상 입력")
    @Test
    void 보너스_번호_정상_입력() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        int result = InputValidator.validateBonusNumber("7", winningNumbers);
        assertThat(result).isEqualTo(7);
    }

    @DisplayName("보너스 번호 검증: 당첨 번호와 중복되면 예외 발생")
    @Test
    void 보너스_번호가_당첨_번호와_중복되면_예외_발생() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> InputValidator.validateBonusNumber("6", winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
    }

    @DisplayName("보너스 번호 검증: 범위를 벗어나면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"0", "46", "-1", "100"})
    void 보너스_번호가_범위를_벗어나면_예외_발생(String input) {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> InputValidator.validateBonusNumber(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_RANGE.getMessage());
    }

    @DisplayName("보너스 번호 검증: 숫자가 아니면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"a", "칠", "7.5"})
    void 보너스_번호가_숫자가_아니면_예외_발생(String input) {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> InputValidator.validateBonusNumber(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_FORMAT.getMessage());
    }

    @DisplayName("보너스 번호 검증: 빈 입력이면 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 보너스_번호가_빈_입력이면_예외_발생(String input) {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        assertThatThrownBy(() -> InputValidator.validateBonusNumber(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_INPUT.getMessage());
    }
}