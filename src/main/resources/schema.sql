DROP TABLE IF EXISTS Product_Category^;
DROP TABLE IF EXISTS Product_ProfileGroup^;
DROP TABLE IF EXISTS Product_Request^;
DROP TABLE IF EXISTS ProductStatus^;
DROP TABLE IF EXISTS ProfileGroup^;
DROP TABLE IF EXISTS Category^;
DROP TABLE IF EXISTS Employee_Role^;
DROP TABLE IF EXISTS Role^;
DROP TABLE IF EXISTS Request^;
DROP TABLE IF EXISTS RequestStatus^;
DROP TABLE IF EXISTS Product^;
DROP TABLE IF EXISTS Employee^;
DROP TABLE IF EXISTS CostCenter^;

CREATE TABLE IF NOT EXISTS Product (
	productCode VARCHAR(10),
	productDescription VARCHAR(50),
	PRIMARY KEY (productCode)
)^;

CREATE TABLE IF NOT EXISTS ProfileGroup (
	profileCode VARCHAR(10),
	profileDescription VARCHAR(50),

	PRIMARY KEY (profileCode)
)^;

CREATE TABLE IF NOT EXISTS Product_ProfileGroup (
	productCode VARCHAR(10),
	profileCode VARCHAR(10),

	PRIMARY KEY (productCode, profileCode),
	FOREIGN KEY (productCode) REFERENCES Product(productCode),
	FOREIGN KEY (profileCode) REFERENCES ProfileGroup(profileCode)
)^;

CREATE TABLE IF NOT EXISTS Category (
	categoryCode VARCHAR(15),
	categoryDescription VARCHAR(50),

	PRIMARY KEY (categoryCode)
)^;

CREATE TABLE IF NOT EXISTS Product_Category (
	productCode VARCHAR(10),
	categoryCode VARCHAR(15),

	PRIMARY KEY (productCode, categoryCode),
	FOREIGN KEY (productCode) REFERENCES Product(productCode),
	FOREIGN KEY (categoryCode) REFERENCES Category(categoryCode)
)^;

CREATE TABLE IF NOT EXISTS ProductStatus (
	statusCode CHAR(4),
	statusDescription VARCHAR(50),

	PRIMARY KEY (statusCode)
)^;

CREATE TABLE IF NOT EXISTS CostCenter (
	costCenterCode VARCHAR(10),
	costCenterDescription VARCHAR(50),

	PRIMARY KEY (costCenterCode)
)^;

CREATE TABLE IF NOT EXISTS RequestStatus (
	statusCode CHAR(4),
	statusDescription VARCHAR(50),

	PRIMARY KEY (statusCode)
)^;

CREATE TABLE IF NOT EXISTS Employee (
	employeeId CHAR(9),
	password VARCHAR(200),
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	reportsTo CHAR(9),
	costCenterCode VARCHAR(10),

	PRIMARY KEY (employeeId, email),
	FOREIGN KEY (reportsTo) REFERENCES Employee(employeeId),
	FOREIGN KEY (costCenterCode) REFERENCES CostCenter(costCenterCode)
)^;

CREATE TABLE IF NOT EXISTS Role (
	roleCode CHAR(4),
	roleDescription  VARCHAR(50),

	PRIMARY KEY (roleCode)
)^;

CREATE TABLE IF NOT EXISTS Employee_Role (
	employeeId CHAR(9),
	roleCode CHAR(4),

	PRIMARY KEY (employeeId, roleCode),
	FOREIGN KEY (employeeId) REFERENCES Employee(employeeId),
	FOREIGN KEY (roleCode) REFERENCES Role(roleCode)
)^;

CREATE TABLE IF NOT EXISTS Request (
	requestId BIGINT AUTO_INCREMENT,
	notes VARCHAR(255),
	dateCreated DATETIME NOT NULL,
	submittedBy CHAR(9) NOT NULL,
	lastModified DATETIME NOT NULL,
	lastModifiedBy CHAR(9) NOT NULL,
	statusCode CHAR(4) NOT NULL,

	PRIMARY KEY (requestId),
	FOREIGN KEY (submittedBy) REFERENCES Employee(employeeId),
	FOREIGN KEY (lastModifiedBy) REFERENCES Employee(employeeId),
	FOREIGN KEY (statusCode) REFERENCES RequestStatus(statusCode)
)^;

