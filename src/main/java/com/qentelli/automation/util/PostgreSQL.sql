--Postgresql 9.2.4 version different db creation scripts

CREATE TABLE env (
   env_id serial primary key,
    name VARCHAR (50) NOT NULL
);

INSERT INTO env (name) VALUES('DEV2');
INSERT INTO env (name) VALUES('DEV3');
INSERT INTO env (name) VALUES('QA2');
INSERT INTO env (name) VALUES('QA3');
INSERT INTO env (name) VALUES('QA5');
INSERT INTO env (name) VALUES('UAT');

CREATE TABLE application (
  application_id serial primary key,
    name VARCHAR (50) NOT NULL
);
INSERT INTO application (name) VALUES('TBB');
INSERT INTO application (name) VALUES('COO');
INSERT INTO application (name) VALUES('SHAC');


CREATE TABLE project (
 project_id serial primary key,
    name VARCHAR (50) NOT NULL
);

INSERT INTO project (name) VALUES('Myx');
INSERT INTO project (name) VALUES('PreferredCustomer');
INSERT INTO project (name) VALUES('Unification');
INSERT INTO project (name) VALUES('Ops');
INSERT INTO project (name) VALUES('6MR');

CREATE TABLE suite (
suite_id serial primary key,
    name VARCHAR (50) NOT NULL
);

INSERT INTO suite (name) VALUES('Smoke');
INSERT INTO suite (name) VALUES('CriticalRegression');
INSERT INTO suite (name) VALUES('FullRegression');

CREATE TABLE locale (
 locale_id serial primary key,
    name VARCHAR (50) NOT NULL
);

INSERT INTO locale (name) VALUES('en_GB');
INSERT INTO locale (name) VALUES('en_US');

CREATE TABLE bucket (
 bucket_id serial primary key,
    name VARCHAR (50) NOT NULL
);

INSERT INTO bucket (name) VALUES('Dev');
INSERT INTO bucket (name) VALUES('Live');

CREATE TABLE set
	( set_id serial PRIMARY KEY NOT NULL,
			project_id INT NOT NULL,
			locale_id INT NOT NULL,
			application_id INT NOT NULL,
			bucket_id INT NOT NULL,
			suite_id INT NOT NULL,
			env_id INT NOT NULL,
		    run_id bigint NOT NULL,
			testrail VARCHAR (50) NOT NULL,
			loglink VARCHAR (5000),
	      	lid bigint NOT NULL,
			mobile Boolean NOT NULL,
			platform VARCHAR (50) NOT NULL,
			browser VARCHAR (50) NOT NULL,
			failed smallint NOT NULL,
			skipped smallint NOT NULL,
			passed smallint NOT NULL,
			total smallint NOT NULL,
			duration bigint NOT NULL,
			start_time bigint NOT NULL,
			end_time bigint NOT NULL,
			time bigint NOT NULL,
			by_user VARCHAR (50) NOT NULL);



ALTER TABLE set ADD CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES project (project_id);
ALTER TABLE set ADD CONSTRAINT fk_application_id FOREIGN KEY (application_id) REFERENCES application (application_id);
ALTER TABLE set ADD CONSTRAINT fk_suite_id FOREIGN KEY (suite_id) REFERENCES suite (suite_id);
ALTER TABLE set	ADD CONSTRAINT fk_bucket_id FOREIGN KEY (bucket_id) REFERENCES bucket (bucket_id);
ALTER TABLE set	ADD CONSTRAINT fk_locale_id FOREIGN KEY (locale_id) REFERENCES locale (locale_id);
ALTER TABLE set ADD CONSTRAINT fk_env_id FOREIGN KEY (env_id) REFERENCES env (env_id);

-- create scenario table

