package br.gov.pe.sjdh.apiIntermunicipal.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Tag(name = "Home", description = "Endpoints de páginas HTML (não fazem parte da API REST)")
@Hidden // Oculta este controller da documentação de API, por não expor recursos REST
public class HomeController {
    @Operation(summary = "Página inicial", description = "Renderiza a página HTML inicial (Thymeleaf)", hidden = true)
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