CREATE TABLE IF NOT EXISTS Product_Request (
	transactionId BIGINT AUTO_INCREMENT,
	requestId BIGINT,
	productCode VARCHAR(10),
	statusCode CHAR(4),

	PRIMARY KEY (transactionId, requestId),
	FOREIGN KEY (productCode) REFERENCES Product(productCode),
	FOREIGN KEY (statusCode) REFERENCES ProductStatus(statusCode)
)^;

#--------------------------------------------------------------------------------------------
#  Stored Procedures
#--------------------------------------------------------------------------------------------
^;

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
DROP PROCEDURE IF EXISTS req_category_lookupAll^;
CREATE PROCEDURE req_category_lookupAll
	()
	BEGIN
		SELECT categoryCode, categoryDescription FROM Category;
	END ^;

#---------------------------------------------------------------------------------------------
# Description: 	Insert a request and returns it with autoincremented id set.
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	inout_notes 							VARCHAR(255),
#								inout_dateCreated					DATETIME,
#								inout_submittedBy 				CHAR(9),
#								inout_lastModified 				DATETIME,
#								inout_lastModifiedBy 			CHAR(9),
#								inout_statusCode					CHAR(4)
#
# Returns:    	request record
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-20
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_request_insert^;
CREATE PROCEDURE req_request_insert
	(
		INOUT inout_notes VARCHAR(255),
		INOUT inout_dateCreated DATETIME,
		INOUT inout_submittedBy CHAR(9),
		INOUT inout_lastModified DATETIME,
		INOUT inout_lastModifiedBy CHAR(9),
		INOUT inout_statusCode CHAR(4),
		OUT out_requestId BIGINT
	)
	BEGIN
		INSERT INTO Request VALUES (null, inout_notes, inout_dateCreated, inout_submittedBy, inout_lastModified, inout_lastModifiedBy, inout_statusCode);
		SELECT LAST_INSERT_ID() INTO out_requestId;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Insert a product into a request and return the product and its status
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_requestId				BIGINT,
#				inout_product_id 			INT UNSIGNED,
#				inout_product_status_id 	INT UNSIGNED
#
# Returns:    	product record
#				product_status record for that product
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-21
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_productInRequest_insert^;
CREATE PROCEDURE req_productInRequest_insert
	(
		IN in_requestId BIGINT,
		INOUT inout_productCode VARCHAR(10),
		INOUT inout_statusCode CHAR(4),
		OUT out_productDescription VARCHAR(50)
	)
	BEGIN
		INSERT INTO Product_Request VALUES (null, in_requestId, inout_productCode, inout_statusCode);
		SELECT productDescription INTO out_productDescription FROM Product WHERE productCode = inout_productCode;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Update a requests status field and returns that request
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	inout_requestId 		BIGINT,
#				inout_statusCode 		CHAR(4)
#
# Returns:    	request record
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-22
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_request_updateStatus^;
CREATE PROCEDURE req_request_updateStatus
	(
		INOUT inout_requestId BIGINT,
		INOUT inout_statusCode CHAR(4),
		OUT out_notes VARCHAR(255),
		OUT out_dateCreated DATETIME,
		OUT out_submittedBy CHAR(9),
		OUT out_lastModified DATETIME,
		OUT out_lastModifiedBy CHAR(9)
	)
	BEGIN
		UPDATE Request SET statusCode = inout_statusCode WHERE requestId = inout_requestId;
		SELECT notes, dateCreated, submittedBy, lastModified, lastModifiedBy INTO out_notes, out_dateCreated, out_submittedBy, out_lastModified, out_lastModifiedBy FROM Request WHERE requestId = inout_requestId;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Retrieve products their product statuses by request id
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_requestId				BIGINT
#
# Returns:    	product and status recordset
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-22
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_productInRequest_lookupByRequestId^;
CREATE PROCEDURE req_productInRequest_lookupByRequestId
	(
		IN in_requestId BIGINT
	)
	BEGIN
		SELECT p.productCode, p.productDescription, pr.statusCode FROM Product p JOIN Product_Request pr ON p.productCode = pr.productCode where pr.requestId = in_requestId;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Verifies if a request record exists, given its id
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_requestId 		BIGINT
#
# Returns:    	out_exists      	BOOLEAN
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-22
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_request_lookupExists^;
CREATE PROCEDURE req_request_lookupExists
	(
		IN in_requestId BIGINT,
		OUT out_exists BOOLEAN
	)
	BEGIN
		SELECT EXISTS(SELECT requestId FROM Request WHERE requestId = in_requestId) INTO out_exists;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Retrieve id and name information for all profileGroups
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	none
#
# Returns:    	resultset
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-22
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_profileGroup_lookupAll^;
CREATE PROCEDURE req_profileGroup_lookupAll
	()
	BEGIN
		select profileCode, profileDescription from ProfileGroup;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Retrieve a profileGroup given its profileCode
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_profileCode   VARCHAR(10)
#
# Returns:    	profileGroup record
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-22
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_profileGroup_lookupById^;
CREATE PROCEDURE req_profileGroup_lookupById
	(
		INOUT inout_profileCode VARCHAR(10),
		OUT out_profileDescription VARCHAR(50)
	)
	BEGIN
		SELECT profileDescription INTO out_profileDescription FROM ProfileGroup WHERE profileCode = inout_profileCode;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Retrieve a list of products associated with a profileGroup, given a profileCode
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_profileCode 		VARCHAR(10)
#
# Returns:    	products recordset
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-23
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_productInProfileGroup_lookupByProfileGroupCode^;
CREATE PROCEDURE req_productInProfileGroup_lookupByProfileGroupCode
	(
		INOUT in_profileCode VARCHAR(10)
	)
	BEGIN
		SELECT p.productCode, p.productDescription FROM Product p JOIN Product_ProfileGroup pg ON p.productCode = pg.productCode WHERE pg.profileCode = in_profileCode;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Verifies if a profileGroup record exists, given its profileCode
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	inout_id 				INT UNSIGNED
#
# Returns:    	out_exists      BOOLEAN
#
# Created By:  	Chris Semiao
# Created On:  	2017-2-23
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_profileGroup_lookupExists^;
CREATE PROCEDURE req_profileGroup_lookupExists
	(
		IN in_profileCode VARCHAR(10),
		OUT out_exists BOOLEAN
	)
	BEGIN
		SELECT EXISTS(SELECT profileCode FROM ProfileGroup WHERE profileCode = in_profileCode) INTO out_exists;
	END ^;

#--------------------------------------------------------------------------------------------
# Description: 	Retrieve a request given its id
#
# Called By:  	Coast Capital Requisitioning Application
#
# Parameters: 	in_id   INT UNSIGNED
#
# Returns:    	request record
#
# Created By:  	Chris Semiao, Felix Tso
# Created On:  	2017-2-23
#---------------------------------------------------------------------------------------------
^;
DROP PROCEDURE IF EXISTS req_request_lookupById^;
CREATE PROCEDURE req_request_lookupById
  (
    INOUT inout_requestId BIGINT,
    OUT out_notes VARCHAR(255),
    OUT out_dateCreated DATETIME,
    OUT out_submittedBy CHAR(9),
    OUT out_lastModified DATETIME,
    OUT out_lastModifiedBy CHAR(9),
    OUT out_statusCode CHAR(4)
  )
  BEGIN
    SELECT notes, dateCreated, submittedBy, lastModified, lastModifiedBy, statusCode INTO out_notes, out_dateCreated, out_submittedBy, out_lastModified, out_lastModifiedBy, out_statusCode FROM request WHERE requestId = inout_requestId;
  END ^;

^;
