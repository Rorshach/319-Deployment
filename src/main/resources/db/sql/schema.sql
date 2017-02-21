DROP TABLE IF EXISTS products_categories;
DROP TABLE IF EXISTS products_profiles;
DROP TABLE IF EXISTS products_requests;
DROP TABLE IF EXISTS product_statuses;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS employees_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS request_statuses;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS cost_centers;

CREATE TABLE IF NOT EXISTS products (
	id INT,
	name VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS profiles (
	id INT,
	name VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products_profiles (
	product_id INT,
	profile_id INT,
	PRIMARY KEY (product_id, profile_id)
);

CREATE TABLE IF NOT EXISTS categories (
	id INT,
	name VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products_categories (
product_id INT,
category_id INT,
PRIMARY KEY (product_id, category_id)
);

CREATE TABLE IF NOT EXISTS product_statuses (
id INT,
status VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cost_centers (
id INT,
name VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS request_statuses (
id INT,
status VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS employees (
 id INT,
 fName VARCHAR(255) NOT NULL,
 lName VARCHAR(255) NOT NULL,
 email VARCHAR(255) NOT NULL,
 reportsTo_id INT,
 costCenter_id INT,

 PRIMARY KEY (id),
 FOREIGN KEY (reportsTo_id) REFERENCES employees(id),
 FOREIGN KEY (costCenter_id) REFERENCES cost_centers(id)
);

CREATE TABLE IF NOT EXISTS roles (
id INT,
name VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS employees_roles (
employee_id INT,
role_id INT,
PRIMARY KEY (employee_id, role_id)
);

CREATE TABLE IF NOT EXISTS requests (
id INT,
notes VARCHAR(255),
dateCreated DATETIME NOT NULL,
submittedBy_id INT NOT NULL,
lastModified DATETIME NOT NULL,
lastModifiedBy_id INT NOT NULL,
status_id INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (submittedBy_id) REFERENCES employees(id),
FOREIGN KEY (lastModifiedBy_id) REFERENCES employees(id),
FOREIGN KEY (status_id) REFERENCES request_statuses(id)
);

CREATE TABLE IF NOT EXISTS products_requests (
id INT,
request_id INT,
product_id INT,
product_status_id INT,
PRIMARY KEY (id, request_id),
FOREIGN KEY (product_id) REFERENCES products(id),
FOREIGN KEY (product_status_id) REFERENCES product_statuses(id)
);