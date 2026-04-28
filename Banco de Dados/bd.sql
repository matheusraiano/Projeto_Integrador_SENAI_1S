-- Código de Matheus Raiano
-- =========================
-- BANCO
-- =========================
CREATE DATABASE senna_bank;
USE senna_bank;
-- =========================
-- USUARIO
-- =========================
CREATE TABLE usuario (
    cd_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nm_usuario VARCHAR(100) NOT NULL,
    cd_cpf CHAR(11) NOT NULL UNIQUE,
    cd_celular VARCHAR(15),
    ds_email VARCHAR(100) UNIQUE,
    senha VARCHAR(255) NOT NULL,
    dt_nascimento DATE,
    dt_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    ds_status VARCHAR(20) DEFAULT 'ativo' -- ativo, inativo, bloqueado
);

-- =========================
-- ENDERECO
-- =========================
CREATE TABLE endereco (
    cd_endereco INT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(100),
    numero VARCHAR(10),
    complemento VARCHAR(50),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado CHAR(2),
    cep CHAR(8),
    fk_cd_usuario INT,

    FOREIGN KEY (fk_cd_usuario) REFERENCES usuario(cd_usuario)
);

-- =========================
-- CONTA
-- =========================
CREATE TABLE conta (
    cd_conta INT AUTO_INCREMENT PRIMARY KEY,
    nr_agencia CHAR(4) NOT NULL DEFAULT '0001',
    nr_conta VARCHAR(20) NOT NULL UNIQUE,
    tp_conta VARCHAR(20) NOT NULL, -- corrente, poupanca
    saldo DECIMAL(10,2) DEFAULT 0.00,
    ds_status VARCHAR(20) DEFAULT 'ativa', -- ativa, encerrada, bloqueada
    dt_abertura DATETIME DEFAULT CURRENT_TIMESTAMP,
    fk_cd_usuario INT,

    FOREIGN KEY (fk_cd_usuario) REFERENCES usuario(cd_usuario)
);

