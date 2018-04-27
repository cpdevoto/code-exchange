class UpdateUserTable < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
      SET ROLE '#{ENV['DATABASE_USERNAME']}';

      DROP TABLE IF EXISTS user_tbl;

      CREATE TABLE user_tbl (
        id SERIAL PRIMARY KEY,
        subject CHARACTER VARYING NOT NULL UNIQUE CHECK (subject <> ''), 
        email CHARACTER VARYING NOT NULL UNIQUE CHECK (email <> ''),
        preferred_username CHARACTER VARYING NOT NULL,
        name CHARACTER VARYING NOT NULL,
        given_name CHARACTER VARYING NOT NULL,
        family_name CHARACTER VARYING NOT NULL,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
      );

    SQL
  end

  def down
    execute <<-SQL
      SET ROLE '#{ENV['DATABASE_USERNAME']}';

      DROP TABLE IF EXISTS user_tbl;

      CREATE TABLE user_tbl (
        id SERIAL PRIMARY KEY,
        email CHARACTER VARYING NOT NULL UNIQUE CHECK (email <> ''),
        uuid uuid NOT NULL DEFAULT gen_random_uuid(),
        first_name CHARACTER VARYING NOT NULL DEFAULT ''::CHARACTER VARYING,
        last_name CHARACTER VARYING NOT NULL DEFAULT ''::CHARACTER VARYING,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
      );

    SQL
  end

end
