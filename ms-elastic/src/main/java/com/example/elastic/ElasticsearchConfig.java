package com.example.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ElasticsearchConfig {

	@Bean
	public RestHighLevelClient client() {
		log.info("Elastic configuration");
		return new RestHighLevelClient(RestClient.builder(new org.apache.http.HttpHost("localhost", 9200, "http")));
	}

}
