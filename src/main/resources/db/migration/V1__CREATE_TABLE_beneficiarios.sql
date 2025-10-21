-- ============================================================
-- TABELAS DE REFERÊNCIA (LOOKUP TABLES)
-- ============================================================

-- Tabela de Tipos de Deficiência
CREATE TABLE IF NOT EXISTS tipos_deficiencia (
    id          SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(50) NOT NULL UNIQUE,
    descricao   VARCHAR(150),
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir dados iniciais
INSERT INTO tipos_deficiencia (id, nome, descricao) VALUES 
(1, 'Física', 'Deficiência que afeta a mobilidade e coordenação motora'),
(2, 'Visual', 'Deficiência relacionada à visão (cegueira ou baixa visão)'),
(3, 'Auditiva', 'Deficiência relacionada à audição (surdez ou baixa audição)'),
(4, 'Intelectual', 'Deficiência que afeta o desenvolvimento intelectual'),
(5, 'Múltipla', 'Combinação de duas ou mais deficiências');

-- Tabela de Status do Benefício
CREATE TABLE IF NOT EXISTS status_beneficio (
    id          SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(30) NOT NULL UNIQUE,
    descricao   VARCHAR(100),
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- Tabela de Locais de Retirada
CREATE TABLE IF NOT EXISTS locais_retirada (
    id          SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(50) NOT NULL UNIQUE,
    endereco    VARCHAR(150),
    telefone    VARCHAR(20),
    horario_funcionamento VARCHAR(100),
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir dados iniciais
INSERT INTO locais_retirada (id, nome, endereco) VALUES
(1, 'Recife', 'Endereço da unidade de Recife'),
(2, 'Camaragibe', 'Endereço da unidade de Camaragibe'),
(3, 'Paulista', 'Endereço da unidade de Paulista'),
(4, 'Jaboatão dos Guararapes', 'Endereço da unidade de Jaboatão'),
(5, 'Olinda', 'Endereço da unidade de Olinda');

-- Tabela de Etnias
CREATE TABLE IF NOT EXISTS etnias (
    id          SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(30) NOT NULL UNIQUE,
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir dados iniciais (baseado no IBGE)
INSERT INTO etnias (id, nome) VALUES
(1, 'Branca'),
(2, 'Preta'),
(3, 'Parda'),
(4, 'Amarela'),
(5, 'Indígena'),
(6, 'Não Declarada');

-- Tabela de Sexo/Gênero
CREATE TABLE IF NOT EXISTS sexos (
    id          SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome        VARCHAR(20) NOT NULL UNIQUE,
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserir dados iniciais
INSERT INTO sexos (id, nome) VALUES
(1, 'Mulher cisgênera'),
(2, 'Mulher transsexual'),
(3, 'Homem cisgênero'),
(4, 'Homem transsexual'),
(5, 'Travesti'),
(6, 'Não binário'),
(7, 'Prefiro não informar');

-- Tabela de Cidades de Pernambuco
CREATE TABLE IF NOT EXISTS cidades (
    id          SMALLINT UNSIGNED NOT NULL PRIMARY KEY,
    nome        VARCHAR(80) NOT NULL UNIQUE,
    uf      CHAR(2) NOT NULL DEFAULT 'PE',
    ativo       BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_nome (nome),
    INDEX idx_estado (uf)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

-- ============================================================
-- TABELA BENEFICIÁRIOS
-- ============================================================

CREATE TABLE IF NOT EXISTS beneficiarios (
    -- Identificação
    id                  BINARY(16) NOT NULL PRIMARY KEY,     -- UUID armazenado em 16 bytes
    nome                VARCHAR(100) NOT NULL,
    nome_mae            VARCHAR(100) NOT NULL,
    cpf                 CHAR(11) NOT NULL UNIQUE,
    rg                  VARCHAR(20) NOT NULL,
    data_nascimento     DATE NOT NULL,

    sexo_id             SMALLINT UNSIGNED NOT NULL,
    etnia_id            SMALLINT UNSIGNED NOT NULL,
    tipo_deficiencia_id SMALLINT UNSIGNED NOT NULL,
    status_beneficio_id SMALLINT UNSIGNED NOT NULL DEFAULT 1, -- Em análise por padrão
    local_retirada_id   SMALLINT UNSIGNED NOT NULL,

    -- Configurações específicas
    vem_livre_acesso_rmr BOOLEAN NOT NULL DEFAULT FALSE,

    -- Contato
    telefone            VARCHAR(15) NOT NULL,               -- Apenas números
    email               VARCHAR(100) NOT NULL,

    -- Endereço
    endereco            VARCHAR(200) NOT NULL,
    numero              VARCHAR(10),
    complemento         VARCHAR(50),
    bairro              VARCHAR(80) NOT NULL,
    cidade_id           SMALLINT UNSIGNED NOT NULL,
    uf              CHAR(2) NOT NULL DEFAULT 'PE',     -- Assumindo Pernambuco
    cep                 CHAR(9),

    -- Caminhos dos arquivos (otimizado)
    path_rg                     VARCHAR(255),              -- Reduzido de 500 para 255
    path_cpf                    VARCHAR(255),
    path_comprovante_endereco   VARCHAR(255),
    path_foto                   VARCHAR(255),
    path_laudo_medico          VARCHAR(255),

    -- Metadados
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ativo               BOOLEAN NOT NULL DEFAULT TRUE,     -- Renomeado de 'status'

    -- Índices e constraints
    INDEX idx_cpf (cpf),
    INDEX idx_nome (nome),
    INDEX idx_status_beneficio (status_beneficio_id),
    INDEX idx_local_retirada (local_retirada_id),
    INDEX idx_tipo_deficiencia (tipo_deficiencia_id),
    INDEX idx_created_at (created_at),
    INDEX idx_cidade (cidade_id),

    -- Chaves estrangeiras
    CONSTRAINT fk_beneficiarios_sexo
        FOREIGN KEY (sexo_id) REFERENCES sexos(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_beneficiarios_etnia
        FOREIGN KEY (etnia_id) REFERENCES etnias(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_beneficiarios_tipo_deficiencia
        FOREIGN KEY (tipo_deficiencia_id) REFERENCES tipos_deficiencia(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_beneficiarios_status_beneficio
        FOREIGN KEY (status_beneficio_id) REFERENCES status_beneficio(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_beneficiarios_local_retirada
        FOREIGN KEY (local_retirada_id) REFERENCES locais_retirada(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,

    CONSTRAINT fk_beneficiarios_cidade
        FOREIGN KEY (cidade_id) REFERENCES cidades(id)
        ON UPDATE CASCADE ON DELETE RESTRICT

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABELA PARA HISTÓRICO DE MUDANÇAS DE STATUS
-- ============================================================

CREATE TABLE IF NOT EXISTS historico_status_beneficiario (
    id                  BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    beneficiario_id     BINARY(16) NOT NULL,
    status_anterior_id  SMALLINT UNSIGNED,
    status_novo_id      SMALLINT UNSIGNED NOT NULL,
    motivo              TEXT,
    usuario_id          BINARY(16),                        -- ID do usuário que fez a alteração
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_beneficiario (beneficiario_id),
    INDEX idx_created_at (created_at),

    CONSTRAINT fk_historico_beneficiario
        FOREIGN KEY (beneficiario_id) REFERENCES beneficiarios(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_historico_status_anterior
        FOREIGN KEY (status_anterior_id) REFERENCES status_beneficio(id),

    CONSTRAINT fk_historico_status_novo
        FOREIGN KEY (status_novo_id) REFERENCES status_beneficio(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- VIEWS PARA FACILITAR CONSULTAS
-- ============================================================

-- View com todos os dados relacionados
CREATE OR REPLACE VIEW vw_beneficiarios_completo AS
SELECT
    b.id,
    b.nome,
    b.nome_mae,
    b.cpf,
    b.rg,
    b.data_nascimento,
    TIMESTAMPDIFF(YEAR, b.data_nascimento, CURDATE()) as idade,
    s.nome as sexo,
    e.nome as etnia,
    td.nome as tipo_deficiencia,
    sb.nome as status_beneficio,
    lr.nome as local_retirada,
    b.vem_livre_acesso_rmr,
    b.telefone,
    b.email,
    CONCAT(b.endereco,
           CASE WHEN b.numero IS NOT NULL THEN CONCAT(', ', b.numero) ELSE '' END,
           CASE WHEN b.complemento IS NOT NULL THEN CONCAT(', ', b.complemento) ELSE '' END
    ) as endereco_completo,
    b.bairro,
    c.nome as cidade,
    b.uf,
    b.cep,
    b.created_at,
    b.updated_at,
    b.ativo
FROM beneficiarios b
    LEFT JOIN sexos s ON b.sexo_id = s.id
    LEFT JOIN etnias e ON b.etnia_id = e.id
    LEFT JOIN tipos_deficiencia td ON b.tipo_deficiencia_id = td.id
    LEFT JOIN status_beneficio sb ON b.status_beneficio_id = sb.id
    LEFT JOIN locais_retirada lr ON b.local_retirada_id = lr.id
    LEFT JOIN cidades c ON b.cidade_id = c.id;

-- ============================================================
-- TRIGGERS PARA AUDITORIA (OPCIONAL)
-- ============================================================



CREATE TRIGGER tr_beneficiario_status_change 
    AFTER UPDATE ON beneficiarios
    FOR EACH ROW
BEGIN
    IF OLD.status_beneficio_id != NEW.status_beneficio_id THEN
        INSERT INTO historico_status_beneficiario 
        (beneficiario_id, status_anterior_id, status_novo_id, created_at)
        VALUES (NEW.id, OLD.status_beneficio_id, NEW.status_beneficio_id, NOW());
    END IF;
END;

