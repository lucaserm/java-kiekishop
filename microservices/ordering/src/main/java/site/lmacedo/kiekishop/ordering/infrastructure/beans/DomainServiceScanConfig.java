package site.lmacedo.kiekishop.ordering.infrastructure.beans;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import site.lmacedo.kiekishop.ordering.domain.model.DomainService;

@Configuration
@ComponentScan(
        basePackages = "site.lmacedo.kiekishop.ordering.domain.model",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = DomainService.class
        )
)
public class DomainServiceScanConfig {
}