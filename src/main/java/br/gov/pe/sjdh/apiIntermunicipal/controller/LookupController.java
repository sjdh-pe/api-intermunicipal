package br.gov.pe.sjdh.apiIntermunicipal.controller;


import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.Etnia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficiencia;
import br.gov.pe.sjdh.apiIntermunicipal.service.LookupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lookup")
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/etnias")
    public List<Etnia> listarEtnias() {
        return lookupService.listarEtnias();
    }

    @GetMapping("/cidades")
    public List<Cidade> listarCidades() {
        return lookupService.listarCidades();
    }

    @GetMapping("/tipos-deficiencia")
    public List<TipoDeficiencia> listarTiposDeficiencia() {
        return lookupService.listarTiposDeficiencia();
    }
}
