package com.moaguide.domain.summary;

import com.moaguide.domain.platform.Platform;
import com.moaguide.dto.NewDto.customDto.SummaryCustomDto;
import com.moaguide.dto.SummaryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Summary")
@Entity
public class Summary {
    @Id
    @Column(name = "Product_Id")
    private String productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="Platform_Id", referencedColumnName="Platform_Id")
    private Platform PlatformId;

    private String name;

    private Integer piece;

    private Integer views;

    @Column(name="now_piece")
    private Integer nowPiece;

    public String getCategory(){return PlatformId.getCategory();}
    public String getPlatform(){return PlatformId.getPlatform();}
    public SummaryDto toDto(){
        return new SummaryDto(productId, PlatformId, name, piece, views,nowPiece);
    }
}