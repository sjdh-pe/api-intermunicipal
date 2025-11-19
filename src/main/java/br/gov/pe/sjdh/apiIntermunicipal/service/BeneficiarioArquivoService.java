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

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

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

    /**
     * Lista arquivos ativos de um beneficiário com URLs de download públicas.
     */
    @Transactional
    public List<ArquivoResumo> listarArquivosDoBeneficiario(UUID beneficiarioId) {
        // consulta com fetch do tipo para evitar N+1
        var lista = beneficiarioArquivoRepository.findAtivosComTipoByBeneficiarioId(beneficiarioId);
        return lista.stream().map(ba -> new ArquivoResumo(
                ba.getId(),
                ba.getTipoArquivo().getId(),
                ba.getTipoArquivo().getNome(),
                ba.getPath(),
                // monta URL: /arquivos/{id}/download
                fromCurrentContextPath()
                        .path("/arquivos/")
                        .path(String.valueOf(ba.getId()))
                        .path("/download")
                        .toUriString()
        )).toList();
    }

    /**
     * Carrega bytes do arquivo físico correspondente ao registro informado.
     */
    @Transactional
    public byte[] carregarArquivo(Long registroId) {
        BeneficiarioArquivo registro = beneficiarioArquivoRepository.findById(registroId)
                .orElseThrow(() -> new NotFoundException("Arquivo não encontrado"));
        try {
            Path path = fileStorageService.getDestino().resolve(registro.getPath());
            if (!Files.exists(path)) {
                throw new NotFoundException("Arquivo físico não encontrado");
            }
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler arquivo: " + e.getMessage(), e);
        }
    }

    /**
     * Carrega bytes + metadados (nome do arquivo e contentType) para download.
     */
    @Transactional
    public ArquivoPayload carregarArquivoComMeta(Long registroId) {
        BeneficiarioArquivo registro = beneficiarioArquivoRepository.findById(registroId)
                .orElseThrow(() -> new NotFoundException("Arquivo não encontrado"));
        try {
            String filename = registro.getPath();
            Path path = fileStorageService.getDestino().resolve(filename);
            if (!Files.exists(path)) {
                throw new NotFoundException("Arquivo físico não encontrado");
            }
            byte[] bytes = Files.readAllBytes(path);
            String contentType = inferContentType(filename);
            return new ArquivoPayload(bytes, filename, contentType);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler arquivo: " + e.getMessage(), e);
        }
    }

    private String inferContentType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".pdf")) return "application/pdf";
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }

    // DTO compacto para listar arquivos com link
    public record ArquivoResumo(
            Long idRegistro,
            Short idTipoArquivo,
            String nomeTipoArquivo,
            String path,
            String url
    ) {}

    // Payload para download
    public record ArquivoPayload(
            byte[] bytes,
            String filename,
            String contentType
    ) {}
}
