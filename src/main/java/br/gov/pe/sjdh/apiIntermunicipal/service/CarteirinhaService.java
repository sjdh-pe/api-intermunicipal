package br.gov.pe.sjdh.apiIntermunicipal.service;

import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.dto.BeneficiarioCompletoDTO;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.model.BeneficiarioArquivo;
import br.gov.pe.sjdh.apiIntermunicipal.domain.beneficiario.repository.BeneficiarioArquivoRepository;
import br.gov.pe.sjdh.apiIntermunicipal.infra.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Serviço responsável por gerar a carteirinha do beneficiário
 * a partir de um PDF template e preenchimento de campos via PDFBox.
 */
@Service
@RequiredArgsConstructor
public class CarteirinhaService {

    private final BeneficiarioService beneficiarioService;
    private final BeneficiarioArquivoRepository beneficiarioArquivoRepository;
    private final FileStorageService fileStorageService;

    //template de carteirinha
    @Value("${carteirinha.template-path:${uploads.base-dir:uploads}/TEMPLATE_PASSE_LIVRE_2.pdf}")
    private String templatePath;

    //dias de validade da carteirinha
    @Value("${carteirinha.validade-dias:365}")
    private int validadeDias;

    //caminho da foto do beneficiário
    @Value("${carteirinha.foto-path:${uploads.base-dir:uploads}/foto2.png}")
    private String fotoPath;

    // tipo_arquivo a ser utilizado como "foto" na carteirinha (default 3)
    @Value("${carteirinha.foto-tipo-id:3}")
    private short fotoTipoId;




    /**
     * Gera um PDF de carteirinha para o beneficiário informado.
     *
     * @param beneficiarioId UUID do beneficiário
     * @return bytes do PDF gerado
     */
    public byte[] gerarCarteirinha(UUID beneficiarioId) {
        // Obtém dados consolidados do beneficiário via view/DTO
        BeneficiarioCompletoDTO dto = beneficiarioService.detalhar(beneficiarioId);


        File template = new File(templatePath);
        if (!template.exists() || !template.isFile()) {
            throw new BusinessException("Template de carteirinha não encontrado em: " + template.getAbsolutePath());
        }

        try (PDDocument doc = PDDocument.load(template);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = doc.getNumberOfPages() > 0 ? doc.getPage(0) : new PDPage(PDRectangle.A4);


            if (doc.getNumberOfPages() == 0) {
                doc.addPage(page);
            }

            // Coordenadas base (em pontos) - canto inferior esquerdo da página como origem
            // Ajuste fino pode ser necessário conforme o template fornecido.

            int fontSize = 7;

            float nomeX = 100f,  nomeY = 118f;
            float maeX = 100f,   maeY = 103f;
            float cpfX = 100f,   cpfY = 88f;
            float deficienciaX = 100f, deficienciaY = 73f;
            float enderecoX = 100f, enderecoY = 59f;
            float nascX = 172f,  nascY = 88f;
            float cidadeX = 172f, cidadeY = 74f;
            float emicaoX = 100f, emicaoY = 30f;
            float validadeX = 150f, validadeY = 30f;


            try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {


                //seleciona a imagem do beneficiário para ser impressa na carteirinha
                File fotoFile = resolveFotoFile(beneficiarioId);

                //se a imagem existir, imprime na carteirinha
                if (fotoFile != null && fotoFile.exists() && fotoFile.isFile()) {
                    PDImageXObject pdImage = PDImageXObject.createFromFileByContent(fotoFile, doc);

                    // ajuste o tamanho
                    float imgWidth = 64f; // largura desejada em pontos
                    float imgHeight = 86.5f; // altura desejada em pontos

                    // posicionar no canto superior esquerdo com margem
                    float imgX = 23.5f;
                    float imgY = 42.5f;


                    // desenha imagem com cantos arredondados (radius = 10)
                    float radius = 10f;
                    float k = 0.5522847498f * radius; // constante para aproximar arco com bezier

                    cs.saveGraphicsState();
                    // caminho do rect arredondado
                    cs.moveTo(imgX + radius, imgY);
                    cs.lineTo(imgX + imgWidth - radius, imgY);
                    cs.curveTo(imgX + imgWidth - radius + k, imgY, imgX + imgWidth, imgY + radius - k, imgX + imgWidth, imgY + radius);
                    cs.lineTo(imgX + imgWidth, imgY + imgHeight - radius);
                    cs.curveTo(imgX + imgWidth, imgY + imgHeight - radius + k, imgX + imgWidth - radius + k, imgY + imgHeight, imgX + imgWidth - radius, imgY + imgHeight);
                    cs.lineTo(imgX + radius, imgY + imgHeight);
                    cs.curveTo(imgX + radius - k, imgY + imgHeight, imgX, imgY + imgHeight - radius + k, imgX, imgY + imgHeight - radius);
                    cs.lineTo(imgX, imgY + radius);
                    cs.curveTo(imgX, imgY + radius - k, imgX + radius - k, imgY, imgX + radius, imgY);
                    cs.closePath();
                    cs.clip();

                    cs.drawImage(pdImage, imgX, imgY, imgWidth, imgHeight);
                    cs.restoreGraphicsState();


                }

                //nome
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, fontSize);
                cs.newLineAtOffset(nomeX, nomeY);
                cs.showText(safe(dto.nome()));
                cs.endText();

                 //nome da mae
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize);
                cs.newLineAtOffset(maeX, maeY);
                cs.showText( dto.nomeMae());
                cs.endText();


                //cpf
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize);
                cs.newLineAtOffset(cpfX, cpfY);
                cs.showText( formatCpf(dto.cpf()));
                cs.endText();

