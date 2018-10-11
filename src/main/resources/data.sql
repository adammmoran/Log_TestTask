INSERT INTO product (id, sizeX, sizeY , sizeZ) VALUES
  (1,4,10,5),
  (2,16,29,10),
  (3,20,20,50),
  (4,5,5,8),
  (5,10,10,15),
  (6,20,20,20),
  (7,25,15,30);

INSERT INTO case (id, sizeX, sizeY , sizeZ) VALUES
  (1,50,25,20),
  (2,100,100,200),
  (3,45,40,40);

INSERT INTO orderline (id, number_Of_Products , product_id) VALUES
  (1,90,1),
  (2,60,3),
  (3,100,7),
  (4,100,6);