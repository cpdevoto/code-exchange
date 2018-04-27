class MerchantUpdates < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
      SET ROLE '#{ENV['DATABASE_USERNAME']}';

      CREATE TABLE address_tbl (
        id SERIAL PRIMARY KEY,
        address1 CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(address1) <> ''),
        address2 CHARACTER VARYING,
        city CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(city) <> ''),
        state CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(state) <> ''),
        zip CHARACTER VARYING NOT NULL UNIQUE CHECK (trim(zip) <> '')
      );


      ALTER TABLE merchant_tbl DROP COLUMN address;
      ALTER TABLE merchant_tbl ADD COLUMN messaging_enabled BOOLEAN NOT NULL DEFAULT TRUE;
      ALTER TABLE merchant_tbl ADD COLUMN address_id INTEGER NOT NULL REFERENCES address_tbl ON DELETE RESTRICT;

	SQL
  end

  def down
    execute <<-SQL
      SET ROLE '#{ENV['DATABASE_USERNAME']}';

      ALTER TABLE merchant_tbl DROP COLUMN address_id;
      ALTER TABLE merchant_tbl DROP COLUMN messaging_enabled;
	  ALTER TABLE merchant_tbl ADD COLUMN address CHARACTER VARYING NOT NULL CHECK (trim(address) <> '');

      DROP TABLE IF EXISTS address_tbl;
	SQL
  end

end
