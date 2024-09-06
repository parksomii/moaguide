package com.moaguide.domain.detail;

import com.moaguide.domain.product.Product;
import com.moaguide.dto.MusicDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "MusicDetail")
public class MusicDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID", nullable = false)
    private Product productId;

    @Column(name = "introduce_song")
    private String introduceSong;

    @Column(name = "genre", length = 30)
    private String genre;

    @Column(name = "singer", length = 30)
    private String singer;

    @Column(name = "writer", length = 30)
    private String writer;

    @Column(name = "composing", length = 30)
    private String composing;

    @Column(name = "arrangements", length = 30)
    private String arrangements;

    @Column(name = "announcement_Date")
    @Temporal(TemporalType.DATE)
    private Date announcementDate;


    @Column(name = "issuance_date")
    @Temporal(TemporalType.DATE)
    private Date issuanceDate;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name="youtube_url")
    private String youtubeUrl;

    private String keyword;

    public MusicDetailDto toDTO() {
        return new MusicDetailDto(
                this.id,
                this.productId,
                this.introduceSong,
                this.genre,
                this.singer,
                this.writer,
                this.composing,
                this.arrangements,
                this.announcementDate,
                this.issuanceDate,
                this.type,
                this.youtubeUrl,
                this.keyword
        );
    }
}
