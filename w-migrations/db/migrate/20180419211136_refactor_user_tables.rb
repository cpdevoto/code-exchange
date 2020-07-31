class RefactorUserTables < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
      SET ROLE '#{ENV['DATABASE_USERNAME']}';

      ALTER TABLE user_tbl DROP COLUMN user_type_id;
      ALTER TABLE user_tbl ADD COLUMN admin BOOLEAN NOT NULL DEFAULT FALSE;

      CREATE INDEX user_tbl_admin_idx ON user_tbl (admin);
      
      DROP TABLE user_type_tbl;
      DROP TABLE merchant_user_merchant_tbl;
      DROP TABLE customer_user_favorite_tbl;
      DROP TABLE merchant_vip_tbl;
      DROP TABLE merchant_user_tbl;
      DROP TABLE customer_user_tbl;
      DROP TABLE admin_user_tbl;

      CREATE TABLE user_favorite_tbl (
        user_id INTEGER REFERENCES user_tbl ON DELETE CASCADE,
        merchant_id INTEGER REFERENCES merchant_tbl ON DELETE CASCADE,
        PRIMARY KEY (user_id, merchant_id)
      );

      GRANT SELECT, INSERT, UPDATE, DELETE ON user_favorite_tbl TO server_user;

      CREATE TABLE merchant_vip_tbl (
        merchant_id INTEGER REFERENCES merchant_tbl ON DELETE CASCADE,
        user_id INTEGER REFERENCES user_tbl ON DELETE CASCADE,
        PRIMARY KEY (merchant_id, user_id)
      );
            
      GRANT SELECT, INSERT, UPDATE, DELETE ON merchant_vip_tbl TO server_user;

      ALTER TABLE merchant_tbl ADD COLUMN user_id INTEGER NOT NULL REFERENCES user_tbl ON DELETE CASCADE;

      CREATE INDEX merchant_tbl_user_id_idx ON merchant_tbl (user_id);

      GRANT SELECT ON merchant_tbl TO server_user;
      GRANT SELECT ON tag_group_tbl TO server_user;
      GRANT SELECT ON tag_tbl TO server_user;
      GRANT SELECT ON merchant_tag_tbl TO server_user;
      REVOKE INSERT, UPDATE, DELETE ON notice_scope_tbl FROM server_user;
      REVOKE USAGE, SELECT ON SEQUENCE notice_scope_tbl_id_seq FROM server_user;
      GRANT SELECT ON notice_scope_tbl TO server_user;
      REVOKE INSERT, UPDATE, DELETE ON notice_type_tbl FROM server_user;
      REVOKE USAGE, SELECT ON SEQUENCE notice_type_tbl_id_seq FROM server_user;
      GRANT SELECT ON notice_type_tbl TO server_user;
      GRANT SELECT ON notice_tbl TO server_user;

	SQL
  end

  def down
    execute <<-SQL
      SET ROLE '#{ENV['DATABASE_USERNAME']}';

      REVOKE SELECT ON merchant_tbl FROM server_user;
      REVOKE SELECT ON tag_group_tbl FROM server_user;
      REVOKE SELECT ON tag_tbl FROM server_user;
      REVOKE SELECT ON merchant_tag_tbl FROM server_user;
      GRANT INSERT, UPDATE, DELETE ON notice_scope_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE notice_scope_tbl_id_seq TO server_user;
      REVOKE SELECT ON notice_scope_tbl FROM server_user;
      GRANT INSERT, UPDATE, DELETE ON notice_type_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE notice_type_tbl_id_seq TO server_user;
      REVOKE SELECT ON notice_type_tbl FROM server_user;
      REVOKE SELECT ON notice_tbl FROM server_user;

      ALTER TABLE merchant_tbl DROP COLUMN user_id;

      DROP TABLE merchant_vip_tbl;
      DROP TABLE user_favorite_tbl;

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

      CREATE TABLE merchant_user_merchant_tbl (
        merchant_user_id INTEGER REFERENCES merchant_user_tbl ON DELETE CASCADE,
        merchant_id INTEGER UNIQUE REFERENCES merchant_tbl ON DELETE CASCADE,
        PRIMARY KEY (merchant_user_id, merchant_id)
      );

      GRANT INSERT, UPDATE, DELETE ON merchant_user_merchant_tbl TO server_user;

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

      CREATE TABLE user_type_tbl (
        id SERIAL PRIMARY KEY,
        name CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(name) <> '')
      );

      INSERT INTO user_type_tbl (id, name) VALUES
        (1, 'Customer'),
        (2, 'Merchant'),
        (3, 'Admin');

      GRANT SELECT ON user_type_tbl TO server_user;

      ALTER TABLE user_tbl DROP COLUMN admin;
      ALTER TABLE user_tbl ADD COLUMN user_type_id INTEGER NOT NULL REFERENCES user_type_tbl ON DELETE RESTRICT DEFAULT 1;

      CREATE INDEX user_tbl_user_type_id_idx ON user_tbl (user_type_id);

	SQL
  end
end
