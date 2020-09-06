package br.com.gps.tracker.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages="br.com.gps.tracker.tasks")
public class PositionPurgeJobConfig {}
