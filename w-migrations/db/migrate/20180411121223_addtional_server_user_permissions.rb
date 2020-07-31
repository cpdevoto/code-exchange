class AddtionalServerUserPermissions < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';
  
      GRANT INSERT, UPDATE, DELETE ON user_tbl TO server_user;
      GRANT USAGE, SELECT ON SEQUENCE user_tbl_id_seq TO server_user;

    SQL
  end

  def down
	execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      REVOKE INSERT, UPDATE, DELETE ON user_tbl FROM server_user;
      REVOKE USAGE, SELECT ON SEQUENCE user_tbl_id_seq FROM server_user;

    SQL
  end
end
