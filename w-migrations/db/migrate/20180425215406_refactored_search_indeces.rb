class RefactoredSearchIndeces < ActiveRecord::Migration[5.0]
  def up
    execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';
  
      DROP INDEX IF EXISTS merchant_tbl_name_idx;
      DROP INDEX IF EXISTS tag_tbl_name_idx;

      CREATE INDEX merchant_tbl_name_idx ON merchant_tbl USING GIN (to_tsvector('english', name));
      CREATE INDEX tag_tbl_name_idx ON tag_tbl USING GIN (to_tsvector('english', name));
    SQL
  end

  def down
	execute <<-SQL
   	  SET ROLE '#{ENV['DATABASE_USERNAME']}';

      DROP INDEX IF EXISTS merchant_tbl_name_idx;
      DROP INDEX IF EXISTS tag_tbl_name_idx;

      CREATE INDEX merchant_tbl_name_idx ON merchant_tbl USING btree ( lower (name) text_pattern_ops);
      CREATE INDEX tag_tbl_name_idx ON tag_tbl USING btree ( lower (name) text_pattern_ops);
    SQL
  end
end
