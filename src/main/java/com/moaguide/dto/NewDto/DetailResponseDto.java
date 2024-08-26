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
    private long price;
    private double priceRate;
    private long totalPrice;
    private double lastDivide;
    private double lastDivideRate;
    private int divideCycle;
    private String link;


    public DetailResponseDto(List<Transaction> transactions, Divide divideDtos, BuildingDetail details) {
        this.productId = divideDtos.getProductId();
        this.name = divideDtos.getName();
        this.category = divideDtos.getCategory();
        this.platform = divideDtos.getPlatform();
        this.totalPrice = transactions.get(0).getPiece() *  transactions.get(0).getPrice();
        this.price =  transactions.get(0).getPrice();
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
        this.totalPrice = transactions.get(0).getPiece() *  transactions.get(0).getPrice();
        this.price =  transactions.get(0).getPrice();
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
