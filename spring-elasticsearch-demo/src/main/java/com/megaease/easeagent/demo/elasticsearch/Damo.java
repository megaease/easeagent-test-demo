/*
 * Copyright (c) 2021, MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
