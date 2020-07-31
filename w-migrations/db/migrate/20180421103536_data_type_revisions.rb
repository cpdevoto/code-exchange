class DataTypeRevisions < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      SET TIMEZONE TO 'GMT';
  
      CREATE EXTENSION IF NOT EXISTS cube;
      CREATE EXTENSION IF NOT EXISTS earthdistance;

      ALTER FUNCTION earth() OWNER TO #{ENV['DATABASE_USERNAME']};

      ALTER TABLE user_tbl ALTER COLUMN id TYPE BIGINT;
      ALTER TABLE merchant_tbl ALTER COLUMN id TYPE BIGINT;
      ALTER TABLE address_tbl ALTER COLUMN id TYPE BIGINT;
      ALTER TABLE notice_tbl ALTER COLUMN id TYPE BIGINT;
      ALTER TABLE tag_tbl ALTER COLUMN id TYPE BIGINT;

      ALTER TABLE merchant_tbl ALTER COLUMN user_id TYPE BIGINT;
      ALTER TABLE merchant_tbl ALTER COLUMN address_id TYPE BIGINT;
      ALTER TABLE user_favorite_tbl ALTER COLUMN user_id TYPE BIGINT;
      ALTER TABLE user_favorite_tbl ALTER COLUMN merchant_id TYPE BIGINT;
      ALTER TABLE merchant_vip_tbl ALTER COLUMN user_id TYPE BIGINT;
      ALTER TABLE merchant_vip_tbl ALTER COLUMN merchant_id TYPE BIGINT;
      ALTER TABLE notice_tbl ALTER COLUMN merchant_id TYPE BIGINT;
      ALTER TABLE merchant_tag_tbl ALTER COLUMN tag_id TYPE BIGINT;
      ALTER TABLE merchant_tag_tbl ALTER COLUMN merchant_id TYPE BIGINT;

      ALTER TABLE merchant_tbl ALTER COLUMN latitude TYPE FLOAT USING CAST(latitude AS FLOAT);
      ALTER TABLE merchant_tbl ALTER COLUMN longitude TYPE FLOAT USING CAST(longitude AS FLOAT);

      CREATE INDEX merchant_tbl_lat_lng_idx on merchant_tbl USING gist(ll_to_earth(latitude, longitude));
      
      -- Alter the earth function to return the radius of the earth in miles
	  CREATE OR REPLACE FUNCTION "public"."earth"() RETURNS "float8" 
		AS $BODY$SELECT '3959'::float8$BODY$
		LANGUAGE sql
		COST 100
		CALLED ON NULL INPUT
		SECURITY INVOKER
		IMMUTABLE;

    SQL
  end

  def down
	execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      ALTER TABLE user_tbl ALTER COLUMN id TYPE INTEGER;
      ALTER TABLE merchant_tbl ALTER COLUMN id TYPE INTEGER;
      ALTER TABLE address_tbl ALTER COLUMN id TYPE INTEGER;
      ALTER TABLE notice_tbl ALTER COLUMN id TYPE INTEGER;
      ALTER TABLE tag_tbl ALTER COLUMN id TYPE INTEGER;

      ALTER TABLE merchant_tbl ALTER COLUMN user_id TYPE INTEGER;
      ALTER TABLE merchant_tbl ALTER COLUMN address_id TYPE INTEGER;
      ALTER TABLE user_favorite_tbl ALTER COLUMN user_id TYPE INTEGER;
      ALTER TABLE user_favorite_tbl ALTER COLUMN merchant_id TYPE INTEGER;
      ALTER TABLE merchant_vip_tbl ALTER COLUMN user_id TYPE INTEGER;
      ALTER TABLE merchant_vip_tbl ALTER COLUMN merchant_id TYPE INTEGER;
      ALTER TABLE notice_tbl ALTER COLUMN merchant_id TYPE INTEGER;
      ALTER TABLE merchant_tag_tbl ALTER COLUMN tag_id TYPE INTEGER;
      ALTER TABLE merchant_tag_tbl ALTER COLUMN merchant_id TYPE INTEGER;

      DROP INDEX IF EXISTS merchant_tbl_lat_lng_idx;

      ALTER TABLE merchant_tbl ALTER COLUMN latitude TYPE CHARACTER VARYING;
      ALTER TABLE merchant_tbl ALTER COLUMN longitude TYPE CHARACTER VARYING;

	  CREATE OR REPLACE FUNCTION "public"."earth"() RETURNS "float8" 
		AS $BODY$SELECT '6378168'::float8$BODY$
		LANGUAGE sql
		COST 100
		CALLED ON NULL INPUT
		SECURITY INVOKER
		IMMUTABLE;

 

    SQL
  end
end
