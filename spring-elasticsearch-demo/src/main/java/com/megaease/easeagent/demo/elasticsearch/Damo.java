package com.megaease.easeagent.demo.elasticsearch;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "easeagent_damo_index")
public class Damo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @ApiModelProperty(value = "name")
    @Field(type = FieldType.Keyword)
    private String name;

    @ApiModelProperty(value = "age")
    @Field(type = FieldType.Double)
    private Double age;


    @ApiModelProperty(value = "dec")
    @Field(type = FieldType.Keyword)
    private String dec;
}
