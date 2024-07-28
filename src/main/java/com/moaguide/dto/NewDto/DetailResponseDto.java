package com.moaguide.dto.NewDto;

import com.moaguide.domain.detail.BuildingDetail;
import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.transaction.Transaction;
import com.moaguide.dto.MusicDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Getter
@AllArgsConstructor
public class DetailResponseDto {
    private String productId;
    private String name;
    private String category;
    private String platform;
    private String price;
    private double priceRate;
    private String totalPrice;
    private double lastDivide;
    private double lastDivideRate;
    private int divideCycle;
    private String link;


    public DetailResponseDto(List<Transaction> transactions, Divide divideDtos, BuildingDetail details) {
        this.productId = divideDtos.getProductId();
        this.name = divideDtos.getName();
        this.category = divideDtos.getCategory();
        this.platform = divideDtos.getPlatform();
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
        this.lastDivide = divideDtos.getDividend();
        this.lastDivideRate = divideDtos.getDividendRate();
        this.divideCycle = details.getDividendCycle();
        this.link = "ready";
    }

    public DetailResponseDto(List<Transaction> transactions, Divide divideDtos, MusicDetailDto details) {
        this.productId = divideDtos.getProductId();
        this.name = divideDtos.getName();
        this.category = divideDtos.getCategory();
        this.platform = divideDtos.getPlatform();
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
        this.lastDivide = divideDtos.getDividend();
        this.lastDivideRate = divideDtos.getDividendRate();
        this.divideCycle = 1;
        this.link = "ready";
    }
}
