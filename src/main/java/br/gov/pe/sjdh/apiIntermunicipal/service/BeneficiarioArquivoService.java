package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.Beneficiario;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.BeneficiarioArquivo;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioArquivoRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioRepository;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoArquivo.TipoArquivo;
import br.gov.pe.sjdh.apiIntermunicipal.domain.lookup.tipoArquivo.TipoArquivoRepository;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeneficiarioArquivoService {

    private final FileStorageService fileStorageService;
    private final BeneficiarioRepository beneficiarioRepository;
    private final TipoArquivoRepository tipoArquivoRepository;
    private final BeneficiarioArquivoRepository beneficiarioArquivoRepository;

    /**
     * Realiza o upload físico do arquivo e persiste o vínculo em beneficiario_arquivo.
     * O path salvo segue o padrão: id_beneficiario + "_" + NOME_TIPO_ARQUIVO + "." + extensao
     */
    @Transactional
    public BeneficiarioArquivo uploadAndPersist(UUID idBeneficiario, Short idTipoArquivo, MultipartFile file) {
        Beneficiario beneficiario = beneficiarioRepository.findById(idBeneficiario)
                .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));
        TipoArquivo tipoArquivo = tipoArquivoRepository.findById(idTipoArquivo)
                .orElseThrow(() -> new NotFoundException("Tipo de arquivo inválido"));

        String fileName = fileStorageService.store(file, idBeneficiario, tipoArquivo.getNome());

        BeneficiarioArquivo registro = BeneficiarioArquivo.builder()
                .beneficiario(beneficiario)
                .tipoArquivo(tipoArquivo)
                .path(fileName)
                .ativo(true)
                .build();

        return beneficiarioArquivoRepository.save(registro);
    }
}