CREATE TABLE scenario
	( scenario_id serial PRIMARY KEY NOT NULL,
			set_id bigint NOT NULL,
			run_id bigint NOT NULL,
	 		scenario_name  VARCHAR (1000) NOT NULL,
			testrail VARCHAR (50) NOT NULL,
			feature_name VARCHAR (1000) NOT NULL,
	 		error_type VARCHAR (1000),
	      	lid bigint NOT NULL,
	 		testRailLink VARCHAR (1000),
			duration bigint NOT NULL,
			start_time bigint NOT NULL,
			end_time bigint NOT NULL,
	 		total_steps smallint NOT NULL,
		    result VARCHAR (50) NOT NULL,
			failed smallint NOT NULL,
			skipped smallint NOT NULL,
			passed smallint NOT NULL,
			sauce_link VARCHAR (500) ,
	 		server_info VARCHAR (500) ,
	 		sauce_video VARCHAR (5000) ,
	 		sauce_html text,
			comment text,
			jira_link VARCHAR (500),
      		FOREIGN KEY(set_id)
	  			REFERENCES set(set_id),
	  				sauce_video VARCHAR (5000) ,
	);

CREATE TABLE step
	( step_id serial PRIMARY KEY NOT NULL,
			scenario_id bigint NOT NULL,
			run_id bigint NOT NULL,
	 		step_name  VARCHAR (1000) NOT NULL,
			testrail VARCHAR (50) NOT NULL,
	      	lid bigint NOT NULL,
	 		line int,
			duration bigint NOT NULL,
			start_time bigint NOT NULL,
			end_time bigint NOT NULL,
		     result VARCHAR (50) NOT NULL,
      		FOREIGN KEY(scenario_id)
	  		REFERENCES scenario(scenario_id)
	);

CREATE OR REPLACE  VIEW v_set AS
    	SELECT set_id, end_time, by_user, run_id, duration,
		application.name AS application_name,
		project.name AS project_name,
		suite.name AS suite_name,
    	env.name AS env_name,
		locale.name AS locale_name,
		bucket.name AS bucket_name,
		loglink,
		mobile,
		passed,
		total,
		failed,
		skipped,
		browser,
		platform
    	FROM set JOIN application ON set.application_id = application.application_id
    	JOIN project ON set.project_id = project.project_id
    	JOIN suite ON set.suite_id = suite.suite_id
    	JOIN locale ON set.locale_id = locale.locale_id
    	JOIN bucket ON set.bucket_id = bucket.bucket_id
    	JOIN env ON set.env_id = env.env_id


 ALTER TABLE public.v_set
     OWNER TO postgres;


CREATE OR REPLACE VIEW public.v_scenario
	  AS
  SELECT v_set.set_id,
     v_set.run_id,
     v_set.application_name,
     v_set.suite_name,
     v_set.env_name,
     v_set.browser,
     v_set.project_name,
     v_set.bucket_name,
     locale.name as locale_name,
     v_set.loglink,
     v_set.mobile,
     v_set.passed AS scenario_passed,
     v_set.total AS scenario_total,
     v_set.failed AS scenario_failed,
     v_set.skipped AS scenario_skipped,
     v_set.platform,
     scenario.run_id,
     scenario.scenario_id,
     scenario.scenario_name,
     scenario.feature_name,
     scenario.error_type,
     scenario.testrail,
     scenario.testraillink,
     scenario.total_steps,
     scenario.duration,
     scenario.result as scenario_result,
     scenario.failed AS step_failed,
     scenario.skipped AS step_skipped,
     scenario.passed AS step_passed,
     scenario.sauce_link,
     scenario.server_info,
     scenario.sauce_video,
     scenario.sauce_html,
     scenario.comment,
     scenario.jira_link,
     scenario.end_time
    FROM scenario
      JOIN v_set ON scenario.run_id = v_set.run_id
      JOIN locale ON scenario.locale_id = locale.locale_id;

	 ALTER TABLE public.v_scenario
		 OWNER TO postgres;

--Add locale_id to scenario table
    ALTER TABLE public.scenario
        ADD COLUMN locale_id integer;

	ALTER TABLE public.scenario
        ADD CONSTRAINT scenario_locale_id_fkey FOREIGN KEY (locale_id)
        REFERENCES public.locale (locale_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

-- Run update query to sync existing set table's locale_id to scenario's newly created locale_id
        UPDATE  scenario
        SET locale_id = set_table.locale_id
        FROM (SELECT set_id, locale_id FROM set) AS set_table
        WHERE scenario.set_id = set_table.set_id

-- Set scenario table's locale_id column property not null to true
    ALTER TABLE scenario ALTER COLUMN locale_id SET NOT NULL;
