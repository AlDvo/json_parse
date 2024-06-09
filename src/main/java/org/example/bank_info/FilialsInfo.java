package org.example.bank_info;

import org.example.Exchanger;
import org.example.bank_info.enums.Currency;
import org.example.bank_info.enums.TypeOfOperation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilialsInfo {
    public static final int BEGIN_INDEX_CURRENCY = 0;
    public static final int END_INDEX_CURRENCY = 3;
    public static final int BEGIN_INDEX_TYPE_OPERATION = 4;

    private String address;
    private String workTime;
    private String filialName;
    private Map<Currency, Map<TypeOfOperation, BigDecimal>> price;

    public FilialsInfo(Exchanger exchanger){
        this.address = exchanger.street_type + " " + exchanger.street + " " + exchanger.home_number;
        this.workTime = exchanger.info_worktime;
        this.filialName = exchanger.filials_text;
        this.price = setTypeOfOperations(exchanger);
    }

    private Map<Currency, Map<TypeOfOperation, BigDecimal>> setTypeOfOperations(Exchanger exchanger) {
        Map<Currency, Map<TypeOfOperation, BigDecimal>> result = new HashMap<>();

        for (Currency value : Currency.values()) {
            Map<TypeOfOperation, BigDecimal> temp = new HashMap<>();
            for (TypeOfOperation typeOfOperation : TypeOfOperation.values()) {
                BigDecimal cur = getActualPrice(exchanger, value, typeOfOperation);
                temp.put(typeOfOperation, cur);
            }
            result.put(value, temp);
        }
        return result;
    }

    private static BigDecimal getActualPrice(Exchanger exchanger, Currency value, TypeOfOperation typeOfOperation) {
        BigDecimal cur = null;
        List<Field> actualFields = Arrays.stream(Exchanger.class.getDeclaredFields()).toList();

        for (Field field : actualFields) {
            boolean equalsCurrency = field.getName().substring(BEGIN_INDEX_CURRENCY, END_INDEX_CURRENCY).toUpperCase().equals(value.name());
            boolean equalsTypeOperation = field.getName().substring(BEGIN_INDEX_TYPE_OPERATION).toUpperCase().equals(typeOfOperation.name());

            if(equalsCurrency && equalsTypeOperation) {
                try {
                    String price = (String) field.get(exchanger);
                    cur = BigDecimal.valueOf(Double.parseDouble(price));
                    break;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return cur;
    }

}
