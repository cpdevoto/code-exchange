class ServerUserPermissions < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';
  
      GRANT SELECT ON user_tbl TO server_user;

    SQL
  end

  def down
	execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      REVOKE SELECT ON user_tbl FROM server_user;

    SQL
  end
end
