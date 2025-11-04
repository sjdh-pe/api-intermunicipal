-- ========================================
-- SCHEMA COMPLETO - 2025-10-29
-- Autor: Raul Michel de França
-- Inclui: Tabelas, FKs, Triggers, Views
-- ========================================

SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET COLLATION_CONNECTION = 'utf8mb4_general_ci';

-- ==============================
-- TABELAS AUXILIARES
-- ==============================

CREATE TABLE `tipos_deficiencia` (
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(50) NOT NULL,
    `descricao` VARCHAR(200),
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `tipos_deficiencia_nome_unique` (`nome`)
);

-- Inserir dados iniciais
INSERT INTO tipos_deficiencia (id, nome, descricao) VALUES
(1, 'Física', 'Deficiência que afeta a mobilidade e coordenação motora'),
(2, 'Visual', 'Deficiência relacionada à visão (cegueira ou baixa visão)'),
(3, 'Auditiva', 'Deficiência relacionada à audição (surdez ou baixa audição)'),
(4, 'Intelectual', 'Deficiência que afeta o desenvolvimento intelectual'),
(5, 'Múltipla', 'Combinação de duas ou mais deficiências');


CREATE TABLE `status_beneficio` (
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(30) NOT NULL,
    `descricao` VARCHAR(100),
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `status_beneficio_nome_unique` (`nome`)
);

-- Inserir dados iniciais
INSERT INTO status_beneficio (id, nome) VALUES
(1, 'Em análise'),
(2, 'Pendente'),
(3, 'Negado'),
(4, 'Aprovado'),
(5, 'Entregue'),
(6, 'Enviadas para Confecção'),
(7, 'Entregue aos Correios'),
(8, 'Entregue ao Município'),
(9, 'Disponível para Retirada'),
(10, 'Pendência Sancionada');


CREATE TABLE `locais_retirada` (
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(50) NOT NULL,
    `endereco` VARCHAR(200),
    `telefone` VARCHAR(20),
    `horario_funcionamento` VARCHAR(100),
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `locais_retirada_nome_unique` (`nome`)
);

-- Inserir dados iniciais
INSERT INTO locais_retirada (id, nome, endereco) VALUES
(1, 'Recife', 'Av. Conde da Boa Vista nº 1410, Empresarial Palmira, 4º andar, Boa Vista, Recife (PE)'),
(2, 'Caruaru', 'Endereço da unidade de Caruaru'),
(3, 'Paulista', 'Endereço da unidade de Paulista'),
(4, 'Jaboatão dos Guararapes', 'Endereço da unidade de Jaboatão'),
(5, 'Olinda', 'Endereço da unidade de Olinda');

CREATE TABLE `etnias` (
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(30) NOT NULL,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `etnias_nome_unique` (`nome`)
);

-- Inserir dados iniciais (baseado no IBGE)
INSERT INTO etnias (id, nome) VALUES
(1, 'Branca'),
(2, 'Preta'),
(3, 'Parda'),
(4, 'Amarela'),
(5, 'Indígena'),
(6, 'Não Declarada');

CREATE TABLE `sexos` (
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(20) NOT NULL,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `sexos_nome_unique` (`nome`)
);

-- Inserir dados iniciais
INSERT INTO sexos (id, nome) VALUES
(1, 'Mulher cisgênera'),
(2, 'Mulher transsexual'),
(3, 'Homem cisgênero'),
(4, 'Homem transsexual'),
(5, 'Travesti'),
(6, 'Não binário'),
(7, 'Prefiro não informar');

CREATE TABLE `cidades` (
    `id` SMALLINT UNSIGNED NOT NULL PRIMARY KEY,
    `nome` VARCHAR(80) NOT NULL,
    `uf` CHAR(2) NOT NULL DEFAULT 'PE',
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `cidades_nome_unique` (`nome`),
    INDEX `cidades_uf_index` (`uf`)
);


