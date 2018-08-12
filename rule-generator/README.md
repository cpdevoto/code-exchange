# rule-generator

A Dropwizard application that can be used to generate anomaly detection rule instances in bulk.

## Instructions

To run the Rule Generator:

  * Clone the ``rule-generator`` GitHub repository.
  * Open a terminal window and set up an SSH tunnel to the developpment PostgreSQL database using a command like the following:

```
ssh -NL 5434:postgres1-db.dc.res0.local:5432 webapp0.dc.res0.local
```  
  
  * Open a new tab in your terminal window and ``cd`` to the ``rule-generator`` directory.
  * Make a copy of the ``docker/local.env.example`` file named ``docker/local.env`` and edit it to reflect the database port you used in your SSH tunnel above as well as the proper password for the development PostgreSQL database from LastPass.
  * Execute the following command to spin up a Docker image for the ``rule-generator`` application:
  
```
./gradlew runApp
```  
  * Open a browser (NOTE: the application has only been tested with Chrome), and navigate to the following URL to use the Rule Generator:
  
  ```
  http://localhost:8080
  ```
  * Follow the instructions on the screen to generate rule instances within the Development database.  
  * When you are done with the Rule Generator, you can stop it by executing the following command in your terminal window:

```
./gradlew stopApp
```  
