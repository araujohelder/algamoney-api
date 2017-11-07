CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	ativo varchar(15),
	logradouro VARCHAR(30),
	numero VARCHAR(4),
	complemento VARCHAR(50),
	cep VARCHAR(14),
	bairro VARCHAR(30),
	cidade VARCHAR(20),
	estado VARCHAR(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,cep,bairro,cidade,estado) values ('Thays  Araujo','true','Rua reinaldo Feijó','180',null,'24110-214','Barro Vermelho','São Gonçalo','RJ');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,cep,bairro,cidade,estado) values ('Maria Salete', 'true','Rua Maria José monteiro','265',null,'22580-214','Areal','Araruama','RJ');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,cep,bairro,cidade,estado) values ('Carmelinda Araujo','true','Rua Luiz Palmier','101','Bloco 06 apto 1012','24110-310','Barreto','Niteró','RJ');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,cep,bairro,cidade,estado) values ('Junior Guerra','true','Rua Alvares de azevedo','189',null,'28331-214','centro','Queimados','RJ');
INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,cep,bairro,cidade,estado) values ('Maria José','true','Rua fernando sodré','205',null,'23510-214','Barro Vermelho','Araruama','RJ');
