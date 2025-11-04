package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.Cidade;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.cidade.CidadeRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.Etnia;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.etnia.EtniaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetirada;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.localRetirada.LocalRetiradaRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.sexoBeneficiario.SexoBeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.statusBeneficio.StatusBeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoArquivo.TipoArquivo;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoArquivo.TipoArquivoRepository;
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
    private final SexoBeneficiarioRepository sexoRepository;
    private final StatusBeneficiarioRepository statusRepository;
    private final LocalRetiradaRepository localRetiradaRepository;
    private final TipoArquivoRepository tipoArquivoRepository;

    public LookupService(EtniaRepository etniaRepository,
                        CidadeRepository cidadeRepository,
                        TipoDeficienciaRepository tipoDeficienciaRepository,
                        SexoBeneficiarioRepository sexoRepository,
                        StatusBeneficiarioRepository statusRepository,
                        LocalRetiradaRepository localRetiradaRepository,
                        TipoArquivoRepository tipoArquivoRepository) {
        this.etniaRepository = etniaRepository;
        this.cidadeRepository = cidadeRepository;
        this.tipoDeficienciaRepository = tipoDeficienciaRepository;
        this.sexoRepository = sexoRepository;
        this.statusRepository = statusRepository;
        this.localRetiradaRepository = localRetiradaRepository;
        this.tipoArquivoRepository = tipoArquivoRepository;
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

    public List<SexoBeneficiario> listarSexos() {
        return sexoRepository.findAll();
    }

    public List<StatusBeneficiario> listarStatusBeneficio() {
        return statusRepository.findAll();
    }

    public List<LocalRetirada> listarLocaisRetirada() {
        return localRetiradaRepository.findAll();
    }

    public List<TipoArquivo> listarTiposArquivo() {
        return tipoArquivoRepository.findAll();
    }
}