-- Inserir dados das cidades de Pernambuco
INSERT INTO cidades (id, nome) VALUES
(5135, 'ABREU E LIMA'),
(5136, 'AFOGADOS DA INGAZEIRA'),
(5137, 'AFRÂNIO'),
(5138, 'AGRESTINA'),
(5140, 'ÁGUA PRETA'),
(5141, 'ÁGUAS BELAS'),
(5143, 'ALAGOINHA'),
(5146, 'ALIANÇA'),
(5147, 'ALTINHO'),
(5148, 'AMARAJI'),
(5150, 'ANGELIM'),
(5152, 'ARAÇOIABA'),
(5153, 'ARARIPINA'),
(5154, 'ARCOVERDE'),
(5158, 'BARRA DE GUABIRABA'),
(5165, 'BARREIROS'),
(5167, 'BELÉM DE MARIA'),
(5168, 'BELÉM DO SÃO FRANCISCO'),
(5169, 'BELO JARDIM'),
(5173, 'BETÂNIA'),
(5174, 'BEZERROS'),
(5177, 'BODOCÓ'),
(5178, 'BOM CONSELHO'),
(5179, 'BOM JARDIM'),
(5182, 'BONITO'),
(5183, 'BREJÃO'),
(5184, 'BREJINHO'),
(5185, 'BREJO DA MADRE DE DEUS'),
(5186, 'BUENOS AIRES'),
(5187, 'BUÍQUE'),
(5189, 'CABO DE SANTO AGOSTINHO'),
(5190, 'CABROBÓ'),
(5192, 'CACHOEIRINHA'),
(5193, 'CAETÉS'),
(5195, 'CALÇADO'),
(5197, 'CALUMBI'),
(5198, 'CAMARAGIBE'),
(5200, 'CAMOCIM DE SÃO FÉLIX'),
(5201, 'CAMUTANGA'),
(5203, 'CANHOTINHO'),
(5204, 'CAPOEIRAS'),
(5211, 'CARNAÍBA'),
(5212, 'CARNAUBEIRA DA PENHA'),
(5214, 'CARPINA'),
(5216, 'CARUARU'),
(5217, 'CASINHAS'),
(5218, 'CATENDE'),
(5222, 'CEDRO'),
(5223, 'CHÃ DE ALEGRIA'),
(5225, 'CHÃ GRANDE'),
(5231, 'CONDADO'),
(5232, 'CORRENTES'),
(5233, 'CORTÊS'),
(5239, 'CUMARU'),
(5240, 'CUPIRA'),
(5242, 'CUSTÓDIA'),
(5244, 'DORMENTES'),
(5246, 'ESCADA'),
(5248, 'EXU'),
(5250, 'FEIRA NOVA'),
(5252, 'FERNANDO DE NORONHA'),
(5253, 'FERREIROS'),
(5254, 'FLORES'),
(5255, 'FLORESTA'),
(5256, 'FREI MIGUELINHO'),
(5258, 'GAMELEIRA'),
(5259, 'GARANHUNS'),
(5260, 'GLÓRIA DO GOITÁ'),
(5261, 'GOIANA'),
(5263, 'GRANITO'),
(5264, 'GRAVATÁ'),
(5270, 'IATI'),
(5271, 'IBIMIRIM'),
(5272, 'IBIRAJUBA'),
(5281, 'IGARASSU'),
(5282, 'IGUARACI'),
(5293, 'ILHA DE ITAMARACÁ'),
(5283, 'INAJÁ'),
(5284, 'INGAZEIRA'),
(5285, 'IPOJUCA'),
(5286, 'IPUBI'),
(5291, 'ITACURUBA'),
(5292, 'ITAÍBA'),
(5294, 'ITAMBÉ'),
(5295, 'ITAPETIM'),
(5296, 'ITAPISSUMA'),
(5297, 'ITAQUITINGA'),
(5302, 'JABOATÃO DOS GUARARAPES'),
(5304, 'JAQUEIRA'),
(5305, 'JATAÚBA'),
(5307, 'JATOBÁ'),
(5309, 'JOÃO ALFREDO'),
(5310, 'JOAQUIM NABUCO'),
(5314, 'JUCATI'),
(5315, 'JUPI'),
(5316, 'JUREMA'),
(5321, 'LAGOA DO CARRO'),
(5322, 'LAGOA DO ITAENGA'),
(5323, 'LAGOA DO OURO'),
(5325, 'LAGOA DOS GATOS'),
(5326, 'LAGOA GRANDE'),
(5329, 'LAJEDO'),
(5331, 'LIMOEIRO'),
(5334, 'MACAPARANA'),
(5335, 'MACHADOS'),
(5337, 'MANARI'),
(5341, 'MARAIAL'),
(5345, 'MIRANDIBA'),
(5347, 'MOREILÂNDIA'),
(5348, 'MORENO'),
(5355, 'NAZARÉ DA MATA'),
(5362, 'OLINDA'),
(5365, 'OROBÓ'),
(5366, 'OROCÓ'),
(5367, 'OURICURI'),
(5369, 'PALMARES'),
(5370, 'PALMEIRINA'),
(5371, 'PANELAS'),
(5377, 'PARANATAMA'),
(5379, 'PARNAMIRIM'),
(5381, 'PASSIRA'),
(5383, 'PAUDALHO'),
(5384, 'PAULISTA'),
(5385, 'PEDRA'),
(5387, 'PESQUEIRA'),
(5388, 'PETROLÂNDIA'),
(5389, 'PETROLINA'),
(5391, 'POÇÃO'),
(5395, 'POMBOS'),
(5399, 'PRIMAVERA'),
(5400, 'QUIPAPÁ'),
(5402, 'QUIXABÁ'),
(5406, 'RECIFE'),
(5407, 'RIACHO DAS ALMAS'),
(5411, 'RIBEIRÃO'),
(5413, 'RIO FORMOSO'),
(5414, 'SAIRÉ'),
(5415, 'SALGADINHO'),
(5416, 'SALGUEIRO'),
(5417, 'SALOÁ'),
(5419, 'SANHARÓ'),
(5420, 'SANTA CRUZ'),
(5421, 'SANTA CRUZ DA BAIXA VERDE'),
(5422, 'SANTA CRUZ DO CAPIBARIBE'),
(5423, 'SANTA FILOMENA'),
(5424, 'SANTA MARIA DA BOA VISTA'),
(5425, 'SANTA MARIA DO CAMBUCÁ'),
(5427, 'SANTA TEREZINHA'),
(5433, 'SÃO BENEDITO DO SUL'),
(5434, 'SÃO BENTO DO UNA'),
(5436, 'SÃO CAITANO'),
(5438, 'SÃO JOÃO'),
(5439, 'SÃO JOAQUIM DO MONTE'),
(5441, 'SÃO JOSÉ DA COROA GRANDE'),
(5442, 'SÃO JOSÉ DO BELMONTE'),
(5443, 'SÃO JOSÉ DO EGITO'),
(5445, 'SÃO LOURENÇO DA MATA'),
(5448, 'SÃO VICENTE FERRER'),
(5453, 'SERRA TALHADA'),
(5454, 'SERRITA'),
(5456, 'SERTÂNIA'),
(5459, 'SIRINHAÉM'),
(5461, 'SOLIDÃO'),
(5462, 'SURUBIM'),
(5463, 'TABIRA'),
(5465, 'TACAIMBÓ'),
(5466, 'TACARATU'),
(5467, 'TAMANDARÉ'),
(5470, 'TAQUARITINGA DO NORTE'),
(5474, 'TEREZINHA'),
(5475, 'TERRA NOVA'),
(5476, 'TIMBAÚBA'),
(5478, 'TORITAMA'),
(5479, 'TRACUNHAÉM'),
(5482, 'TRINDADE'),
(5483, 'TRIUNFO'),
(5485, 'TUPANATINGA'),
(5487, 'TUPARETAMA'),
(5496, 'VENTUROSA'),
(5497, 'VERDEJANTE'),
(5498, 'VERTENTE DO LÉRIO'),
(5499, 'VERTENTES'),
(5500, 'VICÊNCIA'),
(5503, 'VITÓRIA DE SANTO ANTÃO'),
(5505, 'XEXÉU');


