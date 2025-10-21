package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.CidadeRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.Etnia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.EtniaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficiencia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoDeficiencia.TipoDeficienciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LookupService {

    private final EtniaRepository etniaRepository;
    private final CidadeRepository cidadeRepository;
    private final TipoDeficienciaRepository tipoDeficienciaRepository;

    public LookupService(EtniaRepository etniaRepository,
                        CidadeRepository cidadeRepository,
                        TipoDeficienciaRepository tipoDeficienciaRepository) {
        this.etniaRepository = etniaRepository;
        this.cidadeRepository = cidadeRepository;
        this.tipoDeficienciaRepository = tipoDeficienciaRepository;
    }

    public List<Etnia> listarEtnias() {
        return etniaRepository.findAll();
    }

    public List<Cidade> listarCidades() {
        return cidadeRepository.findAll();
    }

    public List<TipoDeficiencia> listarTiposDeficiencia() {
        return tipoDeficienciaRepository.findAll();
    }
}
