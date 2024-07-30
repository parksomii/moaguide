package com.moaguide.dto.NewDto.customDto;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryCustomDto {
    // 주요 상품 현황 및 상품 목록
    private String product_Id;
    private String category;
    private String platform;
    private String name;
    private String price;
    private double priceRate;
    private String totalPrice;
    private double dividend;
    private double lastDivide_rate;


    public SummaryCustomDto(List<Transaction> transactions){
        this.product_Id = transactions.get(0).getProductId();
        this.platform= transactions.get(0).getPlatform();
        this.category = transactions.get(0).getCategory();
        this.name =  transactions.get(0).getName();
        Long total = (long) ( transactions.get(0).getPiece() *  transactions.get(0).getPrice());
        if (total >= 100000000) {
            BigDecimal totalPrice = new BigDecimal(total / 100000000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalPrice = totalPrice + "억원";
        } else if (total >= 10000) {
            BigDecimal totalPrice = new BigDecimal(total / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalPrice = totalPrice + "만원";
        } else {
            this.totalPrice = total + "원";
        }
        long oneprice =  transactions.get(0).getPrice();
        if (oneprice >= 10000) {
            BigDecimal price = new BigDecimal(oneprice / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.price = price + "만원";
        } else {
            this.price = oneprice + "원";
        }
        if ( transactions != null &&  transactions.size() >= 2) {
            int size =  transactions.size();
            double lastPrice =  transactions.get(size - 1).getPrice();
            double secondLastPrice = transactions.get(size - 2).getPrice();

            double rawPriceRate = ((lastPrice - secondLastPrice) / secondLastPrice) * 100;

            BigDecimal bd = new BigDecimal(rawPriceRate).setScale(2, RoundingMode.HALF_UP);
            this.priceRate = bd.doubleValue();
        } else {
            this.priceRate = 0;
        }
        this.dividend=0;
        this.lastDivide_rate=0;
    }

    public SummaryCustomDto(List<Transaction> transactions, Divide divide) {
        this.product_Id = transactions.get(0).getProductId();
        this.platform= transactions.get(0).getPlatform();
        this.category = transactions.get(0).getCategory();
        this.name =  transactions.get(0).getName();
        Long total = (long) ( transactions.get(0).getPiece() *  transactions.get(0).getPrice());
        if (total >= 100000000) {
            BigDecimal totalPrice = new BigDecimal(total / 100000000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalPrice = totalPrice + "억원";
        } else if (total >= 10000) {
            BigDecimal totalPrice = new BigDecimal(total / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalPrice = totalPrice + "만원";
        } else {
            this.totalPrice = total + "원";
        }
        long oneprice =  transactions.get(0).getPrice();
        if (oneprice >= 10000) {
            BigDecimal price = new BigDecimal(oneprice / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.price = price + "만원";
        } else {
            this.price = oneprice + "원";
        }
        if ( transactions != null &&  transactions.size() >= 2) {
            int size =  transactions.size();
            double lastPrice =  transactions.get(size - 1).getPrice();
            double secondLastPrice = transactions.get(size - 2).getPrice();

            double rawPriceRate = ((lastPrice - secondLastPrice) / secondLastPrice) * 100;

            BigDecimal bd = new BigDecimal(rawPriceRate).setScale(2, RoundingMode.HALF_UP);
            this.priceRate = bd.doubleValue();
        } else {
            this.priceRate = 0;
        }
        if (divide != null) {
            this.dividend = divide.getDividend();
            this.lastDivide_rate = divide.getDividendRate();
        } else {
            this.dividend = 0;
            this.lastDivide_rate = 0;
        }
    }
}