-- ==============================
-- TABELAS PRINCIPAIS
-- ==============================

CREATE TABLE `responsaveis` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(80) NOT NULL,
    `cpf` CHAR(11) NOT NULL,
    `rg` VARCHAR(15) NOT NULL,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `responsaveis_cpf_unique` (`cpf`)
);

CREATE TABLE `usuarios` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(100) NOT NULL,
    `cpf` CHAR(11) NOT NULL,
    `senha` VARCHAR(255) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE KEY `usuarios_cpf_unique` (`cpf`),
    UNIQUE KEY `usuarios_email_unique` (`email`)
);

CREATE TABLE `tipo_arquivo` (
    `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome` VARCHAR(32) NOT NULL,
    `descricao` VARCHAR(150),
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `tipo_arquivo_nome_unique` (`nome`)
);

-- Inserir dados tipo_arquivo
INSERT INTO tipo_arquivo (nome, descricao, ativo, created_at)
VALUES
    ('RG', 'Documento de Identidade (frente e verso)', TRUE, CURRENT_TIMESTAMP),
    ('CPF', 'Cadastro de Pessoa Física', TRUE, CURRENT_TIMESTAMP),
    ('Foto 3x4', 'Fotografia recente para identificação', TRUE, CURRENT_TIMESTAMP),
    ('Cartão VEM', 'Cópia do cartão VEM Livre Acesso, se houver', TRUE, CURRENT_TIMESTAMP),
    ('Comprovante de Residência', 'Conta de luz, água ou telefone recente', TRUE, CURRENT_TIMESTAMP),
    ('Laudo Médico', 'Laudo médico que comprove a deficiência', TRUE, CURRENT_TIMESTAMP),
    ('Certidão de Nascimento', 'Certidão de nascimento original do beneficiário', TRUE, CURRENT_TIMESTAMP),
    ('Certidão de Casamento', 'Certidão de casamento original do beneficiário', TRUE, CURRENT_TIMESTAMP),
    ('Declaração Escolar', 'Documento emitido pela instituição de ensino', TRUE, CURRENT_TIMESTAMP),
    ('Comprovante de Renda', 'Holerite, declaração de imposto de renda ou documento equivalente', TRUE, CURRENT_TIMESTAMP),
    ('Termo de Responsabilidade', 'Documento assinado pelo responsável legal', TRUE, CURRENT_TIMESTAMP),
    ('Ofício', 'Documento institucional enviado por órgão público', TRUE, CURRENT_TIMESTAMP),
    ('Outros', 'Qualquer outro tipo de documento adicional', TRUE, CURRENT_TIMESTAMP);

