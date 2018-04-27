
INSERT INTO tag_tbl (id, tag_group_id, name) VALUES
  (1, 1, 'retailer'),
  (2, 1, 'restaurant'),
  (3, 1, 'yarn'),
  (4, 1, 'mexican'),
  (5, 1, 'steak');

INSERT INTO user_tbl (id, subject, email, preferred_username, name, given_name, family_name) VALUES
  (51334, 'c5440f24-b740-4d70-addf-cf9141fb8223', 'cpdevoto@gmail.com', 'cdevoto', 'Carlos Devoto', 'Carlos', 'Devoto'),
  (51335, 'd5440f24-b740-4d70-addf-cf9141fb8223', 'jblow@gmail.com', 'jblow', 'Joe Blow', 'Joe', 'Blow'),
  (51336, 'e6550f24-b740-4d70-addf-cf9141fb8223', 'jwoolly@gmail.com', 'jwoolly', 'Jane Woolly', 'Jane', 'Woolly'),
  (51337, 'e6560f24-b740-4d70-addf-cf9141fb8223', 'projo@gmail.com', 'projo', 'Pedro Rojo', 'Pedro', 'Rojo'),
  (51338, 'e6570f24-b740-4d70-addf-cf9141fb8223', 'gfleming@gmail.com', 'gfleming', 'Gordon Fleming', 'Gordon', 'Fleming');

INSERT INTO address_tbl (id, address1, address2, city, state, zip) VALUES
  (51335, '147 Pierce St', null, 'Birmingham', 'MI', '48009'),
  (51336, '250 E Merrill St', null, 'Birmingham', 'MI', '48009'),
  (51337, '323 N Old Woodward Ave', null, 'Birmingham', 'MI', '48009');

INSERT INTO merchant_tbl (id, user_id, address_id, name, latitude, longitude, short_description, long_description, logo, cover_photo, phone_number, email, messaging_enabled) VALUES
  (51335, 51336, 51335, 'Woolly & Co.', 42.546242, -83.214674, 'Yarn store', 'Best darn yarn store on all of Pierce Street', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/000345.jpg', 
    'http://bucket.s3-us-east-2a.amazonaws.com/wantify/000678.jpg', '(248) 480-4354', 'admin@woollyco.com', true),
  (51336, 51337, 51336, 'Rojo Mexican Bistro', 42.545368, -83.213715, 'Mexican bistro', 'Best darn Mexican bistro on all of E. Merrill Street', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/111345.jpg', 
    'http://bucket.s3-us-east-2a.amazonaws.com/wantify/111678.jpg', '(248) 792-6200', 'admin@rojo.com', true),
  (51337, 51338, 51337, 'Flemings Prime Steakhouse', 42.547887, -83.216100, 'Prime steakhouse', 'Best darn prime steakhouse on all of N. Old Woodward Avenue', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/222345.jpg', 
    'http://bucket.s3-us-east-2a.amazonaws.com/wantify/222678.jpg', '(248) 723-0134', 'admin@flemingsteakhouse.com', true);

INSERT INTO merchant_tag_tbl (merchant_id, tag_id) VALUES
  (51335, 1),
  (51335, 3),
  (51336, 2),
  (51336, 4),
  (51337, 2),
  (51337, 5);

INSERT INTO user_favorite_tbl (user_id, merchant_id) VALUES
  (51334, 51335),
  (51334, 51336),
  (51335, 51335),
  (51335, 51336);
  