-- =========================
-- CHAVE PIX
-- =========================
CREATE TABLE chave_pix (
    cd_chave INT AUTO_INCREMENT PRIMARY KEY,
    tp_chave VARCHAR(20) NOT NULL, -- cpf, email, celular, aleatoria
    ds_chave VARCHAR(150) NOT NULL UNIQUE,
    fk_cd_conta INT,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- TRANSACAO
-- =========================
CREATE TABLE transacao (
    cd_transacao INT AUTO_INCREMENT PRIMARY KEY,
    tp_transacao VARCHAR(20) NOT NULL, -- pix, ted, doc, saque, deposito, transferencia
    valor DECIMAL(10,2) NOT NULL,
    descricao VARCHAR(255),
    dt_transacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    ds_status VARCHAR(20) DEFAULT 'concluida', -- concluida, pendente, cancelada
    fk_conta_origem INT,
    fk_conta_destino INT,

    FOREIGN KEY (fk_conta_origem)  REFERENCES conta(cd_conta),
    FOREIGN KEY (fk_conta_destino) REFERENCES conta(cd_conta)
);

-- =========================
-- CARTAO DEBITO
-- =========================
CREATE TABLE cartao_debito (
    cd_debito INT AUTO_INCREMENT PRIMARY KEY,
    nr_cartao CHAR(16) NOT NULL UNIQUE,
    nm_titular VARCHAR(100) NOT NULL,
    dt_validade DATE NOT NULL,
    cvv CHAR(3) NOT NULL,
    ds_status VARCHAR(20) DEFAULT 'ativo', -- ativo, bloqueado, cancelado
    fk_cd_conta INT UNIQUE,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- CARTAO CREDITO
-- =========================
CREATE TABLE cartao_credito (
    cd_credito INT AUTO_INCREMENT PRIMARY KEY,
    nr_cartao CHAR(16) NOT NULL UNIQUE,
    nm_titular VARCHAR(100) NOT NULL,
    nm_cartao VARCHAR(50), -- Classic, Gold, Infinito
    dt_validade DATE NOT NULL,
    cvv CHAR(3) NOT NULL,
    limite_total DECIMAL(10,2) NOT NULL,
    limite_usado DECIMAL(10,2) DEFAULT 0.00,
    ds_status VARCHAR(20) DEFAULT 'ativo', -- ativo, bloqueado, cancelado
    fk_cd_conta INT,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- FATURA
-- =========================
CREATE TABLE fatura (
    cd_fatura INT AUTO_INCREMENT PRIMARY KEY,
    mes_referencia CHAR(7) NOT NULL, -- formato: 2026-04
    valor_total DECIMAL(10,2) DEFAULT 0.00,
    dt_vencimento DATE NOT NULL,
    dt_pagamento DATE,
    ds_status VARCHAR(20) DEFAULT 'aberta', -- aberta, paga, atrasada
    fk_cd_credito INT,

    FOREIGN KEY (fk_cd_credito) REFERENCES cartao_credito(cd_credito)
);

-- =========================
-- COMPRA CREDITO
-- =========================
CREATE TABLE compra_credito (
    cd_compra INT AUTO_INCREMENT PRIMARY KEY,
    ds_descricao VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL,
    dt_compra DATETIME DEFAULT CURRENT_TIMESTAMP,
    nr_parcelas INT DEFAULT 1,
    fk_cd_fatura INT,

    FOREIGN KEY (fk_cd_fatura) REFERENCES fatura(cd_fatura)
);

-- =========================
-- INVESTIMENTO
-- =========================
CREATE TABLE investimento (
    cd_investimento INT AUTO_INCREMENT PRIMARY KEY,
    tp_investimento VARCHAR(50) NOT NULL, -- CDB, tesouro_direto, renda_variavel, fii
    ds_descricao VARCHAR(255),
    valor_aplicado DECIMAL(10,2) NOT NULL,
    valor_atual DECIMAL(10,2),
    rentabilidade DECIMAL(5,2), -- percentual ex: 115.00 = 115% CDI
    dt_aplicacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    dt_vencimento DATE,
    ds_status VARCHAR(20) DEFAULT 'ativo', -- ativo, resgatado, vencido
    fk_cd_conta INT,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- EMPRESTIMO
-- =========================
CREATE TABLE emprestimo (
    cd_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
    tp_emprestimo VARCHAR(50) NOT NULL, -- pessoal, consignado, garantia, veiculo
    valor_solicitado DECIMAL(10,2) NOT NULL,
    valor_aprovado DECIMAL(10,2),
    taxa_juros DECIMAL(5,2) NOT NULL, -- percentual ao mes
    nr_parcelas INT NOT NULL,
    dt_solicitacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    dt_aprovacao DATETIME,
    ds_status VARCHAR(20) DEFAULT 'pendente', -- pendente, aprovado, recusado, quitado
    fk_cd_conta INT,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- PARCELA EMPRESTIMO
-- =========================
CREATE TABLE parcela_emprestimo (
    cd_parcela INT AUTO_INCREMENT PRIMARY KEY,
    nr_parcela INT NOT NULL, -- 1, 2, 3...
    valor_parcela DECIMAL(10,2) NOT NULL,
    dt_vencimento DATE NOT NULL,
    dt_pagamento DATE,
    ds_status VARCHAR(20)  DEFAULT 'pendente', -- pendente, paga, atrasada
    fk_cd_emprestimo INT,

    FOREIGN KEY (fk_cd_emprestimo) REFERENCES emprestimo(cd_emprestimo)
);

-- =========================
-- NOTIFICACAO
-- =========================
CREATE TABLE notificacao (
    cd_notificacao INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    mensagem VARCHAR(500) NOT NULL,
    tp_notificacao VARCHAR(30), -- transacao, fatura, investimento, seguranca
    ds_lida TINYINT(1) DEFAULT 0, -- 0 = não lida, 1 = lida
    dt_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    fk_cd_usuario INT,

    FOREIGN KEY (fk_cd_usuario) REFERENCES usuario(cd_usuario)
);