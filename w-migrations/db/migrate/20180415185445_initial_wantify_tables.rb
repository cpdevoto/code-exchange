class InitialWantifyTables < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      CREATE TABLE user_type_tbl (
        id SERIAL PRIMARY KEY,
        name CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(name) <> '')
      );

      INSERT INTO user_type_tbl (id, name) VALUES
        (1, 'Customer'),
        (2, 'Merchant'),
        (3, 'Admin');

      GRANT SELECT ON user_type_tbl TO server_user;

      ALTER TABLE user_tbl ADD COLUMN user_type_id INTEGER NOT NULL REFERENCES user_type_tbl ON DELETE RESTRICT DEFAULT 1;

      CREATE INDEX user_tbl_user_type_id_idx ON user_tbl (user_type_id);
      CREATE INDEX user_tbl_subject_idx ON user_tbl (subject);

      CREATE TABLE admin_user_tbl (
        id INTEGER PRIMARY KEY REFERENCES user_tbl ON DELETE CASCADE
      );
  
      GRANT INSERT, UPDATE, DELETE ON admin_user_tbl TO server_user;
 
      CREATE TABLE customer_user_tbl (
        id INTEGER PRIMARY KEY REFERENCES user_tbl ON DELETE CASCADE
      );
  
      GRANT INSERT, UPDATE, DELETE ON admin_user_tbl TO server_user;

      CREATE TABLE merchant_user_tbl (
        id INTEGER PRIMARY KEY REFERENCES user_tbl ON DELETE CASCADE
      );
  
      GRANT INSERT, UPDATE, DELETE ON merchant_user_tbl TO server_user;

      CREATE TABLE merchant_tbl (
        id SERIAL PRIMARY KEY,
        name CHARACTER VARYING NOT NULL CHECK (trim(name) <> ''),
        address CHARACTER VARYING NOT NULL CHECK (trim(address) <> ''),
        latitude CHARACTER VARYING NOT NULL CHECK (trim(latitude) <> ''),
        longitude CHARACTER VARYING NOT NULL CHECK (trim(longitude) <> ''),
        short_description CHARACTER VARYING NOT NULL CHECK (trim(short_description) <> ''), 
        long_description CHARACTER VARYING NOT NULL CHECK (trim(long_description) <> ''),
        logo CHARACTER VARYING NOT NULL CHECK (trim(logo) <> ''),
        cover_photo CHARACTER VARYING NOT NULL CHECK (trim(cover_photo) <> ''),
        phone_number CHARACTER VARYING,
        email CHARACTER VARYING,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        CONSTRAINT merchant_tbl_name_address_idx UNIQUE(name, address)
      );

      GRANT INSERT, UPDATE, DELETE ON merchant_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE merchant_tbl_id_seq TO server_user;
      
      CREATE TABLE merchant_user_merchant_tbl (
        merchant_user_id INTEGER REFERENCES merchant_user_tbl ON DELETE CASCADE,
        merchant_id INTEGER UNIQUE REFERENCES merchant_tbl ON DELETE CASCADE,
        PRIMARY KEY (merchant_user_id, merchant_id)
      );

      GRANT INSERT, UPDATE, DELETE ON merchant_user_merchant_tbl TO server_user;

      CREATE TABLE tag_group_tbl (
        id SERIAL PRIMARY KEY,
        name CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(name) <> '')
      );

      INSERT INTO tag_group_tbl (id, name) VALUES
        (1, 'Merchant Category');

      GRANT INSERT, UPDATE, DELETE ON tag_group_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE tag_group_tbl_id_seq TO server_user;

      CREATE TABLE tag_tbl (
        id SERIAL NOT NULL PRIMARY KEY,
        tag_group_id INTEGER NOT NULL REFERENCES tag_group_tbl ON DELETE CASCADE,
        name CHARACTER VARYING NOT NULL CHECK (trim(name) <> ''),
        CONSTRAINT tag_tbl_tag_group_id_name_idx UNIQUE(tag_group_id, name)
      );
      
      GRANT INSERT, UPDATE, DELETE ON tag_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE tag_tbl_id_seq TO server_user;

      CREATE TABLE merchant_tag_tbl (
        merchant_id INTEGER REFERENCES merchant_tbl ON DELETE CASCADE,
        tag_id INTEGER REFERENCES tag_tbl ON DELETE CASCADE,
        PRIMARY KEY (merchant_id, tag_id)
      );
 
      GRANT INSERT, UPDATE, DELETE ON merchant_tag_tbl TO server_user;

      CREATE TABLE customer_user_favorite_tbl (
        customer_user_id INTEGER REFERENCES customer_user_tbl ON DELETE CASCADE,
        merchant_id INTEGER UNIQUE REFERENCES merchant_tbl ON DELETE CASCADE,
        PRIMARY KEY (customer_user_id, merchant_id)
      );

      GRANT INSERT, UPDATE, DELETE ON customer_user_favorite_tbl TO server_user;

      CREATE TABLE merchant_vip_tbl (
        merchant_id INTEGER UNIQUE REFERENCES merchant_tbl ON DELETE CASCADE,
        customer_user_id INTEGER REFERENCES customer_user_tbl ON DELETE CASCADE,
        PRIMARY KEY (merchant_id, customer_user_id)
      );

      GRANT INSERT, UPDATE, DELETE ON merchant_vip_tbl TO server_user;

      CREATE TABLE notice_scope_tbl (
        id SERIAL PRIMARY KEY,
        name CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(name) <> '')
      );

      INSERT INTO notice_scope_tbl (id, name) VALUES
        (1, 'All Users'),
        (2, 'VIP Users');

      GRANT INSERT, UPDATE, DELETE ON notice_scope_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE notice_scope_tbl_id_seq TO server_user;

      CREATE TABLE notice_type_tbl (
        id SERIAL PRIMARY KEY,
        name CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(name) <> '')
      );

      INSERT INTO notice_type_tbl (id, name) VALUES
        (1, 'Promotional'),
        (2, 'Informational');

      GRANT INSERT, UPDATE, DELETE ON notice_type_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE notice_type_tbl_id_seq TO server_user;

      CREATE TABLE notice_tbl (
        id SERIAL PRIMARY KEY,
        merchant_id INTEGER NOT NULL REFERENCES merchant_tbl ON DELETE CASCADE,
        notice_scope_id INTEGER NOT NULL REFERENCES notice_scope_tbl ON DELETE RESTRICT,
        notice_type_id INTEGER NOT NULL REFERENCES notice_type_tbl ON DELETE RESTRICT,
        title CHARACTER VARYING NOT NULL CHECK (trim(title) <> ''),
        "text" CHARACTER VARYING NOT NULL CHECK (trim("text") <> ''),
        photo CHARACTER VARYING,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
      );

      GRANT INSERT, UPDATE, DELETE ON notice_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE notice_tbl_id_seq TO server_user;

    SQL
  end

  def down
	execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      DROP TABLE IF EXISTS notice_tbl;
      DROP TABLE IF EXISTS notice_type_tbl;
      DROP TABLE IF EXISTS notice_scope_tbl;
      DROP TABLE IF EXISTS merchant_vip_tbl;
      DROP TABLE IF EXISTS customer_user_favorite_tbl;
      DROP TABLE IF EXISTS merchant_tag_tbl;
      DROP TABLE IF EXISTS tag_tbl;
      DROP TABLE IF EXISTS tag_group_tbl;
      DROP TABLE IF EXISTS merchant_user_merchant_tbl;
      DROP TABLE IF EXISTS merchant_tbl;
      DROP TABLE IF EXISTS merchant_user_tbl;
      DROP TABLE IF EXISTS customer_user_tbl;
      DROP TABLE IF EXISTS admin_user_tbl;
     
      ALTER TABLE user_tbl DROP COLUMN user_type_id;

      DROP TABLE IF EXISTS user_type_tbl;

    SQL
  end
end