                 //deficiencia
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize);
                cs.newLineAtOffset(deficienciaX, deficienciaY);
                cs.showText( dto.tipoDeficiencia());
                cs.endText();

                 //endereco
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize - 2);
                cs.newLineAtOffset(enderecoX, enderecoY);
                cs.showText( dto.enderecoCompleto());
                cs.endText();

                //nascimento
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize);
                cs.newLineAtOffset(nascX, nascY);
                String nasc = dto.dataNascimento() != null ? dto.dataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
                cs.showText( nasc);
                cs.endText();

                //cidade
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, fontSize - 2);
                cs.newLineAtOffset(cidadeX, cidadeY);
                cs.showText( safe(dto.cidade()) + "-" + safe(dto.uf()));
                cs.endText();

                 //data emissão
                LocalDate emicao = LocalDate.now();
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, fontSize - 2);
                cs.newLineAtOffset(emicaoX, emicaoY);
                cs.showText(emicao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cs.endText();

                //validade até
                LocalDate validade = LocalDate.now().plusDays(validadeDias);
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_OBLIQUE, fontSize - 2);
                cs.newLineAtOffset(validadeX, validadeY);
                cs.showText(validade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cs.endText();
            }

            doc.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new BusinessException("Falha ao gerar carteirinha: " + e.getMessage());
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    private String formatCpf(String cpf) {
        if (cpf == null) return "";
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() == 11) {
            return digits.substring(0,3) + "." + digits.substring(3,6) + "." + digits.substring(6,9) + "-" + digits.substring(9);
        }
        return cpf;
    }

    /**
     * Resolve o arquivo de foto do beneficiário para ser impresso na carteirinha.
     * Regras:
     * - Tenta encontrar o registro ativo mais recente do tipo "foto3x4" (id.tipo_arquivo = 3).
     * - Se encontrar e o arquivo físico existir, usa esse arquivo.
     * - Caso contrário, tenta usar o caminho padrão configurado em carteirinha.foto-path (fallback).
     * - Se nenhum existir, retorna null e a carteirinha será gerada sem imagem.
     */
    private File resolveFotoFile(UUID beneficiarioId) {
        // Busca registro mais recente do tipo foto
        java.util.Optional<BeneficiarioArquivo> opt =
                beneficiarioArquivoRepository.findTop1ByBeneficiario_IdAndTipoArquivo_IdAndAtivoTrueOrderByCreatedAtDesc(
                        beneficiarioId, fotoTipoId
                );

        if (opt.isPresent()) {
            String path = opt.get().getPath();
            File f = fileStorageService.getDestino().resolve(path).toFile();
            if (f.exists() && f.isFile()) {
                return f;
            }
        }

        // Fallback para foto padrão
        File fallback = new File(fotoPath);
        if (fallback.exists() && fallback.isFile()) {
            return fallback;
        }
        return null;
    }
}
