package lotto.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    @DisplayName("에러 메시지가 [ERROR]로 시작하는지 테스트")
    @Test
    void 모든_에러_메시지가_ERROR_접두사를_포함한다() {
        for (ErrorMessage errorMessage : ErrorMessage.values()) {
            assertThat(errorMessage.getMessage()).startsWith("[ERROR]");
        }
    }

}