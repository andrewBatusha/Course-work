package com.market.coursework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="CpuItems")
public class Item {

    @Id
    private ObjectId id;

    @Field("cpuName")
    private String name;

    private String manufacturer;

    private int multiScore;

    private int cores;

    private int threads;

    private int baseClock;

    private int turboClock;
}