CREATE TABLE `beneficiarios` (
     -- Identificação
    `id` BINARY(16) NOT NULL PRIMARY KEY,
    `nome` VARCHAR(100) NOT NULL,
    `nome_mae` VARCHAR(100) NOT NULL,
    `cpf` CHAR(11) NOT NULL,
    `rg` VARCHAR(20) NOT NULL,
    `data_nascimento` DATE NOT NULL,

    `id_responsavel` INT UNSIGNED NULL,
    `sexo_id` TINYINT UNSIGNED NOT NULL,
    `etnia_id` TINYINT UNSIGNED NOT NULL,
    `tipo_deficiencia_id` TINYINT UNSIGNED NOT NULL,
    `status_beneficio_id` TINYINT UNSIGNED NOT NULL DEFAULT 1,
    `local_retirada_id` TINYINT UNSIGNED NOT NULL,

    `vem_livre_acesso_rmr` BOOLEAN NOT NULL DEFAULT FALSE,

    -- Contato
    `telefone` VARCHAR(15) NOT NULL,
    `email` VARCHAR(100),

    -- Endereço
    `endereco` VARCHAR(200) NOT NULL,
    `numero` VARCHAR(10),
    `complemento` VARCHAR(50),
    `bairro` VARCHAR(80) NOT NULL,
    `cidade_id` SMALLINT UNSIGNED NOT NULL,
    `uf` CHAR(2) NOT NULL DEFAULT 'PE',
    `cep` CHAR(9),

    -- Metadados
    `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,

    UNIQUE KEY `beneficiarios_cpf_unique` (`cpf`),

    -- Índices
    INDEX idx_cpf (cpf),
    INDEX idx_nome (nome),
    INDEX idx_status_beneficio (status_beneficio_id),
    INDEX idx_local_retirada (local_retirada_id),
    INDEX idx_tipo_deficiencia (tipo_deficiencia_id),
    INDEX idx_created_at (created_at),
    INDEX idx_cidade (cidade_id)

);

CREATE TABLE `beneficiario_arquivo` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `beneficiario_id` BINARY(16) NOT NULL,
    `tipo_arquivo_id` TINYINT UNSIGNED NOT NULL,
    `path` VARCHAR(255) NOT NULL,
    `ativo` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `historico_status_beneficiario` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `beneficiario_id` BINARY(16) NOT NULL,
    `status_anterior_id` TINYINT UNSIGNED,
    `status_novo_id` TINYINT UNSIGNED NOT NULL,
    `motivo` TEXT,
    `usuario_id` INT UNSIGNED,
    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_hist_status_beneficiario_beneficiario` (`beneficiario_id`, `status_novo_id`)
);

