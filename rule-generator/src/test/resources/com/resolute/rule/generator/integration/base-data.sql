INSERT INTO customers (id, distributor_id, uuid, name, resolute_start_date) VALUES 
  (1, 1, 'b558664d-fcf7-48e8-9807-e7a7614f22bc', 'TEAM Schostak Family Restaurants', '2014-05-01T00:00:00.000'),
  (2, 1, 'b558664d-fcf7-48e8-9807-e7a7614f22bb', 'Galeforce Holdings', '2014-05-01T00:00:00.000'),
  (3, 1, 'b558664d-fcf7-48e8-9807-e7a7614f22ba', 'McLaren', '2014-05-01T00:00:00.000'),
  (4, 1, 'b558664d-fcf7-48e8-9807-e7a7614f22b9', 'Schostak: Laurel Park Place Office Center', '2014-05-01T00:00:00.000'),
  (5, 1, 'b558664d-fcf7-48e8-9807-e7a7614f22b8', 'Worthington Industries', '2014-05-01T00:00:00.000'),
  (6, 1, 'b558664d-fcf7-48e8-9807-e7a7614f22b7', 'USA Hockey Arena', '2014-05-01T00:00:00.000'),
  (7, 1, '95ee18c1-305a-4074-bea2-04d261e8800b', 'Redico', '2014-05-01T00:00:00.000');

INSERT INTO ad_rule_tbl (id, name, description) VALUES
  (51335, 'Invariant2', 'Computes an invariant rule for a specified point');

INSERT INTO ad_rule_template_tbl (id, ad_rule_id, name, display_name, description, node_filter_expression) VALUES
  (51335, 51335, 'Invariant2', 'Invariant', 'Computes an invariant rule for a specified point', NULL);
  
--UPDATE ad_rule_template_tbl SET node_filter_expression = 'dxCool && !rooftop' WHERE id = 4;  
  