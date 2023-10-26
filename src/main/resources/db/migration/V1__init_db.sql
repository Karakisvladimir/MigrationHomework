CREATE TABLE IF NOT EXISTS worker (
	id IDENTITY PRIMARY KEY,
	name VARCHAR(1000) NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 1000),
	birthday DATE CHECK (YEAR(birthday) > 1990),
	level VARCHAR(10) NOT NULL CHECK (level IN ('Trainee', 'Junior', 'Middle', 'Senior')),
	salary INT CHECK (salary BETWEEN 100 AND 100000)
);

CREATE TABLE IF NOT EXISTS client (
	id IDENTITY PRIMARY KEY,
	name VARCHAR(1000) NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 1000)
);

CREATE TABLE IF NOT EXISTS project (
	id IDENTITY PRIMARY KEY,
	client_id INT,
	start_date DATE,
	finish_date DATE,
	FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE IF NOT EXISTS project_worker (
	project_id INT,
	worker_id INT,
	PRIMARY KEY (project_id, worker_id),
	FOREIGN KEY (project_id) REFERENCES project(id),
	FOREIGN KEY (worker_id) REFERENCES worker(id)
);