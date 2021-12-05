package com.megaease.easeagent.demo.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DamoRepository extends ElasticsearchRepository<Damo, Long> {
    List<Damo> findByName(String name);

    List<Damo> findByAge(Double age);

}
