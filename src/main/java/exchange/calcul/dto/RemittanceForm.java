package exchange.calcul.dto;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

import static exchange.calcul.util.CurrencyUtil.roundTwo;

@Data
@AllArgsConstructor
public class RemittanceForm {

    private String benchCountry;
    private String transCountry;
    private String rate;
    @NotNull
    @Range(min = 10, max = 10000)
    private String remittancePrice;
    private String receptionPrice;

    public RemittanceForm(Remittance remittance){
        CurrencyRate currencyRate = remittance.getCurrencyRate();
        this.benchCountry = currencyRate.getBenchCountry();
        this.transCountry = currencyRate.getTransCountry();
        this.rate = roundTwo(currencyRate.getRate());
        this.remittancePrice = roundTwo(remittance.getRemittancePrice());
        this.receptionPrice = roundTwo(remittance.getReceptionPrice());
    }


}
