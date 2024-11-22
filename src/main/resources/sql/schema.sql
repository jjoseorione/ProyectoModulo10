DROP DATABASE IF EXISTS banunam;
CREATE DATABASE banunam;

USE banunam;

CREATE TABLE clientes(
	noCliente INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
	apPat VARCHAR(50) NOT NULL,
	apMat VARCHAR (50) NOT NULL DEFAULT '',
	rfc CHAR(13) UNIQUE NOT NULL,
	fechaNac DATE NOT NULL,
	email VARCHAR(50) NOT NULL UNIQUE,
	contrasena VARCHAR(30) NOT NULL,
	telefono VARCHAR(20) NOT NULL,

	--Se valida que el cliente sea mayor de edad
	--CONSTRAINT check_validaEdad CHECK (fechaNac < DATE_SUB(NOW(), INTERVAL 18 YEAR)),
	CONSTRAINT check_validaContrasena CHECK (CHAR_LENGTH(contrasena) >= 8),
	CONSTRAINT check_validaCorreo CHECK (email LIKE '%@%')
);

CREATE TABLE cuentas_debito(
	noCuenta INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	noCliente INT UNSIGNED NOT NULL UNIQUE,
	saldo DECIMAL(8,2) NOT NULL DEFAULT 0,

	CONSTRAINT fk_cliente_cuentaDebito FOREIGN KEY(noCliente) REFERENCES clientes (noCliente)
);
ALTER TABLE cuentas_debito AUTO_INCREMENT = 10000000;

CREATE TABLE cuentas_credito(
	noCuenta INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	limCredito DECIMAL(8,2) NOT NULL DEFAULT 0,
	noCliente INT UNSIGNED NOT NULL UNIQUE,
	saldoUtilizado DECIMAL(8,2) NOT NULL DEFAULT 0,
	tasaInteresAnual DECIMAL(4,2) UNSIGNED NOT NULL DEFAULT 0,

	CONSTRAINT fk_cliente_cuentaCredito FOREIGN KEY(noCliente) REFERENCES clientes (noCliente)
);
ALTER TABLE cuentas_credito AUTO_INCREMENT = 20000000;

CREATE TABLE cuentas_prestamo(
	noCuenta INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	noCliente INT UNSIGNED NOT NULL,
	montoSolicitado DECIMAL(8,2) UNSIGNED NOT NULL,
	noPeriodos TINYINT UNSIGNED NOT NULL,
	periodicidad CHAR(1) NOT NULL,
	tasaInteresAnual DECIMAL(4,2) UNSIGNED NOT NULL,
	periodoActivo TINYINT UNSIGNED NOT NULL DEFAULT 1,
	saldoRestante DECIMAL(8,2) UNSIGNED NOT NULL,
	fechaAprobacion DATE NOT NULL,

	CONSTRAINT fk_cliente_cuentaPrestamo FOREIGN KEY(noCliente) REFERENCES clientes (noCliente)
);
ALTER TABLE cuentas_prestamo AUTO_INCREMENT = 30000000,
	ADD CONSTRAINT CHK_periodicidad CHECK (periodicidad = 'Q' OR periodicidad = 'M');

CREATE TABLE tarjetas_debito(
	noTarjeta VARCHAR(16) NOT NULL PRIMARY KEY,
	fisicaElectronica CHAR(1) NOT NULL,
	noCuenta INT UNSIGNED NOT NULL,
	fechaExp DATE NOT NULL,
	cvv SMALLINT(3) UNSIGNED NULL,
	estatus CHAR(1) NOT NULL DEFAULT 'N',

    UNIQUE (noCuenta, fisicaElectronica),
	CONSTRAINT fk_cuentaDebito_tarjetaDebito FOREIGN KEY(noCuenta) REFERENCES cuentas_debito (noCuenta)
);

CREATE TABLE tarjetas_credito(
	noTarjeta VARCHAR(16) NOT NULL PRIMARY KEY,
	fisicaElectronica CHAR(1) NOT NULL,
	adicional TINYINT(1) UNSIGNED NOT NULL DEFAULT 0,
	noCuenta INT UNSIGNED NOT NULL,
	fechaExp DATE NOT NULL,
	cvv SMALLINT(3) UNSIGNED NULL,
	estatus CHAR(1) NOT NULL DEFAULT 'N',

    UNIQUE (noCuenta, fisicaElectronica, adicional),
	CONSTRAINT fk_cuentaCredito_tarjetaCredito FOREIGN KEY(noCuenta) REFERENCES cuentas_credito (noCuenta)
);

CREATE TABLE tipos_movimiento(
	tipoMovimiento TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(50) NOT NULL UNIQUE,
	signoDebito CHAR(1),
	tipoCuenta CHAR(1) NOT NULL
	--signoDebito significa que tendrá el signo real. Es decir, si es deuda, es '-'. Si es saldo a favor, es '+'
	--Cuando el movimiento se aplique a la cuenta del cliente, sumará o restará según el signo y la cuenta. Si la
	--cuenta es de débito, un signo '-' debe restar al saldo y un signo '+' debe sumar, ya que el saldo de la cuenta
	 --es a favor. Si la cuenta es de crédito o préstamo, un signo '-' debe sumar al saldo y un signo '+' debe restar.
	 --La columna productoMovimiento indica si es una operación asociada a una cuenta de débito, crédito o de préstamo
);
ALTER TABLE tipos_movimiento
	ADD CONSTRAINT CHK_signoDebito CHECK (signoDebito = '+' OR signoDebito = '-');
