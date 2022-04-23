package exchange.calcul.api;

import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/currencyRate")
    public CurrencyRateForm currencyRate(@RequestBody CurrencyRateForm changeCurrency){
        return dataService.reqCurrencyRate(changeCurrency);
    }

    @PostMapping("/remittance")
    public RemittanceForm remittance(@RequestBody RemittanceForm form){
        return dataService.reqRemittance(form);
    }

    @GetMapping("/currencyRateAll")
    public ModelMap currencyRateAll(){
        return new ModelMap(dataService.CurrencyRateAll());
    }

    @GetMapping("/dataSave")
    public void dataSave(){
        dataService.rateDataSave();
    }

}