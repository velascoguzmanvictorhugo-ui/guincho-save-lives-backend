package br.com.fiap.guincho.config;

import br.com.fiap.guincho.dominio.GeradorDeRelatorio;
import br.com.fiap.guincho.infra.arquivo.RelacaoDeChamadosEmArquivo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "PATCH",
                                "DELETE",
                                "OPTIONS"
                        )
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        .maxAge(3600);
            }
        };
    }

    @Bean
    public GeradorDeRelatorio geradorDeRelatorio() {
        try {
            File relatorio = new File("relatorio-chamados.txt");
            return new RelacaoDeChamadosEmArquivo(relatorio);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar o arquivo de relatório: " + e.getMessage(), e);
        }
    }
}
