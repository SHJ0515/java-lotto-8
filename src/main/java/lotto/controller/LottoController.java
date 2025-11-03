package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.service.LottoService;
import lotto.validator.InputValidator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.Map;

public class LottoController {

    private final LottoService lottoService;
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController() {
        this.lottoService = new LottoService();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        int purchaseAmount = getPurchaseAmount();
        List<Lotto> lottos = generateAndPrintLottos(purchaseAmount);

        List<Integer> winningNumbers = getWinningNumbers();
        int bonusNumber = getBonusNumber(winningNumbers);

        printResult(lottos, winningNumbers, bonusNumber, purchaseAmount);
    }

    private int getPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.inputPurchaseAmount();
                return InputValidator.validatePurchaseAmount(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private List<Lotto> generateAndPrintLottos(int purchaseAmount) {
        List<Lotto> lottos = lottoService.generateLottos(purchaseAmount);
        outputView.printPurchaseInfo(lottos);
        return lottos;
    }

    private List<Integer> getWinningNumbers() {
        System.out.println();
        while (true) {
            try {
                String input = inputView.inputLottoNumber();
                return InputValidator.validateLottoNumbers(input);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private int getBonusNumber(List<Integer> winningNumbers) {
        System.out.println();
        while (true) {
            try {
                String input = inputView.inputBonusNumber();
                return InputValidator.validateBonusNumber(input, winningNumbers);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void printResult(List<Lotto> lottos, List<Integer> winningNumbers,
                             int bonusNumber, int purchaseAmount) {
        Map<LottoRank, Integer> result = lottoService.checkRanking(lottos, winningNumbers, bonusNumber);
        double profitRate = lottoService.calculateProfitRate(result, purchaseAmount);

        outputView.printStatistics(result);
        outputView.printProfitRate(profitRate);
    }
}