ALTER TABLE tipos_movimiento
	ADD CONSTRAINT CHK_tipoCuenta CHECK (tipoCuenta = 'D' OR tipoCuenta = 'C' OR tipoCuenta = 'P');
	--Se asegura que el movimiento pertenezca al contexto de Débito, Crédito o Préstamo


CREATE TABLE origen_destino_movimientos(
	tipoOrigenDestino TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(30) NOT NULL UNIQUE
	--La tabla origen_destino_movimientos guarda el posible origen o destino de un movimiento. Estas opciones
	--pueden ser un comercio (en caso de que se trate de una compra), otra cuenta de crédito/débito en caso de una
	--transferencia, un cajero (ventanilla) o un cajero automático. --
);

CREATE TABLE movimientos_debito(
	folio BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	timestampMov DATETIME NOT NULL,
	monto DECIMAL(8,2) UNSIGNED NOT NULL,
	tipoMovimiento TINYINT UNSIGNED NOT NULL,
	noCuenta INT UNSIGNED NOT NULL,
	idOrigenDestino VARCHAR(30) NOT NULL,
	tipoOrigenDestino TINYINT UNSIGNED NOT NULL,
	concepto VARCHAR(50) NULL,


	CONSTRAINT fk_tipoMovimiento_movimientosDebito FOREIGN KEY (tipoMovimiento) REFERENCES tipos_movimiento (tipoMovimiento),
	CONSTRAINT fk_cuentaDebito_movimientosDebito FOREIGN KEY (noCuenta) REFERENCES cuentas_debito (noCuenta),
	CONSTRAINT fk_origenDestinoMovimientos_movimientosDebito FOREIGN KEY (tipoOrigenDestino) REFERENCES origen_destino_movimientos (tipoOrigenDestino)
);

CREATE TABLE movimientos_credito(
	folio BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	timestampMov DATETIME NOT NULL,
	monto INT UNSIGNED NOT NULL,
	tipoMovimiento TINYINT UNSIGNED NOT NULL,
	noCuenta INT UNSIGNED NOT NULL,
	idOrigenDestino VARCHAR(30) NOT NULL,
	tipoOrigenDestino TINYINT UNSIGNED NOT NULL,
	concepto VARCHAR(50) NULL,


	CONSTRAINT fk_tipoMovimiento_movimientosCredito FOREIGN KEY (tipoMovimiento) REFERENCES tipos_movimiento (tipoMovimiento),
	CONSTRAINT fk_cuentaCredito_movimientosCredito FOREIGN KEY (noCuenta) REFERENCES cuentas_credito (noCuenta),
	CONSTRAINT fk_origenDestinoMovimientos_movimientosCredito FOREIGN KEY (tipoOrigenDestino) REFERENCES origen_destino_movimientos (tipoOrigenDestino)
);

CREATE TABLE movimientos_prestamo(
	folio BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	timestampMov DATETIME NOT NULL,
	monto INT UNSIGNED NOT NULL,
	tipoMovimiento TINYINT UNSIGNED NOT NULL,
	noCuenta INT UNSIGNED NOT NULL,
	idOrigenDestino VARCHAR(30) NOT NULL,
	tipoOrigenDestino TINYINT UNSIGNED NOT NULL,
	concepto VARCHAR(50) NULL,


	CONSTRAINT fk_tipoMovimiento_movimientosPrestamo FOREIGN KEY (tipoMovimiento) REFERENCES tipos_movimiento (tipoMovimiento),
	CONSTRAINT fk_cuentaPrestamo_movimientosPrestamo FOREIGN KEY (noCuenta) REFERENCES cuentas_prestamo (noCuenta),
	CONSTRAINT fk_origenDestinoMovimientos_movimientosPrestamo FOREIGN KEY (tipoOrigenDestino) REFERENCES origen_destino_movimientos (tipoOrigenDestino)
);

CREATE TABLE tipos_usuario(
	tipoUsuario TINYINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	alias VARCHAR(10) UNIQUE NOT NULL,
	descripcion VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE usuarios(
	idUsuario SMALLINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	usuario VARCHAR(7) NOT NULL UNIQUE,
	nombre VARCHAR(50) NOT NULL,
	apPat VARCHAR(50) NOT NULL,
	apMat VARCHAR (50) NOT NULL DEFAULT '',
	email VARCHAR(50) NOT NULL UNIQUE,
	contrasena VARCHAR(30) NOT NULL,
	tipoUsuario TINYINT UNSIGNED NOT NULL,
	intentos TINYINT UNSIGNED NOT NULL,
	estatus CHAR(1) NOT NULL,
	fechaExpUsuario DATE NOT NULL,
	fechaExpContrasena DATE NOT NULL,

	CONSTRAINT fk_tiposUsuario_usuarios FOREIGN KEY (tipoUsuario) REFERENCES tipos_usuario (tipoUsuario)
);

ALTER TABLE usuarios
	ADD CONSTRAINT CHK_estatus CHECK (estatus = 'A' OR estatus = 'B' OR estatus = 'I');