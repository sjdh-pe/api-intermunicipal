package br.gov.pe.sjdh.apiIntermunicipal.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
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

    public String store(MultipartFile file, UUID id_beneficiario) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Arquivo vazio!");
            }

            // 🔹 Valida o tipo MIME permitido
            String contentType = file.getContentType();
            if (contentType == null || !isAllowedMimeType(contentType)) {
                throw new RuntimeException("Tipo de arquivo não permitido: " + contentType);
            }

            // 🔹 Valida a extensão do arquivo
            String originalName = file.getOriginalFilename();
            if (originalName == null || !hasValidExtension(originalName)) {
                throw new RuntimeException("Extensão de arquivo inválida: " + originalName);
            }

            // 🔹 (Opcional) Limite de tamanho — 20MB
            long maxSize = 20L * 1024 * 1024;
            if (file.getSize() > maxSize) {
                throw new RuntimeException("Arquivo excede o limite de 20MB");
            }

            // 🔹 Monta o novo nome de arquivo
            String fileName = id_beneficiario + "_" + originalName;

            // 🔹 Caminho de destino
            Path destination = this.destino.resolve(fileName);

            // 🔹 Cria diretório se não existir
            if (!Files.exists(this.destino)) {
                Files.createDirectories(this.destino);
            }

            // 🔹 Copia o arquivo com sobrescrita
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return destination.toString();

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
}