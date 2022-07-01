# spring-cities
A Spring Boot & React application to manipulate city information predefined in a CSV file.

## Prerequisites
  The project uses 3 different profiles: "dev", "test" and "prod". Running the application with different profiles requires different prerequisites which are detailed below. The default profile is: "dev".
  
## dev
  - Go to the root directory and run: mvn clean install -Pdev
  - In the same directory, run:
      - On Windows: mvnw spring-boot:run -Pdev
      - On Unix: ./mvnw spring-boot:run -Pdev
  - Go to the src/ui/react-cities directory and run: npm install
  - In the same directory, run: npm start
  - Browse http://localhost:3000
  - Use the account defined in application properties to log in.

  Note: As this profile uses H2 in-memory database, no database configuration is necessary.
    
## test
  - Start a PostgreSQL instance running on port 5432 with user "postgres" and database "spring-cities-test" created in it (These settings can be altered using application-test properties).
  - Go to the root directory and run: mvn clean install -Ptest
  - In the same directory, run:
      - On Windows: mvnw spring-boot:run -Ptest
      - On Unix: ./mvnw spring-boot:run -Ptest
  - Go to the src/ui/react-cities directory and run: npm install
  - In the same directory, run: npm start
  - Browse http://localhost:3000
  - Use the account defined in application properties to log in.

## prod
  - Start a PostgreSQL instance running on port 5432 with user "postgres" and database "spring-cities" created in it. As this profile requires the necessary tables to be pre-created to avoid deleting production data by mistake, create two tables "cities" and "users" with the queries given below:
  
    "cities" table:

        CREATE TABLE IF NOT EXISTS public.cities
        (
            id bigint NOT NULL,
            name character varying(255) COLLATE pg_catalog."default",
            photo text COLLATE pg_catalog."default",
            CONSTRAINT cities_pkey PRIMARY KEY (id)
        )
        TABLESPACE pg_default;
        ALTER TABLE IF EXISTS public.cities
            OWNER to postgres;

    "users" table:

        CREATE TABLE IF NOT EXISTS public.users
        (
            id bigint NOT NULL,
            password character varying(255) COLLATE pg_catalog."default",
            role character varying(255) COLLATE pg_catalog."default",
            user_name character varying(255) COLLATE pg_catalog."default",
            CONSTRAINT users_pkey PRIMARY KEY (id)
        )
        TABLESPACE pg_default;
        ALTER TABLE IF EXISTS public.users
            OWNER to postgres;
  
  - Go to the root directory and run: mvn clean install -Pprod
  - In the same directory, run:
      - On Windows: mvnw spring-boot:run -Pprod
      - On Unix: ./mvnw spring-boot:run -Pprod
  - Browse http://localhost:8080
  - Use the account defined in application properties to log in.
