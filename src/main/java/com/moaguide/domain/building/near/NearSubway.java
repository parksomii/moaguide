package com.moaguide.domain.building.near;


import com.moaguide.dto.NewDto.BuildingDto.NearSubwayDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name="Near_Subway")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(
        name = "NearSubMapping",
        classes = @ConstructorResult(
                targetClass = NearSubwayDto.class,
                columns = {
                        @ColumnResult(name = "station", type = String.class),
                        @ColumnResult(name = "route", type = String.class),
                        @ColumnResult(name = "distance", type = Integer.class),
                        @ColumnResult(name = "time", type = Integer.class),
                }
        )
)
@NamedStoredProcedureQuery(
        name = "NearSubProcedure",
        procedureName = "nearSub",
        resultSetMappings = "NearSubMapping",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
        }
)
public class NearSubway {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String keyword;

    private String station;

    @Column(name="Route")
    private String route;

    @Column(name="Distance")
    private int distance;

    @Column(name="Time")
    private int time;
}