INSERT INTO merchant_vip_tbl (user_id, merchant_id) VALUES
  (51334, 51335),
  (51335, 51335);

  INSERT INTO notice_tbl (id, merchant_id, notice_type_id, notice_scope_id, title, "text", photo, created_at) VALUES
  (51335, 51335, 1, 1, 'Woolly Promotional Message to All #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999111.jpg', '2018-01-08 04:05:06'),
  (51336, 51335, 1, 1, 'Woolly Promotional Message to All #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999112.jpg', '2018-01-09 04:05:06'),
  (51337, 51335, 1, 1, 'Woolly Promotional Message to All #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999113.jpg', '2018-01-10 04:05:06'),
  (51338, 51335, 1, 1, 'Woolly Promotional Message to All #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999114.jpg', '2018-01-11 04:05:06'),
  (51339, 51335, 1, 1, 'Woolly Promotional Message to All #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999115.jpg', '2018-01-12 04:05:06'),
  (51340, 51335, 1, 2, 'Woolly Promotional Message to VIPs #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999116.jpg', '2018-01-08 05:05:06'),
  (51341, 51335, 1, 2, 'Woolly Promotional Message to VIPs #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999117.jpg', '2018-01-09 05:05:06'),
  (51342, 51335, 1, 2, 'Woolly Promotional Message to VIPs #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999118.jpg', '2018-01-10 05:05:06'),
  (51343, 51335, 1, 2, 'Woolly Promotional Message to VIPs #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999119.jpg', '2018-01-11 05:05:06'),
  (51344, 51335, 1, 2, 'Woolly Promotional Message to VIPs #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999120.jpg', '2018-01-12 05:05:06'),
  (51345, 51335, 2, 1, 'Woolly Informational Message to All #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999121.jpg', '2018-01-08 06:05:06'),
  (51346, 51335, 2, 1, 'Woolly Informational Message to All #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999122.jpg', '2018-01-09 06:05:06'),
  (51347, 51335, 2, 1, 'Woolly Informational Message to All #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999123.jpg', '2018-01-10 06:05:06'),
  (51348, 51335, 2, 1, 'Woolly Informational Message to All #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999124.jpg', '2018-01-11 06:05:06'),
  (51349, 51335, 2, 1, 'Woolly Informational Message to All #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999125.jpg', '2018-01-12 06:05:06'),
  (51350, 51335, 2, 2, 'Woolly Informational Message to VIPs #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999126.jpg', '2018-01-08 07:05:06'),
  (51351, 51335, 2, 2, 'Woolly Informational Message to VIPs #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999127.jpg', '2018-01-09 07:05:06'),
  (51352, 51335, 2, 2, 'Woolly Informational Message to VIPs #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999128.jpg', '2018-01-10 07:05:06'),
  (51353, 51335, 2, 2, 'Woolly Informational Message to VIPs #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999129.jpg', '2018-01-11 07:05:06'),
  (51354, 51335, 2, 2, 'Woolly Informational Message to VIPs #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/999130.jpg', '2018-01-12 07:05:06'),

  (51435, 51336, 1, 1, 'Rojo Promotional Message to All #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998111.jpg', '2018-01-08 04:05:07'),
  (51436, 51336, 1, 1, 'Rojo Promotional Message to All #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998112.jpg', '2018-01-09 04:05:07'),
  (51437, 51336, 1, 1, 'Rojo Promotional Message to All #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998113.jpg', '2018-01-10 04:05:07'),
  (51438, 51336, 1, 1, 'Rojo Promotional Message to All #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998114.jpg', '2018-01-11 04:05:07'),
  (51439, 51336, 1, 1, 'Rojo Promotional Message to All #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998115.jpg', '2018-01-12 04:05:07'),
  (51440, 51336, 1, 2, 'Rojo Promotional Message to VIPs #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998116.jpg', '2018-01-08 05:05:07'),
  (51441, 51336, 1, 2, 'Rojo Promotional Message to VIPs #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998117.jpg', '2018-01-09 05:05:07'),
  (51442, 51336, 1, 2, 'Rojo Promotional Message to VIPs #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998118.jpg', '2018-01-10 05:05:07'),
  (51443, 51336, 1, 2, 'Rojo Promotional Message to VIPs #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998119.jpg', '2018-01-11 05:05:07'),
  (51444, 51336, 1, 2, 'Rojo Promotional Message to VIPs #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998120.jpg', '2018-01-12 05:05:07'),
  (51445, 51336, 2, 1, 'Rojo Informational Message to All #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998121.jpg', '2018-01-08 06:05:07'),
  (51446, 51336, 2, 1, 'Rojo Informational Message to All #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998122.jpg', '2018-01-09 06:05:07'),
  (51447, 51336, 2, 1, 'Rojo Informational Message to All #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998123.jpg', '2018-01-10 06:05:07'),
  (51448, 51336, 2, 1, 'Rojo Informational Message to All #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998124.jpg', '2018-01-11 06:05:07'),
  (51449, 51336, 2, 1, 'Rojo Informational Message to All #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998125.jpg', '2018-01-12 06:05:07'),
  (51450, 51336, 2, 2, 'Rojo Informational Message to VIPs #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998126.jpg', '2018-01-08 07:05:07'),
  (51451, 51336, 2, 2, 'Rojo Informational Message to VIPs #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998127.jpg', '2018-01-09 07:05:07'),
  (51452, 51336, 2, 2, 'Rojo Informational Message to VIPs #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998128.jpg', '2018-01-10 07:05:07'),
  (51453, 51336, 2, 2, 'Rojo Informational Message to VIPs #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998129.jpg', '2018-01-11 07:05:07'),
  (51454, 51336, 2, 2, 'Rojo Informational Message to VIPs #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/998130.jpg', '2018-01-12 07:05:07'),

  (51535, 51337, 1, 1, 'Fleming Promotional Message to All #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997111.jpg', '2018-01-08 04:05:08'),
  (51536, 51337, 1, 1, 'Fleming Promotional Message to All #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997112.jpg', '2018-01-09 04:05:08'),
  (51537, 51337, 1, 1, 'Fleming Promotional Message to All #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997113.jpg', '2018-01-10 04:05:08'),
  (51538, 51337, 1, 1, 'Fleming Promotional Message to All #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997114.jpg', '2018-01-11 04:05:08'),
  (51539, 51337, 1, 1, 'Fleming Promotional Message to All #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997115.jpg', '2018-01-12 04:05:08'),
  (51540, 51337, 1, 2, 'Fleming Promotional Message to VIPs #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997116.jpg', '2018-01-08 05:05:08'),
  (51541, 51337, 1, 2, 'Fleming Promotional Message to VIPs #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997117.jpg', '2018-01-09 05:05:08'),
  (51542, 51337, 1, 2, 'Fleming Promotional Message to VIPs #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997118.jpg', '2018-01-10 05:05:08'),
  (51543, 51337, 1, 2, 'Fleming Promotional Message to VIPs #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997119.jpg', '2018-01-11 05:05:08'),
  (51544, 51337, 1, 2, 'Fleming Promotional Message to VIPs #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997120.jpg', '2018-01-12 05:05:08'),
  (51545, 51337, 2, 1, 'Fleming Informational Message to All #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997121.jpg', '2018-01-08 06:05:08'),
  (51546, 51337, 2, 1, 'Fleming Informational Message to All #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997122.jpg', '2018-01-09 06:05:08'),
  (51547, 51337, 2, 1, 'Fleming Informational Message to All #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997123.jpg', '2018-01-10 06:05:08'),
  (51548, 51337, 2, 1, 'Fleming Informational Message to All #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997124.jpg', '2018-01-11 06:05:08'),
  (51549, 51337, 2, 1, 'Fleming Informational Message to All #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997125.jpg', '2018-01-12 06:05:08'),
  (51550, 51337, 2, 2, 'Fleming Informational Message to VIPs #1', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997126.jpg', '2018-01-08 07:05:08'),
  (51551, 51337, 2, 2, 'Fleming Informational Message to VIPs #2', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997127.jpg', '2018-01-09 07:05:08'),
  (51552, 51337, 2, 2, 'Fleming Informational Message to VIPs #3', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997128.jpg', '2018-01-10 07:05:08'),
  (51553, 51337, 2, 2, 'Fleming Informational Message to VIPs #4', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997129.jpg', '2018-01-11 07:05:08'),
  (51554, 51337, 2, 2, 'Fleming Informational Message to VIPs #5', 'We are awesome', 'http://bucket.s3-us-east-2a.amazonaws.com/wantify/997130.jpg', '2018-01-12 07:05:08');
  