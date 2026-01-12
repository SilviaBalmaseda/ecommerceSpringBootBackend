

CREATE USER proyecto_eshop IDENTIFIED BY Abcd1234;


GRANT CONNECT, RESOURCE TO proyecto_eshop;
GRANT UNLIMITED TABLESPACE TO proyecto_eshop;




-- En Oracle no hay 'DROP TABLE IF EXISTS', se borran directamente
-- O se ignoran los errores si no existen.
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE articulo_compra';
   EXECUTE IMMEDIATE 'DROP TABLE articulo';
   EXECUTE IMMEDIATE 'DROP TABLE compra';
   EXECUTE IMMEDIATE 'DROP TABLE informacion_fiscal';
   EXECUTE IMMEDIATE 'DROP TABLE cliente';
EXCEPTION
   WHEN OTHERS THEN NULL; 
END;
/

-- TABLA CLIENTE
CREATE TABLE cliente (
  nif_cif VARCHAR2(20) NOT NULL,
  nombre_completo VARCHAR2(100),
  email VARCHAR2(150),
  fecha_registro TIMESTAMP,
  PRIMARY KEY (nif_cif)
);

-- TABLA INFORMACION_FISCAL (1:1)
CREATE TABLE informacion_fiscal (
  nif_cif VARCHAR2(20) NOT NULL,
  telefono VARCHAR2(20),
  direccion_fiscal VARCHAR2(255),
  PRIMARY KEY (nif_cif),
  CONSTRAINT inf_fiscal_cliente_FK 
    FOREIGN KEY (nif_cif) REFERENCES cliente (nif_cif) 
    ON DELETE CASCADE
);

-- TABLA COMPRA (N:1)
CREATE TABLE compra (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  fecha_compra TIMESTAMP,
  estado VARCHAR2(20),
  direccion_entrega VARCHAR2(255),
  precio_total NUMBER(10,2) DEFAULT 0.0,
  cliente_nif_cif VARCHAR2(20),
  CONSTRAINT compra_cliente_FK 
    FOREIGN KEY (cliente_nif_cif) REFERENCES cliente (nif_cif)
);

-- TABLA ARTICULO
CREATE TABLE articulo (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nombre VARCHAR2(100),
  descripcion CLOB, -- Usamos CLOB para textos largos en Oracle
  precio_actual NUMBER(10,2) DEFAULT 0.0,
  stock NUMBER DEFAULT 0
);

-- TABLA ARTICULO_COMPRA (N:N)
CREATE TABLE articulo_compra (
  articulo_id NUMBER NOT NULL,
  compra_id NUMBER NOT NULL,
  unidades NUMBER DEFAULT 0,
  precio_compra NUMBER(10,2) DEFAULT 0.0,
  PRIMARY KEY (articulo_id, compra_id),
  CONSTRAINT art_comp_articulo_FK FOREIGN KEY (articulo_id) REFERENCES articulo (id),
  CONSTRAINT art_comp_compra_FK FOREIGN KEY (compra_id) REFERENCES compra (id)
);

-- INSERCIÓN DE DATOS (Nota el uso de TO_TIMESTAMP)
INSERT INTO cliente VALUES ('X1234567A', 'Ana García', 'ana.garcia@example.com', TO_TIMESTAMP('2024-01-10 09:15:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO cliente VALUES ('Y7654321B', 'Luis Pérez', 'luis.perez@example.com', TO_TIMESTAMP('2024-02-05 14:30:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO cliente VALUES ('Z9998887C', 'María López', 'maria.lopez@example.com', TO_TIMESTAMP('2024-03-12 11:00:00', 'YYYY-MM-DD HH24:MI:SS'));

INSERT INTO informacion_fiscal VALUES ('X1234567A', '+34 600 111 222', 'C/ Ejemplo 1, 28001 Madrid');
INSERT INTO informacion_fiscal VALUES ('Y7654321B', '+34 600 333 444', 'Av. Prueba 10, 08002 Barcelona');

INSERT INTO articulo (nombre, descripcion, precio_actual, stock) 
VALUES ('Camiseta básica', 'Camiseta 100% algodón, talla M', 19.99, 100);
INSERT INTO articulo (nombre, descripcion, precio_actual, stock) 
VALUES ('Auriculares bluetooth', 'Auriculares inalámbricos con cancelación', 49.50, 50);


COMMIT;