DROP TABLE IF EXISTS products_categories^;
DROP TABLE IF EXISTS products_profiles^;
DROP TABLE IF EXISTS products_requests^;
DROP TABLE IF EXISTS product_statuses^;
DROP TABLE IF EXISTS profiles^;
DROP TABLE IF EXISTS categories^;
DROP TABLE IF EXISTS employees_roles^;
DROP TABLE IF EXISTS roles^;
DROP TABLE IF EXISTS requests^;
DROP TABLE IF EXISTS request_statuses^;
DROP TABLE IF EXISTS products^;
DROP TABLE IF EXISTS employees^;
DROP TABLE IF EXISTS cost_centers^;

CREATE TABLE IF NOT EXISTS products (
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(255),

	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS profiles (
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(255),

	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS products_profiles (
	product_id INT UNSIGNED,
	profile_id INT UNSIGNED,

	PRIMARY KEY (product_id, profile_id)
)^;

CREATE TABLE IF NOT EXISTS categories (
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(255),

	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS products_categories (
	product_id INT UNSIGNED,
	category_id INT UNSIGNED,

	PRIMARY KEY (product_id, category_id)
)^;

CREATE TABLE IF NOT EXISTS product_statuses (
	id INT UNSIGNED AUTO_INCREMENT,
	status VARCHAR(255),
	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS cost_centers (
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(255),

	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS request_statuses (
	id INT UNSIGNED AUTO_INCREMENT,
	status VARCHAR(255),

	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS employees (
	id INT,
	fName VARCHAR(255) NOT NULL,
	lName VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
    pwd VARCHAR(255) NOT NULL,
	reportsTo_id INT,
	costCenter_id INT UNSIGNED NOT NULL,

	PRIMARY KEY (id,email),
	FOREIGN KEY (reportsTo_id) REFERENCES employees(id),
	FOREIGN KEY (costCenter_id) REFERENCES cost_centers(id)
)^;

CREATE TABLE IF NOT EXISTS roles (
	id INT UNSIGNED AUTO_INCREMENT,
	name VARCHAR(255),

	PRIMARY KEY (id)
)^;

CREATE TABLE IF NOT EXISTS employees_roles (
	employee_id INT,
	role_id INT UNSIGNED,

	PRIMARY KEY (employee_id, role_id)
)^;

CREATE TABLE IF NOT EXISTS requests (
	id INT UNSIGNED AUTO_INCREMENT,
	notes VARCHAR(255),
	dateCreated DATETIME NOT NULL,
	submittedBy_id INT NOT NULL,
	lastModified DATETIME NOT NULL,
	lastModifiedBy_id INT NOT NULL,
	status_id INT UNSIGNED NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY (submittedBy_id) REFERENCES employees(id),
	FOREIGN KEY (lastModifiedBy_id) REFERENCES employees(id),
	FOREIGN KEY (status_id) REFERENCES request_statuses(id)
)^;

CREATE TABLE IF NOT EXISTS products_requests (
	id INT UNSIGNED AUTO_INCREMENT,
	request_id INT UNSIGNED,
	product_id INT UNSIGNED,
	product_status_id INT UNSIGNED,

	PRIMARY KEY (id, request_id),
	FOREIGN KEY (product_id) REFERENCES products(id),
	FOREIGN KEY (product_status_id) REFERENCES product_statuses(id)
)^;

#--------------------------------------------------------------------------------------------
#  Stub User
#--------------------------------------------------------------------------------------------
^;

INSERT INTO cost_centers (id, name) VALUES(1, 'a')^;

INSERT INTO employees_roles (employee_id, role_id) VALUES (1, 1)^;

INSERT INTO employees (id,fName,lName, email, pwd, reportsTo_id, costCenter_id) VALUES(1, 'nancy', 'chen', 'a', 'b', 1, 1)^;

#--------------------------------------------------------------------------------------------
#  Stored Procedures
#--------------------------------------------------------------------------------------------
^;

#--------------------------------------------------------------------------------------------
# Description:  Retrieves an employee profile with matching email
#               attribute 
#
# Called By:    Coast Capital Requisitioning Application
# Parameters: 	inout_employee_email VARCHAR(255)
# Returns:    	employee record
#               employee_role record for granting access to pages
#
# Created By:  	Nancy Chen
# Created On:  	2017-2-23
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_employees_lookupEmployeeByEmail^;

CREATE PROCEDURE req_employees_lookupEmployeeByEmail
	(
        OUT out_employee_id INT,
        OUT out_employee_fName VARCHAR(255),
        OUT out_employee_lName VARCHAR(255),
        INOUT inout_employee_email VARCHAR(255),
        OUT out_employee_pwd VARCHAR(255),
	    OUT out_employee_role INT UNSIGNED
    )
	BEGIN
		SELECT employees.id, fName, lName, pwd, role_id
        INTO out_employee_id, out_employee_fName,  out_employee_lName, out_employee_pwd, out_employee_role
        FROM employees
        INNER JOIN employees_roles
        ON employees.id = employees_roles.employee_id
        WHERE employees.email = inout_employee_email;
	END ^;

#--------------------------------------------------------------------------------------------
# Description:  Generates listing of all product categories
#
# Called By:    Coast Capital Requisitioning Application
# Parameters: 	none
# Returns:    	recordset
#
# Created By:  	Chris Semiao, Felix Tso
# Created On:  	2017-2-20
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_categories_getAll^;

CREATE PROCEDURE req_categories_getAll
	()
	BEGIN
		select id, name from categories;
	END ^;

#---------------------------------------------------------------------------------------------
# Description: 	Insert a request and returns it with autoincremented id set.
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	inout_notes 				VARCHAR(255),
#				inout_dateCreated			DATETIME,
#				inout_submittedBy_id 		INT,
#				inout_lastModified 		    DATETIME,
#				inout_lastModifiedBy_id 	INT,
#				inout_status_id			    INT
#
# Returns:    	request record
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-20
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_requests_insert^;

CREATE PROCEDURE req_requests_insert
	(
		INOUT inout_notes VARCHAR(250),
		INOUT inout_dateCreated DATETIME,
		INOUT inout_submittedBy_id INT,
		INOUT inout_lastModified DATETIME,
		INOUT inout_lastModifiedBy_id INT,
		INOUT inout_status_id INT UNSIGNED,
		OUT out_id INT UNSIGNED
	)
	BEGIN
		INSERT INTO requests VALUES (null, inout_notes, inout_dateCreated, inout_submittedBy_id, inout_lastModified, inout_lastModifiedBy_id, inout_status_id);
		SELECT LAST_INSERT_ID() INTO out_id;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Insert a product into a request and return the product and its status
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_request_id 				INT UNSIGNED,
#				inout_product_id 			INT UNSIGNED,
#				inout_product_status_id 	INT UNSIGNED
#
# Returns:    	product record
#				product_status record for product
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-21
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_productInRequest_insert^;

CREATE PROCEDURE req_productInRequest_insert
	(
		IN in_request_id INT UNSIGNED,
		INOUT inout_product_id INT UNSIGNED,
		INOUT inout_product_status_id INT UNSIGNED,
		OUT out_product_name VARCHAR(255)
	)
	BEGIN
		INSERT INTO products_requests VALUES (null, in_request_id, inout_product_id, inout_product_status_id);
		SELECT name INTO out_product_name FROM products WHERE id = inout_product_id;
	END ^;