-- ==============================
-- FOREIGN KEYS
-- ==============================

ALTER TABLE `beneficiarios`
  ADD CONSTRAINT `fk_beneficiarios_responsavel` FOREIGN KEY (`id_responsavel`) REFERENCES `responsaveis`(`id`),
  ADD CONSTRAINT `fk_beneficiarios_tipo_deficiencia` FOREIGN KEY (`tipo_deficiencia_id`) REFERENCES `tipos_deficiencia`(`id`),
  ADD CONSTRAINT `fk_beneficiarios_status_beneficio` FOREIGN KEY (`status_beneficio_id`) REFERENCES `status_beneficio`(`id`),
  ADD CONSTRAINT `fk_beneficiarios_cidade` FOREIGN KEY (`cidade_id`) REFERENCES `cidades`(`id`),
  ADD CONSTRAINT `fk_beneficiarios_sexo` FOREIGN KEY (`sexo_id`) REFERENCES `sexos`(`id`),
  ADD CONSTRAINT `fk_beneficiarios_etnia` FOREIGN KEY (`etnia_id`) REFERENCES `etnias`(`id`),
  ADD CONSTRAINT `fk_beneficiarios_local_retirada` FOREIGN KEY (`local_retirada_id`) REFERENCES `locais_retirada`(`id`);

ALTER TABLE `beneficiario_arquivo`
  ADD CONSTRAINT `fk_beneficiario_arquivo_tipo` FOREIGN KEY (`tipo_arquivo_id`) REFERENCES `tipo_arquivo`(`id`),
  ADD CONSTRAINT `fk_beneficiario_arquivo_beneficiario` FOREIGN KEY (`beneficiario_id`) REFERENCES `beneficiarios`(`id`);

ALTER TABLE `historico_status_beneficiario`
  ADD CONSTRAINT `fk_hist_status_beneficiario` FOREIGN KEY (`beneficiario_id`) REFERENCES `beneficiarios`(`id`),
  ADD CONSTRAINT `fk_hist_status_anterior` FOREIGN KEY (`status_anterior_id`) REFERENCES `status_beneficio`(`id`),
  ADD CONSTRAINT `fk_hist_status_novo` FOREIGN KEY (`status_novo_id`) REFERENCES `status_beneficio`(`id`),
  ADD CONSTRAINT `fk_hist_status_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios`(`id`);

