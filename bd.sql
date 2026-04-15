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
    ds_email VARCHAR(100),
    senha VARCHAR(255)
);

-- =========================
-- ENDERECO
-- =========================
CREATE TABLE endereco (
    cd_endereco INT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(100),
    numero VARCHAR(10),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(50),
    cep VARCHAR(10),
    fk_cd_usuario INT,

    FOREIGN KEY (fk_cd_usuario) REFERENCES usuario(cd_usuario)
);

-- =========================
-- CONTA
-- =========================
CREATE TABLE conta (
    cd_conta INT AUTO_INCREMENT PRIMARY KEY,
    nr_conta VARCHAR(20) NOT NULL,
    tipo VARCHAR(20), -- corrente ou poupanca
    saldo DECIMAL(10,2) DEFAULT 0,
    fk_cd_usuario INT,

    FOREIGN KEY (fk_cd_usuario) REFERENCES usuario(cd_usuario)
);

-- =========================
-- CARTAO DEBITO
-- =========================
CREATE TABLE cartao_debito (
    cd_debito INT AUTO_INCREMENT PRIMARY KEY,
    nr_cartao VARCHAR(16),
    dt_validade DATE,
    fk_cd_conta INT UNIQUE,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- CARTAO CREDITO
-- =========================
CREATE TABLE cartao_credito (
    cd_credito INT AUTO_INCREMENT PRIMARY KEY,
    nr_cartao VARCHAR(16),
    nome_cartao VARCHAR(100),
    limite DECIMAL(10,2),
    fk_cd_conta INT,

    FOREIGN KEY (fk_cd_conta) REFERENCES conta(cd_conta)
);

-- =========================
-- FATURA 
-- =========================
CREATE TABLE fatura (
    cd_fatura INT AUTO_INCREMENT PRIMARY KEY,
    valor_total DECIMAL(10,2),
    data_vencimento DATE,
    ds_status VARCHAR(20), -- aberta, paga
    fk_cd_credito INT,

    FOREIGN KEY (fk_cd_credito) REFERENCES cartao_credito(cd_credito)
);

-- =========================
-- TRANSACAO
-- =========================
CREATE TABLE transacao (
    cd_transacao INT AUTO_INCREMENT PRIMARY KEY,

    tipo VARCHAR(20), -- deposito, saque, transferencia
    valor DECIMAL(10,2) NOT NULL,
    descricao VARCHAR(255),

    dt_transacao DATETIME DEFAULT CURRENT_TIMESTAMP,

    fk_conta_origem INT,
    fk_conta_destino INT,

    FOREIGN KEY (fk_conta_origem) REFERENCES conta(cd_conta),
    FOREIGN KEY (fk_conta_destino) REFERENCES conta(cd_conta)
);