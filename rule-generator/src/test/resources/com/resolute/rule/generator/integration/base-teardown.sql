DELETE FROM customers;
DELETE FROM ad_rule_template_tbl WHERE id = 51335;
DELETE FROM ad_rule_tbl WHERE id = 51335;

--UPDATE ad_rule_template_tbl SET node_filter_expression = NULL WHERE id = 4;