-- ==============================
-- TRIGGERS
-- ==============================

DELIMITER //

-- Trigger: Atualiza historico ao alterar status do beneficiário
CREATE TRIGGER trg_beneficiarios_status_update
AFTER UPDATE ON beneficiarios
FOR EACH ROW
BEGIN
  IF NEW.status_beneficio_id <> OLD.status_beneficio_id THEN
    INSERT INTO historico_status_beneficiario (beneficiario_id, status_anterior_id, status_novo_id, motivo, usuario_id)
    VALUES (NEW.id, OLD.status_beneficio_id, NEW.status_beneficio_id, 'Alteração automática via trigger', NULL);
  END IF;
END;
//

-- Trigger: Loga criação de novo beneficiário
CREATE TRIGGER trg_beneficiarios_insert
AFTER INSERT ON beneficiarios
FOR EACH ROW
BEGIN
  INSERT INTO historico_status_beneficiario (beneficiario_id, status_novo_id, motivo, usuario_id)
  VALUES (NEW.id, NEW.status_beneficio_id, 'Beneficiário cadastrado', NULL);
END;
//

DELIMITER ;

-- ==============================
-- VIEWS
-- ==============================

-- View: Beneficiários detalhados com joins
CREATE OR REPLACE VIEW vw_beneficiarios_detalhes AS
SELECT
  b.id AS id,
  b.nome,
  b.nome_mae,
  b.cpf,
  b.rg,
  b.data_nascimento,
  TIMESTAMPDIFF(YEAR, b.data_nascimento, CURDATE()) as idade,
  s.nome AS sexo,
  e.nome AS etnia,
  td.nome AS tipo_deficiencia,
  sb.nome AS status_beneficio,
  lr.nome AS local_retirada,
  c.nome AS cidade,
  b.uf,
  r.nome AS responsavel,
  b.telefone,
  b.email,
  CONCAT(b.endereco,
           CASE WHEN b.numero IS NOT NULL THEN CONCAT(', ', b.numero) ELSE '' END,
           CASE WHEN b.complemento IS NOT NULL THEN CONCAT(', ', b.complemento) ELSE '' END
    ) as endereco_completo,
  b.endereco,
  b.numero,
  b.bairro,
  b.vem_livre_acesso_rmr,
  b.ativo,
  b.created_at,
  b.updated_at
FROM beneficiarios b
JOIN sexos s ON b.sexo_id = s.id
JOIN etnias e ON b.etnia_id = e.id
JOIN tipos_deficiencia td ON b.tipo_deficiencia_id = td.id
JOIN status_beneficio sb ON b.status_beneficio_id = sb.id
JOIN locais_retirada lr ON b.local_retirada_id = lr.id
JOIN cidades c ON b.cidade_id = c.id
LEFT JOIN responsaveis r ON b.id_responsavel = r.id;

-- View: Contagem de beneficiários por status
CREATE OR REPLACE VIEW vw_beneficiarios_por_status AS
SELECT sb.nome AS status, COUNT(b.id) AS total
FROM beneficiarios b
JOIN status_beneficio sb ON b.status_beneficio_id = sb.id
GROUP BY sb.nome;

-- View: Histórico de alterações
CREATE OR REPLACE VIEW vw_historico_status_completo AS
SELECT
  BIN_TO_UUID(h.beneficiario_id, 1) AS beneficiario_id,
  b.nome AS beneficiario,
  sa.nome AS status_anterior,
  sn.nome AS status_novo,
  h.motivo,
  u.nome AS usuario_responsavel,
  h.created_at
FROM historico_status_beneficiario h
LEFT JOIN beneficiarios b ON b.id = h.beneficiario_id
LEFT JOIN status_beneficio sa ON h.status_anterior_id = sa.id
LEFT JOIN status_beneficio sn ON h.status_novo_id = sn.id
LEFT JOIN usuarios u ON h.usuario_id = u.id;

-- ==============================
-- FIM DO SCRIPT
-- ==============================