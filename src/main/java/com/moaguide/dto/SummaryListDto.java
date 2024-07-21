package com.moaguide.dto;

import com.moaguide.domain.divide.Divide;
import com.moaguide.domain.divide.MusicDivide;
import com.moaguide.domain.transaction.Transaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Getter
public class SummaryListDto {
    private String product_id;
    private String platform_name;
    private String category;
    private String name;
    private String totalprice;
    private String price;
    private double price_rate;
    private double dividend;
    private double dividendRate;
    private List<Transaction> graph;
    private Integer nowPiece;

    public SummaryListDto(Transaction transaction, Divide divide, List<Transaction> graph) {
        this.product_id = transaction.getProductId();
        this.platform_name=transaction.getPlatform();
        this.category = transaction.getCategory();
        this.name = transaction.getName();
        Long total = (long) (transaction.getPiece() * transaction.getPrice());
        if (total >= 100000000) {
            BigDecimal totalPrice = new BigDecimal(total / 100000000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalprice = totalPrice + "억원";
        } else if (total >= 10000) {
            BigDecimal totalPrice = new BigDecimal(total / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalprice = totalPrice + "만원";
        } else {
            this.totalprice = total + "원";
        }
        long oneprice = transaction.getPrice();
        if (oneprice >= 10000) {
            BigDecimal price = new BigDecimal(oneprice / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.price = price + "만원";
        } else {
            this.price = oneprice + "원";
        }
        if (graph != null && graph.size() >= 2) {
            int size = graph.size();
            double lastPrice = graph.get(size - 1).getPrice();
            double secondLastPrice = graph.get(size - 2).getPrice();

            double rawPriceRate = ((lastPrice - secondLastPrice) / secondLastPrice) * 100;

            BigDecimal bd = new BigDecimal(rawPriceRate).setScale(2, RoundingMode.HALF_UP);
            this.price_rate = bd.doubleValue();
        } else {
            this.price_rate = 0;
        }
        if (divide != null) {
            this.dividend = divide.getDividend();
            this.dividendRate = divide.getDividendRate();
        } else {
            this.dividend = 0;
            this.dividendRate = 0;
        }
        this.graph = graph;
        this.nowPiece = transaction.getnowPiece();
    }

    public SummaryListDto(Transaction transaction, List<Transaction> graph) {
        this(transaction, null, graph);
    }

    public SummaryListDto(List<Transaction> transactions, Divide divide) {
        this.product_id = transactions.get(0).getProductId();
        this.platform_name= transactions.get(0).getPlatform();
        this.category = transactions.get(0).getCategory();
        this.name =  transactions.get(0).getName();
        Long total = (long) ( transactions.get(0).getPiece() *  transactions.get(0).getPrice());
        if (total >= 100000000) {
            BigDecimal totalPrice = new BigDecimal(total / 100000000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalprice = totalPrice + "억원";
        } else if (total >= 10000) {
            BigDecimal totalPrice = new BigDecimal(total / 10000.0).setScale(2, RoundingMode.HALF_UP);
            this.totalprice = totalPrice + "만원";
        } else {
            this.totalprice = total + "원";
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
            this.price_rate = bd.doubleValue();
        } else {
            this.price_rate = 0;
        }
        if (divide != null) {
            this.dividend = divide.getDividend();
            this.dividendRate = divide.getDividendRate();
        } else {
            this.dividend = 0;
            this.dividendRate = 0;
        }
        this.graph = null;
        this.nowPiece = transactions.get(0).getnowPiece();
    }
}
