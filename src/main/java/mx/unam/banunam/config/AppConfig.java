package mx.unam.banunam.config;

import mx.unam.banunam.dto.CuentaCreditoDto;
import mx.unam.banunam.dto.CuentaDebitoDto;
import mx.unam.banunam.model.CuentaCredito;
import mx.unam.banunam.model.CuentaDebito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mm.typeMap(CuentaDebito.class, CuentaDebitoDto.class).addMappings(mapper ->{
            mapper.map(src -> src.getCliente().getNoCliente(), CuentaDebitoDto::setNoCliente);
        });
        mm.typeMap(CuentaCredito.class, CuentaCreditoDto.class).addMappings(mapper ->{
            mapper.map(src -> src.getCliente().getNoCliente(), CuentaCreditoDto::setNoCliente);
        });
        return mm;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
