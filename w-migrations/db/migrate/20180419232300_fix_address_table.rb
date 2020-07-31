class FixAddressTable < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';
  
      ALTER TABLE address_tbl DROP CONSTRAINT address_tbl_address1_key;
      ALTER TABLE address_tbl DROP CONSTRAINT address_tbl_city_key;
      ALTER TABLE address_tbl DROP CONSTRAINT address_tbl_state_key;
      ALTER TABLE address_tbl DROP CONSTRAINT address_tbl_zip_key;

    SQL
  end

  def down
	execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      ALTER TABLE address_tbl ADD CONSTRAINT address_tbl_address1_key UNIQUE (address1);
      ALTER TABLE address_tbl ADD CONSTRAINT address_tbl_city_key UNIQUE (city);
      ALTER TABLE address_tbl ADD CONSTRAINT address_tbl_state_key UNIQUE (state);
      ALTER TABLE address_tbl ADD CONSTRAINT address_tbl_zip_key UNIQUE (zip);

    SQL
  end
end
