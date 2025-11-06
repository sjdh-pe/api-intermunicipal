package br.gov.pe.sjdh.apiIntermunicipal.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path destino;

    public FileStorageService(@Value("${uploads.base-dir}") String baseDir) {
        this.destino = Paths.get(baseDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.destino);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório para uploads", e);
        }
    }

    /**
     * Armazena o arquivo fisicamente com o nome padronizado:
     *   id_beneficiario + "_" + nome_tipo_arquivo + "." + extensao
     * onde a extensão é extraída do arquivo original e o nome do tipo vem da tabela tipo_arquivo.
     * Retorna apenas o nome do arquivo (para uso na coluna path), sem diretórios.
     */
    public String store(MultipartFile file, UUID id_beneficiario, String nomeTipoArquivo) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Arquivo vazio!");
            }

            // 🔹 Valida o tipo MIME permitido
            String contentType = file.getContentType();
            if (contentType == null || !isAllowedMimeType(contentType)) {
                throw new RuntimeException("Tipo de arquivo não permitido: " + contentType);
            }

            // 🔹 Valida a extensão do arquivo e extrai a extensão
            String originalName = file.getOriginalFilename();
            if (originalName == null || !hasValidExtension(originalName)) {
                throw new RuntimeException("Extensão de arquivo inválida: " + originalName);
            }
            String extension = "";
            int lastDot = originalName.lastIndexOf('.');
            if (lastDot > 0 && lastDot < originalName.length() - 1) {
                extension = originalName.substring(lastDot + 1);
            }

            // 🔹 (Opcional) Limite de tamanho — 20MB
            long maxSize = 20L * 1024 * 1024;
            if (file.getSize() > maxSize) {
                throw new RuntimeException("Arquivo excede o limite de 20MB");
            }

            // 🔹 Monta o novo nome de arquivo no formato solicitado (sanitizando o nome do tipo)
            String safeTipo = sanitizeTipoNome(nomeTipoArquivo);
            String fileName = id_beneficiario + "_" + safeTipo + "." + extension;

            // 🔹 Caminho de destino
            Path destination = this.destino.resolve(fileName);

            // 🔹 Cria diretório se não existir
            if (!Files.exists(this.destino)) {
                Files.createDirectories(this.destino);
            }

            // 🔹 Copia o arquivo com sobrescrita
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // Retorna apenas o nome do arquivo para ser persistido na coluna path
            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar arquivo: " + e.getMessage(), e);
        }
    }

    private boolean isAllowedMimeType(String mimeType) {
        return Set.of("application/pdf", "image/jpeg", "image/png")
                .contains(mimeType.toLowerCase());
    }

    private boolean hasValidExtension(String filename) {
        String lower = filename.toLowerCase();
        return lower.endsWith(".pdf") || lower.endsWith(".jpg") ||
               lower.endsWith(".jpeg") || lower.endsWith(".png");
    }

    private String sanitizeTipoNome(String nome) {
        if (nome == null || nome.isBlank()) return "arquivo";
        String normalized = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        // Substitui espaços por underscore e remove caracteres não-alfanuméricos (mantém _ e -)
        String cleaned = normalized.trim()
                .replaceAll("\\s+", "_")
                .replaceAll("[^A-Za-z0-9_-]", "");
        return cleaned.isEmpty() ? "arquivo" : cleaned;
    }
}