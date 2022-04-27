package exchange.calcul.service;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.repository.CurrencyRateRepository;
import exchange.calcul.repository.RemittanceRepository;
import exchange.calcul.util.ApiConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DataService {

    private final CurrencyRateRepository currencyRateRepository;
    private final RemittanceRepository remittanceRepository;
    private final ApiConnect apiConnect;

    public CurrencyRateForm reqCurrencyRateForm(CurrencyRateForm currencyRateForm){
        return  new CurrencyRateForm(reqCurrencyRate(currencyRateForm));
    }

    public CurrencyRate reqCurrencyRate(CurrencyRateForm currencyRateForm){
        List<CurrencyRate> CurrencyRates = apiConnect.currencyRatesExtraction(apiConnect.requestCurrencyApi());
        return findOneCurrencyRate(CurrencyRates, currencyRateForm);
    }

    public CurrencyRate findOneCurrencyRate(List<CurrencyRate> crList, CurrencyRateForm currencyRateForm){
        return crList.stream()
                .filter(cr -> cr.getBenchCountry().equals(currencyRateForm.getBenchCountry())
                        && cr.getTransCountry().equals(currencyRateForm.getTransCountry())
                ).collect(Collectors.toList()).get(0);
    }

    public void currencyRateSave(List<CurrencyRate> list){
        currencyRateRepository.saveAllAndFlush(list);
    }

    public RemittanceForm reqRemittance(RemittanceForm form) {
        CurrencyRate currencyRate = CurrencyRate.createCurrencyRate(form);
        Remittance newRemittance = Remittance.createRemittance(form, currencyRate);
        currencyRateRepository.save(currencyRate);
        remittanceRepository.save(newRemittance);
        return new RemittanceForm(newRemittance);
    }

}
